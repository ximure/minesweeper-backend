package com.ximure.minesweeper.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NewGameRequest {
    private final int width;
    private final int height;
    private final int minesCount;

    public NewGameRequest(@JsonProperty("width") int width,
                          @JsonProperty("height") int height,
                          @JsonProperty("mines_count") int minesCount) {
        this.width = width;
        this.height = height;
        this.minesCount = minesCount;
    }
}
