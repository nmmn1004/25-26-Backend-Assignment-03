package com.gdg.jpaexample.dto.Round;

import com.gdg.jpaexample.domain.Round.Round;
import com.gdg.jpaexample.dto.Card.CardInfoResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoundInfoResponseDto {
    private Long id;
    private Long bettingChips;
    private String result;
    private CardInfoResponseDto playerCard;
    private CardInfoResponseDto opponentCard;

    public static RoundInfoResponseDto from(Round round) {
        return RoundInfoResponseDto.builder()
                .id(round.getId())
                .bettingChips(round.getBettingChips())
                .result(round.getResult().name())
                .playerCard(CardInfoResponseDto.from(round.getPlayerCard()))
                .opponentCard(CardInfoResponseDto.from(round.getOpponentCard()))
                .build();
    }
}
