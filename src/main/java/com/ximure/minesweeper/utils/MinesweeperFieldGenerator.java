package com.ximure.minesweeper.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class MinesweeperFieldGenerator {
    public String[][] generateEmptyField(int width, int height) {
        String[][] field = new String[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                field[i][j] = " ";
            }
        }

        return field;
    }

    public String[][] generateBombsField(int width, int height, int minesCount) {
        String[][] field = new String[height][width];

        // инициализация нового поля для бомб на пустые квадраты
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                field[i][j] = " ";
            }
        }

        // заполняем поле с бомбами
        Random rand = new Random();
        for (int i = 0; i < minesCount; i++) {
            int mineX, mineY;
            do {
                mineX = rand.nextInt(width);
                mineY = rand.nextInt(height);
            } while ("X".equals(field[mineY][mineX]));
            field[mineY][mineX] = "X";
        }

        return field;
    }
}
