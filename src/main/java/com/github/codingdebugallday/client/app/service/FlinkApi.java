package com.github.codingdebugallday.client.app.service;

import java.io.File;

import com.github.codingdebugallday.client.app.service.jars.FlinkJarService;
import com.github.codingdebugallday.client.app.service.jobs.FlinkJobService;
import com.github.codingdebugallday.client.domain.entity.jars.JarRunRequest;
import com.github.codingdebugallday.client.domain.entity.jars.JarRunResponseBody;
import com.github.codingdebugallday.client.domain.entity.jars.JarUploadResponseBody;
import com.github.codingdebugallday.client.domain.entity.jobs.*;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/03/26 21:55
 * @since 1.0
 */
public class FlinkApi {

    private ApiClient apiClient;
    /**
     * flink jar 相关 api
     */
    private final FlinkJarService flinkJarService;
    private final FlinkJobService flinkJobService;

    public FlinkApi(RestTemplate restTemplate) {
        this.apiClient = new ApiClient();
        flinkJarService = new FlinkJarService(restTemplate);
        flinkJobService = new FlinkJobService(restTemplate);
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    //===================flink jar api=========================

    /**
     * upload flink jar
     *
     * @param file flink jar file
     * @return org.abigballofmud.flink.api.domain.jars.JarUploadResponseBody
     */
    public JarUploadResponseBody uploadJar(File file) {
        return flinkJarService.uploadJar(file, apiClient);
    }

    /**
     * delete flink jar
     *
     * @param jarId flink jar file
     */
    public void deleteJar(String jarId) {
        flinkJarService.deleteJar(jarId, apiClient);
    }

    /**
     * run flink jar
     *
     * @param jarRunRequest JarRunRequest
     * @return org.abigballofmud.flink.api.domain.jars.JarRunResponseBody
     */
    public JarRunResponseBody runJar(JarRunRequest jarRunRequest) {
        return flinkJarService.runJar(jarRunRequest, apiClient);
    }

    //===================flink job api=========================

    /**
     * Returns an overview over all jobs and their current state.
     *
     * @return JobIdsWithStatusOverview
     */
    public JobIdsWithStatusOverview jobList() {
        return flinkJobService.jobList(apiClient);
    }

    /**
     * Returns an overview over all jobs.
     *
     * @return MultipleJobsDetails
     */
    public MultipleJobsDetails jobsDetails() {
        return flinkJobService.jobsDetails(apiClient);
    }

    /**
     * Returns details of a job.
     *
     * @param jobId jobId
     */
    public JobDetailsInfo jobDetail(String jobId) {
        return flinkJobService.jobsDetail(jobId, apiClient);
    }

    /**
     * cancel job use yarn
     *
     * @param jobId jobId
     * @return if has error return FlinkError
     */
    public FlinkError jobYarnCancel(String jobId) {
        return flinkJobService.jobYarnCancel(jobId, apiClient);
    }

    /**
     * Triggers a savepoint, and optionally cancels the job afterwards.
     * This async operation would return a 'triggerid' for further query identifier.
     *
     * @param savepointTriggerRequestBody SavepointTriggerRequestBody
     * @return TriggerResponse TriggerResponse
     */
    public TriggerResponse jobCancelOptionSavepoints(SavepointTriggerRequestBody savepointTriggerRequestBody) {
        return flinkJobService.jobCancelOptionSavepoints(savepointTriggerRequestBody, apiClient);
    }

}
