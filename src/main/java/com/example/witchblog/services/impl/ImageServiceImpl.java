package com.example.witchblog.services.impl;

import com.example.witchblog.exceptions.CardNotFoundException;
import com.example.witchblog.models.tarot.Image;
import com.example.witchblog.payload.response.ApiResponse;
import com.example.witchblog.payload.response.CardResponse;
import com.example.witchblog.repositories.CardRepository;
import com.example.witchblog.services.ImageService;
import com.example.witchblog.util.ImageUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {
    private CardRepository cardRepository;
    @Override
    public ApiResponse uploadCard(MultipartFile file) throws IOException {
        cardRepository.save(
                Image.builder()
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
        final Image image = findCardByName(name);

        return CardResponse.builder()
                .name(image.getName())
                .type(image.getType())
                .image(ImageUtils.decompressImage(image.getImage())).build();
    }

    @Override
    @Transactional
    public byte[] getCardViewByName(String name) {
        final Image image = findCardByName(name);

        return ImageUtils.decompressImage(image.getImage());
    }

    @Override
    public ApiResponse deleteCardByName(String name) {
        final Image image = findCardByName(name);
        cardRepository.delete(image);
        return new ApiResponse(Boolean.TRUE, "Card: " + name + " deleted successfully.");
    }

    private Image findCardByName(String name) {
        final Image image = cardRepository
                .findByName(name)
                .orElseThrow(() -> new CardNotFoundException("Card: " + name + " doesn't exist in DB."));
        return image;
    }

    public List<String> tarotMock(MultipartFile file){
            List<CardResponse> cardResponses = new ArrayList<>();

            cardResponses.add(getCardInfoByName("kokos.jpg"));
            cardResponses.add(getCardInfoByName("betoniarz.jpg"));
            cardResponses.add(getCardInfoByName("dahmer.jpg"));

            List<String> base64CardsImages = new ArrayList<>();
            for (CardResponse card : cardResponses) {
                String base64Image = Base64.getEncoder().encodeToString(card.getImage());
                base64CardsImages.add(base64Image);
            }
            return base64CardsImages;
    }
}
