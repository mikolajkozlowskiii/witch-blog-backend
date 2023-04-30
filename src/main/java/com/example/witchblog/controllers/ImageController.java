package com.example.witchblog.controllers;

import com.example.witchblog.payload.response.ApiResponse;
import com.example.witchblog.payload.response.CardResponse;
import com.example.witchblog.services.ImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;
    @PostMapping()
    public ResponseEntity<ApiResponse> uploadCard(@RequestParam("image")MultipartFile file) throws IOException {
        ApiResponse apiResponse = imageService.uploadCard(file);

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @Transactional
    @GetMapping(path = {"{name}/info"})
    public ResponseEntity<CardResponse> getCardDetails(@PathVariable("name") String name) throws IOException {
        final CardResponse card = imageService.getCardInfoByName(name);

        return ResponseEntity.ok(card);
    }

    @GetMapping(path = {"{name}"})
    public ResponseEntity<byte[]> getCard(@PathVariable("name") String name) throws IOException {
        final CardResponse card = imageService.getCardInfoByName(name);

        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(card.getType()))
                .body(card.getImage());
    }

    @DeleteMapping(path = {"{name}"})
    public ResponseEntity<ApiResponse> deleteCard(@PathVariable("name") String name) throws IOException {
        ApiResponse apiResponse = imageService.deleteCardByName(name);

        return ResponseEntity.status(HttpStatus.OK)
                .body(apiResponse);
    }

    @PostMapping("/tarot")
    public ResponseEntity<List<String>> tarot(@RequestParam("image")MultipartFile file) throws IOException{
            return ResponseEntity.ok(imageService.tarotMock(file));
    }
}
