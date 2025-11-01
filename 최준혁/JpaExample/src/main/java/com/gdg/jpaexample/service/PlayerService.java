package com.gdg.jpaexample.service;

import com.gdg.jpaexample.domain.Player;
import com.gdg.jpaexample.dto.PlayerRequestDto;
import com.gdg.jpaexample.dto.PlayerResponseDto;
import com.gdg.jpaexample.repository.PlayerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;

    @Transactional
    public PlayerResponseDto savePlayer(PlayerRequestDto playerRequestDto) {
        Player player = Player.builder()
                .name(playerRequestDto.getName())
                .build();

        playerRepository.save(player);

        return PlayerResponseDto.from(player);
    }

    @Transactional
    public PlayerResponseDto getPlayer(Long playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 플레이어입니다."));

        return PlayerResponseDto.from(player);
    }

    // 이름만 수정하는 경우
    @Transactional
    public PlayerResponseDto updatePlayer(Long playerId, PlayerRequestDto playerRequestDto) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 플레이어입니다."));

        if (playerRequestDto.getName() != null) {
            player.update(playerRequestDto.getName(), player.getRecord());
        }
        // record 수정 없음
        return PlayerResponseDto.from(player);
    }

    // 기록만 수정하는 경우
    @Transactional
    public PlayerResponseDto updatePlayer(Long playerId, Long record) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 플레이어입니다."));

        if (record != null) {
            player.update(player.getName(), record);
        }
        return PlayerResponseDto.from(player);
    }

    @Transactional
    public void deletePlayer(Long playerId) {
        playerRepository.deleteById(playerId);
    }
}

