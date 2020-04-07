package com.github.codingdebugallday.client.infra.converter;

import com.github.codingdebugallday.client.api.dto.UploadJarDTO;
import com.github.codingdebugallday.client.domain.entity.UploadJar;
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
public interface UploadJarConvertMapper {

    UploadJarConvertMapper INSTANCE = Mappers.getMapper(UploadJarConvertMapper.class);

    /**
     * entityToDTO
     *
     * @param uploadJar UploadJar
     * @return org.abigballofmud.flink.client.api.dto.ClusterDTO
     */
    UploadJarDTO entityToDTO(UploadJar uploadJar);

    /**
     * dtoToEntity
     *
     * @param uploadJarDTO UploadJarDTO
     * @return org.abigballofmud.flink.client.domain.entity.Cluster
     */
    UploadJar dtoToEntity(UploadJarDTO uploadJarDTO);

}
