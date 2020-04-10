package com.github.codingdebugallday.client.app.service.tm;

import com.github.codingdebugallday.client.api.dto.ClusterDTO;
import com.github.codingdebugallday.client.app.service.ApiClient;
import com.github.codingdebugallday.client.app.service.FlinkCommonService;
import com.github.codingdebugallday.client.domain.entity.tm.TaskManagerDetail;
import com.github.codingdebugallday.client.domain.entity.tm.TaskManagerInfo;
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
 * @author isacc 2020/04/09 17:35
 * @since 1.0
 */
@Slf4j
public class FlinkTaskManagerService extends FlinkCommonService {

    private final RestTemplate restTemplate;

    public FlinkTaskManagerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public TaskManagerInfo taskMangerList(ApiClient apiClient) {
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        try {
            return getForEntity(restTemplate, clusterDTO.getJobManagerUrl() + FlinkApiConstant.TaskManager.TM_LIST,
                    TaskManagerInfo.class);
        } catch (Exception e) {
            for (String url : clusterDTO.getJobManagerStandbyUrlSet()) {
                try {
                    return getForEntity(restTemplate, url + FlinkApiConstant.TaskManager.TM_LIST,
                            TaskManagerInfo.class);
                } catch (Exception ex) {
                    // ignore
                }
            }
            throw new FlinkApiCommonException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error.flink.tm.list");
        }
    }

    public TaskManagerDetail taskManagerDetail(String tmId, ApiClient apiClient) {
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        try {
            return getForEntity(restTemplate,
                    clusterDTO.getJobManagerUrl() + FlinkApiConstant.TaskManager.TM_DETAIL,
                    TaskManagerDetail.class, tmId);
        } catch (Exception e) {
            for (String url : clusterDTO.getJobManagerStandbyUrlSet()) {
                try {
                    return getForEntity(restTemplate,
                            url + FlinkApiConstant.TaskManager.TM_DETAIL,
                            TaskManagerDetail.class, tmId);
                } catch (Exception ex) {
                    // ignore
                }
            }
            throw new FlinkApiCommonException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error.flink.tm.detail");
        }
    }

    public String taskManagerLog(String tmId, ApiClient apiClient) {
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        try {
            return getForEntity(restTemplate,
                    clusterDTO.getJobManagerUrl() + FlinkApiConstant.TaskManager.TM_LOG,
                    String.class, tmId);
        } catch (Exception e) {
            for (String url : clusterDTO.getJobManagerStandbyUrlSet()) {
                try {
                    return getForEntity(restTemplate,
                            url + FlinkApiConstant.TaskManager.TM_LOG,
                            String.class, tmId);
                } catch (Exception ex) {
                    // ignore
                }
            }
            throw new FlinkApiCommonException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error.flink.tm.log");
        }
    }

    public String taskManagerStdout(String tmId, ApiClient apiClient) {
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        try {
            return getForEntity(restTemplate,
                    clusterDTO.getJobManagerUrl() + FlinkApiConstant.TaskManager.TM_STDOUT,
                    String.class, tmId);
        } catch (Exception e) {
            for (String url : clusterDTO.getJobManagerStandbyUrlSet()) {
                try {
                    return getForEntity(restTemplate,
                            url + FlinkApiConstant.TaskManager.TM_STDOUT,
                            String.class, tmId);
                } catch (Exception ex) {
                    // ignore
                }
            }
            throw new FlinkApiCommonException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error.flink.tm.stdout");
        }
    }
}
