package com.github.codingdebugallday.client.app.service;

import com.github.codingdebugallday.client.api.dto.ClusterDTO;

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
