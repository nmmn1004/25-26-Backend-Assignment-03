package com.gdg.jpaexample.dto;

import jakarta.annotation.Nullable;
import lombok.Getter;

@Getter
public class GameRequestDto {
    private Long playerId;
    private Long bettingChips;
}
