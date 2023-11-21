package com.tictactoe.tictactoe.repositories;

import com.tictactoe.tictactoe.models.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findFirstByPlayer2IsNullAndPlayer1_IdNot(Long playerId);
}
