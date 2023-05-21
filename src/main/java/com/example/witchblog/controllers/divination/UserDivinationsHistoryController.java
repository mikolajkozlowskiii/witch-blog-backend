package com.example.witchblog.controllers.divination;

import com.example.witchblog.dto.tarot.response.UserDivinationsHistoryResponse;
import com.example.witchblog.services.tarot.UserDivinationsHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/history")
@RequiredArgsConstructor
public class UserDivinationsHistoryController {
    private final UserDivinationsHistoryService userDivinationsHistoryService;

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
}
