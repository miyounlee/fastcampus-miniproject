package com.application.miniproject.admin;

import com.application.miniproject._core.security.Aes256;
import com.application.miniproject.admin.dto.AdminResponse;
import com.application.miniproject.admin.dto.AdminRequest;
import com.application.miniproject.event.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final Aes256 aes256;

    @Transactional
    public List<AdminResponse.EventRequestListDTO> getEventRequestList() {
        List<Event> events = adminRepository.findAllEvents();

        return events.stream()
                .map(event -> {
                    String decryptedUsername = aes256.decrypt(event.getUser().getUsername());
                    String decryptedEmail = aes256.decrypt(event.getUser().getEmail());
                    return AdminResponse.EventRequestListDTO.builder()
                            .eventId(event.getId())
                            .userId(event.getUser().getId())
                            .annualCount(event.getUser().getAnnualCount())
                            .userName(decryptedUsername)
                            .userEmail(decryptedEmail)
                            .eventType(event.getEventType().toString())
                            .startDate(event.getStartDate())
                            .endDate(event.getEndDate())
                            .orderState(event.getOrderState().toString())
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public AdminResponse.LeaveApprovalDTO approveLeave(AdminRequest.ApprovalDTO request) {
        adminRepository.findEventById(request.getEventId()).setOrderState(request.getOrderState());
        Event event = adminRepository.findEventById(request.getEventId());

        return AdminResponse.LeaveApprovalDTO.builder()
                .userId(event.getUser().getId())
                .userName(aes256.decrypt(event.getUser().getUsername()))
                .userEmail(aes256.decrypt(event.getUser().getEmail()))
                .annualCount(event.getUser().getAnnualCount())
                .eventType(event.getEventType().toString())
                .eventId(event.getId())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .orderState(event.getOrderState().toString())
                .build();
    }

    @Transactional
    public AdminResponse.DutyApprovalDTO approveDuty(AdminRequest.ApprovalDTO request) {
        adminRepository.findEventById(request.getEventId()).setOrderState(request.getOrderState());
        Event event = adminRepository.findEventById(request.getEventId());

        return AdminResponse.DutyApprovalDTO.builder()
                .userId(event.getUser().getId())
                .userName(aes256.decrypt(event.getUser().getUsername()))
                .userEmail(aes256.decrypt(event.getUser().getEmail()))
                .annualCount(event.getUser().getAnnualCount())
                .eventType(event.getEventType().toString())
                .eventId(event.getId())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .orderState(event.getOrderState().toString())
                .build();
    }
}
