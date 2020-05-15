package com.github.codingdebugallday.client.domain.entity.jobs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * SavepointInfo
 * </p>
 *
 * @author isacc 2020/5/15 16:42
 * @since 1.0
 */
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SavepointInfo {


    /**
     * status : {"id":"COMPLETED"}
     * operation : {"location":"file:/E:/myGitCode/flink-api-spring-boot-starter/savepoint1/savepoint-c3d2ad-aff370603065"}
     */

    private StatusBean status;
    private OperationBean operation;

    @NoArgsConstructor
    @Data
    public static class StatusBean {
        /**
         * id : COMPLETED
         */

        private String id;
    }

    @NoArgsConstructor
    @Data
    public static class OperationBean {
        /**
         * location : file:/E:/myGitCode/flink-api-spring-boot-starter/savepoint1/savepoint-c3d2ad-aff370603065
         */

        private String location;
    }
}
