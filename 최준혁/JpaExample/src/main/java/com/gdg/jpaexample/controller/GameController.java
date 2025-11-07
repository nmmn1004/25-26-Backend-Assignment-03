package com.gdg.jpaexample.controller;

import com.gdg.jpaexample.dto.Game.GameSaveRequestDto;
import com.gdg.jpaexample.dto.Game.GameInfoResponseDto;
import com.gdg.jpaexample.service.game.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameController {
    private final GameService gameService;

    @PostMapping
    public ResponseEntity<GameInfoResponseDto> saveGame(@RequestBody GameSaveRequestDto gameRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gameService.saveGame(gameRequestDto));
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<GameInfoResponseDto> getGame(@PathVariable Long gameId) {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.getGame(gameId));
    }

    @DeleteMapping("/{gameId}")
    public ResponseEntity<GameInfoResponseDto> deleteGame(@PathVariable Long gameId) {
        gameService.deleteGame(gameId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<GameInfoResponseDto>> getAllGame() {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.getAllGame());
    }

    @GetMapping("/result/{gameId}")
    public ResponseEntity<GameInfoResponseDto> getResult(@PathVariable Long gameId) {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.getGameResult(gameId));
    }
}
