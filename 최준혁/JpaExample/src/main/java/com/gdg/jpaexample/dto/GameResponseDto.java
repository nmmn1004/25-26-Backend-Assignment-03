package com.gdg.jpaexample.dto;

import com.gdg.jpaexample.domain.Game;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class GameResponseDto {
    private Long id;
    private Long bettingChips;
    private Long chips;
    private Integer result;
    private List<Integer> playerCard;
    private List<Integer> opponentCard;
    private LocalDate date;
    private Long playerId;
    private String playerName;

    public static GameResponseDto from(Game game) {
        return GameResponseDto.builder()
                .id(game.getId())
                .bettingChips(game.getBettingChips())
                .chips(game.getChips())
                .result(game.getResult())
                .playerCard(new ArrayList<>(game.getPlayerCard()))
                .opponentCard(new ArrayList<>(game.getOpponentCard()))
                .date(game.getDate())
                .playerId(game.getPlayer().getId())
                .playerName(game.getPlayer().getName())
                .build();
    }
}
