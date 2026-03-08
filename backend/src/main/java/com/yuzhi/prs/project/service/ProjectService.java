package com.yuzhi.prs.project.service;

import com.yuzhi.prs.contract.domain.Contract;
import com.yuzhi.prs.customer.domain.Customer;
import com.yuzhi.prs.project.domain.Project;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private final Map<String, Project> projects = new ConcurrentHashMap<>();

    public Project createProject(String projectCode, String projectName, Customer customer, Contract contract) {
        Project project = new Project(
            projectCode,
            projectCode,
            projectName,
            customer,
            contract,
            true,
            true
        );

        projects.put(project.id(), project);
        return project;
    }
}
