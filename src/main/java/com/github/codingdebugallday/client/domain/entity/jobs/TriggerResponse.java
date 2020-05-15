package com.github.codingdebugallday.client.domain.entity.jobs;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/04/08 18:20
 * @since 1.0
 */
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TriggerResponse {

    private List<String> errors;

    /**
     * request-id : 09378564653658d04a3a21766d6054ff
     */
    @JsonAlias("request-id")
    private String requestId;
}
