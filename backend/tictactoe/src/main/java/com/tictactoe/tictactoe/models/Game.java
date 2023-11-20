package com.tictactoe.tictactoe.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.tictactoe.tictactoe.models.GameSlot.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "game")
public class Game {
    private static List<List<Integer>> finishers = List.of(
            List.of(0, 1, 2),
            List.of(3, 4, 5),
            List.of(6, 7, 8),
            List.of(0, 3, 6),
            List.of(1, 4, 7),
            List.of(2, 5, 8),
            List.of(0, 4, 8),
            List.of(6, 4, 2)
    );
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

    @Enumerated(EnumType.STRING)
    private GameSlot winner;

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
        var winnerSlot = getWinner();
        winnerSlot.ifPresent(gameSlot -> this.winner = gameSlot);
        return new MoveInfo(index, gameSlots.get(index), this.playerTurn.getId(), winnerSlot.isPresent());
    }

    public Optional<GameSlot> getWinner() {
        for (var winningInxSetup : finishers) {
            var signs = winningInxSetup
                    .stream()
                    .map(this.gameSlots::get)
                    .filter(sign -> !NONE.equals(sign))
                    .toList();
            if (signs.size() == 3) {
                if(signs.stream().allMatch(X::equals)) {
                    return Optional.of(X);
                }
                if(signs.stream().allMatch(O::equals)) {
                    return Optional.of(O);
                }
            }
        }
        if (this.gameSlots
                .stream()
                .allMatch(NONE::equals)) {
            return Optional.of(NONE);
        }
        return Optional.empty();
    }

    public Optional<User> getWinnerPlayer() {
        var winnerSlot = getWinner();
        if (winnerSlot.isPresent() && winnerSlot.get().equals(X)) {
            return Optional.of(this.player1);
        }
        if (winnerSlot.isPresent() && winnerSlot.get().equals(O)) {
            return Optional.of(this.player2);
        }
        return Optional.empty();
    }
}
