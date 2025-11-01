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
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oauth_id")
    private Long id;

    @Column(name = "player_name")
    private String name;

    @Column(name = "player_date")
    private LocalDate date;

    @Column(name = "player_record")
    private Long record;

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
