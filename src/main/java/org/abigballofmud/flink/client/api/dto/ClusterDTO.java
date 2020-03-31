package org.abigballofmud.flink.client.api.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/03/25 17:51
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClusterDTO implements Serializable {

    private static final long serialVersionUID = 854464206375410197L;

    public static final String FIELD_CLUSTER_ID = "clusterId";

    private Long clusterId;

    @NotBlank
    private String clusterCode;

    private String clusterDesc;
    @NotBlank
    private String jobManagerUrl;

    /**
     * 若配置了Ha，这里是备用的jm，逗号分割
     */
    private String jobManagerStandbyUrl;

    private Integer enabledFlag;

    private Long tenantId;
    private Long objectVersionNumber;
    private LocalDateTime creationDate;
    private Long createdBy;
    private LocalDateTime lastUpdateDate;
    private Long lastUpdatedBy;

    @Transient
    private Set<String> jobManagerStandbyUrlSet;

}
