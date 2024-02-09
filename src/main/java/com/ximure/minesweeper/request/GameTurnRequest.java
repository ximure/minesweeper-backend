package com.ximure.minesweeper.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class GameTurnRequest {
    private final UUID gameId;
    private final int col;
    private final int row;

    public GameTurnRequest(@JsonProperty("game_id") UUID gameId,
                           @JsonProperty("col") int col,
                           @JsonProperty("row") int row) {
        this.gameId = gameId;
        this.col = col;
        this.row = row;
    }
}
