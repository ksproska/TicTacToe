package com.tictactoe.tictactoe.models.entities;

import com.tictactoe.tictactoe.models.GameStartSetup;
import com.tictactoe.tictactoe.models.ValidatedMoveResponse;
import com.tictactoe.tictactoe.models.MoveRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.tictactoe.tictactoe.models.entities.GameSign.*;

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

    @JoinTable(name = "game_slots", joinColumns = @JoinColumn(name = "game_id"))
    @Column(name = "game_slots", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<GameSign> gameSlots;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private User player1;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn()
    private User player2;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn()
    private User playerTurn;

    @Enumerated(EnumType.STRING)
    private GameSign winner;

    public Game(User player1) {
        this.gameSlots = List.of(
                NONE, NONE, NONE,
                NONE, NONE, NONE,
                NONE, NONE, NONE
        );
        this.player1 = player1;
        this.playerTurn = player1;
    }

    public void setPlayer2(User player2) {
        this.player2 = player2;
        if (this.playerTurn == null) {
            this.playerTurn = player2;
        }
    }

    public GameStartSetup getGameStartSetup(Long playerId) {
        boolean enableMove = Objects.equals(this.playerTurn.getId(), playerId);
        GameSign playerSign = (playerId.equals(player1.getId())) ? X : O;
        return new GameStartSetup(
                this.id,
                this.gameSlots,
                enableMove,
                playerSign
        );
    }

    public ValidatedMoveResponse move(MoveRequest moveRequest) {
        moveRequest.validateForGame(this.playerTurn, this.gameSlots);

        int index = moveRequest.index();
        GameSign playerSign = (this.playerTurn.equals(this.player1)) ? X : O;
        gameSlots.set(index, playerSign);
        setNextPlayerTurn();

        var winnerSlot = getWinnerSign();
        winnerSlot.ifPresent(gameSign -> this.winner = gameSign);

        Long nextPlayerId = Optional.ofNullable(this.playerTurn).map(User::getId).orElse((long) -1);
        boolean isGameFinished = winnerSlot.isPresent();
        return new ValidatedMoveResponse(
                index,
                playerSign,
                nextPlayerId,
                isGameFinished
        );
    }

    public Optional<User> getWinnerPlayer() {
        return getWinnerSign()
                .flatMap(this::getPlayerForGameSign);
    }

    private void setNextPlayerTurn() {
        if (this.player1.equals(this.playerTurn)) {
            this.playerTurn = this.player2;
        } else {
            this.playerTurn = this.player1;
        }
    }

    private Optional<GameSign> getWinnerSign() {
        for (var winningInxSetup : finishers) {
            var signs = winningInxSetup
                    .stream()
                    .map(this.gameSlots::get)
                    .filter(sign -> !NONE.equals(sign))
                    .toList();
            if (signs.size() == 3) {
                if (signs.stream().allMatch(X::equals)) {
                    return Optional.of(X);
                }
                if (signs.stream().allMatch(O::equals)) {
                    return Optional.of(O);
                }
            }
        }
        return (this.gameSlots.stream().allMatch(NONE::equals)) ? Optional.of(NONE) : Optional.empty();
    }

    private Optional<User> getPlayerForGameSign(GameSign sign) {
        if (sign.equals(X)) return Optional.of(player1);
        if (sign.equals(O)) return Optional.of(player2);
        return Optional.empty();
    }
}
