package com.gdg.jpaexample.repository;

import com.gdg.jpaexample.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {

}
