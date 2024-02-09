package com.ximure.minesweeper.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class GameInfoResponse implements MinesweeperResponse {
    private final UUID gameId;
    private final int width;
    private final int height;
    private final int minesCount;
    private final boolean completed;
    private final String[][] field;

    public GameInfoResponse(@JsonProperty("game_id") UUID gameId,
                            @JsonProperty("width") int width,
                            @JsonProperty("height") int height,
                            @JsonProperty("mines_count") int minesCount,
                            @JsonProperty("completed") boolean completed,
                            @JsonProperty("field") String[][] field) {
        this.gameId = gameId;
        this.width = width;
        this.height = height;
        this.minesCount = minesCount;
        this.completed = completed;
        this.field = field;
    }
}
