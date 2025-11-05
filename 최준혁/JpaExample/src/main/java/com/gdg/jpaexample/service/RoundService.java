package com.gdg.jpaexample.service;

import com.gdg.jpaexample.domain.Card.Card;
import com.gdg.jpaexample.domain.Card.CardOwner;
import com.gdg.jpaexample.domain.Card.CardUtil;
import com.gdg.jpaexample.domain.Game;
import com.gdg.jpaexample.domain.Round.Round;
import com.gdg.jpaexample.domain.Round.RoundResult;
import com.gdg.jpaexample.dto.Round.RoundInfoResponseDto;
import com.gdg.jpaexample.dto.Round.RoundSaveRequestDto;
import com.gdg.jpaexample.repository.GameRepository;
import com.gdg.jpaexample.repository.RoundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoundService {
    private final RoundRepository roundRepository;
    private final GameRepository gameRepository;

    @Transactional
    public RoundInfoResponseDto saveRound(RoundSaveRequestDto roundSaveRequestDto) {
        Game game = gameRepository.findById(roundSaveRequestDto.getGameId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임입니다. (id=" + roundSaveRequestDto.getGameId() + ")"));

        if (roundSaveRequestDto.getBettingChips() > game.getChips()) {
            throw new IllegalArgumentException("베팅 칩이 보유 칩보다 많습니다.");
        }

        Round round = new Round(roundSaveRequestDto.getBettingChips(), game);

        Card playerCard = new Card(CardOwner.PLAYER, round);
        Card opponentCard = new Card(CardOwner.OPPONENT, round);

        round.getCards().add(playerCard);
        round.getCards().add(opponentCard);

        game.getRounds().add(round);
        game.update(game.getChips() - roundSaveRequestDto.getBettingChips(), game.getPlayer());

        roundRepository.save(round);

        return RoundInfoResponseDto.from(round);
    }

    @Transactional(readOnly = true)
    public RoundInfoResponseDto getLatestRoundByGame(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임입니다. (id=" + gameId + ")"));

        Round latest = game.getRounds().stream()
                .reduce((first, second) -> second)
                .orElseThrow(() -> new IllegalArgumentException("아직 생성된 라운드가 없습니다. (gameId=" + gameId + ")"));

        return RoundInfoResponseDto.from(latest);
    }

    @Transactional(readOnly = true)
    public List<RoundInfoResponseDto> getAllRoundsByGame(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임입니다. (id=" + gameId + ")"));

        return game.getRounds().stream()
                .map(RoundInfoResponseDto::from)
                .toList();
    }

    @Transactional
    public RoundInfoResponseDto updateLatestRound(Long gameId, RoundSaveRequestDto roundSaveRequestDto) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임입니다. (id=" + gameId + ")"));

        Round latest = game.getRounds().stream()
                .reduce((first, second) -> second)
                .orElseThrow(() -> new IllegalArgumentException("수정할 라운드가 존재하지 않습니다. (gameId=" + gameId + ")"));

        if (roundSaveRequestDto.getBettingChips() != null) {
            latest.updateBettingChips(roundSaveRequestDto.getBettingChips());
        }

        game.update(game.getChips() - roundSaveRequestDto.getBettingChips(), game.getPlayer());

        return RoundInfoResponseDto.from(latest);
    }

    @Transactional
    public RoundInfoResponseDto updateRoundResult(Long gameId) {
        CardUtil cardUtil = new CardUtil();

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임입니다. (id=" + gameId + ")"));

        Round latest = game.getRounds().stream()
                .reduce((first, second) -> second)
                .orElseThrow(() -> new IllegalArgumentException("수정할 라운드가 존재하지 않습니다. (gameId=" + gameId + ")"));

        List<Card> cards = latest.getCards();

        List<Integer> playerCards = cards.stream()
                .filter(c -> c.getOwner() == CardOwner.PLAYER)
                .flatMap(c -> List.of(c.getCard1(), c.getCard2()).stream())
                .collect(Collectors.toList());

        List<Integer> opponentCards = cards.stream()
                .filter(c -> c.getOwner() == CardOwner.OPPONENT)
                .flatMap(c -> List.of(c.getCard1(), c.getCard2()).stream())
                .collect(Collectors.toList());

        int playerScore = cardUtil.calculateHandCard(playerCards);
        int opponentScore = cardUtil.calculateHandCard(opponentCards);

        long modifiedChips = game.getChips();
        RoundResult roundResult;

        if (playerScore > 21) {
            modifiedChips -= latest.getBettingChips();
            roundResult = RoundResult.LOSE;
        } else if (opponentScore > 21) {
            modifiedChips += latest.getBettingChips();
            roundResult = RoundResult.WIN;
        } else if (playerScore == opponentScore) {
            roundResult = RoundResult.DRAW;
        } else if (playerScore == 21 || opponentScore > playerScore) {
            modifiedChips -= latest.getBettingChips();
            roundResult = RoundResult.LOSE;
        } else {
            modifiedChips += latest.getBettingChips();
            roundResult = RoundResult.WIN;
        }

        latest.updateResult(roundResult);
        game.update(modifiedChips, game.getPlayer());

        return RoundInfoResponseDto.from(latest);
    }

    @Transactional
    public void deleteRound(Long roundId) {
        roundRepository.deleteById(roundId);
    }
}