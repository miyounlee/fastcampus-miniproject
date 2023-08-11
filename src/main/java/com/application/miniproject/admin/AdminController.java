package com.application.miniproject.admin;

import com.application.miniproject.admin.dto.AdminRequest;
import com.application.miniproject.admin.dto.AdminResponse;
import com.application.miniproject._core.util.ApiUtils;
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
    public ResponseEntity<ApiUtils<List<AdminResponse.EventRequestListDTO>>> getEventRequestList() {
        List<AdminResponse.EventRequestListDTO> requestList = adminService.getEventRequestList();
        return ResponseEntity.ok(new ApiUtils<>(requestList));
    }

    @PostMapping("/leave/approval")
    public ResponseEntity<ApiUtils<AdminResponse.LeaveApprovalDTO>> leaveApproval(@RequestBody AdminRequest.ApprovalDTO request) {
        AdminResponse.LeaveApprovalDTO approvalResult = adminService.approveLeave(request);
        return ResponseEntity.ok(new ApiUtils<>(approvalResult));
    }

    @PostMapping("/duty/approval")
    public ResponseEntity<ApiUtils<AdminResponse.DutyApprovalDTO>> dutyApproval(@RequestBody AdminRequest.ApprovalDTO request) {
        AdminResponse.DutyApprovalDTO approvalResult = adminService.approveDuty(request);
        return ResponseEntity.ok(new ApiUtils<>(approvalResult));
    }
}
