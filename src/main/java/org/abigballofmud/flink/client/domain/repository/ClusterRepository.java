package org.abigballofmud.flink.client.domain.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.abigballofmud.flink.client.api.dto.ClusterDTO;
import org.abigballofmud.flink.client.domain.entity.Cluster;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/03/28 2:20
 * @since 1.0
 */
public interface ClusterRepository {

    /**
     * 分页条件查询flink集群
     *
     * @param clusterDTO  ClusterDTO
     * @param clusterPage Page<Cluster>
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.abigballofmud.flink.platform.api.dto.ClusterDTO>
     */
    IPage<ClusterDTO> pageAndSortDTO(ClusterDTO clusterDTO, Page<Cluster> clusterPage);

    /**
     * 条件删除flink集群
     *
     * @param clusterDTO ClusterDTO
     */
    void delete(ClusterDTO clusterDTO);

    /**
     * 查看flink集群详情
     *
     * @param tenantId  租户id
     * @param clusterId flink clusterId
     * @return org.abigballofmud.flink.client.api.dto.ClusterDTO
     */
    ClusterDTO detail(Long tenantId, Long clusterId);

    /**
     * 根据clusterCode获取唯一条
     *
     * @param clusterCode clusterCode
     * @return org.abigballofmud.flink.client.api.dto.ClusterDTO
     */
    ClusterDTO selectOne(String clusterCode);
}
