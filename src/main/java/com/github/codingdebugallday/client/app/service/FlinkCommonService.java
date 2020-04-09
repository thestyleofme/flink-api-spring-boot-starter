package com.github.codingdebugallday.client.app.service;

import com.github.codingdebugallday.client.infra.utils.RetryUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/04/09 17:46
 * @since 1.0
 */
@Slf4j
public class FlinkCommonService {

    /**
     * getForEntity
     *
     * @param restTemplate RestTemplate
     * @param url          url
     * @param responseType Class<T>
     * @param uriVariables Object... uriVariables
     * @return T
     */
    public <T> T getForEntity(RestTemplate restTemplate,
                              String url,
                              Class<T> responseType,
                              Object... uriVariables) throws Exception {
        return RetryUtil.executeWithRetry(() -> {
            ResponseEntity<T> responseEntity = restTemplate.getForEntity(
                    url,
                    responseType,
                    uriVariables);
            log.debug("response, status: {}, body: {}", responseEntity.getStatusCode(), responseEntity.getBody());
            return responseEntity;
        }, 3, 1000L, true).getBody();
    }

    /**
     * exchange
     *
     * @param restTemplate  RestTemplate
     * @param url           url
     * @param method        HttpMethod
     * @param requestEntity HttpEntity<?>
     * @param responseType  Class<T>
     * @param uriVariables  Object... uriVariables
     * @return T
     */
    public <T> T exchange(RestTemplate restTemplate,
                          String url,
                          HttpMethod method,
                          @Nullable HttpEntity<?> requestEntity,
                          Class<T> responseType,
                          Object... uriVariables) throws Exception {
        return RetryUtil.executeWithRetry(() -> {
            ResponseEntity<T> responseEntity = restTemplate.exchange(url, method,
                    requestEntity, responseType, uriVariables);
            log.debug("response, status: {}, body: {}", responseEntity.getStatusCode(), responseEntity.getBody());
            return responseEntity;
        }, 3, 1000L, true).getBody();
    }

}
