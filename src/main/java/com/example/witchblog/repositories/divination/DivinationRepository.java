package com.example.witchblog.repositories.divination;

import com.example.witchblog.entity.divination.Divination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DivinationRepository extends JpaRepository<Divination, Long> {
}
