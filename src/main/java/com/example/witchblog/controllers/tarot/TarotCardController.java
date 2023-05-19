package com.example.witchblog.controllers.tarot;

import com.example.witchblog.entity.tarot.TarotCard;
import com.example.witchblog.security.userDetails.CurrentUser;
import com.example.witchblog.security.userDetails.UserDetailsImpl;
import com.example.witchblog.services.tarot.TarotCardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/tarot")
@RequiredArgsConstructor
public class TarotCardController {
    private final TarotCardService tarotCardService;
    @PostMapping()
    public ResponseEntity<?> saveTarotCard(@Valid @RequestBody TarotCard request) {
        final TarotCard tarotCard = tarotCardService.save(request);
        return new ResponseEntity<>(tarotCard, HttpStatus.CREATED);
    }

    @PostMapping("/all")
    public ResponseEntity<?> saveTarotCards(@Valid @RequestBody List<TarotCard> cards) {
        final List<TarotCard> tarotCards = tarotCardService.save(cards);
        return new ResponseEntity<>(tarotCards, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllTarotCards(){
        final List<TarotCard> tarotCard = tarotCardService.findAll();
        return ResponseEntity.ok(tarotCard);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getTarotCardById(@PathVariable Long id){
        final TarotCard tarotCard = tarotCardService.findCardById(id);
        return ResponseEntity.ok(tarotCard);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getTarotCardByName(@PathVariable String name){
        System.out.println(name);
        final TarotCard tarotCard = tarotCardService.findCardByName(name);

        return ResponseEntity.ok(tarotCard);
    }

    @PostMapping("random")
    public ResponseEntity<List<TarotCard>> getRandomThreeCards(
            @RequestParam() MultipartFile file,
            @RequestParam(defaultValue = "3") Integer numOfCards) throws IOException {
        List<TarotCard> tarotCards = tarotCardService.getRandomCards(numOfCards);

        return new ResponseEntity<>(tarotCards, HttpStatus.OK);
    }

    @GetMapping("random")
    public ResponseEntity<List<TarotCard>> getRandomThreeCards(@RequestParam(defaultValue = "3") Integer numOfCards) {
        List<TarotCard> tarotCards = tarotCardService.getRandomCards(numOfCards);
        return new ResponseEntity<>(tarotCards, HttpStatus.OK);
    }

    @GetMapping("divination")
    public ResponseEntity<List<TarotCard>> createDivination(@CurrentUser UserDetailsImpl currentUser,
                                                            @RequestParam(defaultValue = "3") Integer numOfCards) {
        List<TarotCard> tarotCards = tarotCardService.generateDivinationForUser(currentUser, numOfCards);
        return new ResponseEntity<>(tarotCards, HttpStatus.OK);
    }

}
