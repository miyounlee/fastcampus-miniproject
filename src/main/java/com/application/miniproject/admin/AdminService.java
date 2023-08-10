package com.application.miniproject.admin;

import com.application.miniproject._core.security.Aes256;
import com.application.miniproject.admin.dto.AdminResponse;
import com.application.miniproject.admin.dto.AdminRequest;
import com.application.miniproject.event.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final Aes256 aes256;
    public List<AdminResponse.EventRequestListDTO> getEventRequestList() {
        List<Event> events = adminRepository.findAllEvents();

        return events.stream()
                .map(event -> {
                    String decryptedUsername = aes256.decrypt(event.getUser().getUsername());
                    String decryptedEmail = aes256.decrypt(event.getUser().getEmail());
                    return AdminResponse.EventRequestListDTO.builder()
                            .eventId(event.getId())
                            .userId(event.getUser().getId())
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

    public AdminResponse.LeaveApprovalDTO approveLeave(AdminRequest.ApprovalDTO request) {
        adminRepository.findEventById(request.getEventId()).setOrderState(request.getOrderState());
        Event event = adminRepository.findEventById(request.getEventId());

        return AdminResponse.LeaveApprovalDTO.builder()
                .userId(event.getUser().getId())
                .userName(aes256.decrypt(event.getUser().getUsername()))
                .userEmail(aes256.decrypt(event.getUser().getEmail()))
                .eventType(event.getEventType().toString())
                .eventId(event.getId())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .orderState(event.getOrderState().toString())
                .build();
    }

    public AdminResponse.DutyApprovalDTO approveDuty(AdminRequest.ApprovalDTO request) {
        adminRepository.findEventById(request.getEventId()).setOrderState(request.getOrderState());
        Event event = adminRepository.findEventById(request.getEventId());

        return AdminResponse.DutyApprovalDTO.builder()
                .userName(aes256.decrypt(event.getUser().getUsername()))
                .userEmail(aes256.decrypt(event.getUser().getEmail()))
                .eventType(event.getEventType().toString())
                .eventId(event.getId())
                .startedDate(event.getStartDate())
                .orderState(event.getOrderState().toString())
                .build();
    }
}
