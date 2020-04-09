package com.github.codingdebugallday.client.domain.entity.jobs;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/04/09 16:39
 * @since 1.0
 */
@NoArgsConstructor
@Data
public class JobExceptionsInfo {

    public static final String FIELD_NAME_ROOT_EXCEPTION = "root-exception";
    public static final String FIELD_NAME_TIMESTAMP = "timestamp";
    public static final String FIELD_NAME_ALL_EXCEPTIONS = "all-exceptions";
    public static final String FIELD_NAME_TRUNCATED = "truncated";

    @JsonProperty(FIELD_NAME_ROOT_EXCEPTION)
    private String rootException;

    @JsonProperty(FIELD_NAME_TIMESTAMP)
    private Long rootTimestamp;

    @JsonProperty(FIELD_NAME_ALL_EXCEPTIONS)
    private List<ExecutionExceptionInfo> allExceptions;

    @JsonProperty(FIELD_NAME_TRUNCATED)
    private boolean truncated;

    @NoArgsConstructor
    @Data
    public static class ExecutionExceptionInfo {
        public static final String FIELD_NAME_EXCEPTION = "exception";
        public static final String FIELD_NAME_TASK = "task";
        public static final String FIELD_NAME_LOCATION = "location";
        public static final String FIELD_NAME_TIMESTAMP = "timestamp";

        @JsonProperty(FIELD_NAME_EXCEPTION)
        private String exception;

        @JsonProperty(FIELD_NAME_TASK)
        private String task;

        @JsonProperty(FIELD_NAME_LOCATION)
        private String location;

        @JsonProperty(FIELD_NAME_TIMESTAMP)
        private long timestamp;
    }

}
