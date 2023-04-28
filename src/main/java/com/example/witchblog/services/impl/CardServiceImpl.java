package com.example.witchblog.services.impl;

import com.example.witchblog.exceptions.CardNotFoundException;
import com.example.witchblog.models.Card;
import com.example.witchblog.payload.response.ApiResponse;
import com.example.witchblog.payload.response.CardResponse;
import com.example.witchblog.repositories.CardRepository;
import com.example.witchblog.services.CardService;
import com.example.witchblog.util.ImageUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CardServiceImpl implements CardService {
    private CardRepository cardRepository;
    @Override
    public ApiResponse uploadCard(MultipartFile file) throws IOException {
        cardRepository.save(
                Card.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .image(ImageUtils.compressImage(file.getBytes())).build()
        );

        return new ApiResponse(Boolean.TRUE, "Card " +
                        file.getOriginalFilename() + " uploaded successfully.");
    }

    @Override
    @Transactional
    public CardResponse getCardInfoByName(String name) {
        final Card card = findCardByName(name);

        return CardResponse.builder()
                .name(card.getName())
                .type(card.getType())
                .image(ImageUtils.decompressImage(card.getImage())).build();
    }

    @Override
    @Transactional
    public byte[] getCardViewByName(String name) {
        final Card card  = findCardByName(name);

        return ImageUtils.decompressImage(card.getImage());
    }

    @Override
    public ApiResponse deleteCardByName(String name) {
        final Card card  = findCardByName(name);
        cardRepository.delete(card);
        return new ApiResponse(Boolean.TRUE, "Card: " + name + " deleted successfully.");
    }

    private Card findCardByName(String name) {
        final Card card = cardRepository
                .findByName(name)
                .orElseThrow(() -> new CardNotFoundException("Card: " + name + " doesn't exist in DB."));
        return card;
    }
}
