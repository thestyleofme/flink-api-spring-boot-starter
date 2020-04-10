package com.github.codingdebugallday.client.domain.entity.tm;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/04/10 14:56
 * @since 1.0
 */
@NoArgsConstructor
@Data
public class TaskManagerDetail {


    /**
     * id : 136083a20c8d0dae2182320fc972179a
     * path : akka.tcp://flink@192.168.12.246:40418/user/taskmanager_0
     * dataPort : 34598
     * timeSinceLastHeartbeat : 1586501033738
     * slotsNumber : 3
     * freeSlots : 2
     * hardware : {"cpuCores":4,"physicalMemory":16825131008,"freeMemory":1749024768,"managedMemory":1505922928}
     * metrics : {"heapUsed":542676184,"heapCommitted":1749024768,"heapMax":1749024768,"nonHeapUsed":108611256,"nonHeapCommitted":112218112,"nonHeapMax":444596224,"directCount":11512,"directUsed":379639749,"directMax":379639747,"mappedCount":0,"mappedUsed":0,"mappedMax":0,"memorySegmentsAvailable":11479,"memorySegmentsTotal":11489,"garbageCollectors":[{"name":"G1_Young_Generation","count":47,"time":8794},{"name":"G1_Old_Generation","count":0,"time":0}]}
     */

    private String id;
    private String path;
    private int dataPort;
    private long timeSinceLastHeartbeat;
    private int slotsNumber;
    private int freeSlots;
    private HardwareBean hardware;
    private MetricsBean metrics;

    @NoArgsConstructor
    @Data
    public static class HardwareBean {
        /**
         * cpuCores : 4
         * physicalMemory : 16825131008
         * freeMemory : 1749024768
         * managedMemory : 1505922928
         */

        private int cpuCores;
        private long physicalMemory;
        private int freeMemory;
        private int managedMemory;
    }

    @NoArgsConstructor
    @Data
    public static class MetricsBean {
        /**
         * heapUsed : 542676184
         * heapCommitted : 1749024768
         * heapMax : 1749024768
         * nonHeapUsed : 108611256
         * nonHeapCommitted : 112218112
         * nonHeapMax : 444596224
         * directCount : 11512
         * directUsed : 379639749
         * directMax : 379639747
         * mappedCount : 0
         * mappedUsed : 0
         * mappedMax : 0
         * memorySegmentsAvailable : 11479
         * memorySegmentsTotal : 11489
         * garbageCollectors : [{"name":"G1_Young_Generation","count":47,"time":8794},{"name":"G1_Old_Generation","count":0,"time":0}]
         */

        private int heapUsed;
        private int heapCommitted;
        private int heapMax;
        private int nonHeapUsed;
        private int nonHeapCommitted;
        private int nonHeapMax;
        private int directCount;
        private int directUsed;
        private int directMax;
        private int mappedCount;
        private int mappedUsed;
        private int mappedMax;
        private int memorySegmentsAvailable;
        private int memorySegmentsTotal;
        private List<GarbageCollectorsBean> garbageCollectors;

        @NoArgsConstructor
        @Data
        public static class GarbageCollectorsBean {
            /**
             * name : G1_Young_Generation
             * count : 47
             * time : 8794
             */

            private String name;
            private int count;
            private int time;
        }
    }
}
