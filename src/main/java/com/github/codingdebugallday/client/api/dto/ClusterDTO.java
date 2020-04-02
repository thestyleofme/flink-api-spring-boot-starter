package com.github.codingdebugallday.client.api.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

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

    public static final String FIELD_CLUSTER_ID = "cluster_id";

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

    //===========other===========

    @Transient
    private Set<String> jobManagerStandbyUrlSet;
    @Transient
    private String host;
    @Transient
    private Integer port;
    @Transient
    @Valid
    private List<NodeDTO> nodeDTOList;

}
