package com.gdg.jpaexample.dto.Round;

import com.gdg.jpaexample.domain.Round.Round;
import com.gdg.jpaexample.dto.Card.CardInfoResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RoundInfoResponseDto {
    private Long id;
    private Long bettingChips;
    private String result;
    private List<CardInfoResponseDto> playerCards;
    private List<CardInfoResponseDto> opponentCards;

    public static RoundInfoResponseDto from(Round round) {
        return RoundInfoResponseDto.builder()
                .id(round.getId())
                .bettingChips(round.getBettingChips())
                .result(round.getResult().name())
                .playerCards(round.getPlayerCards().stream()
                        .map(CardInfoResponseDto::from)
                        .toList())
                .opponentCards(round.getOpponentCards().stream()
                        .map(CardInfoResponseDto::from)
                        .toList())
                .build();
    }
}
