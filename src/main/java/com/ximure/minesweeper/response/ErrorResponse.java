package com.ximure.minesweeper.response;

import lombok.Data;

@Data
public class ErrorResponse implements MinesweeperResponse {
    private final String error;

    public ErrorResponse(String error) {
        this.error = error;
    }
}
