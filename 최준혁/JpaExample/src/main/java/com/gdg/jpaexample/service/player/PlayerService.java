package com.gdg.jpaexample.service.player;

import com.gdg.jpaexample.domain.Player;
import com.gdg.jpaexample.dto.Player.PlayerSaveRequestDto;
import com.gdg.jpaexample.dto.Player.PlayerInfoResponseDto;
import com.gdg.jpaexample.repository.PlayerRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final PlayerCreator playerCreator;
    private final PlayerFinder playerFinder;

    /**
    * @param playerSaveRequestDto - 클라이언트로 요청받는 객체
    * @return 플레이어의 닉네임을 repository에 저장하고 저장한 객체를 반환
    */
    @Transactional
    public PlayerInfoResponseDto savePlayer(PlayerSaveRequestDto  playerSaveRequestDto) {
        return PlayerInfoResponseDto.from(playerCreator.create(playerSaveRequestDto));
    }

    @Transactional(readOnly = true)
    public PlayerInfoResponseDto getPlayer(Long playerId) {
        return PlayerInfoResponseDto.from(playerFinder.findByIdOrThrow(playerId));
    }

    @Transactional(readOnly = true)
    public List<PlayerInfoResponseDto> getAllPlayer() {
        return playerFinder.findAll().stream()
                .map(PlayerInfoResponseDto::from)
                .toList();
    }

    /**
     * @param playerId
     * @param playerSaveRequestDto
     * @return
     * */
    @Transactional
    public PlayerInfoResponseDto updatePlayer(Long playerId, PlayerSaveRequestDto playerSaveRequestDto) {
        Player player = playerFinder.findByIdOrThrow(playerId);

        player.update(playerSaveRequestDto.getName(), player.getRecord());

        return PlayerInfoResponseDto.from(player);
    }

//  헬퍼 클래스로 옮길 지 고민
    @Transactional
    public PlayerInfoResponseDto updatePlayer(Long playerId, Long record) {
        Player player = playerFinder.findByIdOrThrow(playerId);

        if (record != null) {
            player.update(player.getName(), record);
        }
        return PlayerInfoResponseDto.from(player);
    }
w
    @Transactional
    public void deletePlayer(Long playerId) {
        playerRepository.delete(playerFinder.findByIdOrThrow(playerId));
    }
}

