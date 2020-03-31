package org.abigballofmud.flink.client.app.service;

import org.abigballofmud.flink.client.api.dto.ClusterDTO;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/03/26 23:11
 * @since 1.0
 */
public class ApiClient {

    private ClusterDTO clusterDTO;

    public ClusterDTO getClusterDTO() {
        return clusterDTO;
    }

    public void setClusterDTO(ClusterDTO clusterDTO) {
        this.clusterDTO = clusterDTO;
    }
}
