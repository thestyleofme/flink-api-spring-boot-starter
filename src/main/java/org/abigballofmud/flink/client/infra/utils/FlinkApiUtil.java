package org.abigballofmud.flink.client.infra.utils;

import org.abigballofmud.flink.client.api.dto.ClusterDTO;
import org.abigballofmud.flink.client.app.service.ApiClient;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/03/28 0:58
 * @since 1.0
 */
public class FlinkApiUtil {

    private FlinkApiUtil() {
        throw new IllegalStateException("util class");
    }

    /**
     * 校验ApiClient中的FlinkCluster是否设置
     * clusterCode以及jobManagerUrl不能为空
     *
     * @param apiClient ApiClient
     * @return boolean 是否设置
     */
    public boolean checkApiClient(ApiClient apiClient) {
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        return Preconditions.checkAllNotNull(clusterDTO,
                clusterDTO.getClusterCode(),
                clusterDTO.getJobManagerUrl());
    }

}
