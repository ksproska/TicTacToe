package com.tictactoe.tictactoe.repositories;

import com.tictactoe.tictactoe.models.entities.GameScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameScoreRepository extends JpaRepository<GameScore, Long> {}
