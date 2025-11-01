package com.gdg.jpaexample.domain;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long id;

    @ElementCollection
    @Column(name = "player_card")
    private List<Integer> playerCard;

    @ElementCollection
    @Column(name = "opponent_card")
    private List<Integer> opponentCard;

    private LocalDate date;

    @Column(name = "chips")
    private Long chips;

    @Column(name = "betting_chips")
    private Long bettingChips;

    @Column(name = "game_result")
    private Integer result;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    private Player player;

    @Builder
    public Game(long bettingChips, Player player) {
        this.chips = 10000L;
        this.bettingChips = bettingChips;
        this.player = player;
        this.result = null;

        CardUtil util = new CardUtil();
        this.playerCard = util.generateRandomCards();
        this.opponentCard = util.generateRandomCards();

        this.date = LocalDate.now();
    }

    public void update(long bettingChips, long chips, Player player) {
        this.bettingChips = bettingChips;
        this.chips = chips;
        this.player = player;
    }

    public void update(int result) {
        this.result = result;
    }
}
