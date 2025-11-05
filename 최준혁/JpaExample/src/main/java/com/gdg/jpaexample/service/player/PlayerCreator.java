package com.gdg.jpaexample.service.player;

import com.gdg.jpaexample.domain.Player;
import com.gdg.jpaexample.dto.Player.PlayerSaveRequestDto;
import com.gdg.jpaexample.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlayerCreator {
    private final PlayerRepository playerRepository;

    public Player create(PlayerSaveRequestDto playerSaveRequestDto) {
        Player player = Player.builder()
                .name(playerSaveRequestDto.getName())
                .build();

        return playerRepository.save(player);
    }
}
