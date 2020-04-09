package com.github.codingdebugallday.client.app.service;

import java.util.Set;

import com.github.codingdebugallday.client.infra.exceptions.FlinkApiCommonException;
import com.github.codingdebugallday.client.infra.utils.RetryUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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
     * getForEntityStandby
     *
     * @param restTemplate            RestTemplate
     * @param url                     url
     * @param jobManagerStandbyUrlSet Set<String>
     * @param extraUrl                extraUrl
     * @param responseType            Class<T>
     * @param errorCode               errorCode
     * @param uriVariables            Object... uriVariables
     * @return T
     */
    public <T> T getForEntityStandby(RestTemplate restTemplate,
                                     String url,
                                     Set<String> jobManagerStandbyUrlSet,
                                     String extraUrl,
                                     Class<T> responseType,
                                     String errorCode,
                                     Object... uriVariables) {
        try {
            return getForEntity(restTemplate, url + extraUrl, responseType, uriVariables);
        } catch (Exception e) {
            for (String standbyUrl : jobManagerStandbyUrlSet) {
                try {
                    return getForEntity(restTemplate, standbyUrl + extraUrl, responseType, uriVariables);
                } catch (Exception ex) {
                    // ignore
                }
            }
            throw new FlinkApiCommonException(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorCode);
        }
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

    /**
     * exchangeStandby
     *
     * @param restTemplate            RestTemplate
     * @param url                     url
     * @param jobManagerStandbyUrlSet Set<String>
     * @param extraUrl                extraUrl
     * @param method                  HttpMethod
     * @param requestEntity           HttpEntity<?>
     * @param responseType            Class<T>
     * @param errorCode               errorCode
     * @param uriVariables            Object... uriVariables
     * @return T
     */
    public <T> T exchangeStandby(RestTemplate restTemplate,
                                 String url,
                                 Set<String> jobManagerStandbyUrlSet,
                                 String extraUrl,
                                 HttpMethod method,
                                 @Nullable HttpEntity<?> requestEntity,
                                 Class<T> responseType,
                                 String errorCode,
                                 Object... uriVariables) {
        try {
            return exchange(restTemplate, url + extraUrl, method, requestEntity, responseType, uriVariables);
        } catch (Exception e) {
            for (String standbyUrl : jobManagerStandbyUrlSet) {
                try {
                    return exchange(restTemplate, standbyUrl + extraUrl, method, requestEntity, responseType, uriVariables);
                } catch (Exception ex) {
                    // ignore
                }
            }
            throw new FlinkApiCommonException(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorCode);
        }
    }
}
