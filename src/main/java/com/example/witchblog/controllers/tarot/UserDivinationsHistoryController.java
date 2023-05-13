package com.example.witchblog.controllers.tarot;

import com.example.witchblog.dto.tarot.response.UserDivinationsHistoryResponse;
import com.example.witchblog.entity.tarot.TarotCard;
import com.example.witchblog.entity.tarot.UserDivinationsHistory;
import com.example.witchblog.entity.users.User;
import com.example.witchblog.security.userDetails.CurrentUser;
import com.example.witchblog.security.userDetails.UserDetailsImpl;
import com.example.witchblog.services.tarot.TarotCardService;
import com.example.witchblog.services.tarot.UserDivinationsHistoryService;
import com.example.witchblog.services.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/history")
@RequiredArgsConstructor
public class UserDivinationsHistoryController {
    private final UserDivinationsHistoryService userDivinationsHistoryService;
    private final TarotCardService tarotCardService;
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<Page<UserDivinationsHistoryResponse>> findUsersDivinations(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "4") Integer pageSize,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ){
            return ResponseEntity.ok(userDivinationsHistoryService
                    .findAllByUserId(userId, PageRequest.of(pageNo, pageSize, Sort.by(sortBy))));

    }
    @GetMapping("random")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<UserDivinationsHistory> generateRandomDivinations(@CurrentUser UserDetailsImpl currentUser){
        System.out.println(currentUser.getId());
        User user = userService.findUserById(currentUser.getId());
        List<TarotCard> tarotCards = tarotCardService.getRandomCards(3);
        Set<TarotCard> tarotCardSet = new HashSet<>(tarotCards);
        return ResponseEntity.ok(userDivinationsHistoryService.buildDivination(user, tarotCardSet));
    }
}
