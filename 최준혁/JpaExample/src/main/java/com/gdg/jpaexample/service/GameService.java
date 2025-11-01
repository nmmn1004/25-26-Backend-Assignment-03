package com.gdg.jpaexample.service;

import com.gdg.jpaexample.domain.CardUtil;
import com.gdg.jpaexample.domain.Game;
import com.gdg.jpaexample.domain.Player;
import com.gdg.jpaexample.dto.GameRequestDto;
import com.gdg.jpaexample.dto.GameResponseDto;
import com.gdg.jpaexample.repository.PlayerRepository;
import com.gdg.jpaexample.repository.GameRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final PlayerService playerService;

    @Transactional
    public GameResponseDto saveGame(GameRequestDto gameRequestDto) {
        if (gameRequestDto.getBettingChips() == null || gameRequestDto.getBettingChips() > 10000) {
            throw new IllegalArgumentException("베팅 칩은 10000을 넘을 수 없습니다.");
        }

        Player player = playerRepository.findById(gameRequestDto.getPlayerId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 플레이어입니다."));


        Game game = Game.builder()
                .player(player)
                .bettingChips(gameRequestDto.getBettingChips())
                .build();

        gameRepository.save(game);

        return GameResponseDto.from(game);
    }

    @Transactional(readOnly = true)
    public GameResponseDto getGame(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임입니다."));

        return GameResponseDto.from(game);
    }

    @Transactional
    public GameResponseDto updateGame(Long gameId, GameRequestDto gameRequestDto) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임입니다."));

        Player player = playerRepository.findById(gameRequestDto.getPlayerId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 플레이어입니다."));

        if (game.getResult() != null)
            throw new IllegalArgumentException("이미 종료된 게임입니다.");

        if (gameRequestDto.getBettingChips() > game.getChips()) {
            throw new IllegalArgumentException("베팅 칩은 보유 칩을 넘을 수 없습니다.");
        }

        if (gameRequestDto.getBettingChips() < game.getBettingChips()) {
            throw new IllegalArgumentException("이전보다 더 높게 걸어야 합니다.");
        }

        game.update(gameRequestDto.getBettingChips(), game.getChips(), player);

        return GameResponseDto.from(game);
    }

    @Transactional
    public void deleteGame(Long gameId) {
        gameRepository.deleteById(gameId);
    }

    @Transactional
    public List<GameResponseDto> getAllGame() {
        return gameRepository.findAll()
                .stream()
                .map(GameResponseDto::from)
                .toList();
    }

    @Transactional
    public GameResponseDto getRoundResult(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임입니다."));

        Player player = playerRepository.findById(game.getPlayer().getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 플레이어입니다."));

        if (game.getResult() != null)
            throw new IllegalArgumentException("이미 종료된 게임입니다.");

        CardUtil util = new CardUtil();

        int p = util.calculateHandCard(game.getPlayerCard());
        int o = util.calculateHandCard(game.getOpponentCard());

        long modifiedChips;

        if (p > 21) {
            modifiedChips = game.getChips() - game.getBettingChips();
            game.update(-1); // 플레이어 버스트
        }
        if (o > 21) {
            modifiedChips = game.getChips() + game.getBettingChips();
            game.update(1);  // 상대 버스트
        }

        if (p == o)
            game.update(0); // 무승부

        if (o == 21 || o > p) {
            modifiedChips = game.getChips() - game.getBettingChips();
            game.update(-1); // 상대 승
        } else {
            modifiedChips = game.getChips() + game.getBettingChips();
            game.update(1);  // 플레이어 승
        }

        game.update(game.getBettingChips(), modifiedChips, player);
        player.update(player.getName(), game.getChips());
        return GameResponseDto.from(game);
    }
}
