package org.abigballofmud.flink.client.app.service.impl;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.abigballofmud.flink.client.api.dto.ClusterDTO;
import org.abigballofmud.flink.client.app.service.ClusterService;
import org.abigballofmud.flink.client.domain.entity.Cluster;
import org.abigballofmud.flink.client.domain.repository.ClusterRepository;
import org.abigballofmud.flink.client.infra.context.FlinkApiContext;
import org.abigballofmud.flink.client.infra.converter.ClusterConvertMapper;
import org.abigballofmud.flink.client.infra.mapper.ClusterMapper;
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
        ClusterDTO dto = ClusterConvertMapper.INSTANCE.entityToDTO(cluster);
        dto.setJobManagerStandbyUrlSet(Stream
                .of(clusterDTO.getJobManagerStandbyUrl().split(";"))
                .collect(Collectors.toSet()));
        return dto;
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
