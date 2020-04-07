package com.github.codingdebugallday.client.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
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
@TableName(value = "flink_upload_jar")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UploadJar implements Serializable {

    private static final long serialVersionUID = 5997028378766205793L;

    public static final String FIELD_UPLOAD_JAR_ID = "UPLOAD_JAR_ID";
    public static final String FIELD_TENANT_ID = "tenant_id";
    public static final String FIELD_CLUSTER_CODE = "cluster_code";
    public static final String FIELD_JAR_CODE = "jar_code";
    public static final String FIELD_SYSTEM_PROVIDED = "system_provided";
    public static final String FIELD_FILENAME = "filename";

    @TableId(type = IdType.AUTO)
    private Long uploadJarId;

    private String jarCode;
    private String clusterCode;

    private String jarDesc;
    private String version;
    private String entryClass;

    /**
     * 是否是系统提供的（平台预先上传jar做为平台功能使用）
     */
    private Integer systemProvided;

    private String filename;
    private String jarName;
    private String status;

    private Long tenantId;
    @Version
    private Long objectVersionNumber;
    private LocalDateTime creationDate;
    private Long createdBy;
    private LocalDateTime lastUpdateDate;
    private Long lastUpdatedBy;

}
