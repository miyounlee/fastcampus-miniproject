package com.application.miniproject.admin;

import com.application.miniproject.admin.dto.AdminRequest;
import com.application.miniproject.admin.dto.AdminResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/event/request")
    public ResponseEntity<List<AdminResponse.EventRequestListDTO>> getEventRequestList() {
        return ResponseEntity.ok(adminService.getEventRequestList());
    }

    @PostMapping("/leave/approval")
    public ResponseEntity<String> leaveApproval(@RequestBody AdminRequest.ApprovalDTO request) {
        adminService.approve(request);
        return ResponseEntity.ok().body("Leave has been updated to " + request.getOrderState());
    }

    @PostMapping("/duty/approval")
    public ResponseEntity<String> dutyApproval(@RequestBody AdminRequest.ApprovalDTO request) {
        adminService.approve(request);
        return ResponseEntity.ok().body("Duty has been updated to " + request.getOrderState());
    }
}
