package com.github.codingdebugallday.client.infra.autoconfigure;

import com.github.codingdebugallday.client.domain.repository.ClusterRepository;
import com.github.codingdebugallday.client.domain.repository.NodeRepository;
import com.github.codingdebugallday.client.infra.context.FlinkApiContext;
import com.github.codingdebugallday.client.infra.exceptions.RestTemplateErrorHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/03/26 21:28
 * @since 1.0
 */
@Configuration
@ComponentScan("com.github.codingdebugallday.client")
@MapperScan({
        "com.github.codingdebugallday.client.**.mapper"
})
public class FlinkApiAutoConfiguration {

    @Bean
    public FlinkApiContext flinkApiContext(@Qualifier("flinkRestTemplate") RestTemplate restTemplate,
                                           ClusterRepository clusterRepository,
                                           NodeRepository nodeRepository) {
        return new FlinkApiContext(restTemplate, clusterRepository, nodeRepository);
    }

    @Bean("flinkRestTemplate")
    public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        restTemplate.setErrorHandler(new RestTemplateErrorHandler());
        return restTemplate;
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(5000);
        factory.setConnectTimeout(15000);
        return factory;
    }
}
