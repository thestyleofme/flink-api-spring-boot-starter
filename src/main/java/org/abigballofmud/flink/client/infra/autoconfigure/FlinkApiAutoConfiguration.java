package org.abigballofmud.flink.client.infra.autoconfigure;

import org.abigballofmud.flink.client.domain.repository.ClusterRepository;
import org.abigballofmud.flink.client.infra.context.FlinkApiContext;
import org.abigballofmud.flink.client.infra.exceptions.RestTemplateErrorHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
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
@ComponentScan("org.abigballofmud.flink.client")
@MapperScan({
        "org.abigballofmud.flink.client.**.mapper"
})
public class FlinkApiAutoConfiguration {

    @Bean
    public FlinkApiContext flinkApiContext(RestTemplate restTemplate, ClusterRepository clusterRepository) {
        return new FlinkApiContext(restTemplate, clusterRepository);
    }

    @Bean("flinkRestTemplate")
    public RestTemplate restTemplate(ClientHttpRequestFactory simpleClientHttpRequestFactory) {
        RestTemplate restTemplate = new RestTemplate(simpleClientHttpRequestFactory);
        restTemplate.setErrorHandler(new RestTemplateErrorHandler());
        return restTemplate;
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(5000);
        factory.setConnectTimeout(15000);
        return factory;
    }
}
