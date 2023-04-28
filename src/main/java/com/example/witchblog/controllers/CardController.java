package com.example.witchblog.controllers;

import com.example.witchblog.payload.response.ApiResponse;
import com.example.witchblog.payload.response.CardResponse;
import com.example.witchblog.repositories.CardRepository;
import com.example.witchblog.services.CardService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;
    @PostMapping()
    public ResponseEntity<ApiResponse> uploadCard(@RequestParam("image")MultipartFile file) throws IOException {
        ApiResponse apiResponse = cardService.uploadCard(file);

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @Transactional
    @GetMapping(path = {"{name}/info"})
    public ResponseEntity<CardResponse> getCardDetails(@PathVariable("name") String name) throws IOException {
        final CardResponse card = cardService.getCardInfoByName(name);

        return ResponseEntity.ok(card);
    }

    @GetMapping(path = {"{name}"})
    public ResponseEntity<byte[]> getCard(@PathVariable("name") String name) throws IOException {
        final CardResponse card = cardService.getCardInfoByName(name);

        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(card.getType()))
                .body(card.getImage());
    }

    @DeleteMapping(path = {"{name}"})
    public ResponseEntity<ApiResponse> deleteCard(@PathVariable("name") String name) throws IOException {
        ApiResponse apiResponse = cardService.deleteCardByName(name);

        return ResponseEntity.status(HttpStatus.OK)
                .body(apiResponse);
    }
}
