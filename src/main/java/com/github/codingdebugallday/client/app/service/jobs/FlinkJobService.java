package com.github.codingdebugallday.client.app.service.jobs;

import java.time.Duration;
import java.util.Optional;
import java.util.function.Predicate;

import com.github.codingdebugallday.client.api.dto.ClusterDTO;
import com.github.codingdebugallday.client.app.service.ApiClient;
import com.github.codingdebugallday.client.app.service.FlinkCommonService;
import com.github.codingdebugallday.client.domain.entity.jobs.*;
import com.github.codingdebugallday.client.infra.constants.FlinkApiConstant;
import com.github.codingdebugallday.client.infra.exceptions.FlinkApiCommonException;
import com.github.codingdebugallday.client.infra.utils.JSON;
import com.github.codingdebugallday.client.infra.utils.RestTemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.retry.Backoff;
import reactor.retry.Repeat;
import reactor.retry.RepeatContext;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/04/08 11:35
 * @since 1.0
 */
@Slf4j
public class FlinkJobService extends FlinkCommonService {

    private static final String STATUS_COMPLETED = "COMPLETED";

    private final RestTemplate restTemplate;

    public FlinkJobService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public JobIdsWithStatusOverview jobList(ApiClient apiClient) {
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        try {
            return getForEntity(restTemplate,
                    clusterDTO.getJobManagerUrl() + FlinkApiConstant.Jobs.JOB_LIST,
                    JobIdsWithStatusOverview.class);
        } catch (Exception e) {
            for (String url : clusterDTO.getJobManagerStandbyUrlSet()) {
                try {
                    return getForEntity(restTemplate,
                            url + FlinkApiConstant.Jobs.JOB_LIST,
                            JobIdsWithStatusOverview.class);
                } catch (Exception ex) {
                    // ignore
                }
            }
            throw new FlinkApiCommonException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error.flink.job.list");
        }
    }

    public MultipleJobsDetails jobsDetails(ApiClient apiClient) {
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        try {
            return getForEntity(restTemplate,
                    clusterDTO.getJobManagerUrl() + FlinkApiConstant.Jobs.JOB_OVERVIEW,
                    MultipleJobsDetails.class);
        } catch (Exception e) {
            for (String url : clusterDTO.getJobManagerStandbyUrlSet()) {
                try {
                    return getForEntity(restTemplate,
                            url + FlinkApiConstant.Jobs.JOB_OVERVIEW,
                            MultipleJobsDetails.class);
                } catch (Exception ex) {
                    // ignore
                }
            }
            throw new FlinkApiCommonException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error.flink.jobs.details");
        }
    }

    public JobDetailsInfo jobsDetail(String jobId, ApiClient apiClient) {
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        try {
            return getForEntity(restTemplate,
                    clusterDTO.getJobManagerUrl() + FlinkApiConstant.Jobs.JOB_DETAIL,
                    JobDetailsInfo.class, jobId);
        } catch (Exception e) {
            for (String url : clusterDTO.getJobManagerStandbyUrlSet()) {
                try {
                    return getForEntity(restTemplate,
                            url + FlinkApiConstant.Jobs.JOB_DETAIL,
                            JobDetailsInfo.class, jobId);
                } catch (Exception ex) {
                    // ignore
                }
            }
            throw new FlinkApiCommonException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error.flink.job.detail");
        }
    }

    public FlinkApiErrorResponse jobYarnCancel(String jobId, ApiClient apiClient) {
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        try {
            return getForEntity(restTemplate,
                    clusterDTO.getJobManagerUrl() + FlinkApiConstant.Jobs.JOB_YARN_CANCEL,
                    FlinkApiErrorResponse.class, jobId);
        } catch (Exception e) {
            for (String url : clusterDTO.getJobManagerStandbyUrlSet()) {
                try {
                    return getForEntity(restTemplate,
                            url + FlinkApiConstant.Jobs.JOB_YARN_CANCEL,
                            FlinkApiErrorResponse.class, jobId);
                } catch (Exception ex) {
                    // ignore
                }
            }
            throw new FlinkApiCommonException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error.flink.job.yarn.cancel");
        }
    }

