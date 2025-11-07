package com.gdg.jpaexample.service.game;

import com.gdg.jpaexample.domain.Game;
import com.gdg.jpaexample.exception.game.GameNotFoundException;
import com.gdg.jpaexample.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GameFinder {
    private final GameRepository gameRepository;

    @Transactional(readOnly = true)
    public Game findByIdOrThrow(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));
    }

    @Transactional(readOnly = true)
    public List<Game> findAll() {
        return gameRepository.findAll();
    }
}
