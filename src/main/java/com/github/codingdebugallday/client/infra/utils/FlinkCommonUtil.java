package com.github.codingdebugallday.client.infra.utils;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import com.github.codingdebugallday.client.infra.exceptions.FlinkCommonException;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/04/07 11:18
 * @since 1.0
 */
public class FlinkCommonUtil {

    private FlinkCommonUtil() {
        throw new IllegalStateException("util class");
    }

    public static File multiPartFileToFile(MultipartFile multipartFile) {
        Assert.notNull(multipartFile, "multipartFile must not be null");
        try {
            File toFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            byte[] bytes = FileCopyUtils.copyToByteArray(multipartFile.getInputStream());
            FileCopyUtils.copy(bytes, toFile);
            return toFile;
        } catch (IOException e) {
            throw new FlinkCommonException("multiPartFileToFile error", e);
        }
    }
}
