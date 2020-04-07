package com.github.codingdebugallday.client.app.service;

import java.io.File;

import com.github.codingdebugallday.client.domain.entity.jars.JarRunRequest;
import com.github.codingdebugallday.client.domain.entity.jars.JarRunResponseBody;
import com.github.codingdebugallday.client.domain.entity.jars.JarUploadResponseBody;
import com.github.codingdebugallday.client.app.service.jars.FlinkJarService;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/03/26 21:55
 * @since 1.0
 */
public class FlinkApi {

    private ApiClient apiClient;
    /**
     * flink jar 相关 api
     */
    private final FlinkJarService flinkJarService;

    public FlinkApi(RestTemplate restTemplate) {
        this.apiClient = new ApiClient();
        flinkJarService = new FlinkJarService(restTemplate);
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    //===================flink jar api=========================

    /**
     * upload flink jar
     *
     * @param file flink jar file
     * @return org.abigballofmud.flink.api.domain.jars.JarUploadResponseBody
     */
    public JarUploadResponseBody uploadJar(File file) {
        return flinkJarService.uploadJar(file, apiClient);
    }

    /**
     * delete flink jar
     *
     * @param jarId flink jar file
     */
    public void deleteJar(String jarId) {
        flinkJarService.deleteJar(jarId, apiClient);
    }

    /**
     * run flink jar
     *
     * @param jarRunRequest JarRunRequest
     * @return org.abigballofmud.flink.api.domain.jars.JarRunResponseBody
     */
    public JarRunResponseBody runJar(JarRunRequest jarRunRequest) {
        return flinkJarService.runJar(jarRunRequest, apiClient);
    }

}
