package com.example.witchblog.repositories.tarot;

import com.example.witchblog.entity.divination.DivinationUserHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

public interface UserDivinationsHistoryRepository extends JpaRepository<DivinationUserHistory, Long> {

    Page<DivinationUserHistory> findAll(Pageable pageable);
    Page<DivinationUserHistory> findAllByUserId(Long userId, Pageable pageable);
}
