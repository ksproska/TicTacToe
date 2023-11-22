package com.tictactoe.tictactoe.repositories;

import com.tictactoe.tictactoe.models.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findFirstByPlayer2IsNullAndPlayer1_IdNotAndWinnerNull(Long playerId);
    List<Game> findAllByPlayer1_IdOrPlayer2_IdAndWinnerNull(Long player1Id, Long player2Id);
}
