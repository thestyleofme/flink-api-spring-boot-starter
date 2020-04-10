package com.github.codingdebugallday.client.app.service.impl;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import javax.annotation.Resource;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.codingdebugallday.client.api.dto.UploadJarDTO;
import com.github.codingdebugallday.client.app.service.FlinkApi;
import com.github.codingdebugallday.client.app.service.UploadJarService;
import com.github.codingdebugallday.client.domain.entity.UploadJar;
import com.github.codingdebugallday.client.domain.entity.jars.JarUploadResponseBody;
import com.github.codingdebugallday.client.infra.context.FlinkApiContext;
import com.github.codingdebugallday.client.infra.converter.UploadJarConvertMapper;
import com.github.codingdebugallday.client.infra.mapper.UploadJarMapper;
import com.github.codingdebugallday.client.infra.utils.FlinkCommonUtil;
import com.github.codingdebugallday.client.infra.utils.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/04/07 11:02
 * @since 1.0
 */
@Service("flinkUploadJarService")
@Slf4j
public class UploadJarServiceImpl extends ServiceImpl<UploadJarMapper, UploadJar> implements UploadJarService {

    private final ExecutorService executorService = ThreadPoolUtil.getExecutorService();

    @Resource
    private FlinkApiContext flinkApiContext;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UploadJarDTO update(UploadJarDTO uploadJarDTO, MultipartFile multipartFile) {
        // 更新
        UploadJar uploadJar = UploadJarConvertMapper.INSTANCE.dtoToEntity(uploadJarDTO);
        updateById(uploadJar);
        // 是否需要替换jar包
        Optional.ofNullable(multipartFile).ifPresent(jarFile -> {
            Assert.notNull(uploadJarDTO.getFilename(), "overwrite jar filename cannot be null");
            FlinkApi flinkApi = flinkApiContext.get(uploadJarDTO.getClusterCode(), uploadJarDTO.getTenantId());
            // 先删除以前上传的jar
            flinkApi.deleteJar(uploadJar.getFilename());
            // 重新上传并更新
            uploadJarAndUpdateAsync(flinkApi, uploadJar, multipartFile);
        });
        return UploadJarConvertMapper.INSTANCE.entityToDTO(uploadJar);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(UploadJarDTO uploadJarDTO) {
        // 删flink jar
        FlinkApi flinkApi = flinkApiContext.get(uploadJarDTO.getClusterCode(), uploadJarDTO.getTenantId());
        flinkApi.deleteJar(uploadJarDTO.getJarName());
        // 删表
        UploadJar uploadJar = UploadJarConvertMapper.INSTANCE.dtoToEntity(uploadJarDTO);
        this.removeById(uploadJar.getUploadJarId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UploadJarDTO upload(UploadJarDTO uploadJarDTO, MultipartFile multipartFile) {
        // 插表
        UploadJar uploadJar = UploadJarConvertMapper.INSTANCE.dtoToEntity(uploadJarDTO);
        save(uploadJar);
        // 调用api上传jar
        FlinkApi flinkApi = flinkApiContext.get(uploadJarDTO.getClusterCode(), uploadJarDTO.getTenantId());
        uploadJarAndUpdateAsync(flinkApi, uploadJar, multipartFile);
        return UploadJarConvertMapper.INSTANCE.entityToDTO(uploadJar);
    }

    private void uploadJarAndUpdateAsync(FlinkApi flinkApi, UploadJar uploadJar, MultipartFile multipartFile) {
        // 调用api上传jar
        CompletableFuture<JarUploadResponseBody> completableFuture = CompletableFuture.supplyAsync(() ->
                flinkApi.uploadJar(FlinkCommonUtil.multiPartFileToFile(multipartFile)), executorService);
        completableFuture.whenComplete((t, u) -> {
            // 回写上传后结果
            log.debug("jar upload response: {}", t);
            // 乐观锁去更新
            UploadJar entity = getById(uploadJar.getUploadJarId());
            String filename = t.getFilename();
            entity.setFilename(filename);
            entity.setStatus(t.getStatus());
            entity.setJarName(filename.substring(filename.lastIndexOf('/') + 1));
            this.updateById(entity);
        }).exceptionally(e -> {
            log.error("error: {}", e.getMessage());
            return null;
        });
    }

}
