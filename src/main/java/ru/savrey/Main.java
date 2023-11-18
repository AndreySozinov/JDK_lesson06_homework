package ru.savrey;
// Monty Hall problem
// реализовать код для демонстрации парадокса Монти Холла
// и наглядно убедиться в верности парадокса
// (запустить игру в цикле на 1000 и вывести итоговый счет).
// Необходимо:
// ● Создать свой Java Maven или Gradle проект;
// ● Самостоятельно реализовать прикладную задачу;
// ● Сохранить результат в HashMap<шаг теста, результат>
// ● Вывести на экран статистику по победам и поражениям

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int GAMES = 1000;
        int CHOSEN_DOOR = 1; // Номер двери 1..3

        HashMap<Integer, Boolean> gameResultsIfChanging = new HashMap<>();
        HashMap<Integer, Boolean> gameResultsIfNot = new HashMap<>();

        for (int i = 0; i < GAMES; i++) {
            Game game = new Game(CHOSEN_DOOR, true);
            gameResultsIfChanging.put(i, game.isGamerWin());
        }
        float winsAmount1 = Collections.frequency(new ArrayList<Boolean>(gameResultsIfChanging.values()), true);

        for (int i = 0; i < GAMES; i++){
            Game game = new Game(CHOSEN_DOOR, false);
            gameResultsIfNot.put(i, game.isGamerWin());
        }
        float winsAmount2 = Collections.frequency(new ArrayList<Boolean>(gameResultsIfNot.values()), true);

        System.out.printf("Процент побед если дверь меняется: %.2f\n", winsAmount1 * 100 / GAMES);
        System.out.printf("Процент побед если дверь НЕ меняется: %.2f", winsAmount2 * 100 / GAMES);
    }
}

enum Content {
    GOAT,
    CAR
}

class Door {
    private final Content content;
    private boolean isOpen;

    public Door(Content content) {
        this.content = content;
        isOpen = false;
    }

    public Content getContent() {
        return content;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void open() {
        isOpen = true;
    }
}

class Game {
    private boolean isGamerWin = false;

    public Game(int gamerChoice, boolean changeSelection) {
        // Расстановка козлов и автомобиля за дверями
        Random r = new Random();
        int winDoor = r.nextInt(0,3);
        Door[] doors = new Door[3];
        for (int i = 0; i < doors.length; i++) {
            if (i == winDoor) {
                doors[i] = new Door(Content.CAR);
            } else {
                doors[i] = new Door(Content.GOAT);
            }
        }

        gamerChoice--;

        // Ведущий открывает невыбранную игроком дверь с козлом
        boolean openedDoor = false;
        for (int i = 0; i < doors.length; i++) {
            if (!openedDoor){
                if (i != gamerChoice && doors[i].getContent() != Content.CAR) {
                    doors[i].open();
                    openedDoor = true;
                }
            }
        }

        // Игрок меняет выбор если changeSelection = true
        if (changeSelection) {
            for (int i = 0; i < doors.length; i++) {
                if (i != gamerChoice && !doors[i].isOpen()) {
                    gamerChoice = i;
                    break;
                }
            }
        }

        // Проверка выигрыша
        if (doors[gamerChoice].getContent() == Content.CAR) {
            this.isGamerWin = true;
        }
    }

    public boolean isGamerWin() {
        return this.isGamerWin;
    }
}