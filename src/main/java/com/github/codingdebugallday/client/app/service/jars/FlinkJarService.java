package com.github.codingdebugallday.client.app.service.jars;

import java.io.File;
import java.util.Objects;

import com.github.codingdebugallday.client.api.dto.ClusterDTO;
import com.github.codingdebugallday.client.app.service.ApiClient;
import com.github.codingdebugallday.client.app.service.FlinkCommonService;
import com.github.codingdebugallday.client.domain.entity.jars.JarRunRequest;
import com.github.codingdebugallday.client.domain.entity.jars.JarRunResponseBody;
import com.github.codingdebugallday.client.domain.entity.jars.JarUploadResponseBody;
import com.github.codingdebugallday.client.infra.constants.FlinkApiConstant;
import com.github.codingdebugallday.client.infra.utils.JSON;
import com.github.codingdebugallday.client.infra.utils.RestTemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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
public class FlinkJarService extends FlinkCommonService {

    private final RestTemplate restTemplate;

    public FlinkJarService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private boolean checkApiClient(ApiClient apiClient) {
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        return !Objects.isNull(clusterDTO) &&
                !StringUtils.isEmpty(clusterDTO.getJobManagerUrl());
    }

    public JarRunResponseBody runJar(JarRunRequest jarRunRequest, ApiClient apiClient) {
        HttpEntity<String> requestEntity =
                new HttpEntity<>((JSON.toJson(jarRunRequest)), RestTemplateUtil.applicationJsonHeaders());
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        return exchangeStandby(restTemplate,
                clusterDTO.getJobManagerUrl(),
                clusterDTO.getJobManagerStandbyUrlSet(),
                FlinkApiConstant.Jars.RUN_JAR,
                HttpMethod.POST,
                requestEntity,
                JarRunResponseBody.class,
                "error.flink.jar.run",
                jarRunRequest.getJarId());
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
        return exchangeStandby(restTemplate,
                clusterDTO.getJobManagerUrl(),
                clusterDTO.getJobManagerStandbyUrlSet(),
                FlinkApiConstant.Jars.UPLOAD_JAR,
                HttpMethod.POST,
                requestEntity,
                JarUploadResponseBody.class,
                "error.flink.jar.upload");
    }

    public void deleteJar(String jarId, ApiClient apiClient) {
        ClusterDTO clusterDTO = apiClient.getClusterDTO();
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>(1);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body);
        exchangeStandby(restTemplate,
                clusterDTO.getJobManagerUrl(),
                clusterDTO.getJobManagerStandbyUrlSet(),
                FlinkApiConstant.Jars.DELETE_JAR,
                HttpMethod.DELETE,
                requestEntity,
                String.class,
                "error.flink.jar.delete",
                jarId);
    }
}
