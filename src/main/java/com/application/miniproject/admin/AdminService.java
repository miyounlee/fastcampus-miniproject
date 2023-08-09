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
import javax.persistence.EntityNotFoundException;

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
    public AdminResponse.ApprovalResultDTO approve(AdminRequest.ApprovalDTO request) {
        Event event = adminRepository.findEventById(request.getEventId());

        if(event == null) {
            throw new EntityNotFoundException(request.getEventId() + " not found");
        }

        event.setOrderState(request.getOrderState());

        if(!"LEAVE".equals(request.getApprovalType()) && !"DUTY".equals(request.getApprovalType())) {
            throw new IllegalArgumentException("잘못된 승인 형태입니다.");
        }

        return AdminResponse.ApprovalResultDTO.builder()
                .eventId(event.getId())
                .userName(aes256.decrypt(event.getUser().getUsername()))
                .userEmail(aes256.decrypt(event.getUser().getEmail()))
                .eventType(event.getEventType().toString())
                .orderState(event.getOrderState().toString())
                .build();
    }
}
