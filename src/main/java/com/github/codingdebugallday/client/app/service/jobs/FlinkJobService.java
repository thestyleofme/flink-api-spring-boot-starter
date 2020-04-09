package com.github.codingdebugallday.client.app.service.jobs;

import java.util.Optional;

import com.github.codingdebugallday.client.api.dto.ClusterDTO;
import com.github.codingdebugallday.client.app.service.ApiClient;
import com.github.codingdebugallday.client.app.service.FlinkCommonService;
import com.github.codingdebugallday.client.domain.entity.jobs.*;
import com.github.codingdebugallday.client.infra.constants.FlinkApiConstant;
import com.github.codingdebugallday.client.infra.utils.JSON;
import com.github.codingdebugallday.client.infra.utils.RestTemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
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
public class FlinkJobService extends FlinkCommonService {

    private final RestTemplate restTemplate;

    public FlinkJobService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public JobIdsWithStatusOverview jobList(ApiClient apiClient) {
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        return getForEntityStandby(restTemplate,
                clusterDTO.getJobManagerUrl(),
                clusterDTO.getJobManagerStandbyUrlSet(),
                FlinkApiConstant.Jobs.JOB_LIST,
                JobIdsWithStatusOverview.class,
                "error.flink.job.list");
    }

    public MultipleJobsDetails jobsDetails(ApiClient apiClient) {
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        return getForEntityStandby(restTemplate,
                clusterDTO.getJobManagerUrl(),
                clusterDTO.getJobManagerStandbyUrlSet(),
                FlinkApiConstant.Jobs.JOB_OVERVIEW,
                MultipleJobsDetails.class,
                "error.flink.jobs.details");
    }

    public JobDetailsInfo jobsDetail(String jobId, ApiClient apiClient) {
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        return getForEntityStandby(restTemplate,
                clusterDTO.getJobManagerUrl(),
                clusterDTO.getJobManagerStandbyUrlSet(),
                FlinkApiConstant.Jobs.JOB_DETAIL,
                JobDetailsInfo.class,
                "error.flink.jobs.detail",
                jobId);
    }

    public FlinkApiErrorResponse jobYarnCancel(String jobId, ApiClient apiClient) {
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        return getForEntityStandby(restTemplate,
                clusterDTO.getJobManagerUrl(),
                clusterDTO.getJobManagerStandbyUrlSet(),
                FlinkApiConstant.Jobs.JOB_YARN_CANCEL,
                FlinkApiErrorResponse.class,
                "error.flink.job.yarn.cancel",
                jobId);
    }

    public TriggerResponse jobCancelOptionSavepoints(SavepointTriggerRequestBody savepointTriggerRequestBody, ApiClient apiClient) {
        HttpEntity<String> requestEntity =
                new HttpEntity<>((JSON.toJson(savepointTriggerRequestBody)), RestTemplateUtil.applicationJsonHeaders());
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        return exchangeStandby(restTemplate,
                clusterDTO.getJobManagerUrl(),
                clusterDTO.getJobManagerStandbyUrlSet(),
                FlinkApiConstant.Jobs.JOB_CANCEL_WITH_SAVEPOINTS,
                HttpMethod.POST, requestEntity, TriggerResponse.class,
                "error.flink.jar.cancel.option.savepoint",
                savepointTriggerRequestBody.getJobId());
    }

    public FlinkApiErrorResponse jobTerminate(String jobId, String mode, ApiClient apiClient) {
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>(1);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body);
        return exchangeStandby(restTemplate,
                clusterDTO.getJobManagerUrl(),
                clusterDTO.getJobManagerStandbyUrlSet(),
                FlinkApiConstant.Jobs.JOB_TERMINATE,
                HttpMethod.PATCH, requestEntity, FlinkApiErrorResponse.class,
                "error.flink.job.terminate",
                jobId, Optional.ofNullable(mode).orElse("cancel"));
    }

    public TriggerResponse jobRescale(String jobId, int parallelism, ApiClient apiClient) {
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>(1);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body);
        return exchangeStandby(restTemplate,
                clusterDTO.getJobManagerUrl(),
                clusterDTO.getJobManagerStandbyUrlSet(),
                FlinkApiConstant.Jobs.JOB_RESCALING,
                HttpMethod.PATCH, requestEntity, TriggerResponse.class,
                "error.flink.job.rescaling",
                jobId, parallelism);
    }

    public JobExceptionsInfo jobException(String jobId, String maxExceptions, ApiClient apiClient) {
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        String extraUrl;
        if (StringUtils.isEmpty(maxExceptions)) {
            extraUrl = FlinkApiConstant.Jobs.JOB_EXCEPTIONS;
        } else {
            extraUrl = String.format("%s?maxExceptions=%s", FlinkApiConstant.Jobs.JOB_EXCEPTIONS, maxExceptions);
        }
        return getForEntityStandby(restTemplate,
                clusterDTO.getJobManagerUrl(),
                clusterDTO.getJobManagerStandbyUrlSet(),
                extraUrl,
                JobExceptionsInfo.class,
                "error.flink.job.exception",
                jobId);
    }
}
