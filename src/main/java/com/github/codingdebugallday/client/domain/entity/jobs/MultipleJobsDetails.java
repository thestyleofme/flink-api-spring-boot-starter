package com.github.codingdebugallday.client.domain.entity.jobs;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/04/08 15:17
 * @since 1.0
 */
@NoArgsConstructor
@Data
public class MultipleJobsDetails {


    private List<JobDetail> jobs;
    private List<String> errors;

    @NoArgsConstructor
    @Data
    public static class JobDetail {
        /**
         * jid : 017575ebda37f5f38f24e7bd024b7f7a
         * name : SQL Job
         * state : RUNNING
         * start-time : 1586315540096
         * end-time : -1
         * duration : 14590420
         * last-modification : 1586315543790
         * tasks : {"total":2,"created":0,"scheduled":0,"deploying":0,"running":2,"finished":0,"canceling":0,"canceled":0,"failed":0,"reconciling":0}
         */

        private String jid;
        private String name;
        private String state;
        private long starttime;
        private int endtime;
        private int duration;
        private long lastmodification;
        private Tasks tasks;

        @NoArgsConstructor
        @Data
        public static class Tasks {
            /**
             * total : 2
             * created : 0
             * scheduled : 0
             * deploying : 0
             * running : 2
             * finished : 0
             * canceling : 0
             * canceled : 0
             * failed : 0
             * reconciling : 0
             */

            private int total;
            private int created;
            private int scheduled;
            private int deploying;
            private int running;
            private int finished;
            private int canceling;
            private int canceled;
            private int failed;
            private int reconciling;
        }
    }
}
