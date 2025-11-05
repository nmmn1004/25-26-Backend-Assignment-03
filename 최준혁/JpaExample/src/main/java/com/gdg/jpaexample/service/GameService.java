package com.gdg.jpaexample.service;

import com.gdg.jpaexample.domain.Game;
import com.gdg.jpaexample.domain.Player;
import com.gdg.jpaexample.domain.Round.Round;
import com.gdg.jpaexample.dto.Game.GameSaveRequestDto;
import com.gdg.jpaexample.dto.Game.GameInfoResponseDto;
import com.gdg.jpaexample.repository.GameRepository;
import com.gdg.jpaexample.service.player.PlayerFinder;
import com.gdg.jpaexample.service.player.PlayerService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final PlayerFinder playerFinder;
    private final PlayerService playerService;

    @Transactional
    public GameInfoResponseDto saveGame(GameSaveRequestDto gameRequestDto) {
        Player player = playerFinder.findByIdOrThrow(gameRequestDto.getPlayerId());

        Game game = Game.builder()
                .player(player)
                .build();

        gameRepository.save(game);

        return GameInfoResponseDto.from(game);
    }

    @Transactional(readOnly = true)
    public GameInfoResponseDto getGame(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임입니다."));

        return GameInfoResponseDto.from(game);
    }

    @Transactional
    public List<GameInfoResponseDto> getAllGame() {
        return gameRepository.findAll()
                .stream()
                .map(GameInfoResponseDto::from)
                .toList();
    }

    @Transactional
    public GameInfoResponseDto getGameResult(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임입니다."));

        playerService.updatePlayer(game.getPlayer().getId(), game.getChips());

        return GameInfoResponseDto.from(game);
    }

    @Transactional
    public void deleteGame(Long gameId) {
        gameRepository.deleteById(gameId);
    }

}
