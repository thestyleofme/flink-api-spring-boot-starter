package com.github.codingdebugallday.client.app.service;

import java.util.List;
import java.util.Map;

import com.github.codingdebugallday.client.api.dto.ClusterDTO;
import com.github.codingdebugallday.client.domain.entity.jobs.*;
import com.github.codingdebugallday.client.domain.entity.tm.TaskManagerDetail;
import com.github.codingdebugallday.client.domain.entity.tm.TaskManagerInfo;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/03/31 12:00
 * @since 1.0
 */
public interface ClusterService {

    /**
     * 创建集群
     *
     * @param clusterDTO ClusterDTO
     * @return org.abigballofmud.flink.client.api.dto.ClusterDTO
     */
    ClusterDTO insert(ClusterDTO clusterDTO);

    /**
     * 修改集群
     *
     * @param clusterDTO ClusterDTO
     * @return org.abigballofmud.flink.client.api.dto.ClusterDTO
     */
    ClusterDTO update(ClusterDTO clusterDTO);

    /**
     * 删除集群
     *
     * @param clusterDTO ClusterDTO
     */
    void delete(ClusterDTO clusterDTO);

    /**
     * 概览flink job列表
     *
     * @param tenantId    租户id
     * @param clusterCode clusterCode
     * @return JobIdsWithStatusOverview
     */
    JobIdsWithStatusOverview jobList(Long tenantId, String clusterCode);

    /**
     * flink job列表详情
     *
     * @param tenantId    租户id
     * @param clusterCode clusterCode
     * @return JobIdsWithStatusOverview
     */
    MultipleJobsDetails jobsDetails(Long tenantId, String clusterCode);

    /**
     * flink job详情
     *
     * @param tenantId    租户id
     * @param clusterCode clusterCode
     * @param jobId       jobId
     * @return JobDetailsInfo
     */
    JobDetailsInfo jobDetail(Long tenantId, String clusterCode, String jobId);

    /**
     * 使用yarn停止flink job
     *
     * @param tenantId    租户id
     * @param clusterCode clusterCode
     * @param jobId       jobId
     * @return FlinkApiErrorResponse
     */
    FlinkApiErrorResponse jobYarnCancel(Long tenantId, String clusterCode, String jobId);

    /**
     * 直接停止job
     *
     * @param tenantId    租户id
     * @param clusterCode clusterCode
     * @param jobId       jobId
     * @param mode        mode
     * @return FlinkApiErrorResponse
     */
    FlinkApiErrorResponse jobTerminate(Long tenantId, String clusterCode, String jobId, String mode);

    /**
     * 停止flink job并保存savepoint
     *
     * @param tenantId                    租户id
     * @param clusterCode                 clusterCode
     * @param savepointTriggerRequestBody SavepointTriggerRequestBody
     * @return TriggerResponse
     */
    TriggerResponse jobCancelOptionSavepoints(Long tenantId, String clusterCode,
                                              SavepointTriggerRequestBody savepointTriggerRequestBody);

    /**
     * 重新调节job
     *
     * @param tenantId    租户id
     * @param clusterCode clusterCode
     * @param jobId       jobId
     * @param parallelism parallelism
     * @return TriggerResponse
     */
    TriggerResponse jobRescale(Long tenantId, String clusterCode, String jobId, int parallelism);

    /**
     * Returns the non-recoverable exceptions that have been observed by the job.
     * The truncated flag defines whether more exceptions occurred, but are not listed,
     * because the response would otherwise get too big.
     *
     * @param tenantId      租户id
     * @param clusterCode   clusterCode
     * @param jobId         jobId
     * @param maxExceptions Comma-separated list of integer values that specifies the upper limit of exceptions to return
     * @return JobExceptionsInfo
     */
    JobExceptionsInfo jobException(Long tenantId, String clusterCode, String jobId, String maxExceptions);

    /**
     * 获取flink taskmanager列表集合
     *
     * @param tenantId    租户id
     * @param clusterCode clusterCode
     * @return com.github.codingdebugallday.client.domain.entity.tm.TaskManagerInfo
     */
    TaskManagerInfo taskMangerList(Long tenantId, String clusterCode);

    /**
     * 获取flink taskmanager的详情
     *
     * @param tenantId    租户id
     * @param clusterCode clusterCode
     * @param tmId        taskmanager id
     * @return com.github.codingdebugallday.client.domain.entity.tm.TaskManagerDetail
     */
    TaskManagerDetail taskManagerDetail(Long tenantId, String clusterCode, String tmId);

    /**
     * 获取flink taskmanager的日志
     *
     * @param tenantId    租户id
     * @param clusterCode clusterCode
     * @param tmId        taskmanager id
     * @return String log
     */
    String taskManagerLog(Long tenantId, String clusterCode, String tmId);

    /**
     * 获取flink taskmanager的标准输出
     *
     * @param tenantId    租户id
     * @param clusterCode clusterCode
     * @param tmId        taskmanager id
     * @return String stdout
     */
    String taskManagerStdout(Long tenantId, String clusterCode, String tmId);

    /**
     * 获取flink jobmanager配置信息
     *
     * @param tenantId    租户id
     * @param clusterCode clusterCode
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.String>>
     */
    List<Map<String, String>> jobManagerConfig(Long tenantId, String clusterCode);

    /**
     * 获取flink jobmanager日志
     *
     * @param tenantId    租户id
     * @param clusterCode clusterCode
     * @return String log
     */
    String jobManagerLog(Long tenantId, String clusterCode);

    /**
     * 获取flink jobmanager标准输出
     *
     * @param tenantId    租户id
     * @param clusterCode clusterCode
     * @return String log
     */
    String jobManagerStdout(Long tenantId, String clusterCode);

}
