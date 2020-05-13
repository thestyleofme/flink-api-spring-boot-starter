package com.github.codingdebugallday.client.domain.entity.jobs;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/04/08 15:57
 * @since 1.0
 */
@NoArgsConstructor
@Data
public class JobDetailsInfo {

    private List<String> errors;


    /**
     * jid : 017575ebda37f5f38f24e7bd024b7f7a
     * name : SQL Job
     * isStoppable : false
     * state : RUNNING
     * start-time : 1586315540096
     * end-time : -1
     * duration : 17035407
     * now : 1586332575503
     * timestamps : {"CANCELED":0,"CANCELLING":0,"FAILING":0,"FINISHED":0,"RUNNING":1586315540150,"RECONCILING":0,"RESTARTING":0,"CREATED":1586315540096,"SUSPENDED":0,"FAILED":0}
     * vertices : [{"id":"cbc357ccb763df2852fee8c4fc7d55f2","name":"Source: KafkaTableSource(user_id, item_id, category_id, behavior, ts) -> SourceConversion(table=[default_catalog.default_database.user_log, source: [KafkaTableSource(user_id, item_id, category_id, behavior, ts)]], fields=[user_id, item_id, category_id, behavior, ts]) -> Calc(select=[(ts DATE_FORMAT _UTF-16LE'yyyy-MM-dd HH:00') AS dt, user_id])","parallelism":1,"status":"RUNNING","start-time":1586315541265,"end-time":-1,"duration":17034238,"tasks":{"CANCELING":0,"CREATED":0,"CANCELED":0,"FAILED":0,"FINISHED":0,"SCHEDULED":0,"RECONCILING":0,"DEPLOYING":0,"RUNNING":1},"metrics":{"read-bytes":0,"read-bytes-complete":true,"write-bytes":1736704,"write-bytes-complete":true,"read-records":0,"read-records-complete":true,"write-records":35599,"write-records-complete":true}},{"id":"c27dcf7b54ef6bfd6cff02ca8870b681","name":"GroupAggregate(groupBy=[dt], select=[dt, COUNT(*) AS pv, COUNT(DISTINCT user_id) AS uv]) -> SinkConversionToTuple2 -> Sink: JDBCUpsertTableSink(dt, pv, uv)","parallelism":1,"status":"RUNNING","start-time":1586315541268,"end-time":-1,"duration":17034235,"tasks":{"CANCELING":0,"CREATED":0,"CANCELED":0,"FAILED":0,"FINISHED":0,"SCHEDULED":0,"RECONCILING":0,"DEPLOYING":0,"RUNNING":1},"metrics":{"read-bytes":1744351,"read-bytes-complete":true,"write-bytes":0,"write-bytes-complete":true,"read-records":35599,"read-records-complete":true,"write-records":0,"write-records-complete":true}}]
     * status-counts : {"CANCELING":0,"CREATED":0,"CANCELED":0,"FAILED":0,"FINISHED":0,"SCHEDULED":0,"RECONCILING":0,"DEPLOYING":0,"RUNNING":2}
     * plan : {"jid":"017575ebda37f5f38f24e7bd024b7f7a","name":"SQL Job","nodes":[{"id":"c27dcf7b54ef6bfd6cff02ca8870b681","parallelism":1,"operator":"","operator_strategy":"","description":"GroupAggregate(groupBy=[dt], select=[dt, COUNT(*) AS pv, COUNT(DISTINCT user_id) AS uv]) -&gt; SinkConversionToTuple2 -&gt; Sink: JDBCUpsertTableSink(dt, pv, uv)","inputs":[{"num":0,"id":"cbc357ccb763df2852fee8c4fc7d55f2","ship_strategy":"HASH","exchange":"pipelined_bounded"}],"optimizer_properties":{}},{"id":"cbc357ccb763df2852fee8c4fc7d55f2","parallelism":1,"operator":"","operator_strategy":"","description":"Source: KafkaTableSource(user_id, item_id, category_id, behavior, ts) -&gt; SourceConversion(table=[default_catalog.default_database.user_log, source: [KafkaTableSource(user_id, item_id, category_id, behavior, ts)]], fields=[user_id, item_id, category_id, behavior, ts]) -&gt; Calc(select=[(ts DATE_FORMAT _UTF-16LE'yyyy-MM-dd HH:00') AS dt, user_id])","optimizer_properties":{}}]}
     */

