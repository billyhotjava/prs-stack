package com.yuzhi.prs.finance.web;

import com.yuzhi.prs.audit.AuditEventService;
import com.yuzhi.prs.finance.domain.AdvanceFact;
import com.yuzhi.prs.finance.domain.CollectionFact;
import com.yuzhi.prs.finance.domain.InvoiceFact;
import com.yuzhi.prs.finance.domain.ReimbursementFact;
import com.yuzhi.prs.finance.service.BusinessFinanceFactService;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/finance/facts")
public class FinanceFactResource {

    private final BusinessFinanceFactService businessFinanceFactService;
    private final AuditEventService auditEventService;

    public FinanceFactResource(
        BusinessFinanceFactService businessFinanceFactService,
        AuditEventService auditEventService
    ) {
        this.businessFinanceFactService = businessFinanceFactService;
        this.auditEventService = auditEventService;
    }

    @PostMapping("/invoice")
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceFact recordInvoice(@RequestBody InvoiceFactRequest request) {
        InvoiceFact fact = businessFinanceFactService.recordInvoice(new InvoiceFact(
            request.factId(),
            request.projectId(),
            YearMonth.parse(request.accountingPeriod()),
            request.amount(),
            request.currency(),
            request.invoiceNumber(),
            request.customerName()
        ));
        auditEventService.recordCurrentUser("finance.invoice-recorded", "invoice-fact", fact.id(), Map.of(
            "projectId", fact.projectId(),
            "amount", fact.amount(),
            "currency", fact.currency()
        ));
        return fact;
    }

    @PostMapping("/collection")
    @ResponseStatus(HttpStatus.CREATED)
    public CollectionFact recordCollection(@RequestBody CollectionFactRequest request) {
        CollectionFact fact = businessFinanceFactService.recordCollection(new CollectionFact(
            request.factId(),
            request.projectId(),
            YearMonth.parse(request.accountingPeriod()),
            request.amount(),
            request.currency(),
            request.payerName(),
            request.referenceNumber()
        ));
        auditEventService.recordCurrentUser("finance.collection-recorded", "collection-fact", fact.id(), Map.of(
            "projectId", fact.projectId(),
            "amount", fact.amount(),
            "currency", fact.currency()
        ));
        return fact;
    }

    @PostMapping("/reimbursement")
    @ResponseStatus(HttpStatus.CREATED)
    public ReimbursementFact recordReimbursement(@RequestBody ReimbursementFactRequest request) {
        ReimbursementFact fact = businessFinanceFactService.recordReimbursement(new ReimbursementFact(
            request.factId(),
            request.projectId(),
            YearMonth.parse(request.accountingPeriod()),
            request.amount(),
            request.currency(),
            request.payeeName(),
            request.expenseType()
        ));
        auditEventService.recordCurrentUser("finance.reimbursement-recorded", "reimbursement-fact", fact.id(), Map.of(
            "projectId", fact.projectId(),
            "amount", fact.amount(),
            "currency", fact.currency()
        ));
        return fact;
    }

    @PostMapping("/advance")
    @ResponseStatus(HttpStatus.CREATED)
    public AdvanceFact recordAdvance(@RequestBody AdvanceFactRequest request) {
        AdvanceFact fact = businessFinanceFactService.recordAdvance(new AdvanceFact(
            request.factId(),
            request.projectId(),
            YearMonth.parse(request.accountingPeriod()),
            request.amount(),
            request.currency(),
            request.receiverName(),
            request.purpose()
        ));
        auditEventService.recordCurrentUser("finance.advance-recorded", "advance-fact", fact.id(), Map.of(
            "projectId", fact.projectId(),
            "amount", fact.amount(),
            "currency", fact.currency()
        ));
        return fact;
    }

    @GetMapping("/projects/{projectId}")
    public BusinessFinanceFactService.ProjectFinanceView getProjectFinanceView(@PathVariable String projectId) {
        return businessFinanceFactService.buildProjectView(projectId);
    }

    public record InvoiceFactRequest(
        String factId,
        String projectId,
        String accountingPeriod,
        BigDecimal amount,
        String currency,
        String invoiceNumber,
        String customerName
    ) {}

    public record CollectionFactRequest(
        String factId,
        String projectId,
        String accountingPeriod,
        BigDecimal amount,
        String currency,
        String payerName,
        String referenceNumber
    ) {}

    public record ReimbursementFactRequest(
        String factId,
        String projectId,
        String accountingPeriod,
        BigDecimal amount,
        String currency,
        String payeeName,
        String expenseType
    ) {}

    public record AdvanceFactRequest(
        String factId,
        String projectId,
        String accountingPeriod,
        BigDecimal amount,
        String currency,
        String receiverName,
        String purpose
    ) {}
}
