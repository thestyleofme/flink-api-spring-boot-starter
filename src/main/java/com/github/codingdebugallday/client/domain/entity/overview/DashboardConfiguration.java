package com.github.codingdebugallday.client.domain.entity.overview;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * ClusterConfigInfo
 * </p>
 *
 * @author isacc 2020/5/9 11:37
 * @since 1.0
 */
@NoArgsConstructor
@Data
public class DashboardConfiguration {


    /**
     * refresh-interval : 3000
     * timezone-name : China Time
     * timezone-offset : 28800000
     * flink-version : 1.10.0
     * flink-revision : aa4eb8f @ 07.02.2020 @ 19:18:19 CET
     * features : {"web-submit":true}
     */
    @JsonAlias("refresh-interval")
    private int refreshInterval;
    @JsonAlias("timezone-name")
    private String timezoneName;
    @JsonAlias("timezone-offset")
    private int timezoneOffset;
    @JsonAlias("flink-version")
    private String flinkVersion;
    @JsonAlias("flink-revision")
    private String flinkRevision;
    private FeaturesBean features;

    @NoArgsConstructor
    @Data
    public static class FeaturesBean {
        /**
         * web-submit : true
         */
        @JsonAlias("web-submit")
        private boolean webSubmit;
    }
}
