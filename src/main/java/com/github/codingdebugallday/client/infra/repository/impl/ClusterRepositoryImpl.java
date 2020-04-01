package com.github.codingdebugallday.client.infra.repository.impl;

import java.util.Optional;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.codingdebugallday.client.api.dto.ClusterDTO;
import com.github.codingdebugallday.client.domain.entity.Cluster;
import com.github.codingdebugallday.client.domain.repository.ClusterRepository;
import com.github.codingdebugallday.client.infra.converter.ClusterConvertMapper;
import com.github.codingdebugallday.client.infra.mapper.ClusterMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/03/28 2:24
 * @since 1.0
 */
@Component
public class ClusterRepositoryImpl implements ClusterRepository {

    private final ClusterMapper clusterMapper;

    public ClusterRepositoryImpl(ClusterMapper clusterMapper) {
        this.clusterMapper = clusterMapper;
    }

    @Override
    public IPage<ClusterDTO> pageAndSortDTO(ClusterDTO clusterDTO, Page<Cluster> clusterPage) {
        final QueryWrapper<Cluster> queryWrapper = this.commonQueryWrapper(clusterDTO);
        Page<Cluster> entityPage = clusterMapper.selectPage(clusterPage, queryWrapper);
        final Page<ClusterDTO> dtoPage = new Page<>();
        BeanUtils.copyProperties(entityPage, dtoPage);
        dtoPage.setRecords(entityPage.getRecords().stream()
                .map(ClusterConvertMapper.INSTANCE::entityToDTO)
                .collect(Collectors.toList()));
        return dtoPage;
    }

    @Override
    public void delete(ClusterDTO clusterDTO) {
        clusterMapper.delete(commonQueryWrapper(clusterDTO));
    }

    @Override
    public ClusterDTO detail(Long tenantId, Long clusterId) {
        Cluster cluster = clusterMapper.selectOne(detailWrapper(tenantId, clusterId));
        return ClusterConvertMapper.INSTANCE.entityToDTO(cluster);
    }

    @Override
    public ClusterDTO selectOne(String clusterCode) {
        QueryWrapper<Cluster> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Cluster.FIELD_CLUSTER_CODE, clusterCode);
        Cluster cluster = clusterMapper.selectOne(queryWrapper);
        return ClusterConvertMapper.INSTANCE.entityToDTO(cluster);
    }

    private QueryWrapper<Cluster> detailWrapper(Long tenantId, Long clusterId) {
        final QueryWrapper<Cluster> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Cluster.FIELD_TENANT_ID, tenantId);
        queryWrapper.eq(Cluster.FIELD_CLUSTER_ID, clusterId);
        return queryWrapper;
    }

    private QueryWrapper<Cluster> commonQueryWrapper(ClusterDTO clusterDTO) {
        final QueryWrapper<Cluster> queryWrapper = new QueryWrapper<>();
        Optional.ofNullable(clusterDTO.getClusterCode())
                .ifPresent(s -> queryWrapper.or().like(Cluster.FIELD_CLUSTER_CODE, s));
        Optional.ofNullable(clusterDTO.getJobManagerUrl())
                .ifPresent(s -> queryWrapper.or().like(Cluster.FIELD_JOB_MANAGER_URL, s));
        queryWrapper.eq(Cluster.FIELD_TENANT_ID, clusterDTO.getTenantId());
        return queryWrapper;
    }
}
