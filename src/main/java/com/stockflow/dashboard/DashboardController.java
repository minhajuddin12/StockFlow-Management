package com.stockflow.dashboard;

import com.stockflow.config.AuthenticatedUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;
    private final AuthenticatedUser authenticatedUser;

    public DashboardController(DashboardService dashboardService, AuthenticatedUser authenticatedUser) {
        this.dashboardService = dashboardService;
        this.authenticatedUser = authenticatedUser;
    }

    @GetMapping("/summary")
    public DashboardResponse summary() {
        return dashboardService.summary(authenticatedUser.getCurrentCompanyId());
    }
}