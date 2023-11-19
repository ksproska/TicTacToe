package com.tictactoe.tictactoe.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

import static com.tictactoe.tictactoe.models.GameSlot.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, name = "game_id")
    private Long id;

//    @ElementCollection(targetElement = GameSlot.class)
    @JoinTable(name = "game_slots", joinColumns = @JoinColumn(name = "game_id"))
    @Column(name = "game_slots", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<GameSlot> gameSlots;

    @ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(nullable = false)
    private User player1;

    @ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn()
    private User player2;

    @ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(nullable = false)
    private User playerTurn;

    public Game(User player1) {
        this.gameSlots = List.of(
                NONE, NONE, NONE,
                NONE, NONE, NONE,
                NONE, NONE, NONE
        );
        this.player1 = player1;
        this.playerTurn = player1;
    }

    public GameInfo getGameInfo(Long playerId) {
        boolean startsFirst;
        GameSlot sign;
        if (Objects.equals(this.player1.getId(), playerId)) {
            startsFirst = true;
            sign = X;
        } else {
            startsFirst = false;
            sign = O;
        }
        return new GameInfo(this.id, this.gameSlots, startsFirst, sign);
    }
}
