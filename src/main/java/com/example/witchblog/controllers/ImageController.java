package com.example.witchblog.controllers;

import com.example.witchblog.payload.response.ApiResponse;
import com.example.witchblog.payload.response.ImageResponse;
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
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;
    @PostMapping()
    public ResponseEntity<ApiResponse> uploadCard(@RequestParam("image")MultipartFile file) throws IOException {
        ApiResponse apiResponse = imageService.uploadImage(file);

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @Transactional
    @GetMapping(path = {"/name/{name}/info"})
    public ResponseEntity<ImageResponse> getCardDetails(@PathVariable("name") String name) throws IOException {
        final ImageResponse card = imageService.getImageInfoByName(name);

        return ResponseEntity.ok(card);
    }

    @GetMapping(path = {"/name/{name}"})
    public ResponseEntity<byte[]> getCard(@PathVariable("name") String name) throws IOException {
        final ImageResponse imageResponse = imageService.getImageInfoByName(name);

        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(imageResponse.getType()))
                .body(imageResponse.getImage());
    }

    @DeleteMapping(path = {"/name/{name}"})
    public ResponseEntity<ApiResponse> deleteCard(@PathVariable("name") String name) {
        ApiResponse apiResponse = imageService.deleteImageByName(name);

        return ResponseEntity.status(HttpStatus.OK)
                .body(apiResponse);
    }

    @GetMapping("/tarot/name/{tarotCardName}")
    public ResponseEntity<byte[]> getImageCardByName(@PathVariable String tarotCardName){
        final ImageResponse imageResponse = imageService.getImageResponseByCardName(tarotCardName);

        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(imageResponse.getType()))
                .body(imageResponse.getImage());
    }

    @PostMapping("/base64/tarot/name")
    public ResponseEntity<List<String>> getBase64ImageCardByName(@RequestBody List<String> tarotCardNames) {
        List<String> base64Images = imageService.getBase64ImagesByCardName(tarotCardNames);
        
        return ResponseEntity.ok(base64Images);
    }


}


