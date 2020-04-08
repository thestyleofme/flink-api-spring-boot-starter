package com.github.codingdebugallday.client.domain.entity.jobs;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/04/08 11:49
 * @since 1.0
 */
@NoArgsConstructor
@Data
public class JobIdsWithStatusOverview {


    private List<JobIdWithStatus> jobs;
    private List<String> errors;

    @NoArgsConstructor
    @Data
    public static class JobIdWithStatus {
        /**
         * id : 017575ebda37f5f38f24e7bd024b7f7a
         * status : RUNNING
         */

        private String id;
        private String status;
    }
}
