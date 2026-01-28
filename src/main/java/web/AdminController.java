package com.antonio.napuhanci.web;

import com.antonio.napuhanci.service.AdminBookingService;
import com.antonio.napuhanci.web.dto.AdminBookingRequestResponse;
import com.antonio.napuhanci.web.dto.AdminDecisionRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/admin/requests", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {

    private final AdminBookingService adminBookingService;

    public AdminController(AdminBookingService adminBookingService) {
        this.adminBookingService = adminBookingService;
    }

    @GetMapping("/pending")
    public List<AdminBookingRequestResponse> pending() {
        return adminBookingService.getPendingRequests()
                .stream()
                .map(AdminBookingRequestResponse::from)
                .toList();
    }

    @PutMapping("/{id}/confirm")
    public AdminBookingRequestResponse confirm(
            @PathVariable long id,
            @RequestBody(required = false) AdminDecisionRequest body
    ) {
        String note = body == null ? null : body.adminNote();
        return AdminBookingRequestResponse.from(adminBookingService.confirm(id, note));
    }

    @PutMapping("/{id}/reject")
    public AdminBookingRequestResponse reject(
            @PathVariable long id,
            @RequestBody(required = false) AdminDecisionRequest body
    ) {
        String note = body == null ? null : body.adminNote();
        return AdminBookingRequestResponse.from(adminBookingService.reject(id, note));
    }
}
