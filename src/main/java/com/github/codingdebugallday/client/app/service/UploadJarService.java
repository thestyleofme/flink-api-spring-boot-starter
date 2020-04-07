package com.github.codingdebugallday.client.app.service;

import com.github.codingdebugallday.client.api.dto.UploadJarDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/04/07 10:48
 * @since 1.0
 */
public interface UploadJarService {

    /**
     * 上传jar
     *
     * @param uploadJarDTO  UploadJarDTO
     * @param multipartFile jar file
     * @return com.github.codingdebugallday.client.api.dto.UploadJarDTO
     */
    UploadJarDTO upload(UploadJarDTO uploadJarDTO, MultipartFile multipartFile);

    /**
     * 更新上传的jar
     *
     * @param uploadJarDTO  UploadJarDTO
     * @param multipartFile jar file
     * @return com.github.codingdebugallday.client.api.dto.UploadJarDTO
     */
    UploadJarDTO update(UploadJarDTO uploadJarDTO, MultipartFile multipartFile);

    /**
     * 删除之前通过flink api上传的jar
     *
     * @param uploadJarDTO UploadJarDTO
     */
    void delete(UploadJarDTO uploadJarDTO);
}
