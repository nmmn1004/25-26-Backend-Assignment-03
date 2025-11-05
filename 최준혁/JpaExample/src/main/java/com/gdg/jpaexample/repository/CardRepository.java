package com.gdg.jpaexample.repository;

import com.gdg.jpaexample.domain.Card.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
