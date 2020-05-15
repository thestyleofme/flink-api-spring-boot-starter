package com.github.codingdebugallday.client.app.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.codingdebugallday.client.api.dto.ClusterDTO;
import com.github.codingdebugallday.client.api.dto.NodeDTO;
import com.github.codingdebugallday.client.api.dto.NodeSettingInfo;
import com.github.codingdebugallday.client.app.service.ClusterService;
import com.github.codingdebugallday.client.app.service.FlinkApi;
import com.github.codingdebugallday.client.domain.entity.Cluster;
import com.github.codingdebugallday.client.domain.entity.Node;
import com.github.codingdebugallday.client.domain.entity.jobs.*;
import com.github.codingdebugallday.client.domain.entity.overview.DashboardConfiguration;
import com.github.codingdebugallday.client.domain.entity.tm.TaskManagerDetail;
import com.github.codingdebugallday.client.domain.entity.tm.TaskManagerInfo;
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
        // 需要删除的节点
        List<Long> existNodeList = selectByClusterCode(cluster.getClusterCode(), cluster.getTenantId()).stream()
                .map(Node::getNodeId)
                .collect(Collectors.toList());
        List<Node> nodeList = genNodeList(clusterDTO);
        List<Long> curNodeList = nodeList.stream()
                .filter(node -> node.getNodeId() != null)
                .map(Node::getNodeId)
                .collect(Collectors.toList());
        // 取差集 就是需要删除的节点
        existNodeList.removeAll(curNodeList);
        nodeMapper.deleteBatchIds(existNodeList);
        //  需要新增和更新
        List<NodeDTO> nodeDTOList = nodeList.stream().map(node -> {
            if (Objects.isNull(node.getNodeId())) {
                nodeMapper.insert(node);
            } else {
                nodeMapper.updateById(node);
            }
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

    @Override
    public DashboardConfiguration overviewConfig(Long tenantId, String clusterCode) {
        FlinkApi flinkApi = flinkApiContext.get(clusterCode, tenantId);
        return flinkApi.overviewConfig();
    }

    @Override
    public Map<String, Object> overview(Long tenantId, String clusterCode) {
        FlinkApi flinkApi = flinkApiContext.get(clusterCode, tenantId);
        return flinkApi.overview();
    }

    @Override
    public JobIdsWithStatusOverview jobList(Long tenantId, String clusterCode) {
        FlinkApi flinkApi = flinkApiContext.get(clusterCode, tenantId);
        return flinkApi.jobList();
    }

    @Override
    public MultipleJobsDetails jobsDetails(Long tenantId, String clusterCode) {
        FlinkApi flinkApi = flinkApiContext.get(clusterCode, tenantId);
        return flinkApi.jobsDetails();
    }

    @Override
    public JobDetailsInfo jobDetail(Long tenantId, String clusterCode, String jobId) {
        FlinkApi flinkApi = flinkApiContext.get(clusterCode, tenantId);
        return flinkApi.jobDetail(jobId);
    }

    @Override
    public FlinkApiErrorResponse jobYarnCancel(Long tenantId, String clusterCode, String jobId) {
        FlinkApi flinkApi = flinkApiContext.get(clusterCode, tenantId);
        return flinkApi.jobYarnCancel(jobId);
    }

    @Override
    public FlinkApiErrorResponse jobTerminate(Long tenantId, String clusterCode, String jobId, String mode) {
        FlinkApi flinkApi = flinkApiContext.get(clusterCode, tenantId);
        return flinkApi.jobTerminate(jobId, mode);
    }

    @Override
    public TriggerResponseWithSavepoint jobCancelOptionSavepoints(Long tenantId, String clusterCode,
                                                     SavepointTriggerRequestBody savepointTriggerRequestBody) {
        FlinkApi flinkApi = flinkApiContext.get(clusterCode, tenantId);
        return flinkApi.jobCancelOptionSavepoints(savepointTriggerRequestBody);
    }

    @Override
    public TriggerResponse jobRescale(Long tenantId, String clusterCode, String jobId, int parallelism) {
        FlinkApi flinkApi = flinkApiContext.get(clusterCode, tenantId);
        return flinkApi.jobRescale(jobId, parallelism);
    }

    @Override
    public JobExceptionsInfo jobException(Long tenantId, String clusterCode, String jobId, String maxExceptions) {
        FlinkApi flinkApi = flinkApiContext.get(clusterCode, tenantId);
        return flinkApi.jobException(jobId, maxExceptions);
    }

    @Override
    public TaskManagerInfo taskMangerList(Long tenantId, String clusterCode) {
        FlinkApi flinkApi = flinkApiContext.get(clusterCode, tenantId);
        return flinkApi.taskMangerList();
    }

    @Override
    public TaskManagerDetail taskManagerDetail(Long tenantId, String clusterCode, String tmId) {
        FlinkApi flinkApi = flinkApiContext.get(clusterCode, tenantId);
        return flinkApi.taskManagerDetail(tmId);
    }

    @Override
    public String taskManagerLog(Long tenantId, String clusterCode, String tmId) {
        FlinkApi flinkApi = flinkApiContext.get(clusterCode, tenantId);
        return flinkApi.taskManagerLog(tmId);
    }

    @Override
    public String taskManagerStdout(Long tenantId, String clusterCode, String tmId) {
        FlinkApi flinkApi = flinkApiContext.get(clusterCode, tenantId);
        return flinkApi.taskManagerStdout(tmId);
    }

    @Override
    public List<Map<String, String>> jobManagerConfig(Long tenantId, String clusterCode) {
        FlinkApi flinkApi = flinkApiContext.get(clusterCode, tenantId);
        return flinkApi.jobManagerConfig();
    }

    @Override
    public String jobManagerLog(Long tenantId, String clusterCode) {
        FlinkApi flinkApi = flinkApiContext.get(clusterCode, tenantId);
        return flinkApi.jobManagerLog();
    }

    @Override
    public String jobManagerStdout(Long tenantId, String clusterCode) {
        FlinkApi flinkApi = flinkApiContext.get(clusterCode, tenantId);
        return flinkApi.jobManagerStdout();
    }

    private List<Node> genNodeList(ClusterDTO clusterDTO) {
        return clusterDTO.getNodeDTOList().stream().map(nodeDTO -> {
            nodeDTO.setClusterCode(clusterDTO.getClusterCode());
            nodeDTO.setTenantId(clusterDTO.getTenantId());
            NodeSettingInfo nodeSettingInfo = JSON.toObj(nodeDTO.getSettingInfo(), NodeSettingInfo.class);
            // 第一次新增时对密码进行加密 或者 该节点需要对密码进行更新
            if (Objects.isNull(nodeDTO.getNodeId()) ||
                    Boolean.TRUE.equals(nodeSettingInfo.getChangePassword())) {
                nodeSettingInfo.setPassword(jasyptStringEncryptor.encrypt(nodeSettingInfo.getPassword()));
            }
            nodeDTO.setSettingInfo(JSON.toJson(nodeSettingInfo));
            return NodeConvertMapper.INSTANCE.dtoToEntity(nodeDTO);
        }).collect(Collectors.toList());
    }

    public List<Node> selectByClusterCode(String clusterCode, Long tenantId) {
        QueryWrapper<Node> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Node.FIELD_CLUSTER_CODE, clusterCode);
        queryWrapper.eq(Node.FIELD_TENANT_ID, tenantId);
        return nodeMapper.selectList(queryWrapper);
    }

}
