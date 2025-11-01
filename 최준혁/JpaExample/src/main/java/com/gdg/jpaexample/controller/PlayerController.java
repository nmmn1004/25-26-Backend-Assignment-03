package com.gdg.jpaexample.controller;

import com.gdg.jpaexample.dto.PlayerRequestDto;
import com.gdg.jpaexample.dto.PlayerResponseDto;
import com.gdg.jpaexample.service.PlayerService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/players")
public class PlayerController {
    private final PlayerService playerService;

    @PostMapping
    public ResponseEntity<PlayerResponseDto> savePlayer(@RequestBody PlayerRequestDto playerRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(playerService.savePlayer(playerRequestDto));
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<PlayerResponseDto> getPlayer(@PathVariable Long playerId) {
        return ResponseEntity.status(HttpStatus.OK).body(playerService.getPlayer(playerId));
    }

    @PatchMapping("/{playerId}")
    public ResponseEntity<PlayerResponseDto> updatePlayer(@PathVariable Long playerId, @RequestBody PlayerRequestDto playerRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(playerService.updatePlayer(playerId, playerRequestDto));
    }

    @DeleteMapping("/{playerId}")
    public ResponseEntity<PlayerResponseDto> deletePlayer(@PathVariable Long playerId) {
        playerService.deletePlayer(playerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
//메롱