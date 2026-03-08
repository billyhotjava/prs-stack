package com.yuzhi.prs.finance.service;

import com.yuzhi.prs.finance.domain.AdvanceFact;
import com.yuzhi.prs.finance.domain.CollectionFact;
import com.yuzhi.prs.finance.domain.FactSourceType;
import com.yuzhi.prs.finance.domain.InvoiceFact;
import com.yuzhi.prs.finance.domain.ProjectFinanceFact;
import com.yuzhi.prs.finance.domain.ReimbursementFact;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BusinessFinanceFactService {

    private final List<InvoiceFact> invoiceFacts = new ArrayList<>();
    private final List<CollectionFact> collectionFacts = new ArrayList<>();
    private final List<ReimbursementFact> reimbursementFacts = new ArrayList<>();
    private final List<AdvanceFact> advanceFacts = new ArrayList<>();
    private final ProjectFinanceFactService projectFinanceFactService;

    public BusinessFinanceFactService(ProjectFinanceFactService projectFinanceFactService) {
        this.projectFinanceFactService = projectFinanceFactService;
    }

    public InvoiceFact recordInvoice(InvoiceFact invoiceFact) {
        invoiceFacts.add(invoiceFact);
        projectFinanceFactService.recordFact(new ProjectFinanceFact(
            "FACT-" + invoiceFact.id(),
            invoiceFact.projectId(),
            invoiceFact.accountingPeriod(),
            "invoice",
            FactSourceType.INVOICE,
            invoiceFact.id(),
            invoiceFact.amount(),
            invoiceFact.currency(),
            "finance"
        ));
        return invoiceFact;
    }

    public CollectionFact recordCollection(CollectionFact collectionFact) {
        collectionFacts.add(collectionFact);
        projectFinanceFactService.recordFact(new ProjectFinanceFact(
            "FACT-" + collectionFact.id(),
            collectionFact.projectId(),
            collectionFact.accountingPeriod(),
            "collection",
            FactSourceType.COLLECTION,
            collectionFact.id(),
            collectionFact.amount(),
            collectionFact.currency(),
            "finance"
        ));
        return collectionFact;
    }

    public ReimbursementFact recordReimbursement(ReimbursementFact reimbursementFact) {
        reimbursementFacts.add(reimbursementFact);
        projectFinanceFactService.recordFact(new ProjectFinanceFact(
            "FACT-" + reimbursementFact.id(),
            reimbursementFact.projectId(),
            reimbursementFact.accountingPeriod(),
            "reimbursement",
            FactSourceType.REIMBURSEMENT,
            reimbursementFact.id(),
            reimbursementFact.amount(),
            reimbursementFact.currency(),
            "finance"
        ));
        return reimbursementFact;
    }

    public AdvanceFact recordAdvance(AdvanceFact advanceFact) {
        advanceFacts.add(advanceFact);
        projectFinanceFactService.recordFact(new ProjectFinanceFact(
            "FACT-" + advanceFact.id(),
            advanceFact.projectId(),
            advanceFact.accountingPeriod(),
            "advance",
            FactSourceType.ADVANCE,
            advanceFact.id(),
            advanceFact.amount(),
            advanceFact.currency(),
            "finance"
        ));
        return advanceFact;
    }

    public ProjectFinanceView buildProjectView(String projectId) {
        return new ProjectFinanceView(
            invoiceFacts.stream().filter(fact -> fact.projectId().equals(projectId)).sorted(Comparator.comparing(InvoiceFact::accountingPeriod)).toList(),
            collectionFacts.stream().filter(fact -> fact.projectId().equals(projectId)).sorted(Comparator.comparing(CollectionFact::accountingPeriod)).toList(),
            reimbursementFacts.stream().filter(fact -> fact.projectId().equals(projectId)).sorted(Comparator.comparing(ReimbursementFact::accountingPeriod)).toList(),
            advanceFacts.stream().filter(fact -> fact.projectId().equals(projectId)).sorted(Comparator.comparing(AdvanceFact::accountingPeriod)).toList(),
            projectFinanceFactService.listProjectFacts(projectId)
        );
    }

    public record ProjectFinanceView(
        List<InvoiceFact> invoiceFacts,
        List<CollectionFact> collectionFacts,
        List<ReimbursementFact> reimbursementFacts,
        List<AdvanceFact> advanceFacts,
        List<ProjectFinanceFact> ledgerFacts
    ) {}
}
