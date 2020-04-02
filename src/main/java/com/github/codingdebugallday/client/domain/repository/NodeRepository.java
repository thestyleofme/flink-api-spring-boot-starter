package com.github.codingdebugallday.client.domain.repository;

import java.util.List;

import com.github.codingdebugallday.client.api.dto.NodeDTO;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/03/28 2:20
 * @since 1.0
 */
public interface NodeRepository {

    /**
     * 根据clusterCode获取flink节点集合
     *
     * @param clusterCode clusterCode
     * @param tenantId    租户id
     * @return org.abigballofmud.flink.client.api.dto.ClusterDTO
     */
    List<NodeDTO> selectByClusterCode(String clusterCode, Long tenantId);
}
