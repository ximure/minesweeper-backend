package com.ximure.minesweeper.utils;


import com.ximure.minesweeper.entity.MinesweeperField;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MinesweeperFieldUpdater {
    public MinesweeperField turn(MinesweeperField field, int row, int col) {
        // проверяем, кликнули ли на бомбу
        if (field.getBombsField()[row][col].equals("X")) {
            // если да, открываем всё поле
            for (int i = 0; i < field.getBombsField().length; i++) {
                for (int j = 0; j < field.getBombsField()[0].length; j++) {
                    if (field.getBombsField()[i][j].equals("X")) {
                        // ставим на кликнутое поле индикатор бомбы
                        field.getGameField()[i][j] = "X";
                    } else {
                        // подсчитываем бомбы вокруг кликнутного квадрата и ставим их кол-во (0 если отсутствуют)
                        int bombCount = countBombs(field, i, j);
                        field.getGameField()[i][j] = bombCount > 0 ? String.valueOf(bombCount) : "0";
                    }
                }
            }
            field.setCompleted(true);
            return field;
        }

        // обновляем поле по логике игры и время последней активности
        field.setGameStartTime(LocalDateTime.now());
        openField(field, row, col);

        if (checkWin(field)) {
            field.setCompleted(true);
            revealBombs(field);
        }

        return field;
    }

    private boolean checkWin(MinesweeperField field) {
        int unopenedCount = 0;
        for (int i = 0; i < field.getGameField().length; i++) {
            for (int j = 0; j < field.getGameField()[0].length; j++) {
                if (field.getGameField()[i][j].equals(" ")) {
                    unopenedCount++;
                }
            }
        }
        return unopenedCount == field.getMinesCount();
    }

    private void revealBombs(MinesweeperField field) {
        for (int i = 0; i < field.getBombsField().length; i++) {
            for (int j = 0; j < field.getBombsField()[0].length; j++) {
                if (field.getBombsField()[i][j].equals("X")) {
                    field.getGameField()[i][j] = "M";
                }
            }
        }
    }

    private int countBombs(MinesweeperField field, int row, int col) {
        int bombCount = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;
                // проверка границ поля
                if (newRow >= 0 && newCol >= 0 && newRow < field.getBombsField().length && newCol < field.getBombsField()[0].length) {
                    if (field.getBombsField()[newRow][newCol].equals("X")) {
                        bombCount++;
                    }
                }
            }
        }
        return bombCount;
    }

    private void openField(MinesweeperField field, int row, int col) {
        if (row < 0 || col < 0 || row >= field.getBombsField().length || col >= field.getBombsField()[0].length) {
            return;
        }

        if (!field.getGameField()[row][col].equals(" ") || field.getBombsField()[row][col].equals("X")) {
            return;
        }

        // подсчёт количества бомб вокруг квадрата
        int bombCount = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;
                // проверка границ поля
                if (newRow >= 0 && newCol >= 0 && newRow < field.getBombsField().length && newCol < field.getBombsField()[0].length) {
                    if (field.getBombsField()[newRow][newCol].equals("X")) {
                        bombCount++;
                    }
                }
            }
        }

        // обновляем квадрат на значение с количеством бомб вокруг
        if (bombCount > 0) {
            field.getGameField()[row][col] = String.valueOf(bombCount);
        } else {
            field.getGameField()[row][col] = "0";
            // открытие полей если вокруг нет бомб
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    openField(field, row + i, col + j);
                }
            }
        }
    }
}
