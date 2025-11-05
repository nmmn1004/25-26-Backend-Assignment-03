package com.gdg.jpaexample.repository;

import com.gdg.jpaexample.domain.Game;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
/*    @EntityGraph(attributePaths = {"player", "rounds", "rounds.playerCards", "rounds.opponentCards"})
    Optional<Game> findById(Long id);*/
}
