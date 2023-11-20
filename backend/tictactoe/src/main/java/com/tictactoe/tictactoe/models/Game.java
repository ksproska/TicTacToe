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

    public MoveInfo move(MoveRequest moveRequest) {
        Long playerId = moveRequest.playerId();
        int index = moveRequest.index();
        if (!this.playerTurn.getId().equals(playerId)) {
            throw new IllegalStateException("it is not this player turn");
        }
        if (index >= this.gameSlots.size() || index < 0) {
            throw new IllegalStateException("incorrect index " + index);
        }
        if (this.gameSlots.get(index) != NONE) {
            throw new IllegalStateException("index already filled");
        }
        if(this.player1.equals(this.playerTurn)) {
            gameSlots.set(index, X);
            this.playerTurn = this.player2;
        }
        else {
            gameSlots.set(index, O);
            this.playerTurn = this.player1;
        }
        return new MoveInfo(index, gameSlots.get(index), this.playerTurn.getId());
    }
}
