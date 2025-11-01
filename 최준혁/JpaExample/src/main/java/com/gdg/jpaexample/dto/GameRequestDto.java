package com.gdg.jpaexample.dto;

import jakarta.annotation.Nullable;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
public class GameRequestDto {
    @NotNull
    private Long playerId;
    @NotNull
    private Long bettingChips;
}
