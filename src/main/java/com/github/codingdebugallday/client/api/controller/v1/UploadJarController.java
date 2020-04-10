package com.github.codingdebugallday.client.api.controller.v1;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.codingdebugallday.client.api.dto.GroupDTO;
import com.github.codingdebugallday.client.api.dto.UploadJarDTO;
import com.github.codingdebugallday.client.app.service.UploadJarService;
import com.github.codingdebugallday.client.domain.entity.UploadJar;
import com.github.codingdebugallday.client.domain.repository.UploadJarRepository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/4/7 10:12
 * @since 1.0
 */
@RestController("flinkUploadJarController.v1")
@RequestMapping("/v1/{tenantId}/upload-jar")
public class UploadJarController {

    private final UploadJarService uploadJarService;
    private final UploadJarRepository uploadJarRepository;

    public UploadJarController(UploadJarService uploadJarService,
                               UploadJarRepository uploadJarRepository) {
        this.uploadJarService = uploadJarService;
        this.uploadJarRepository = uploadJarRepository;
    }

    @GetMapping
    public IPage<UploadJarDTO> list(@PathVariable Long tenantId,
                                    UploadJarDTO uploadJarDTO,
                                    Page<UploadJar> uploadJarPage) {
        uploadJarDTO.setTenantId(tenantId);
        uploadJarPage.addOrder(OrderItem.desc(UploadJar.FIELD_UPLOAD_JAR_ID));
        return uploadJarRepository.pageAndSortDTO(uploadJarDTO, uploadJarPage);
    }

    @GetMapping("/{id}")
    public UploadJarDTO detail(@PathVariable Long tenantId,
                               @PathVariable Long id) {
        return uploadJarRepository.detail(tenantId, id);
    }

    @PostMapping
    public UploadJarDTO upload(@PathVariable Long tenantId,
                               @RequestPart(value = "uploadJarDTO") @Validated(value = GroupDTO.Insert.class) UploadJarDTO uploadJarDTO,
                               @RequestPart(value = "file") MultipartFile multipartFile) {
        uploadJarDTO.setTenantId(tenantId);
        return uploadJarService.upload(uploadJarDTO, multipartFile);
    }

    @PutMapping
    public UploadJarDTO update(@PathVariable Long tenantId,
                               @RequestPart(value = "uploadJarDTO") @Validated(value = GroupDTO.Update.class) UploadJarDTO uploadJarDTO,
                               @RequestPart(value = "file", required = false) MultipartFile multipartFile) {
        uploadJarDTO.setTenantId(tenantId);
        return uploadJarService.update(uploadJarDTO, multipartFile);
    }

    @DeleteMapping
    public void delete(@PathVariable Long tenantId,
                       @RequestBody @Validated(value = GroupDTO.Delete.class) UploadJarDTO uploadJarDTO) {
        uploadJarDTO.setTenantId(tenantId);
        uploadJarService.delete(uploadJarDTO);
    }

}
