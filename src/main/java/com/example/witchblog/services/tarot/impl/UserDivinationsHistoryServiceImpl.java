package com.example.witchblog.services.tarot.impl;

import com.example.witchblog.dto.tarot.response.UserDivinationsHistoryResponse;
import com.example.witchblog.entity.tarot.TarotCard;
import com.example.witchblog.entity.tarot.UserDivinationsHistory;
import com.example.witchblog.entity.users.User;
import com.example.witchblog.exceptions.SortByException;
import com.example.witchblog.repositories.tarot.UserDivinationsHistoryRepository;
import com.example.witchblog.security.userDetails.UserDetailsImpl;
import com.example.witchblog.services.tarot.UserDivinationsHistoryService;
import com.example.witchblog.services.tarot.mappers.UserDivinationsHistoryMapper;
import com.example.witchblog.services.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserDivinationsHistoryServiceImpl implements UserDivinationsHistoryService {
    private UserDivinationsHistoryRepository userDivinationsHistoryRepository;
    private UserDivinationsHistoryMapper mapper;
    private UserService userService;
    @Override
    public UserDivinationsHistory buildDivination(User user, Set<TarotCard> tarotCards) {
        UserDivinationsHistory userDivinationsHistory = UserDivinationsHistory.builder()
                .user(user)
                .cards(tarotCards)
                .createdAt(Instant.now())
                .build();
        return save(userDivinationsHistory);
    }

    @Override
    public UserDivinationsHistory saveUserDivination(UserDetailsImpl currentUser, Set<TarotCard> tarotCards) {
        User user = userService.findCurrentUser(currentUser);
        UserDivinationsHistory userDivinationsHistory = buildDivination(user, tarotCards);
        return save(userDivinationsHistory);
    }

    @Override
    public UserDivinationsHistory save(UserDivinationsHistory history){
        return userDivinationsHistoryRepository.save(history);
    }

    @Override
    public Page<UserDivinationsHistory> findAll(Pageable pageable) {
        return userDivinationsHistoryRepository.findAll(pageable);
    }

    @Override
    public Page<UserDivinationsHistoryResponse> findAllByUserId(Long userId, Pageable pageable) {
        try{
            Page<UserDivinationsHistory> userDivinationsHistory =
                    userDivinationsHistoryRepository.findAllByUserId(userId, pageable);
            List<UserDivinationsHistoryResponse> userDivinationsHistoryResponses =
                    userDivinationsHistory.stream().map(s->mapper.map(s)).toList();
            return new PageImpl<UserDivinationsHistoryResponse>(userDivinationsHistoryResponses);
        } catch (PropertyReferenceException ex){
            throw new SortByException(pageable.getSort().toString());
        }
    }
}
