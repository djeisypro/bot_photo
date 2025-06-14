package ru.tpu.javaEElabs;

import java.util.Scanner;
import java.util.*;
import java.lang.*;

public class SeaBattle {
    private static final int BOARD_SIZE = 10;
    private int[][] machineBoard;
    private int[][] playerBoard;
    private Scanner scanner;
    
    public SeaBattle() {
        machineBoard = new int[BOARD_SIZE][BOARD_SIZE];
        playerBoard = new int[BOARD_SIZE][BOARD_SIZE];
        scanner = new Scanner(System.in);
        initializeBoards();
    }
    
    private void initializeBoards() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                machineBoard[i][j] = 0;
                playerBoard[i][j] = 0;
            }
        }
    }
    
    public void startGame() {
        System.out.println("Выберите вариант игры \n(0 - машина, 1 - человек) ");
        int choice = scanner.nextInt();
        
        if (choice == 0) {
            playAgainstComputer();
        } else if (choice == 1) {
            playAgainstHuman();
        }
    }
    
    private void playAgainstComputer() {
        ComputerPlayer computer = new ComputerPlayer();
        HumanPlayer player = new HumanPlayer();
        
        computer.placeShips();
        player.placeShips();
        
        machineBoard = computer.getBoard();
        playerBoard = player.getBoard();
        
        GameBoard.printPlayerBoard(machineBoard, playerBoard);
        System.out.println("Игра началась!");
        
        int machineHits = 0;
        int playerHits = 0;
        int machineShots = 0;
        int playerShots = 0;
        
        while (machineHits < 4 || playerHits < 4 || machineShots <= BOARD_SIZE * BOARD_SIZE || playerShots <= BOARD_SIZE * BOARD_SIZE) {
            // Ход игрока
            System.out.println("Введите координаты куда будете стрелять\n(сначала координаты по горизонтали)");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            playerShots++;
            
            if (machineBoard[x][y] != 0 && machineBoard[x][y] != 8 && machineBoard[x][y] != 9) {
                System.out.println("Попадание!");
                machineBoard[x][y] = 9;
                playerHits++;
            } else {
                System.out.println("Мимо");
                machineBoard[x][y] = 8;
            }
            
            // Ход компьютера
            int m, n;
            do {
                m = (int) (Math.random() * BOARD_SIZE);
                n = (int) (Math.random() * BOARD_SIZE);
            } while (playerBoard[m][n] == 8 || playerBoard[m][n] == 9);
            
            machineShots++;
            System.out.println("Теперь очередь машины");
            
            if (playerBoard[m][n] != 0 && playerBoard[m][n] != 8 && playerBoard[m][n] != 9) {
                System.out.println("Машина попадает!");
                playerBoard[m][n] = 9;
                GameBoard.printPlayerBoard(machineBoard, playerBoard);
                machineHits++;
            } else {
                System.out.println("Машина не попала");
                if (playerBoard[m][n] != 8 && playerBoard[m][n] != 9) {
                    playerBoard[m][n] = 8;
                }
                GameBoard.printPlayerBoard(machineBoard, playerBoard);
            }
            
            // Проверка окончания игры
            if (machineHits >= 4) {
                System.out.println("Игра окончена! Победитель машина!!!");
                System.exit(0);
            }
            if (playerHits >= 4) {
                System.out.println("Игра окончена! Победитель игрок!!!");
                System.exit(0);
            }
            if (machineShots >= BOARD_SIZE * BOARD_SIZE) {
                System.out.println("Игра окончена! Израсходовано количество выстрелов равное числу клеток(для машины)");
                break;
            }
            if (playerShots >= BOARD_SIZE * BOARD_SIZE) {
                System.out.println("Игра окончена! Израсходовано количество выстрелов равное числу клеток(для человека)");
                break;
            }
        }
        
        System.out.println("playerShots = " + playerShots + "\nmachineShots = " + machineShots);
    }
    
    private void playAgainstHuman() {
        HumanPlayer player1 = new HumanPlayer();
        HumanPlayer player2 = new HumanPlayer();
        
        System.out.println("Заполнение поля 1-м игроком");
        player1.placeShips();
        
        System.out.println("Заполнение поля 2-м игроком");
        player2.placeShips();
        
        machineBoard = player1.getBoard();
        playerBoard = player2.getBoard();
        
        GameBoard.printPlayerBoard(machineBoard, playerBoard);
        System.out.println("Игра началась!");
        
        int player1Hits = 0;
        int player2Hits = 0;
        int player1Shots = 0;
        int player2Shots = 0;
        
        while (player1Hits < 4 || player2Hits < 4 || player1Shots <= BOARD_SIZE * BOARD_SIZE || player2Shots <= BOARD_SIZE * BOARD_SIZE) {
            // Ход первого игрока
            System.out.println("Ход первого игрока");
            System.out.println("Введите координаты куда будете стрелять\n(сначала координаты по горизонтали)");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            player1Shots++;
            
            if (playerBoard[x][y] != 0 && playerBoard[x][y] != 8 && playerBoard[x][y] != 9) {
                System.out.println("Попадание!");
                playerBoard[x][y] = 9;
                player1Hits++;
            } else {
                System.out.println("Мимо");
                playerBoard[x][y] = 8;
            }
            
            // Ход второго игрока
            System.out.println("Ход второго игрока");
            System.out.println("Введите координаты куда будете стрелять\n(сначала координаты по горизонтали)");
            x = scanner.nextInt();
            y = scanner.nextInt();
            player2Shots++;
            
            if (machineBoard[x][y] != 0 && machineBoard[x][y] != 8 && machineBoard[x][y] != 9) {
                System.out.println("Попадание!");
                machineBoard[x][y] = 9;
                player2Hits++;
            } else {
                System.out.println("Мимо");
                machineBoard[x][y] = 8;
            }
            
            GameBoard.printPlayerBoard(machineBoard, playerBoard);
            
            // Проверка окончания игры
            if (player1Hits >= 4) {
                System.out.println("Игра окончена! Победитель первый игрок!!!");
                System.exit(0);
            }
            if (player2Hits >= 4) {
                System.out.println("Игра окончена! Победитель второй игрок!!!");
                System.exit(0);
            }
            if (player1Shots >= BOARD_SIZE * BOARD_SIZE) {
                System.out.println("Игра окончена! Израсходовано количество выстрелов равное числу клеток(для первого игрока)");
                break;
            }
            if (player2Shots >= BOARD_SIZE * BOARD_SIZE) {
                System.out.println("Игра окончена! Израсходовано количество выстрелов равное числу клеток(для второго игрока)");
                break;
            }
        }
        
        System.out.println("player1Shots = " + player1Shots + "\nplayer2Shots = " + player2Shots);
    }
    
    public static void main(String[] args) {
        SeaBattle game = new SeaBattle();
        game.startGame();
    }
} 