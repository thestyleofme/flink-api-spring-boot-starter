package com.github.codingdebugallday.client.app.service.overview;

import java.util.Map;

import com.github.codingdebugallday.client.api.dto.ClusterDTO;
import com.github.codingdebugallday.client.app.service.ApiClient;
import com.github.codingdebugallday.client.app.service.FlinkCommonService;
import com.github.codingdebugallday.client.domain.entity.overview.DashboardConfiguration;
import com.github.codingdebugallday.client.infra.constants.FlinkApiConstant;
import com.github.codingdebugallday.client.infra.exceptions.FlinkApiCommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * flink Cluster Overview
 * </p>
 *
 * @author isacc 2020/5/9 11:27
 * @since 1.0
 */
@Slf4j
public class ClusterOverviewService extends FlinkCommonService {

    private final RestTemplate restTemplate;

    public ClusterOverviewService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public DashboardConfiguration overviewConfig(ApiClient apiClient) {
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        try {
            return getForEntity(restTemplate,
                    clusterDTO.getJobManagerUrl() + FlinkApiConstant.Overview.CONFIG,
                    DashboardConfiguration.class);
        } catch (Exception e) {
            for (String url : clusterDTO.getJobManagerStandbyUrlSet()) {
                try {
                    return getForEntity(restTemplate,
                            url + FlinkApiConstant.Overview.CONFIG,
                            DashboardConfiguration.class);
                } catch (Exception ex) {
                    // ignore
                }
            }
            throw new FlinkApiCommonException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error.flink.overview.config");
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String,Object> overview(ApiClient apiClient) {
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        try {
            return getForEntity(restTemplate,
                    clusterDTO.getJobManagerUrl() + FlinkApiConstant.Overview.VIEW,
                    Map.class);
        } catch (Exception e) {
            for (String url : clusterDTO.getJobManagerStandbyUrlSet()) {
                try {
                    return getForEntity(restTemplate,
                            url + FlinkApiConstant.Overview.VIEW,
                            Map.class);
                } catch (Exception ex) {
                    // ignore
                }
            }
            throw new FlinkApiCommonException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error.flink.overview");
        }
    }

}