    private String jid;
    private String name;
    private boolean isStoppable;
    private String state;
    @JsonAlias("start-time")
    private long startTime;
    @JsonAlias("end-time")
    private int endTime;
    private int duration;
    private long now;
    private TimestampsBean timestamps;
    @JsonAlias("status-counts")
    private StatusCountsBean statusCounts;
    private PlanBean plan;
    private List<VerticesBean> vertices;

    @NoArgsConstructor
    @Data
    public static class TimestampsBean {
        /**
         * CANCELED : 0
         * CANCELLING : 0
         * FAILING : 0
         * FINISHED : 0
         * RUNNING : 1586315540150
         * RECONCILING : 0
         * RESTARTING : 0
         * CREATED : 1586315540096
         * SUSPENDED : 0
         * FAILED : 0
         */

        @JsonAlias("CANCELED")
        private int canceled;
        @JsonAlias("CANCELLING")
        private int cancelling;
        @JsonAlias("FAILING")
        private int failing;
        @JsonAlias("FINISHED")
        private int finished;
        @JsonAlias("RUNNING")
        private long running;
        @JsonAlias("RECONCILING")
        private int reconciling;
        @JsonAlias("RECONCILING")
        private int restarting;
        @JsonAlias("CREATED")
        private long created;
        @JsonAlias("SUSPENDED")
        private int suspended;
        @JsonAlias("FAILED")
        private int failed;
    }

    @NoArgsConstructor
    @Data
    public static class StatusCountsBean {
        /**
         * CANCELING : 0
         * CREATED : 0
         * CANCELED : 0
         * FAILED : 0
         * FINISHED : 0
         * SCHEDULED : 0
         * RECONCILING : 0
         * DEPLOYING : 0
         * RUNNING : 2
         */

        @JsonAlias("CANCELING")
        private int canceling;
        @JsonAlias("CREATED")
        private int created;
        @JsonAlias("CANCELED")
        private int canceled;
        @JsonAlias("FAILED")
        private int failed;
        @JsonAlias("FINISHED")
        private int finished;
        @JsonAlias("SCHEDULED")
        private int scheduled;
        @JsonAlias("RECONCILING")
        private int reconciling;
        @JsonAlias("DEPLOYING")
        private int deploying;
        @JsonAlias("RUNNING")
        private int running;
    }

    @NoArgsConstructor
    @Data
    public static class PlanBean {
        /**
         * jid : 017575ebda37f5f38f24e7bd024b7f7a
         * name : SQL Job
         * nodes : [{"id":"c27dcf7b54ef6bfd6cff02ca8870b681","parallelism":1,"operator":"","operator_strategy":"","description":"GroupAggregate(groupBy=[dt], select=[dt, COUNT(*) AS pv, COUNT(DISTINCT user_id) AS uv]) -&gt; SinkConversionToTuple2 -&gt; Sink: JDBCUpsertTableSink(dt, pv, uv)","inputs":[{"num":0,"id":"cbc357ccb763df2852fee8c4fc7d55f2","ship_strategy":"HASH","exchange":"pipelined_bounded"}],"optimizer_properties":{}},{"id":"cbc357ccb763df2852fee8c4fc7d55f2","parallelism":1,"operator":"","operator_strategy":"","description":"Source: KafkaTableSource(user_id, item_id, category_id, behavior, ts) -&gt; SourceConversion(table=[default_catalog.default_database.user_log, source: [KafkaTableSource(user_id, item_id, category_id, behavior, ts)]], fields=[user_id, item_id, category_id, behavior, ts]) -&gt; Calc(select=[(ts DATE_FORMAT _UTF-16LE'yyyy-MM-dd HH:00') AS dt, user_id])","optimizer_properties":{}}]
         */

        private String jid;
        private String name;
        private List<NodesBean> nodes;

        @NoArgsConstructor
        @Data
        public static class NodesBean {
            /**
             * id : c27dcf7b54ef6bfd6cff02ca8870b681
             * parallelism : 1
             * operator :
             * operator_strategy :
             * description : GroupAggregate(groupBy=[dt], select=[dt, COUNT(*) AS pv, COUNT(DISTINCT user_id) AS uv]) -&gt; SinkConversionToTuple2 -&gt; Sink: JDBCUpsertTableSink(dt, pv, uv)
             * inputs : [{"num":0,"id":"cbc357ccb763df2852fee8c4fc7d55f2","ship_strategy":"HASH","exchange":"pipelined_bounded"}]
             * optimizer_properties : {}
             */

