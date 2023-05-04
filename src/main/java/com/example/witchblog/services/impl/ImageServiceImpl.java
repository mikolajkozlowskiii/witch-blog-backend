package com.example.witchblog.services.impl;

import com.example.witchblog.exceptions.CardNotFoundException;
import com.example.witchblog.models.tarot.Image;
import com.example.witchblog.models.tarot.TarotCard;
import com.example.witchblog.payload.response.ApiResponse;
import com.example.witchblog.payload.response.ImageResponse;
import com.example.witchblog.repositories.ImageRepository;
import com.example.witchblog.services.ImageService;
import com.example.witchblog.services.TarotCardService;
import com.example.witchblog.util.ImageUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {
    private ImageRepository imageRepository;
    private TarotCardService tarotCardService;
    @Override
    public ApiResponse uploadImage(MultipartFile file) throws IOException {
        imageRepository.save(
                Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .image(ImageUtils.compressImage(file.getBytes())).build()
        );

        return new ApiResponse(Boolean.TRUE, "Card " +
                        file.getOriginalFilename() + " uploaded successfully.");
    }

    @Override
    public ApiResponse uploadImage(MultipartFile file, String cardName) throws IOException {
        imageRepository.save(
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
    public ImageResponse getImageInfoByName(String name) {
        final Image image = findImageByName(name);

        return ImageResponse.builder()
                .name(image.getName())
                .type(image.getType())
                .image(ImageUtils.decompressImage(image.getImage())).build();
    }

    @Override
    @Transactional
    public byte[] getImageViewByName(String name) {
        final Image image = findImageByName(name);

        return ImageUtils.decompressImage(image.getImage());
    }

    @Override
    public ApiResponse deleteImageByName(String name) {
        final Image image = findImageByName(name);
        imageRepository.delete(image);
        return new ApiResponse(Boolean.TRUE, "Card: " + name + " deleted successfully.");
    }

    @Override
    public ImageResponse getImageResponseByCardName(String tarotCardName) {
        TarotCard tarotCard = tarotCardService.findCardByName(tarotCardName);
        Image image = findImageByName(tarotCard.getImg());
        return ImageResponse.builder()
                .name(image.getName())
                .type(image.getType())
                .image(ImageUtils.decompressImage(image.getImage())).build();
    }

    @Override
    public String getBase64ImageByCardName(String tarotCardName) {
        TarotCard tarotCard = tarotCardService.findCardByName(tarotCardName);
        Image image = findImageByName(tarotCard.getName());
        return convertImageToBase64(image);
    }

    @Override
    public List<String> getBase64ImagesByCardName(List<String> tarotCardsNames) {
        List<TarotCard> tarotCards = tarotCardsNames
                .stream()
                .map(s -> tarotCardService.findCardByName(s))
                .toList();

        return tarotCards
                .stream()
                .map(s->findImageByName(s.getImg()))
                .map(this::convertImageToBase64)
                .toList();
    }

    private String convertImageToBase64(Image imageInfo) {
        byte[] decompressedImage = ImageUtils.decompressImage(imageInfo.getImage());
        return Base64.getEncoder().encodeToString(decompressedImage);
    }

    private Image findImageByName(String name) {
        final Image image = imageRepository
                .findByName(name)
                .orElseThrow(() -> new CardNotFoundException("Card: " + name + " doesn't exist in DB."));
        return image;
    }
}
