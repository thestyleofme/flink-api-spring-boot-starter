package com.github.codingdebugallday.client.domain.entity.tm;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/04/09 17:41
 * @since 1.0
 */
@NoArgsConstructor
@Data
public class TaskManagerInfo {


    private List<TaskmanagersBean> taskmanagers;

    @NoArgsConstructor
    @Data
    public static class TaskmanagersBean {
        /**
         * id : 136083a20c8d0dae2182320fc972179a
         * path : akka.tcp://flink@192.168.12.246:40418/user/taskmanager_0
         * dataPort : 34598
         * timeSinceLastHeartbeat : 1586424961277
         * slotsNumber : 3
         * freeSlots : 2
         * hardware : {"cpuCores":4,"physicalMemory":16825131008,"freeMemory":1749024768,"managedMemory":1505922928}
         */

        private String id;
        private String path;
        private int dataPort;
        private long timeSinceLastHeartbeat;
        private int slotsNumber;
        private int freeSlots;
        private HardwareBean hardware;

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
    }
}
