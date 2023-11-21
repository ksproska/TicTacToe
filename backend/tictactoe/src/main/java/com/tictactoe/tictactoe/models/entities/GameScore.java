package com.tictactoe.tictactoe.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "game_scores")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class GameScore {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, name = "game_score_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private User player1;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private User player2;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn()
    private User winner;

    public GameScore(User player1, User player2, User winner) {
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
    }
}
