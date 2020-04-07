package com.github.codingdebugallday.client.domain.entity.jars;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/03/27 14:04
 * @since 1.0
 */
@NoArgsConstructor
@Data
public class JarRunResponseBody {


    /**
     * jobid : c1fdcb57789886ac0b2c52a67d0083cc
     */

    private String jobid;
    private List<String> errors;
}
