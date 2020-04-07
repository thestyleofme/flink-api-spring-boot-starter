package com.github.codingdebugallday.client.api.controller.v1;

import javax.validation.Valid;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.codingdebugallday.client.api.dto.ClusterDTO;
import com.github.codingdebugallday.client.app.service.ClusterService;
import com.github.codingdebugallday.client.domain.entity.Cluster;
import com.github.codingdebugallday.client.domain.repository.ClusterRepository;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2020/03/28 1:14
 * @since 1.0
 */
@RestController("clusterController.v1")
@RequestMapping("/v1/{tenantId}/cluster")
public class ClusterController {

    private final ClusterRepository clusterRepository;
    private final ClusterService clusterService;

    public ClusterController(ClusterRepository clusterRepository,
                             ClusterService clusterService) {
        this.clusterRepository = clusterRepository;
        this.clusterService = clusterService;
    }

    @GetMapping
    public IPage<ClusterDTO> list(@PathVariable Long tenantId,
                                  ClusterDTO clusterDTO,
                                  Page<Cluster> clusterPage) {
        clusterDTO.setTenantId(tenantId);
        clusterPage.addOrder(OrderItem.desc(Cluster.FIELD_CLUSTER_ID));
        return clusterRepository.pageAndSortDTO(clusterDTO, clusterPage);
    }

    @GetMapping("/{clusterId}")
    public ClusterDTO detail(@PathVariable Long tenantId,
                             @PathVariable Long clusterId) {
        return clusterRepository.detail(tenantId, clusterId);
    }

    @PostMapping
    public ClusterDTO insert(@PathVariable Long tenantId,
                             @RequestBody @Valid ClusterDTO clusterDTO) {
        clusterDTO.setTenantId(tenantId);
        return clusterService.insert(clusterDTO);
    }

    @PutMapping
    public ClusterDTO update(@PathVariable Long tenantId,
                             @RequestBody @Valid ClusterDTO clusterDTO) {
        clusterDTO.setTenantId(tenantId);
        return clusterService.update(clusterDTO);
    }

    @DeleteMapping
    public void delete(@PathVariable Long tenantId,
                       @RequestBody ClusterDTO clusterDTO) {
        clusterDTO.setTenantId(tenantId);
        clusterService.delete(clusterDTO);
    }

}
