package com.gdg.jpaexample.repository;

import com.gdg.jpaexample.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {

}
