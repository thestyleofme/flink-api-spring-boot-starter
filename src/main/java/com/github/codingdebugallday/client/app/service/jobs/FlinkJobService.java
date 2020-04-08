package com.github.codingdebugallday.client.app.service.jobs;

import com.github.codingdebugallday.client.api.dto.ClusterDTO;
import com.github.codingdebugallday.client.app.service.ApiClient;
import com.github.codingdebugallday.client.domain.entity.jobs.*;
import com.github.codingdebugallday.client.infra.constants.FlinkApiConstant;
import com.github.codingdebugallday.client.infra.exceptions.FlinkApiCommonException;
import com.github.codingdebugallday.client.infra.utils.JSON;
import com.github.codingdebugallday.client.infra.utils.RestTemplateUtil;
import com.github.codingdebugallday.client.infra.utils.RetryUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/04/08 11:35
 * @since 1.0
 */
@Slf4j
public class FlinkJobService {

    private final RestTemplate restTemplate;

    public FlinkJobService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public JobIdsWithStatusOverview jobList(ApiClient apiClient) {
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        try {
            return RetryUtil.executeWithRetry(() -> {
                        ResponseEntity<JobIdsWithStatusOverview> responseEntity = restTemplate.getForEntity(
                                clusterDTO.getJobManagerUrl() + FlinkApiConstant.Jobs.JOB_LIST, JobIdsWithStatusOverview.class);
                        log.debug("response, status: {}, body: {}", responseEntity.getStatusCode(), responseEntity.getBody());
                        return responseEntity;
                    },
                    3, 1000L, true).getBody();
        } catch (Exception e) {
            for (String url : clusterDTO.getJobManagerStandbyUrlSet()) {
                try {
                    return RetryUtil.executeWithRetry(() -> {
                                ResponseEntity<JobIdsWithStatusOverview> responseEntity = restTemplate.getForEntity(
                                        url + FlinkApiConstant.Jobs.JOB_LIST, JobIdsWithStatusOverview.class);
                                log.debug("response, status: {}, body: {}", responseEntity.getStatusCode(), responseEntity.getBody());
                                return responseEntity;
                            },
                            3, 1000L, true).getBody();
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
            return RetryUtil.executeWithRetry(() -> {
                        ResponseEntity<MultipleJobsDetails> responseEntity = restTemplate.getForEntity(
                                clusterDTO.getJobManagerUrl() + FlinkApiConstant.Jobs.JOB_OVERVIEW, MultipleJobsDetails.class);
                        log.debug("response, status: {}, body: {}", responseEntity.getStatusCode(), responseEntity.getBody());
                        return responseEntity;
                    },
                    3, 1000L, true).getBody();
        } catch (Exception e) {
            for (String url : clusterDTO.getJobManagerStandbyUrlSet()) {
                try {
                    return RetryUtil.executeWithRetry(() -> {
                                ResponseEntity<MultipleJobsDetails> responseEntity = restTemplate.getForEntity(
                                        url + FlinkApiConstant.Jobs.JOB_OVERVIEW, MultipleJobsDetails.class);
                                log.debug("response, status: {}, body: {}", responseEntity.getStatusCode(), responseEntity.getBody());
                                return responseEntity;
                            },
                            3, 1000L, true).getBody();
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
            return RetryUtil.executeWithRetry(() -> {
                        ResponseEntity<JobDetailsInfo> responseEntity = restTemplate.getForEntity(
                                clusterDTO.getJobManagerUrl() + String.format(FlinkApiConstant.Jobs.JOB_DETAIL, jobId),
                                JobDetailsInfo.class);
                        log.debug("response, status: {}, body: {}", responseEntity.getStatusCode(), responseEntity.getBody());
                        return responseEntity;
                    },
                    3, 1000L, true).getBody();
        } catch (Exception e) {
            for (String url : clusterDTO.getJobManagerStandbyUrlSet()) {
                try {
                    return RetryUtil.executeWithRetry(() -> {
                                ResponseEntity<JobDetailsInfo> responseEntity = restTemplate.getForEntity(
                                        url + String.format(FlinkApiConstant.Jobs.JOB_DETAIL, jobId), JobDetailsInfo.class);
                                log.debug("response, status: {}, body: {}", responseEntity.getStatusCode(), responseEntity.getBody());
                                return responseEntity;
                            },
                            3, 1000L, true).getBody();
                } catch (Exception ex) {
                    // ignore
                }
            }
            throw new FlinkApiCommonException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error.flink.job.detail");
        }
    }

    public FlinkError jobYarnCancel(String jobId, ApiClient apiClient) {
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        try {
            return RetryUtil.executeWithRetry(() -> {
                        ResponseEntity<FlinkError> responseEntity = restTemplate.getForEntity(
                                clusterDTO.getJobManagerUrl() + String.format(FlinkApiConstant.Jobs.JOB_YARN_CANCEL, jobId),
                                FlinkError.class);
                        log.debug("response, status: {}, body: {}", responseEntity.getStatusCode(), responseEntity.getBody());
                        return responseEntity;
                    },
                    3, 1000L, true).getBody();
        } catch (Exception e) {
            for (String url : clusterDTO.getJobManagerStandbyUrlSet()) {
                try {
                    return RetryUtil.executeWithRetry(() -> {
                                ResponseEntity<FlinkError> responseEntity = restTemplate.getForEntity(
                                        url + String.format(FlinkApiConstant.Jobs.JOB_YARN_CANCEL, jobId), FlinkError.class);
                                log.debug("response, status: {}, body: {}", responseEntity.getStatusCode(), responseEntity.getBody());
                                return responseEntity;
                            },
                            3, 1000L, true).getBody();
                } catch (Exception ex) {
                    // ignore
                }
            }
            throw new FlinkApiCommonException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error.flink.job.yarn.cancel");
        }
    }

    public TriggerResponse jobCancelOptionSavepoints(SavepointTriggerRequestBody savepointTriggerRequestBody,
                                            ApiClient apiClient) {
        HttpEntity<String> requestEntity =
                new HttpEntity<>((JSON.toJson(savepointTriggerRequestBody)), RestTemplateUtil.applicationJsonHeaders());
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        try {
            return RetryUtil.executeWithRetry(() -> {
                        ResponseEntity<TriggerResponse> responseEntity = restTemplate.exchange(clusterDTO.getJobManagerUrl() +
                                        String.format(FlinkApiConstant.Jobs.JOB_CANCEL_WITH_SAVEPOINTS, savepointTriggerRequestBody.getJobId()),
                                HttpMethod.POST, requestEntity, TriggerResponse.class);
                        log.debug("response, status: {}, body: {}", responseEntity.getStatusCode(), responseEntity.getBody());
                        return responseEntity;
                    },
                    3, 1000L, true).getBody();
        } catch (Exception e) {
            for (String url : clusterDTO.getJobManagerStandbyUrlSet()) {
                try {
                    return RetryUtil.executeWithRetry(() -> {
                                ResponseEntity<TriggerResponse> responseEntity = restTemplate.exchange(
                                        url + String.format(FlinkApiConstant.Jobs.JOB_CANCEL_WITH_SAVEPOINTS, savepointTriggerRequestBody.getJobId()),
                                        HttpMethod.POST, requestEntity, TriggerResponse.class);
                                log.debug("response, status: {}, body: {}", responseEntity.getStatusCode(), responseEntity.getBody());
                                return responseEntity;
                            },
                            3, 1000L, true).getBody();
                } catch (Exception ex) {
                    // ignore
                }
            }
            throw new FlinkApiCommonException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error.flink.jar.cancel.option.savepoint");
        }
    }
}
