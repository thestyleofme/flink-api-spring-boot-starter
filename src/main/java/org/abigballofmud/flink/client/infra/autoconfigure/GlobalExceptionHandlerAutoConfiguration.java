package org.abigballofmud.flink.client.infra.autoconfigure;

import org.abigballofmud.flink.client.infra.exceptions.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 全局异常处理配置
 * </p>
 *
 * @author abigballofmud 2019/11/21 14:51
 * @since 1.0
 */
@Configuration
public class GlobalExceptionHandlerAutoConfiguration {

    @Bean("flinkGlobalExceptionHandler")
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

}
