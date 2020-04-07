package com.github.codingdebugallday.client.infra.repository.impl;

import java.util.Optional;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.codingdebugallday.client.api.dto.UploadJarDTO;
import com.github.codingdebugallday.client.domain.entity.UploadJar;
import com.github.codingdebugallday.client.domain.repository.UploadJarRepository;
import com.github.codingdebugallday.client.infra.converter.UploadJarConvertMapper;
import com.github.codingdebugallday.client.infra.exceptions.FlinkCommonException;
import com.github.codingdebugallday.client.infra.mapper.UploadJarMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/04/07 10:45
 * @since 1.0
 */
@Component
public class UploadJarRepositoryImpl implements UploadJarRepository {

    private final UploadJarMapper uploadJarMapper;

    public UploadJarRepositoryImpl(UploadJarMapper uploadJarMapper) {
        this.uploadJarMapper = uploadJarMapper;
    }

    @Override
    public IPage<UploadJarDTO> pageAndSortDTO(UploadJarDTO uploadJarDTO, Page<UploadJar> uploadJarPage) {
        final QueryWrapper<UploadJar> queryWrapper = this.commonQueryWrapper(uploadJarDTO);
        Page<UploadJar> entityPage = uploadJarMapper.selectPage(uploadJarPage, queryWrapper);
        final Page<UploadJarDTO> dtoPage = new Page<>();
        BeanUtils.copyProperties(entityPage, dtoPage);
        dtoPage.setRecords(entityPage.getRecords().stream()
                .map(UploadJarConvertMapper.INSTANCE::entityToDTO)
                .collect(Collectors.toList()));
        return dtoPage;
    }

    @Override
    public UploadJarDTO detail(Long tenantId, Long uploadJarId) {
        UploadJar uploadJar = uploadJarMapper.selectOne(detailWrapper(tenantId, uploadJarId));
        return UploadJarConvertMapper.INSTANCE.entityToDTO(uploadJar);
    }

    @Override
    public UploadJarDTO findMaxVersionJarByCode(String jarCode, String clusterCode, Long tenantId) {
        final QueryWrapper<UploadJar> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(UploadJar.FIELD_TENANT_ID, tenantId);
        queryWrapper.eq(UploadJar.FIELD_CLUSTER_CODE, clusterCode);
        queryWrapper.eq(UploadJar.FIELD_JAR_CODE, jarCode);
        Optional<UploadJar> max = uploadJarMapper.selectList(queryWrapper).stream()
                .max((o1, o2) -> o1.getVersion().compareToIgnoreCase(o2.getVersion()));
        if (!max.isPresent()) {
            throw new FlinkCommonException("can not find upload jar");
        }
        return UploadJarConvertMapper.INSTANCE.entityToDTO(max.get());
    }

    private QueryWrapper<UploadJar> detailWrapper(Long tenantId, Long uploadJarId) {
        final QueryWrapper<UploadJar> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(UploadJar.FIELD_TENANT_ID, tenantId);
        queryWrapper.eq(UploadJar.FIELD_UPLOAD_JAR_ID, uploadJarId);
        return queryWrapper;
    }

    private QueryWrapper<UploadJar> commonQueryWrapper(UploadJarDTO uploadJarDTO) {
        final QueryWrapper<UploadJar> queryWrapper = new QueryWrapper<>();
        Optional.ofNullable(uploadJarDTO.getClusterCode())
                .ifPresent(s -> queryWrapper.or().like(UploadJar.FIELD_CLUSTER_CODE, s));
        Optional.ofNullable(uploadJarDTO.getJarCode())
                .ifPresent(s -> queryWrapper.or().like(UploadJar.FIELD_JAR_CODE, s));
        Optional.ofNullable(uploadJarDTO.getSystemProvided())
                .ifPresent(s -> queryWrapper.or().eq(UploadJar.FIELD_SYSTEM_PROVIDED, s));
        Optional.ofNullable(uploadJarDTO.getFilename())
                .ifPresent(s -> queryWrapper.or().like(UploadJar.FIELD_FILENAME, s));
        queryWrapper.eq(UploadJar.FIELD_TENANT_ID, uploadJarDTO.getTenantId());
        return queryWrapper;
    }
}
