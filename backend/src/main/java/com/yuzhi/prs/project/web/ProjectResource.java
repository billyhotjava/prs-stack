package com.yuzhi.prs.project.web;

import com.yuzhi.prs.contract.domain.Contract;
import com.yuzhi.prs.customer.domain.Customer;
import com.yuzhi.prs.project.domain.Project;
import com.yuzhi.prs.project.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projects")
public class ProjectResource {

    private final ProjectService projectService;

    public ProjectResource(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Project createProject(@RequestBody CreateProjectRequest request) {
        Customer customer = new Customer(request.customerId(), request.customerName());
        Contract contract = new Contract(request.contractId(), request.contractCode(), request.customerId());
        return projectService.createProject(request.projectCode(), request.projectName(), customer, contract);
    }

    public record CreateProjectRequest(
        String projectCode,
        String projectName,
        String customerId,
        String customerName,
        String contractId,
        String contractCode
    ) {}
}
