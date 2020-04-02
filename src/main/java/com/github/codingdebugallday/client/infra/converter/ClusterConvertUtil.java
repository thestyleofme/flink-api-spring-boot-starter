package com.github.codingdebugallday.client.infra.converter;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    private static final Pattern JM_URL_REGEX = Pattern.compile("http://(.*?):(\\d+)");

    private ClusterConvertUtil() {
        throw new IllegalStateException("util class");
    }

    public static Set<String> standbyUrlToSet(String jmStandbyUrl) {
        return Stream.of(jmStandbyUrl.split(";")).collect(Collectors.toSet());
    }

    public static String getJmHost(String jmUrl){
        Matcher matcher = JM_URL_REGEX.matcher(jmUrl);
        if(matcher.find()){
            return matcher.group(1);
        }
        throw new IllegalStateException("flink job manager url not match, http://{ip}:{port}");
    }

    public static Integer getJmPort(String jmUrl){
        Matcher matcher = JM_URL_REGEX.matcher(jmUrl);
        if(matcher.find()){
            return Integer.parseInt(matcher.group(2));
        }
        throw new IllegalStateException("flink job manager url not match, http://{ip}:{port}");
    }

}
