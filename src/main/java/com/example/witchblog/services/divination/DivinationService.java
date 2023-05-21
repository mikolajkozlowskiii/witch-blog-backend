package com.example.witchblog.services.divination;

import com.example.witchblog.dto.response.DivinationResponse;
import com.example.witchblog.dto.response.DivinationUserHistoryResponse;
import com.example.witchblog.entity.divination.Divination;
import com.example.witchblog.entity.divination.DivinationUserHistory;
import com.example.witchblog.security.userDetails.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;


public interface DivinationService {
    public DivinationResponse makeUserDivination(UserDetailsImpl currentUser) throws IOException;
    Page<DivinationUserHistoryResponse> getUserDivinations(UserDetailsImpl currentUser, Pageable pageable);
}
