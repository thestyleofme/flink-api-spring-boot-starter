package com.github.codingdebugallday.client.domain.entity.jobs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * <p>
 * TriggerResponseWithSavepoint
 * </p>
 *
 * @author isacc 2020/5/15 16:41
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TriggerResponseWithSavepoint {

    private TriggerResponse triggerResponse;

    private SavepointInfo savepointInfo;

}
