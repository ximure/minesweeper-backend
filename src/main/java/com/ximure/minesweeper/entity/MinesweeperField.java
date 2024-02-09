package com.ximure.minesweeper.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class MinesweeperField {
    private final UUID gameUUID;
    private String[][] gameField;
    private String[][] bombsField;
    private final int height;
    private final int width;
    private final int minesCount;
    private boolean completed;
    private LocalDateTime gameStartTime;

    public MinesweeperField(UUID gameUUID, String[][] gameField, String[][] bombsField, int height, int width, int minesCount, boolean completed) {
        this.gameUUID = gameUUID;
        this.gameField = gameField;
        this.bombsField = bombsField;
        this.height = height;
        this.width = width;
        this.minesCount = minesCount;
        this.completed = completed;
    }
}
