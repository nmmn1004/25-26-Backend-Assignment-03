package com.gdg.jpaexample.controller;

import com.gdg.jpaexample.dto.GameRequestDto;
import com.gdg.jpaexample.dto.GameResponseDto;
import com.gdg.jpaexample.service.GameService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    public ResponseEntity<GameResponseDto> saveGame(@RequestBody GameRequestDto gameRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gameService.saveGame(gameRequestDto));
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<GameResponseDto> getGame(@PathVariable Long gameId) {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.getGame(gameId));
    }

    @PatchMapping("/{gameId}")
    public ResponseEntity<GameResponseDto> updateGame(@PathVariable Long gameId, @RequestBody GameRequestDto gameRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.updateGame(gameId, gameRequestDto));
    }

    @DeleteMapping("/{gameId}")
    public ResponseEntity<GameResponseDto> deleteGame(@PathVariable Long gameId) {
        gameService.deleteGame(gameId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<GameResponseDto>> getAllGame() {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.getAllGame());
    }

    @GetMapping("/result/{gameId}")
    public ResponseEntity<GameResponseDto> getResult(@PathVariable Long gameId) {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.getRoundResult(gameId));
    }
}