            private String id;
            private int parallelism;
            private String operator;
            @JsonAlias("operator_strategy")
            private String operatorStrategy;
            private String description;
            @JsonAlias("optimizer_properties")
            private OptimizerPropertiesBean optimizerProperties;
            private List<InputsBean> inputs;

            @NoArgsConstructor
            @Data
            public static class OptimizerPropertiesBean {
            }

            @NoArgsConstructor
            @Data
            public static class InputsBean {
                /**
                 * num : 0
                 * id : cbc357ccb763df2852fee8c4fc7d55f2
                 * ship_strategy : HASH
                 * exchange : pipelined_bounded
                 */

                private int num;
                private String id;
                @JsonAlias("ship_strategy")
                private String shipStrategy;
                private String exchange;
            }
        }
    }

    @NoArgsConstructor
    @Data
    public static class VerticesBean {
        /**
         * id : cbc357ccb763df2852fee8c4fc7d55f2
         * name : Source: KafkaTableSource(user_id, item_id, category_id, behavior, ts) -> SourceConversion(table=[default_catalog.default_database.user_log, source: [KafkaTableSource(user_id, item_id, category_id, behavior, ts)]], fields=[user_id, item_id, category_id, behavior, ts]) -> Calc(select=[(ts DATE_FORMAT _UTF-16LE'yyyy-MM-dd HH:00') AS dt, user_id])
         * parallelism : 1
         * status : RUNNING
         * start-time : 1586315541265
         * end-time : -1
         * duration : 17034238
         * tasks : {"CANCELING":0,"CREATED":0,"CANCELED":0,"FAILED":0,"FINISHED":0,"SCHEDULED":0,"RECONCILING":0,"DEPLOYING":0,"RUNNING":1}
         * metrics : {"read-bytes":0,"read-bytes-complete":true,"write-bytes":1736704,"write-bytes-complete":true,"read-records":0,"read-records-complete":true,"write-records":35599,"write-records-complete":true}
         */

        private String id;
        private String name;
        private int parallelism;
        private String status;
        @JsonAlias("start-time")
        private long startTime;
        @JsonAlias("end-time")
        private int endTime;
        private int duration;
        private TasksBean tasks;
        private MetricsBean metrics;

        @NoArgsConstructor
        @Data
        public static class TasksBean {
            /**
             * CANCELING : 0
             * CREATED : 0
             * CANCELED : 0
             * FAILED : 0
             * FINISHED : 0
             * SCHEDULED : 0
             * RECONCILING : 0
             * DEPLOYING : 0
             * RUNNING : 1
             */

            @JsonAlias("CANCELING")
            private int canceling;
            @JsonAlias("CREATED")
            private int created;
            @JsonAlias("CANCELED")
            private int canceled;
            @JsonAlias("FAILED")
            private int failed;
            @JsonAlias("FINISHED")
            private int finished;
            @JsonAlias("SCHEDULED")
            private int scheduled;
            @JsonAlias("RECONCILING")
            private int reconciling;
            @JsonAlias("DEPLOYING")
            private int deploying;
            @JsonAlias("RUNNING")
            private int running;
        }

        @NoArgsConstructor
        @Data
        public static class MetricsBean {
            /**
             * read-bytes : 0
             * read-bytes-complete : true
             * write-bytes : 1736704
             * write-bytes-complete : true
             * read-records : 0
             * read-records-complete : true
             * write-records : 35599
             * write-records-complete : true
             */

            @JsonAlias("read-bytes")
            private int readBytes;
            @JsonAlias("read-bytes-complete")
            private boolean readBytesComplete;
            @JsonAlias("write-bytes")
            private int writeBytes;
            @JsonAlias("write-bytes-complete")
            private boolean writeBytesComplete;
            @JsonAlias("read-records")
            private int readRecords;
            @JsonAlias("read-records-complete")
            private boolean readRecordsComplete;
            @JsonAlias("write-records")
            private int writeRecords;
            @JsonAlias("write-records-complete")
            private boolean writeRecordsComplete;
        }
    }
}
