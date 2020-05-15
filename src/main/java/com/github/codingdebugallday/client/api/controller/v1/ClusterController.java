package com.github.codingdebugallday.client.api.controller.v1;

import java.util.List;
import java.util.Map;
import javax.validation.Valid;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.codingdebugallday.client.api.dto.ClusterDTO;
import com.github.codingdebugallday.client.app.service.ClusterService;
import com.github.codingdebugallday.client.domain.entity.Cluster;
import com.github.codingdebugallday.client.domain.entity.jobs.*;
import com.github.codingdebugallday.client.domain.entity.overview.DashboardConfiguration;
import com.github.codingdebugallday.client.domain.entity.tm.TaskManagerDetail;
import com.github.codingdebugallday.client.domain.entity.tm.TaskManagerInfo;
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
@RestController("flinkClusterController.v1")
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

    @GetMapping("overview/{clusterCode}/config")
    public DashboardConfiguration overviewConfig(@PathVariable Long tenantId,
                                                 @PathVariable String clusterCode) {
        return clusterService.overviewConfig(tenantId, clusterCode);
    }

    @GetMapping("overview/{clusterCode}")
    public Map<String, Object> overview(@PathVariable Long tenantId,
                                        @PathVariable String clusterCode) {
        return clusterService.overview(tenantId, clusterCode);
    }

    @GetMapping("job/{clusterCode}/overview")
    public JobIdsWithStatusOverview jobList(@PathVariable Long tenantId,
                                            @PathVariable String clusterCode) {
        return clusterService.jobList(tenantId, clusterCode);
    }

    @GetMapping("job/{clusterCode}/details")
    public MultipleJobsDetails jobsDetails(@PathVariable Long tenantId,
                                           @PathVariable String clusterCode) {
        return clusterService.jobsDetails(tenantId, clusterCode);
    }

    @GetMapping("job/{clusterCode}/detail")
    public JobDetailsInfo jobDetail(@PathVariable Long tenantId,
                                    @PathVariable String clusterCode,
                                    String jobId) {
        return clusterService.jobDetail(tenantId, clusterCode, jobId);
    }

    @GetMapping("job/{clusterCode}/yarn-cancel")
    public FlinkApiErrorResponse jobYarnCancel(@PathVariable Long tenantId,
                                               @PathVariable String clusterCode,
                                               String jobId) {
        return clusterService.jobYarnCancel(tenantId, clusterCode, jobId);
    }

    @PostMapping("job/{clusterCode}/cancel-savepoint")
    public TriggerResponseWithSavepoint jobCancelOptionSavepoints(@PathVariable Long tenantId,
                                                     @PathVariable String clusterCode,
                                                     @RequestBody SavepointTriggerRequestBody savepointTriggerRequestBody) {
        return clusterService.jobCancelOptionSavepoints(tenantId, clusterCode, savepointTriggerRequestBody);
    }

    @GetMapping("job/{clusterCode}/terminate")
    public FlinkApiErrorResponse jobTerminate(@PathVariable Long tenantId,
                                              @PathVariable String clusterCode,
                                              String jobId,
                                              @RequestParam(required = false) String mode) {
        return clusterService.jobTerminate(tenantId, clusterCode, jobId, mode);
    }

    @GetMapping("job/{clusterCode}/rescale")
    public TriggerResponse jobRescale(@PathVariable Long tenantId,
                                      @PathVariable String clusterCode,
                                      String jobId,
                                      int parallelism) {
        return clusterService.jobRescale(tenantId, clusterCode, jobId, parallelism);
    }

    @GetMapping("job/{clusterCode}/exception")
    public JobExceptionsInfo jobException(@PathVariable Long tenantId,
                                          @PathVariable String clusterCode,
                                          String jobId,
                                          @RequestParam(required = false) String maxExceptions) {
        return clusterService.jobException(tenantId, clusterCode, jobId, maxExceptions);
    }

    @GetMapping("tm-list/{clusterCode}")
    public TaskManagerInfo taskMangerList(@PathVariable Long tenantId,
                                          @PathVariable String clusterCode) {
        return clusterService.taskMangerList(tenantId, clusterCode);
    }

    @GetMapping("tm-list/{clusterCode}/detail")
    public TaskManagerDetail taskManagerDetail(@PathVariable Long tenantId,
                                               @PathVariable String clusterCode,
                                               String tmId) {
        return clusterService.taskManagerDetail(tenantId, clusterCode, tmId);
    }

    @GetMapping("tm-list/{clusterCode}/log")
    public String taskManagerLog(@PathVariable Long tenantId,
                                 @PathVariable String clusterCode,
                                 String tmId) {
        return clusterService.taskManagerLog(tenantId, clusterCode, tmId);
    }

    @GetMapping("tm-list/{clusterCode}/stdout")
    public String taskManagerStdout(@PathVariable Long tenantId,
                                    @PathVariable String clusterCode,
                                    String tmId) {
        return clusterService.taskManagerStdout(tenantId, clusterCode, tmId);
    }

    @GetMapping("jm/{clusterCode}/config")
    public List<Map<String, String>> jobManagerConfig(@PathVariable Long tenantId,
                                                      @PathVariable String clusterCode) {
        return clusterService.jobManagerConfig(tenantId, clusterCode);
    }

    @GetMapping("jm/{clusterCode}/log")
    public String jobManagerLog(@PathVariable Long tenantId,
                                @PathVariable String clusterCode) {
        return clusterService.jobManagerLog(tenantId, clusterCode);
    }

    @GetMapping("jm/{clusterCode}/stdout")
    public String jobManagerStdout(@PathVariable Long tenantId,
                                   @PathVariable String clusterCode) {
        return clusterService.jobManagerStdout(tenantId, clusterCode);
    }

}
