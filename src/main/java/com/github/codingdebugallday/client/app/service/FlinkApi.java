package com.github.codingdebugallday.client.app.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.github.codingdebugallday.client.app.service.jars.FlinkJarService;
import com.github.codingdebugallday.client.app.service.jm.FlinkJobManagerService;
import com.github.codingdebugallday.client.app.service.jobs.FlinkJobService;
import com.github.codingdebugallday.client.app.service.tm.FlinkTaskManagerService;
import com.github.codingdebugallday.client.domain.entity.jars.JarRunRequest;
import com.github.codingdebugallday.client.domain.entity.jars.JarRunResponseBody;
import com.github.codingdebugallday.client.domain.entity.jars.JarUploadResponseBody;
import com.github.codingdebugallday.client.domain.entity.jobs.*;
import com.github.codingdebugallday.client.domain.entity.tm.TaskManagerDetail;
import com.github.codingdebugallday.client.domain.entity.tm.TaskManagerInfo;
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
    private final FlinkTaskManagerService flinkTaskManagerService;
    private final FlinkJobManagerService flinkJobManagerService;

    public FlinkApi(RestTemplate restTemplate) {
        this.apiClient = new ApiClient();
        flinkJarService = new FlinkJarService(restTemplate);
        flinkJobService = new FlinkJobService(restTemplate);
        flinkTaskManagerService = new FlinkTaskManagerService(restTemplate);
        flinkJobManagerService = new FlinkJobManagerService(restTemplate);
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    //========================================================
    //===================flink jar api=========================
    //========================================================
    //========================================================

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

    //========================================================
    //===================flink job api=========================
    //========================================================
    //========================================================

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
    public FlinkApiErrorResponse jobYarnCancel(String jobId) {
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

    /**
     * Terminates a job
     *
     * @param jobId jobId
     * @param mode  optional, the termination mode, the only supported value is: "cancel"
     * @return com.github.codingdebugallday.client.domain.entity.jobs.FlinkError
     */
    public FlinkApiErrorResponse jobTerminate(String jobId, String mode) {
        return flinkJobService.jobTerminate(jobId, mode, apiClient);
    }

    /**
     * Triggers the rescaling of a job.
     * This async operation would return a 'triggerid' for further query identifier.
     *
     * @param jobId       jobId
     * @param parallelism parallelism
     * @return com.github.codingdebugallday.client.domain.entity.jobs.TriggerResponse
     */
    public TriggerResponse jobRescale(String jobId, int parallelism) {
        return flinkJobService.jobRescale(jobId, parallelism, apiClient);
    }

    /**
     * Returns the non-recoverable exceptions that have been observed by the job.
     * The truncated flag defines whether more exceptions occurred, but are not listed,
     * because the response would otherwise get too big.
     *
     * @param jobId         jobId
     * @param maxExceptions Comma-separated list of integer values that specifies the upper limit of exceptions to return
     * @return JobExceptionsInfo
     */
    public JobExceptionsInfo jobException(String jobId, String maxExceptions) {
        return flinkJobService.jobException(jobId, maxExceptions, apiClient);
    }

    //========================================================
    //===================flink tm api=========================
    //========================================================
    //========================================================

    public TaskManagerInfo taskMangerList() {
        return flinkTaskManagerService.taskMangerList(apiClient);
    }

    public TaskManagerDetail taskManagerDetail(String tmId) {
        return flinkTaskManagerService.taskManagerDetail(tmId, apiClient);
    }

    public String taskManagerLog(String tmId) {
        return flinkTaskManagerService.taskManagerLog(tmId, apiClient);
    }

    public String taskManagerStdout(String tmId) {
        return flinkTaskManagerService.taskManagerStdout(tmId, apiClient);
    }

    //========================================================
    //===================flink jm api=========================
    //========================================================
    //========================================================

    public List<Map<String,String>> jobManagerConfig() {
        return flinkJobManagerService.jobManagerConfig(apiClient);
    }

    public String jobManagerLog(){
        return flinkJobManagerService.jobManagerLog(apiClient);
    }

    public String jobManagerStdout(){
        return flinkJobManagerService.jobManagerStdout(apiClient);
    }
}
