package com.example.witchblog.services.divination;

import com.example.witchblog.dto.response.DivinationResponse;
import com.example.witchblog.dto.response.DivinationUserHistoryResponse;
import com.example.witchblog.dto.tarot.request.TarotCardRequest;
import com.example.witchblog.entity.divination.Divination;
import com.example.witchblog.entity.divination.DivinationUserHistory;
import com.example.witchblog.entity.tarot.TarotCard;
import com.example.witchblog.security.userDetails.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Set;


public interface DivinationService {
    DivinationResponse makeUserDivination(UserDetailsImpl currentUser) throws IOException;
    DivinationResponse makeUserDivinationForMobileApp(UserDetailsImpl currentUser,
                                                      Set<TarotCardRequest> cards) throws IOException;
    DivinationResponse makeDivinationAnonymoulsy(Set<TarotCardRequest> cards) throws IOException;
    Page<DivinationUserHistoryResponse> getUserDivinations(UserDetailsImpl currentUser, Pageable pageable);
}
