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
        public static final String DELETE_JAR = "/v1/jars/{1}";
        /**
         * running a jar previously uploaded via '/jars/upload'
         */
        public static final String RUN_JAR = "/v1/jars/{1}/run";
    }

    public static final class Overview {
        private Overview() {
        }
        /**
         * flink overview url
         */
        public static final String CONFIG = "/v1/config";
        public static final String VIEW = "/v1/overview";
    }

    public static final class Jobs {
        private Jobs() {
        }
        /**
         * flink job url
         */
        public static final String JOB_LIST = "/v1/jobs";
        public static final String JOB_OVERVIEW = "/v1/jobs/overview";
        public static final String JOB_DETAIL = "/v1/jobs/{1}";
        public static final String JOB_YARN_CANCEL = "/v1/jobs/{1}/yarn-cancel";
        public static final String JOB_CANCEL_WITH_SAVEPOINTS = "/v1/jobs/{1}/savepoints";
        public static final String JOB_SAVEPOINT_STATUS = "/v1/jobs/{1}/savepoints/{2}";
        public static final String JOB_TERMINATE  = "/v1/jobs/{1}?mode={2}";
        public static final String JOB_RESCALING = "/v1/jobs/{1}/rescaling?parallelism={2}";
        public static final String JOB_EXCEPTIONS = "/v1/jobs/{1}/exceptions";
    }

    public static final class TaskManager{
        private TaskManager() {
        }
        /**
         * flink taskmanager url
         */
        public static final String TM_LIST = "/v1/taskmanagers";
        public static final String TM_DETAIL = "/v1/taskmanagers/{1}";
        public static final String TM_LOG = "/v1/taskmanagers/{1}/log";
        public static final String TM_STDOUT= "/v1/taskmanagers/{1}/stdout";

    }

    public static final class JobManager{
        private JobManager() {
        }
        /**
         * flink jobmanager url
         */
        public static final String JM_CONFIG = "/v1/jobmanager/config";
        public static final String JM_LOG = "/jobmanager/log";
        public static final String JM_STDOUT = "/jobmanager/stdout";

    }

}
