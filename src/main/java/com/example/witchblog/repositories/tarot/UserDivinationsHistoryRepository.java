package com.example.witchblog.repositories.tarot;

import com.example.witchblog.entity.tarot.UserDivinationsHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

public interface UserDivinationsHistoryRepository extends JpaRepository<UserDivinationsHistory, Long> {

    Page<UserDivinationsHistory> findAll(Pageable pageable);
    Page<UserDivinationsHistory> findAllByUserId(Long userId, Pageable pageable);
}
