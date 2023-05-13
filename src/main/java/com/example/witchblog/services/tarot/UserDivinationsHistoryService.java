package com.example.witchblog.services.tarot;

import com.example.witchblog.dto.tarot.response.UserDivinationsHistoryResponse;
import com.example.witchblog.entity.tarot.TarotCard;
import com.example.witchblog.entity.tarot.UserDivinationsHistory;
import com.example.witchblog.entity.users.User;
import com.example.witchblog.security.userDetails.UserDetailsImpl;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface UserDivinationsHistoryService {
    UserDivinationsHistory buildDivination(User user, Set<TarotCard> tarotCards);
    UserDivinationsHistory saveUserDivination(UserDetailsImpl currentUser, Set<TarotCard> tarotCards);
    UserDivinationsHistory save(UserDivinationsHistory history);
    Page<UserDivinationsHistory> findAll(Pageable pageable);
    Page<UserDivinationsHistoryResponse> findAllByUserId(Long userId, Pageable pageable);
}
