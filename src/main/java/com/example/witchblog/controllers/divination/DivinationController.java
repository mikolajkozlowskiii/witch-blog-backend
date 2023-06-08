package com.example.witchblog.controllers.divination;

import com.example.witchblog.dto.response.DivinationResponse;
import com.example.witchblog.dto.response.DivinationUserHistoryResponse;
import com.example.witchblog.dto.tarot.request.TarotCardRequest;
import com.example.witchblog.entity.divination.Divination;
import com.example.witchblog.entity.divination.DivinationUserHistory;
import com.example.witchblog.security.userDetails.CurrentUser;
import com.example.witchblog.security.userDetails.UserDetailsImpl;
import com.example.witchblog.services.divination.DivinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/divination")
@RequiredArgsConstructor
public class DivinationController {
    private final DivinationService divinationService;

    @PostMapping
    public ResponseEntity<DivinationResponse> generateDivination(@CurrentUser UserDetailsImpl currentUser) throws IOException {
        return ResponseEntity.ok(divinationService.makeUserDivination(currentUser));
    }

    @PostMapping("/mobile")
    public ResponseEntity<DivinationResponse> generateDivinationFormMobileApp(
            @CurrentUser UserDetailsImpl currentUser, @RequestBody Set<TarotCardRequest> cards) throws IOException {
        return ResponseEntity.ok(divinationService.makeUserDivinationForMobileApp(currentUser, cards));
    }

    @PostMapping("/anonymous")
    public ResponseEntity<DivinationResponse> generateDivinationAnonymously(@RequestBody Set<TarotCardRequest> cards) throws IOException {
        return ResponseEntity.ok(divinationService.makeDivinationAnonymoulsy(cards));
    }

    @GetMapping("/history")
    public ResponseEntity<Page<DivinationUserHistoryResponse>> getAllDivinations(
            @CurrentUser UserDetailsImpl currentUser,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "4") Integer pageSize,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ){
        return ResponseEntity.ok(divinationService.getUserDivinations(currentUser, PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending())));
    }
}
