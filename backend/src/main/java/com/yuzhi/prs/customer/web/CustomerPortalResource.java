package com.yuzhi.prs.customer.web;

import com.yuzhi.prs.customer.service.CustomerPortalService;
import com.yuzhi.prs.service.domain.ServiceFeedEntry;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/customer-portal/customers")
public class CustomerPortalResource {

    private final CustomerPortalService customerPortalService;

    public CustomerPortalResource(CustomerPortalService customerPortalService) {
        this.customerPortalService = customerPortalService;
    }

    @GetMapping("/{customerId}/overview")
    public CustomerPortalService.CustomerOverview getOverview(@PathVariable String customerId) {
        try {
            return customerPortalService.buildOverview(customerId);
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
    }

    @GetMapping("/{customerId}/history")
    public List<ServiceFeedEntry> getHistory(@PathVariable String customerId) {
        try {
            return customerPortalService.listServiceHistory(customerId);
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
    }
}
