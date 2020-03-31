package org.abigballofmud.flink.client.app.service.jars;

import java.io.File;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import org.abigballofmud.flink.client.api.dto.ClusterDTO;
import org.abigballofmud.flink.client.app.service.ApiClient;
import org.abigballofmud.flink.client.domain.entity.jars.JarRunRequest;
import org.abigballofmud.flink.client.domain.entity.jars.JarRunResponseBody;
import org.abigballofmud.flink.client.domain.entity.jars.JarUploadResponseBody;
import org.abigballofmud.flink.client.infra.constants.FlinkApiConstant;
import org.abigballofmud.flink.client.infra.exceptions.FlinkApiCommonException;
import org.abigballofmud.flink.client.infra.utils.JSON;
import org.abigballofmud.flink.client.infra.utils.RestTemplateUtil;
import org.abigballofmud.flink.client.infra.utils.RetryUtil;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/03/26 22:42
 * @since 1.0
 */
@Slf4j
public class FlinkJarService {

    private final RestTemplate restTemplate;

    public FlinkJarService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public JarRunResponseBody runJar(JarRunRequest jarRunRequest, ApiClient apiClient) {
        HttpEntity<String> requestEntity =
                new HttpEntity<>((JSON.toJson(jarRunRequest)), RestTemplateUtil.applicationJsonHeaders());
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        try {
            return RetryUtil.executeWithRetry(() ->
                            restTemplate.exchange(clusterDTO.getJobManagerUrl() +
                                            String.format(FlinkApiConstant.Jars.RUN_JAR, jarRunRequest.getJarId()),
                                    HttpMethod.POST, requestEntity, JarRunResponseBody.class),
                    3, 1000L, true).getBody();
        } catch (Exception e) {
            for (String url : clusterDTO.getJobManagerStandbyUrlSet()) {
                try {
                    return RetryUtil.executeWithRetry(() -> restTemplate.exchange(
                            url + String.format(FlinkApiConstant.Jars.RUN_JAR, jarRunRequest.getJarId()),
                            HttpMethod.POST, requestEntity, JarRunResponseBody.class),
                            3, 1000L, true).getBody();
                } catch (Exception ex) {
                    // ignore
                }
            }
        }
        throw new FlinkApiCommonException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error.flink.jar.run");
    }


    /**
     * 上传flink jar
     *
     * @param file      jar file
     * @param apiClient ApiClient
     * @return org.abigballofmud.flink.api.domain.jars.JarUploadResponseBody
     */
    public JarUploadResponseBody uploadJar(File file, ApiClient apiClient) {
        // 校验apiClient中的flinkCluster
        Assert.isTrue(checkApiClient(apiClient), "Please check the flink jobManagerUrl and uploadJarPath are configured");
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>(1);
        body.add("file", new FileSystemResource(file));
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, RestTemplateUtil.applicationMultiDataHeaders());
        try {
            // 先尝试使用jm 主url，三次失败后使用备用
            return RetryUtil.executeWithRetry(() ->
                            restTemplate.exchange(
                                    clusterDTO.getJobManagerUrl() + FlinkApiConstant.Jars.UPLOAD_JAR,
                                    HttpMethod.POST, requestEntity, JarUploadResponseBody.class),
                    3, 1000L, true).getBody();
        } catch (Exception e) {
            // 若配置了HA 这里使用备用
            for (String url : clusterDTO.getJobManagerStandbyUrlSet()) {
                try {
                    return RetryUtil.executeWithRetry(() ->
                                    restTemplate.exchange(
                                            url + FlinkApiConstant.Jars.UPLOAD_JAR,
                                            HttpMethod.POST, requestEntity, JarUploadResponseBody.class),
                            3, 1000L, true).getBody();
                } catch (Exception ex) {
                    // ignore
                }
            }
        }
        throw new FlinkApiCommonException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error.flink.jar.upload");
    }

    private boolean checkApiClient(ApiClient apiClient) {
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        return !Objects.isNull(clusterDTO) &&
                !StringUtils.isEmpty(clusterDTO.getJobManagerUrl());
    }
}
