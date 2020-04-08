package com.github.codingdebugallday.client.domain.entity.jobs;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 一般无返回值可用此类
 * </p>
 *
 * @author isacc 2020/04/08 17:43
 * @since 1.0
 */
@NoArgsConstructor
@Data
public class FlinkError {

    private List<String> errors;
}
