package com.gdg.jpaexample.controller;

import com.gdg.jpaexample.dto.Player.PlayerSaveRequestDto;
import com.gdg.jpaexample.dto.Player.PlayerInfoResponseDto;
import com.gdg.jpaexample.service.player.PlayerService;
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
@RequestMapping("/players")
public class PlayerController {
    private final PlayerService playerService;

    @PostMapping
    public ResponseEntity<PlayerInfoResponseDto> savePlayer(@RequestBody PlayerSaveRequestDto playerRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(playerService.savePlayer(playerRequestDto));
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<PlayerInfoResponseDto> getPlayer(@PathVariable Long playerId) {
        return ResponseEntity.status(HttpStatus.OK).body(playerService.getPlayer(playerId));
    }

    @GetMapping
    public ResponseEntity<List<PlayerInfoResponseDto>> getPlayer() {
        return ResponseEntity.status(HttpStatus.OK).body(playerService.getAllPlayer());
    }

    @PatchMapping("/{playerId}")
    public ResponseEntity<PlayerInfoResponseDto> updatePlayer(@PathVariable Long playerId, @RequestBody PlayerSaveRequestDto playerRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(playerService.updatePlayer(playerId, playerRequestDto));
    }

    @DeleteMapping("/{playerId}")
    public ResponseEntity<PlayerInfoResponseDto> deletePlayer(@PathVariable Long playerId) {
        playerService.deletePlayer(playerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
//메롱