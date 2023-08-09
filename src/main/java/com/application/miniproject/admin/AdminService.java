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
                    event.getUser().setUsername(decryptedUsername);
                    event.getUser().setEmail(decryptedEmail);
                    return new AdminResponse.EventRequestListDTO(event);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void leaveApproval(AdminRequest.ApprovalDTO request) {
        Event event = adminRepository.findEventById(request.getEventId());
        event.setOrderState(request.getOrderState());
    }

    @Transactional
    public void dutyApproval(AdminRequest.ApprovalDTO request) {
        Event event = adminRepository.findEventById(request.getEventId());
        event.setOrderState(request.getOrderState());
    }
}
