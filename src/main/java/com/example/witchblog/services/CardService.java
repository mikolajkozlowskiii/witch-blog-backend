package com.example.witchblog.services;

import com.example.witchblog.models.Card;
import com.example.witchblog.payload.response.ApiResponse;
import com.example.witchblog.payload.response.CardResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CardService {
    ApiResponse uploadCard(MultipartFile file) throws IOException;
    CardResponse getCardInfoByName(String name);
    byte[] getCardViewByName(String name);
    ApiResponse deleteCardByName(String name);
    List<String> tarotMock(MultipartFile file);
}
