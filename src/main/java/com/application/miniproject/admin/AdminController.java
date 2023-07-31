package com.application.miniproject.admin;


import com.application.miniproject.admin.dto.AdminRespose;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    @RequestMapping("/join")
    public ResponseEntity<AdminResponse> adminLogin(@RequestBody AdminLoginRequest request) {
        AdminResponse admin = adminService.adminLogin(request);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }
    @GetMapping("/event/request")
    public ResponseEntity<EventRequestListResponse> getEventRequestList() {
        EventRequestListResponse response = adminService.getEventRequestList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/duty/approval")
    public ResponseEntity<EventApprovalResponse> dutyApproval(@RequestBody EventApprovalRequest request) {
        EventApprovalResponse response = adminService.dutyApproval(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/leave/approval")
    public ResponseEntity<EventApprovalResponse> leaveApproval(@RequestBody EventApprovalRequest request) {
        EventApprovalResponse response = adminService.leaveApproval(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
