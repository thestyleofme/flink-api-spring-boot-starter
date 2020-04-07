package com.github.codingdebugallday.client.domain.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.codingdebugallday.client.api.dto.UploadJarDTO;
import com.github.codingdebugallday.client.domain.entity.UploadJar;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/04/07 10:45
 * @since 1.0
 */
public interface UploadJarRepository {

    /**
     * 分页条件查询flink上传的jar
     *
     * @param uploadJarDTO  UploadJarDTO
     * @param uploadJarPage Page<UploadJar>
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.github.codingdebugallday.client.api.dto.UploadJarDTO>
     */
    IPage<UploadJarDTO> pageAndSortDTO(UploadJarDTO uploadJarDTO, Page<UploadJar> uploadJarPage);

    /**
     * 详细查询上传jar的信息
     *
     * @param tenantId    租户id
     * @param uploadJarId id
     * @return com.github.codingdebugallday.client.api.dto.UploadJarDTO
     */
    UploadJarDTO detail(Long tenantId, Long uploadJarId);

    /**
     * 查找该集群下最新版本的jar
     *
     * @param jarCode     jarCode
     * @param clusterCode clusterCode
     * @param tenantId    租户id
     * @return com.github.codingdebugallday.client.api.dto.UploadJarDTO
     */
    UploadJarDTO findMaxVersionJarByCode(String jarCode, String clusterCode, Long tenantId);
}
