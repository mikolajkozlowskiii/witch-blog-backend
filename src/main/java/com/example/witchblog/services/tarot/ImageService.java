package com.example.witchblog.services.tarot;

import com.example.witchblog.dto.response.ApiResponse;
import com.example.witchblog.dto.tarot.response.ImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    ApiResponse uploadImage(MultipartFile file) throws IOException;
    ApiResponse uploadImage(MultipartFile file, String cardName) throws IOException;
    ImageResponse getImageInfoByName(String name);
    byte[] getImageViewByName(String name);
    ApiResponse deleteImageByName(String name);
    ImageResponse getImageResponseByCardName(String tarotCardName);
    String getBase64ImageByCardName(String tarotCardName);
    List<String> getBase64ImagesByCardName(List<String> tarotCardsNames);
}