    public TriggerResponseWithSavepoint jobCancelOptionSavepoints(SavepointTriggerRequestBody savepointTriggerRequestBody, ApiClient apiClient) {
        HttpEntity<String> requestEntity =
                new HttpEntity<>((JSON.toJson(savepointTriggerRequestBody)), RestTemplateUtil.applicationJsonHeaders());
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        TriggerResponse triggerResponse;
        SavepointInfo savepointInfo = null;
        try {
            triggerResponse = exchange(restTemplate,
                    clusterDTO.getJobManagerUrl() + FlinkApiConstant.Jobs.JOB_CANCEL_WITH_SAVEPOINTS,
                    HttpMethod.POST, requestEntity, TriggerResponse.class, savepointTriggerRequestBody.getJobId());
            if (CollectionUtils.isEmpty(triggerResponse.getErrors())) {
                // 返回状态必须是COMPLETED才结束，否则重试
                Predicate<RepeatContext<SavepointInfo>> repeatPredicate = savepointInfoRepeatContext -> {
                    SavepointInfo context = savepointInfoRepeatContext.applicationContext();
                    String currentStatus = context.getStatus().getId();
                    log.info("currentStatus: {}", currentStatus);
                    return !STATUS_COMPLETED.equals(currentStatus);
                };
                Mono<SavepointInfo> savepointInfoMono = WebClient.create()
                        .get()
                        .uri(clusterDTO.getJobManagerUrl() + FlinkApiConstant.Jobs.JOB_SAVEPOINT_STATUS,
                                savepointTriggerRequestBody.getJobId(),
                                triggerResponse.getRequestId())
                        .retrieve()
                        .bodyToMono(SavepointInfo.class);
                savepointInfo = savepointInfoMono
                        .repeatWhen(Repeat.onlyIf(repeatPredicate)
                                .backoff(Backoff.fixed(Duration.ofMillis(500L)))
                                .repeatMax(3)
                                .withApplicationContext(savepointInfoMono.block()))
                        .blockLast();
            }
            return TriggerResponseWithSavepoint.builder()
                    .savepointInfo(savepointInfo)
                    .triggerResponse(triggerResponse).build();
        } catch (Exception e) {
            for (String url : clusterDTO.getJobManagerStandbyUrlSet()) {
                try {
                    triggerResponse = exchange(restTemplate,
                            url + FlinkApiConstant.Jobs.JOB_CANCEL_WITH_SAVEPOINTS,
                            HttpMethod.POST, requestEntity, TriggerResponse.class, savepointTriggerRequestBody.getJobId());
                    if (CollectionUtils.isEmpty(triggerResponse.getErrors())) {
                        savepointInfo = getForEntity(restTemplate,
                                url + FlinkApiConstant.Jobs.JOB_SAVEPOINT_STATUS,
                                SavepointInfo.class,
                                savepointTriggerRequestBody.getJobId(),
                                triggerResponse.getRequestId());
                    }
                    return TriggerResponseWithSavepoint.builder()
                            .savepointInfo(savepointInfo)
                            .triggerResponse(triggerResponse).build();
                } catch (Exception ex) {
                    // ignore
                }
            }
            throw new FlinkApiCommonException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error.flink.jar.cancel.option.savepoint");
        }
    }

    public FlinkApiErrorResponse jobTerminate(String jobId, String mode, ApiClient apiClient) {
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>(1);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body);
        try {
            return exchange(restTemplate,
                    clusterDTO.getJobManagerUrl() + FlinkApiConstant.Jobs.JOB_TERMINATE,
                    HttpMethod.PATCH, requestEntity, FlinkApiErrorResponse.class,
                    jobId, Optional.ofNullable(mode).orElse("cancel"));
        } catch (Exception e) {
            for (String url : clusterDTO.getJobManagerStandbyUrlSet()) {
                try {
                    exchange(restTemplate,
                            url + FlinkApiConstant.Jobs.JOB_TERMINATE,
                            HttpMethod.PATCH, requestEntity, FlinkApiErrorResponse.class,
                            jobId, Optional.ofNullable(mode).orElse("cancel"));
                } catch (Exception ex) {
                    // ignore
                }
            }
            throw new FlinkApiCommonException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error.flink.job.terminate");
        }
    }

    public TriggerResponse jobRescale(String jobId, int parallelism, ApiClient apiClient) {
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>(1);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body);
        try {
            return exchange(restTemplate,
                    clusterDTO.getJobManagerUrl() + FlinkApiConstant.Jobs.JOB_RESCALING,
                    HttpMethod.PATCH, requestEntity, TriggerResponse.class,
                    jobId, parallelism);
        } catch (Exception e) {
            for (String url : clusterDTO.getJobManagerStandbyUrlSet()) {
                try {
                    return exchange(restTemplate,
                            url + FlinkApiConstant.Jobs.JOB_RESCALING,
                            HttpMethod.PATCH, requestEntity, TriggerResponse.class,
                            jobId, parallelism);
                } catch (Exception ex) {
                    // ignore
                }
            }
            throw new FlinkApiCommonException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error.flink.job.rescaling");
        }
    }

    public JobExceptionsInfo jobException(String jobId, String maxExceptions, ApiClient apiClient) {
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        String extraUrl;
        if (StringUtils.isEmpty(maxExceptions)) {
            extraUrl = FlinkApiConstant.Jobs.JOB_EXCEPTIONS;
        } else {
            extraUrl = String.format("%s?maxExceptions=%s", FlinkApiConstant.Jobs.JOB_EXCEPTIONS, maxExceptions);
        }
        try {
            return getForEntity(restTemplate,
                    clusterDTO.getJobManagerUrl() + extraUrl,
                    JobExceptionsInfo.class, jobId);
        } catch (Exception e) {
            for (String url : clusterDTO.getJobManagerStandbyUrlSet()) {
                try {
                    return getForEntity(restTemplate,
                            url + extraUrl,
                            JobExceptionsInfo.class, jobId);
                } catch (Exception ex) {
                    // ignore
                }
            }
            throw new FlinkApiCommonException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error.flink.job.exception");
        }
    }
}
