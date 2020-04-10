package com.github.codingdebugallday.client.app.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.codingdebugallday.client.api.dto.ClusterDTO;
import com.github.codingdebugallday.client.api.dto.NodeDTO;
import com.github.codingdebugallday.client.api.dto.NodeSettingInfo;
import com.github.codingdebugallday.client.app.service.ClusterService;
import com.github.codingdebugallday.client.domain.entity.Cluster;
import com.github.codingdebugallday.client.domain.entity.Node;
import com.github.codingdebugallday.client.domain.repository.ClusterRepository;
import com.github.codingdebugallday.client.infra.context.FlinkApiContext;
import com.github.codingdebugallday.client.infra.converter.ClusterConvertMapper;
import com.github.codingdebugallday.client.infra.converter.NodeConvertMapper;
import com.github.codingdebugallday.client.infra.mapper.ClusterMapper;
import com.github.codingdebugallday.client.infra.mapper.NodeMapper;
import com.github.codingdebugallday.client.infra.utils.JSON;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/03/31 12:02
 * @since 1.0
 */
@Service("flinkClusterService")
public class ClusterServiceImpl extends ServiceImpl<ClusterMapper, Cluster> implements ClusterService {

    private final ClusterMapper clusterMapper;
    private final NodeMapper nodeMapper;
    private final FlinkApiContext flinkApiContext;
    private final ClusterRepository clusterRepository;

    @Resource
    private StringEncryptor jasyptStringEncryptor;

    public ClusterServiceImpl(ClusterMapper clusterMapper,
                              NodeMapper nodeMapper,
                              FlinkApiContext flinkApiContext,
                              ClusterRepository clusterRepository) {
        this.clusterMapper = clusterMapper;
        this.nodeMapper = nodeMapper;
        this.flinkApiContext = flinkApiContext;
        this.clusterRepository = clusterRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ClusterDTO insert(ClusterDTO clusterDTO) {
        Cluster cluster = ClusterConvertMapper.INSTANCE.dtoToEntity(clusterDTO);
        // 插集群表
        clusterMapper.insert(cluster);
        // 插节点表
        List<NodeDTO> nodeDTOList = genNodeList(clusterDTO).stream().map(node -> {
            nodeMapper.insert(node);
            return NodeConvertMapper.INSTANCE.entityToDTO(nodeMapper.selectById(node.getNodeId()));
        }).collect(Collectors.toList());
        ClusterDTO dto = ClusterConvertMapper.INSTANCE.entityToDTO(cluster);
        dto.setNodeDTOList(nodeDTOList);
        return dto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ClusterDTO update(ClusterDTO clusterDTO) {
        Cluster cluster = ClusterConvertMapper.INSTANCE.dtoToEntity(clusterDTO);
        // 更新集群表
        clusterMapper.updateById(cluster);
        // flinkApiContext还需remove掉
        flinkApiContext.remove(clusterDTO.getClusterCode());
        // 更新节点表
        List<NodeDTO> nodeDTOList = genNodeList(clusterDTO).stream().map(node -> {
            nodeMapper.updateById(node);
            return NodeConvertMapper.INSTANCE.entityToDTO(nodeMapper.selectById(node.getNodeId()));
        }).collect(Collectors.toList());
        ClusterDTO dto = ClusterConvertMapper.INSTANCE.entityToDTO(cluster);
        dto.setNodeDTOList(nodeDTOList);
        return dto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(ClusterDTO clusterDTO) {
        clusterRepository.delete(clusterDTO);
        // flinkApiContext还需remove掉
        flinkApiContext.remove(clusterDTO.getClusterCode());
    }

    private List<Node> genNodeList(ClusterDTO clusterDTO) {
        return clusterDTO.getNodeDTOList().stream().map(nodeDTO -> {
            nodeDTO.setClusterCode(clusterDTO.getClusterCode());
            nodeDTO.setTenantId(clusterDTO.getTenantId());
            NodeSettingInfo nodeSettingInfo = JSON.toObj(nodeDTO.getSettingInfo(), NodeSettingInfo.class);
            nodeSettingInfo.setPassword(jasyptStringEncryptor.encrypt(nodeSettingInfo.getPassword()));
            nodeDTO.setSettingInfo(JSON.toJson(nodeSettingInfo));
            return NodeConvertMapper.INSTANCE.dtoToEntity(nodeDTO);
        }).collect(Collectors.toList());
    }

}
