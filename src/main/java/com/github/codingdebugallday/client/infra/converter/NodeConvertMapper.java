package com.github.codingdebugallday.client.infra.converter;

import com.github.codingdebugallday.client.api.dto.NodeDTO;
import com.github.codingdebugallday.client.domain.entity.Node;
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
public interface NodeConvertMapper {

    NodeConvertMapper INSTANCE = Mappers.getMapper(NodeConvertMapper.class);

    /**
     * entityToDTO
     *
     * @param node Node
     * @return org.abigballofmud.flink.client.api.dto.NodeDTO
     */
    NodeDTO entityToDTO(Node node);

    /**
     * dtoToEntity
     *
     * @param nodeDTO NodeDTO
     * @return org.abigballofmud.flink.client.domain.entity.Node
     */
    Node dtoToEntity(NodeDTO nodeDTO);

}
