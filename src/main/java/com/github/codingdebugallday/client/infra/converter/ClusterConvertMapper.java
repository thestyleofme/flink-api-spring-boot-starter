package com.github.codingdebugallday.client.infra.converter;

import com.github.codingdebugallday.client.api.dto.ClusterDTO;
import com.github.codingdebugallday.client.domain.entity.Cluster;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/03/31 12:12
 * @since 1.0
 */
@Mapper
public interface ClusterConvertMapper {

    ClusterConvertMapper INSTANCE = Mappers.getMapper(ClusterConvertMapper.class);

    /**
     * entityToDTO
     *
     * @param cluster Cluster
     * @return org.abigballofmud.flink.client.api.dto.ClusterDTO
     */
    @Mapping(target = "jobManagerStandbyUrlSet",
            expression = "java(com.github.codingdebugallday.client.infra.converter.ClusterConvertUtil.standbyUrlToSet(cluster.getJobManagerStandbyUrl()))")
    @Mapping(target = "host",
            expression = "java(com.github.codingdebugallday.client.infra.converter.ClusterConvertUtil.getJmHost(cluster.getJobManagerUrl()))")
    @Mapping(target = "port",
            expression = "java(com.github.codingdebugallday.client.infra.converter.ClusterConvertUtil.getJmPort(cluster.getJobManagerUrl()))")
    ClusterDTO entityToDTO(Cluster cluster);

    /**
     * dtoToEntity
     *
     * @param clusterDTO ClusterDTO
     * @return org.abigballofmud.flink.client.domain.entity.Cluster
     */
    Cluster dtoToEntity(ClusterDTO clusterDTO);

}
