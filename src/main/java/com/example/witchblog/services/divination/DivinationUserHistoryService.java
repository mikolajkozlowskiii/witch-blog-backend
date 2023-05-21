package com.example.witchblog.services.divination;

import com.example.witchblog.entity.divination.Divination;
import com.example.witchblog.entity.divination.DivinationUserHistory;
import com.example.witchblog.security.userDetails.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

public interface DivinationUserHistoryService {
    DivinationUserHistory save(Divination divination, UserDetailsImpl userDetails);
    Page<DivinationUserHistory> findAllByUserId(Long id, Pageable pageable);
}
