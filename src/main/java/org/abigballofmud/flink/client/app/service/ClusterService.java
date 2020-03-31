package org.abigballofmud.flink.client.app.service;

import org.abigballofmud.flink.client.api.dto.ClusterDTO;

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
}
