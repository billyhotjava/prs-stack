package com.yuzhi.prs.customer.service;

import com.yuzhi.prs.customer.domain.CustomerPortalAccount;
import com.yuzhi.prs.service.domain.ServiceFeedEntry;
import com.yuzhi.prs.service.service.ServiceFeedService;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class CustomerPortalService {

    private final Map<String, CustomerPortalAccount> accountsByCustomerId = new ConcurrentHashMap<>();
    private final ServiceFeedService serviceFeedService;

    public CustomerPortalService(ServiceFeedService serviceFeedService) {
        this.serviceFeedService = serviceFeedService;
        registerAccount(
            "PORTAL-DEFAULT",
            "CUS-001",
            "IFC Property",
            "Ms. Lin",
            List.of("PRJ-001")
        );
    }

    public CustomerPortalAccount registerAccount(
        String accountId,
        String customerId,
        String customerName,
        String contactName,
        List<String> visibleProjectIds
    ) {
        CustomerPortalAccount account = new CustomerPortalAccount(
            accountId,
            customerId,
            customerName,
            contactName,
            List.copyOf(visibleProjectIds),
            true
        );
        accountsByCustomerId.put(customerId, account);
        return account;
    }

    public CustomerOverview buildOverview(String customerId) {
        CustomerPortalAccount account = requireAccount(customerId);
        List<ServiceFeedEntry> history = listServiceHistory(customerId);
        String latestSummary = history.stream()
            .max(Comparator.comparing(ServiceFeedEntry::serviceDate))
            .map(ServiceFeedEntry::summary)
            .orElse("No service history yet");

        return new CustomerOverview(
            account.customerId(),
            account.customerName(),
            account.visibleProjectIds().size(),
            history.size(),
            latestSummary
        );
    }

    public List<ServiceFeedEntry> listServiceHistory(String customerId) {
        CustomerPortalAccount account = requireAccount(customerId);
        return account.visibleProjectIds().stream()
            .flatMap(projectId -> serviceFeedService.listProjectFeed(projectId).stream())
            .sorted(Comparator.comparing(ServiceFeedEntry::serviceDate).reversed())
            .toList();
    }

    private CustomerPortalAccount requireAccount(String customerId) {
        CustomerPortalAccount account = accountsByCustomerId.get(customerId);
        if (account == null || !account.active()) {
            throw new IllegalArgumentException("No active customer portal account for " + customerId);
        }
        return account;
    }

    public record CustomerOverview(
        String customerId,
        String customerName,
        int projectCount,
        int visibleFeedCount,
        String latestSummary
    ) {}
}
