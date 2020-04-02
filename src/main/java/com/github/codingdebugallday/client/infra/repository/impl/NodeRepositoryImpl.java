package com.github.codingdebugallday.client.infra.repository.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.codingdebugallday.client.api.dto.NodeDTO;
import com.github.codingdebugallday.client.domain.entity.Node;
import com.github.codingdebugallday.client.domain.repository.NodeRepository;
import com.github.codingdebugallday.client.infra.converter.NodeConvertMapper;
import com.github.codingdebugallday.client.infra.mapper.NodeMapper;
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
public class NodeRepositoryImpl implements NodeRepository {

    private final NodeMapper nodeMapper;

    public NodeRepositoryImpl(NodeMapper nodeMapper) {
        this.nodeMapper = nodeMapper;
    }

    @Override
    public List<NodeDTO> selectByClusterCode(String clusterCode, Long tenantId) {
        QueryWrapper<Node> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Node.FIELD_CLUSTER_CODE, clusterCode);
        queryWrapper.eq(Node.FIELD_TENANT_ID, tenantId);
        return nodeMapper.selectList(queryWrapper).stream()
                .map(NodeConvertMapper.INSTANCE::entityToDTO)
                .collect(Collectors.toList());
    }

}
