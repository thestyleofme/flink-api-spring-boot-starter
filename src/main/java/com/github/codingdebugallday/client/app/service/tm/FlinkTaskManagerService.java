package com.github.codingdebugallday.client.app.service.tm;

import com.github.codingdebugallday.client.api.dto.ClusterDTO;
import com.github.codingdebugallday.client.app.service.ApiClient;
import com.github.codingdebugallday.client.app.service.FlinkCommonService;
import com.github.codingdebugallday.client.domain.entity.tm.TaskManagerInfo;
import com.github.codingdebugallday.client.infra.constants.FlinkApiConstant;
import lombok.extern.slf4j.Slf4j;
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
        return getForEntityStandby(restTemplate,
                clusterDTO.getJobManagerUrl(),
                clusterDTO.getJobManagerStandbyUrlSet(),
                FlinkApiConstant.TaskManager.TM_LIST,
                TaskManagerInfo.class,
                "error.flink.tm.list");
    }
}
