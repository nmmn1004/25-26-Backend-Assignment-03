package com.gdg.jpaexample.domain.Card;

import com.gdg.jpaexample.domain.Round.Round;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer card1;
    private Integer card2;

    @Enumerated(EnumType.STRING)
    private CardOwner owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "round_id")
    private Round round;

    public Card(CardOwner owner, Round round) {
        this.owner = owner;
        this.round = round;

        CardUtil cardUtil = new CardUtil();
        this.card1 = cardUtil.generateRandomCards();
        this.card2 = cardUtil.generateRandomCards();
    }
}
