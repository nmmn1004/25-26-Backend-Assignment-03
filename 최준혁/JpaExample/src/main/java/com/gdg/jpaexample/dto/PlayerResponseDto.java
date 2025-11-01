package com.gdg.jpaexample.dto;

import com.gdg.jpaexample.domain.Player;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class PlayerResponseDto {
    private Long id;
    private String name;
    private LocalDate date;
    private Long record;

    public static PlayerResponseDto from(Player player) {
        return PlayerResponseDto.builder()
                .id(player.getId())
                .name(player.getName())
                .date(player.getDate())
                .record(player.getRecord())
                .build();
    }
}
