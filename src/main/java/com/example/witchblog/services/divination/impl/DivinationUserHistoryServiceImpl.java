package com.example.witchblog.services.divination.impl;

import com.example.witchblog.entity.divination.Divination;
import com.example.witchblog.entity.divination.DivinationUserHistory;
import com.example.witchblog.repositories.divination.DivinationUserHistoryRepository;
import com.example.witchblog.security.userDetails.UserDetailsImpl;
import com.example.witchblog.services.divination.DivinationUserHistoryService;
import com.example.witchblog.services.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class DivinationUserHistoryServiceImpl implements DivinationUserHistoryService {
    private final DivinationUserHistoryRepository divinationUserHistoryRepository;
    private final UserService userService;
    @Override
    public DivinationUserHistory save(Divination divination, UserDetailsImpl userDetails) {
        DivinationUserHistory divinationUserHistory = getDivinationUserHistory(divination, userDetails);
        return divinationUserHistoryRepository.save(divinationUserHistory);
    }

    private DivinationUserHistory getDivinationUserHistory(Divination divination, UserDetailsImpl userDetails) {
        return DivinationUserHistory.builder()
                .user(userService.findCurrentUser(userDetails))
                .divination(divination)
                .createdAt(Instant.now())
                .build();
    }

    @Override
    public Page<DivinationUserHistory> findAllByUserId(Long id, Pageable pageable) {
        return divinationUserHistoryRepository.findAllByUserId(id, pageable);
    }
}
