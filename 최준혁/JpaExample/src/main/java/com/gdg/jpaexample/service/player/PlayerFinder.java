package com.gdg.jpaexample.service.player;

import com.gdg.jpaexample.domain.Player;
import com.gdg.jpaexample.exception.player.PlayerNotFoundException;
import com.gdg.jpaexample.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PlayerFinder {
    private final PlayerRepository playerRepository;

    @Transactional
    public Player findByIdOrThrow(Long playerId) {
        return playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerId));
    }

    @Transactional
    public List<Player> findAll() {
        return playerRepository.findAll();
    }
}
