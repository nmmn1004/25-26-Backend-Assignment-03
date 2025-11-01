package com.gdg.jpaexample.dto;

import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
public class PlayerRequestDto {
    @NotNull
    private String name;
}