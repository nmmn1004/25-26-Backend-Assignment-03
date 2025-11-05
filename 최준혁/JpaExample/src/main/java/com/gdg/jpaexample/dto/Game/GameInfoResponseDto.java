package com.gdg.jpaexample.dto.Game;

import com.gdg.jpaexample.domain.Game;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class GameInfoResponseDto {
    private Long id;
    private Long chips;
    private LocalDate date;
    private Long playerId;
    private String playerName;

    public static GameInfoResponseDto from(Game game) {
        return GameInfoResponseDto.builder()
                .id(game.getId())
                .chips(game.getChips())
                .date(game.getDate())
                .playerId(game.getPlayer().getId())
                .playerName(game.getPlayer().getName())
                .build();
    }
}
