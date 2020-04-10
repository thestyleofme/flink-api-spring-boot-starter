package com.github.codingdebugallday.client.app.service.jm;

import java.util.List;
import java.util.Map;

import com.github.codingdebugallday.client.api.dto.ClusterDTO;
import com.github.codingdebugallday.client.app.service.ApiClient;
import com.github.codingdebugallday.client.app.service.FlinkCommonService;
import com.github.codingdebugallday.client.infra.constants.FlinkApiConstant;
import com.github.codingdebugallday.client.infra.exceptions.FlinkApiCommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/04/10 15:36
 * @since 1.0
 */
@Slf4j
public class FlinkJobManagerService extends FlinkCommonService {

    private final RestTemplate restTemplate;

    public FlinkJobManagerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, String>> jobManagerConfig(ApiClient apiClient) {
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        try {
            return getForEntity(restTemplate,
                    clusterDTO.getJobManagerUrl() + FlinkApiConstant.JobManager.JM_CONFIG,
                    List.class);
        } catch (Exception e) {
            for (String url : clusterDTO.getJobManagerStandbyUrlSet()) {
                try {
                    return getForEntity(restTemplate,
                            url + FlinkApiConstant.JobManager.JM_CONFIG,
                            List.class);
                } catch (Exception ex) {
                    // ignore
                }
            }
            throw new FlinkApiCommonException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error.flink.jm.config");
        }
    }

    public String jobManagerLog(ApiClient apiClient) {
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        try {
            return getForEntity(restTemplate,
                    clusterDTO.getJobManagerUrl() + FlinkApiConstant.JobManager.JM_LOG,
                    String.class);
        } catch (Exception e) {
            for (String url : clusterDTO.getJobManagerStandbyUrlSet()) {
                try {
                    return getForEntity(restTemplate,
                            url + FlinkApiConstant.JobManager.JM_LOG,
                            String.class);
                } catch (Exception ex) {
                    // ignore
                }
            }
            throw new FlinkApiCommonException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error.flink.jm.log");
        }
    }

    public String jobManagerStdout(ApiClient apiClient) {
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        try {
            return getForEntity(restTemplate,
                    clusterDTO.getJobManagerUrl() + FlinkApiConstant.JobManager.JM_STDOUT,
                    String.class);
        } catch (Exception e) {
            for (String url : clusterDTO.getJobManagerStandbyUrlSet()) {
                try {
                    return getForEntity(restTemplate,
                            url + FlinkApiConstant.JobManager.JM_STDOUT,
                            String.class);
                } catch (Exception ex) {
                    // ignore
                }
            }
            throw new FlinkApiCommonException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error.flink.jm.stdout");
        }
    }
}
