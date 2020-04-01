package com.github.codingdebugallday.client.infra.converter;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/04/01 10:27
 * @since 1.0
 */
public class ClusterConvertUtil {

    private ClusterConvertUtil() {
        throw new IllegalStateException("util class");
    }

    public static Set<String> standbyUrlToSet(String jmStandbyUrl) {
        return Stream.of(jmStandbyUrl.split(";")).collect(Collectors.toSet());
    }
}
