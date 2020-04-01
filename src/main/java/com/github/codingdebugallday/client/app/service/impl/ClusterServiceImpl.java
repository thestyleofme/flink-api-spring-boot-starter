package com.github.codingdebugallday.client.app.service.impl;

import com.github.codingdebugallday.client.api.dto.ClusterDTO;
import com.github.codingdebugallday.client.app.service.ClusterService;
import com.github.codingdebugallday.client.domain.entity.Cluster;
import com.github.codingdebugallday.client.domain.repository.ClusterRepository;
import com.github.codingdebugallday.client.infra.context.FlinkApiContext;
import com.github.codingdebugallday.client.infra.converter.ClusterConvertMapper;
import com.github.codingdebugallday.client.infra.mapper.ClusterMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/03/31 12:02
 * @since 1.0
 */
@Service
public class ClusterServiceImpl implements ClusterService {

    private final ClusterMapper clusterMapper;
    private final FlinkApiContext flinkApiContext;
    private final ClusterRepository clusterRepository;

    public ClusterServiceImpl(ClusterMapper clusterMapper,
                              FlinkApiContext flinkApiContext,
                              ClusterRepository clusterRepository) {
        this.clusterMapper = clusterMapper;
        this.flinkApiContext = flinkApiContext;
        this.clusterRepository = clusterRepository;
    }

    @Override
    public ClusterDTO insert(ClusterDTO clusterDTO) {
        Cluster cluster = ClusterConvertMapper.INSTANCE.dtoToEntity(clusterDTO);
        clusterMapper.insert(cluster);
        return ClusterConvertMapper.INSTANCE.entityToDTO(cluster);
    }

    @Override
    public ClusterDTO update(ClusterDTO clusterDTO) {
        Cluster cluster = ClusterConvertMapper.INSTANCE.dtoToEntity(clusterDTO);
        clusterMapper.updateById(cluster);
        // flinkApiContext还需remove掉
        flinkApiContext.remove(clusterDTO.getClusterCode());
        return ClusterConvertMapper.INSTANCE.entityToDTO(cluster);
    }

    @Override
    public void delete(ClusterDTO clusterDTO) {
        clusterRepository.delete(clusterDTO);
        // flinkApiContext还需remove掉
        flinkApiContext.remove(clusterDTO.getClusterCode());
    }

}
