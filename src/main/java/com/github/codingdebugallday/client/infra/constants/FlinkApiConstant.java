package com.github.codingdebugallday.client.infra.constants;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/03/27 1:11
 * @since 1.0
 */
public final class FlinkApiConstant {

    private FlinkApiConstant() {
    }

    public static final class Jars {
        private Jars() {
        }

        /**
         * flink jar upload url
         */
        public static final String UPLOAD_JAR = "/v1/jars/upload";
        /**
         * flink jar delete url
         */
        public static final String DELETE_JAR = "/v1/jars/%s";
        /**
         * running a jar previously uploaded via '/jars/upload'
         */
        public static final String RUN_JAR = "/v1/jars/%s/run";
    }

    public static final class Jobs {
        private Jobs() {
        }
        /**
         * flink job url
         */
        public static final String JOB_LIST = "/v1/jobs";
        public static final String JOB_OVERVIEW = "/v1/jobs/overview";
        public static final String JOB_DETAIL = "/v1/jobs/%s";
        public static final String JOB_YARN_CANCEL = "/v1/jobs/%s/yarn-cancel";
        public static final String JOB_CANCEL_WITH_SAVEPOINTS = "/v1/jobs/%s/savepoints";
        public static final String JOB_TERMINATE  = "/v1/jobs/%s";
        public static final String JOB_EXECUTION_RESULT = "/v1/jobs/%s/execution-result";
        public static final String JOB_EXCEPTIONS = "/v1/jobs/%s/exceptions";
        public static final String JOB_RESCALING = "/v1/jobs/%s/rescaling";
    }
}
