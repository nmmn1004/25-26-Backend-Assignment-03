package com.gdg.jpaexample.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
/**
* @param id - 플레이어의 id
* @param name - 플레이어의 닉네임
* @param record - 플레이어의 최고 기록
* @param LocalDate - 플레이어 생성년월일
* @param games - 플레이어가 플레이한 게임 1:N
*/
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oauth_id")
    private Long id;

    @Column(name = "name", length = 20)
    private String name;

    private Long record;

    private LocalDate date;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
    private List<Game> games = new ArrayList<>();

    @Builder
    public Player(String name) {
        this.name = name;
        this.record = 0L;
        this.date = LocalDate.now();
    }

    public void update(String name, long record) {
        this.name = name;
        this.record = record;
    }
}
