package com.github.codingdebugallday.client.domain.entity.jobs;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/04/08 17:55
 * @since 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SavepointTriggerRequestBody {

    @JsonAlias({"targetDirectory"})
    @JsonProperty("target-directory")
    private String targetDirectory;
    @JsonAlias({"cancelJob"})
    @JsonProperty("cancel-job")
    @Builder.Default
    private Boolean cancelJob = false;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String jobId;
}
