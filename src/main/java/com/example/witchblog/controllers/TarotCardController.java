package com.example.witchblog.controllers;

import com.example.witchblog.models.tarot.TarotCard;
import com.example.witchblog.services.TarotCardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/tarot")
@RequiredArgsConstructor
public class TarotCardController {
    private final TarotCardService tarotCardService;
    @PostMapping()
    public ResponseEntity<?> saveCard(@Valid @RequestBody TarotCard request) {
        TarotCard tarotCard = tarotCardService.save(request);
        return ResponseEntity.ok(tarotCard);
    }
}
