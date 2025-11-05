package com.gdg.jpaexample.repository;

import com.gdg.jpaexample.domain.Round.Round;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoundRepository extends JpaRepository<Round, Long> {
}
