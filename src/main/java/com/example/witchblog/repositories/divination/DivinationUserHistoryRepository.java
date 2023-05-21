package com.example.witchblog.repositories.divination;

import com.example.witchblog.entity.divination.DivinationUserHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DivinationUserHistoryRepository extends JpaRepository<DivinationUserHistory, Long> {
    Page<DivinationUserHistory> findAllByUserId(Long userId, Pageable pageable);
}
