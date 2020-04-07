package com.github.codingdebugallday.client.api.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/4/7 10:12
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UploadJarDTO implements Serializable {


    private static final long serialVersionUID = 7138278817927514343L;

    public static final String FIELD_UPLOAD_JAR_ID = "UPLOAD_JAR_ID";
    @NotNull(groups = {GroupDTO.Update.class, GroupDTO.Delete.class})
    private Long uploadJarId;

    @NotBlank(groups = GroupDTO.Insert.class)
    private String jarCode;
    @NotBlank(groups = {GroupDTO.Insert.class, GroupDTO.Update.class, GroupDTO.Delete.class})
    private String clusterCode;

    private String jarDesc;
    @NotBlank(groups = GroupDTO.Insert.class)
    private String version;
    private String entryClass;

    /**
     * 是否是系统提供的（平台预先上传jar做为平台功能使用）
     */
    @NotNull(groups = GroupDTO.Insert.class)
    private Integer systemProvided;

    private String filename;
    @NotBlank(groups = GroupDTO.Delete.class)
    private String jarName;
    private String status;

    private Long tenantId;
    @NotBlank(groups = {GroupDTO.Update.class})
    private Long objectVersionNumber;
    private LocalDateTime creationDate;
    private Long createdBy;
    private LocalDateTime lastUpdateDate;
    private Long lastUpdatedBy;

}

