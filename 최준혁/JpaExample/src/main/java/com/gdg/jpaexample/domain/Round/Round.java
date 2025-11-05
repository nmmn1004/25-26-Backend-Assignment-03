package com.gdg.jpaexample.domain.Round;

import com.gdg.jpaexample.domain.Card.Card;
import com.gdg.jpaexample.domain.Card.CardOwner;
import com.gdg.jpaexample.domain.Card.CardUtil;
import com.gdg.jpaexample.domain.Game;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Round {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bettingChips;

    private RoundResult result;

    @OneToMany(mappedBy = "round", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> playerCards = new ArrayList<>();

    @OneToMany(mappedBy = "round", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> opponentCards = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @Builder
    public Round(long bettingChips, Game game) {
        this.bettingChips = bettingChips;
        this.result = RoundResult.PENDING;
        this.game = game;

        CardUtil cardUtil = new CardUtil();

        Card playerCard = new Card(CardOwner.PLAYER, this);
        this.playerCards.add(playerCard);

        Card opponentCard = new Card(CardOwner.OPPONENT, this);
        this.opponentCards.add(opponentCard);

    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void updateBettingChips(long bettingChips) {
        this.bettingChips = bettingChips;
    }

    public void updateResult(RoundResult result) {
        this.result = result;
    }
}