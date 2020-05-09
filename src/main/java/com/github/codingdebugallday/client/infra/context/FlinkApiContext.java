package com.github.codingdebugallday.client.infra.context;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.github.codingdebugallday.client.api.dto.ClusterDTO;
import com.github.codingdebugallday.client.app.service.FlinkApi;
import com.github.codingdebugallday.client.domain.repository.ClusterRepository;
import com.github.codingdebugallday.client.domain.repository.NodeRepository;
import com.github.codingdebugallday.client.infra.exceptions.FlinkApiCommonException;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/03/28 0:37
 * @since 1.0
 */
public class FlinkApiContext {

    private final Map<String, FlinkApi> flinkApiMap = new ConcurrentHashMap<>(16);

    private final RestTemplate flinkRestTemplate;

    private final ClusterRepository clusterRepository;
    private final NodeRepository nodeRepository;

    public FlinkApiContext(RestTemplate flinkRestTemplate,
                           ClusterRepository clusterRepository,
                           NodeRepository nodeRepository) {
        this.flinkRestTemplate = flinkRestTemplate;
        this.clusterRepository = clusterRepository;
        this.nodeRepository = nodeRepository;
    }

    public FlinkApi get(String clusterCode, Long tenantId) {
        if (Objects.isNull(flinkApiMap.get(clusterCode))) {
            FlinkApi flinkApi = new FlinkApi(flinkRestTemplate);
            ClusterDTO clusterDTO =
                    Optional.ofNullable(clusterRepository.selectOne(clusterCode, tenantId))
                            .orElseThrow(() -> new FlinkApiCommonException(HttpStatus.BAD_REQUEST.value(),
                                    "no flink cluster of code[" + clusterCode + "], please check your clusterCode!"));
            clusterDTO.setNodeDTOList(nodeRepository.selectByClusterCode(clusterCode, tenantId));
            flinkApi.getApiClient().setClusterDTO(clusterDTO);
            flinkApiMap.put(clusterCode, flinkApi);
            return flinkApi;
        }
        return flinkApiMap.get(clusterCode);
    }

    /**
     * 在删除了flink cluster后，需要remove掉
     *
     * @param clusterCode clusterCode
     */
    public void remove(String clusterCode) {
        flinkApiMap.remove(clusterCode);
    }


}
