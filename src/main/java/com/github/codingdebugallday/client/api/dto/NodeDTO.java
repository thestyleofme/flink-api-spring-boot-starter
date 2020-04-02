package com.github.codingdebugallday.client.api.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
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
public class NodeDTO implements Serializable {

    private static final long serialVersionUID = 854464206375410197L;

    public static final String FIELD_NODE_ID = "nodeId";

    private Long nodeId;

    private String clusterCode;
    @NotBlank
    private String nodeCode;

    private String nodeDesc;

    /**
     * {
     *     "username": "xxx",
     *     "password": "xxx"
     * }
     */
    @NotBlank
    private String settingInfo;

    private Integer enabledFlag;

    private Long tenantId;
    private Long objectVersionNumber;
    private LocalDateTime creationDate;
    private Long createdBy;
    private LocalDateTime lastUpdateDate;
    private Long lastUpdatedBy;

}
