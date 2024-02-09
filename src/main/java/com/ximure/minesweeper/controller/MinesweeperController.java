package com.ximure.minesweeper.controller;

import com.ximure.minesweeper.entity.MinesweeperField;
import com.ximure.minesweeper.request.GameTurnRequest;
import com.ximure.minesweeper.request.NewGameRequest;
import com.ximure.minesweeper.response.ErrorResponse;
import com.ximure.minesweeper.response.GameInfoResponse;
import com.ximure.minesweeper.response.MinesweeperResponse;
import com.ximure.minesweeper.utils.MinesweeperFieldGenerator;
import com.ximure.minesweeper.utils.MinesweeperFieldUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class MinesweeperController {
    private final List<MinesweeperField> gameFields = new ArrayList<>();
    private final MinesweeperFieldGenerator fieldGenerator;
    private final MinesweeperFieldUpdater fieldUpdater;

    @Autowired
    public MinesweeperController(MinesweeperFieldGenerator fieldGenerator, MinesweeperFieldUpdater fieldUpdater) {
        this.fieldGenerator = fieldGenerator;
        this.fieldUpdater = fieldUpdater;
    }

    @PostMapping("/turn")
    public ResponseEntity<MinesweeperResponse> turn(@RequestBody @Validated GameTurnRequest request) {
        // забираем из списка игр необходимое поле игры по UUID
        MinesweeperField requestGameField = gameFields
                .stream()
                .filter(field -> field.getGameUUID().equals(request.getGameId()))
                .findFirst()
                .orElse(null);

        // обработка ошибки в случае если не найдётся нужное игровое поле
        if (requestGameField == null) {
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(String.format("игра с идентификатором %s не была создана или устарела (неактуальна)", request.getGameId())));
        }

        // обработка ошибки если игрок пытается играть при завершенной игре
        if (requestGameField.isCompleted()) {
            return ResponseEntity.badRequest().body(
                    new ErrorResponse("Игра завершена")
            );
        }

        // обработка ошибки при клике на уже открытое поле
        if (requestGameField.getGameField()[request.getRow()][request.getCol()] != " ") {
            return ResponseEntity.badRequest().body(
                    new ErrorResponse("Уже открытая ячейка"));
        }

        // обновление поля по логике игры
        MinesweeperField fieldAfterTurn = fieldUpdater.turn(
                requestGameField,
                request.getRow(),
                request.getCol());

        return ResponseEntity.ok(new GameInfoResponse(
                requestGameField.getGameUUID(),
                requestGameField.getWidth(),
                requestGameField.getHeight(),
                requestGameField.getMinesCount(),
                requestGameField.isCompleted(),
                requestGameField.getGameField()
        ));
    }

    @PostMapping("/new")
    public ResponseEntity<MinesweeperResponse> createNewGame(@RequestBody @Validated NewGameRequest request) {
        // проверка значений ширины и высоты
        boolean correctWidth = request.getWidth() > 30 ? false : true;
        boolean correctHeight = request.getHeight() > 30 ? false : true;
        if (!correctWidth || !correctHeight) {
            StringBuilder errorStringBuilder = new StringBuilder();
            if (!correctWidth) {
                errorStringBuilder.append("ширина ");
            }
            if (!correctHeight) {
                if (errorStringBuilder.length() > 0) {
                    errorStringBuilder.append(" и ");
                }
                errorStringBuilder.append("высота ");
            }
            errorStringBuilder.append("должна быть не меньше 2 и не больше 30");

            return ResponseEntity.badRequest().body(
                    new ErrorResponse(errorStringBuilder.toString())
            );
        }

        // проверка, ввели ли количество мин больше, чем width * height
        int totalFieldSquares = request.getWidth() * request.getHeight();
        if (request.getMinesCount() >= totalFieldSquares) {
            return ResponseEntity.badRequest().body(
                    new ErrorResponse("количество мин должно быть не менее 1 и не более "
                            + String.valueOf(totalFieldSquares - 1)));
        }

        // создаём новое игровое поле
        MinesweeperField newField = new MinesweeperField(
                UUID.randomUUID(),
                fieldGenerator.generateEmptyField(
                        request.getWidth(),
                        request.getHeight()),
                fieldGenerator.generateBombsField(
                        request.getWidth(),
                        request.getHeight(),
                        request.getMinesCount()
                ),
                request.getHeight(),
                request.getWidth(),
                request.getMinesCount(),
                false
        );

        // закидываем новое игровое поле в список игр и ставим время начала игры
        newField.setGameStartTime(LocalDateTime.now());
        gameFields.add(newField);

        // возвращаем ответ с игровым полем
        return ResponseEntity.ok(new GameInfoResponse(
                newField.getGameUUID(),
                request.getWidth(),
                request.getHeight(),
                request.getMinesCount(),
                false,
                newField.getGameField())
        );
    }

    // удаление старых игр каждые 5 минут
    @Scheduled(fixedRate = 5 * 60 * 1000)
    private void oldGamesRemovalSchedule() {
        Iterator<MinesweeperField> iterator = gameFields.iterator();
        while (iterator.hasNext()) {
            MinesweeperField field = iterator.next();
            if (field.getGameStartTime().isBefore(LocalDateTime.now().minus(5, ChronoUnit.MINUTES))) {
                iterator.remove();
            }
        }
    }
}
