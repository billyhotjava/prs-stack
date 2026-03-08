package com.yuzhi.prs.finance.service;

import com.yuzhi.prs.finance.domain.ProjectFinanceFact;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class ProjectFinanceFactService {

    private final Map<String, ProjectFinanceFact> factsByKey = new ConcurrentHashMap<>();

    public ProjectFinanceFact recordFact(ProjectFinanceFact fact) {
        factsByKey.put(fact.reconciliationKey(), fact);
        return fact;
    }

    public List<ProjectFinanceFact> listProjectFacts(String projectId) {
        return factsByKey.values().stream()
            .filter(fact -> fact.projectId().equals(projectId))
            .sorted(Comparator.comparing(ProjectFinanceFact::accountingPeriod).thenComparing(ProjectFinanceFact::factType))
            .toList();
    }

    public List<ProjectFinanceFact> listAllFacts() {
        return factsByKey.values().stream()
            .sorted(Comparator.comparing(ProjectFinanceFact::projectId).thenComparing(ProjectFinanceFact::accountingPeriod))
            .toList();
    }
}
