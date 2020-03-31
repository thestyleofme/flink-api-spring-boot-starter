package com.github.codingdebugallday.client.infra.context;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.github.codingdebugallday.client.domain.repository.ClusterRepository;
import com.github.codingdebugallday.client.app.service.FlinkApi;
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

    public FlinkApiContext(RestTemplate flinkRestTemplate,
                           ClusterRepository clusterRepository) {
        this.flinkRestTemplate = flinkRestTemplate;
        this.clusterRepository = clusterRepository;
    }

    public FlinkApi get(String clusterCode) {
        if (Objects.isNull(flinkApiMap.get(clusterCode))) {
            FlinkApi flinkApi = new FlinkApi(flinkRestTemplate);
            flinkApi.getApiClient().setClusterDTO(clusterRepository.selectOne(clusterCode));
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
