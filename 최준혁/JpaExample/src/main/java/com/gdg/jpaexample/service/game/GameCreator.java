package com.gdg.jpaexample.service.game;

import com.gdg.jpaexample.domain.Game;
import com.gdg.jpaexample.domain.Player;
import com.gdg.jpaexample.dto.Game.GameSaveRequestDto;
import com.gdg.jpaexample.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameCreator {
    private final GameRepository gameRepository;

    public Game create(Player player, GameSaveRequestDto gameSaveRequestDto) {
        Game game = Game.builder()
                .player(player)
                .build();

        return gameRepository.save(game);
    }
}
