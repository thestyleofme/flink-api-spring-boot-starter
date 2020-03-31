package org.abigballofmud.flink.client.infra.converter;

import org.abigballofmud.flink.client.api.dto.ClusterDTO;
import org.abigballofmud.flink.client.domain.entity.Cluster;
import org.mapstruct.Mapper;
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
    ClusterDTO entityToDTO(Cluster cluster);

    /**
     * dtoToEntity
     *
     * @param clusterDTO ClusterDTO
     * @return org.abigballofmud.flink.client.domain.entity.Cluster
     */
    Cluster dtoToEntity(ClusterDTO clusterDTO);

}
