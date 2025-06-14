package ru.tpu.javaEElabs;

import java.util.Scanner;

public class HumanPlayer {
    private int[][] board;
    private Scanner scanner;
    
    public HumanPlayer() {
        board = new int[GameBoard.BOARD_SIZE][GameBoard.BOARD_SIZE];
        scanner = new Scanner(System.in);
        initializeBoard();
    }
    
    private void initializeBoard() {
        for (int i = 0; i < GameBoard.BOARD_SIZE; i++) {
            for (int j = 0; j < GameBoard.BOARD_SIZE; j++) {
                board[i][j] = 0;
            }
        }
    }
    
    public void placeShips() {
        placeOneDeckShips();
        placeTwoDeckShips();
        placeThreeDeckShips();
        placeFourDeckShip();
    }
    
    private void placeOneDeckShips() {
        System.out.println("Введите координаты (через пробел)\nоднопалубных кораблей (сначала координаты по горизонтали)");
        GameBoard.printPlayerBoard(board, board);
        for (int i = 0; i < 4; i++) {
            while (true) {
                System.out.println((i + 1) + "-й корабль");
                int xx = scanner.nextInt();
                int yy = scanner.nextInt();
                if (xx < 0 || xx > GameBoard.BOARD_SIZE - 1 || yy < 0 || yy > GameBoard.BOARD_SIZE - 1) continue;
                if (ShipPlacement.canPlaceShip(xx, yy, board)) break;
                else
                    System.out.println("\nНевозможно расположить корабль \n");
            }
            board[xx][yy] = 1;
        }
    }
    
    private void placeTwoDeckShips() {
        System.out.println("Введите координаты (через пробел)\nдвухпалубных кораблей (сначала координаты по горизонтали)");
        GameBoard.printPlayerBoard(board, board);
        for (int i = 0; i <= 2; i++) {
            while (true) {
                System.out.println((i + 1) + "-й корабль ");
                int xx = scanner.nextInt();
                int yy = scanner.nextInt();
                if (xx < 0 || xx > GameBoard.BOARD_SIZE - 1 || yy < 0 || yy > GameBoard.BOARD_SIZE - 1) continue;
                int xx1 = xx;
                int yy1 = yy;
                while (true) {
                    System.out.println("Направление " + (i + 1) + "-го корабля (1:\\/; 2: <-; 3: /\\; 4: ->): ");
                    int z = scanner.nextInt();
                    z = z - 1;
                    if (z > -1 && z < 4) break;
                }
                z = ShipPlacement.getShipDirection(z, 1);
                if (ShipPlacement.isHorizontal()) yy1 = yy1 + z;
                else xx1 = xx1 + z;
                if (((yy + z < GameBoard.BOARD_SIZE && yy + z > -1 && ShipPlacement.isHorizontal()) || 
                     (xx + z < GameBoard.BOARD_SIZE && xx + z > -1 && !ShipPlacement.isHorizontal())) && 
                    (ShipPlacement.canPlaceShip(xx, yy, board) && ShipPlacement.canPlaceShip(xx1, yy1, board)))
                    break;
                else System.out.println("\nНевозможно расположить корабль \n");
            }
            z = z / 2;
            board[xx][yy] = 2;
            board[xx1][yy1] = 2;
        }
    }
    
    private void placeThreeDeckShips() {
        System.out.println("Введите координаты (через пробел)\nтрехпалубных кораблей (сначала координаты по горизонтали)");
        GameBoard.printPlayerBoard(board, board);
        for (int j = 0; j < 2; j++) {
            while (true) {
                System.out.println((j + 1) + "-й корабль");
                int xx = scanner.nextInt();
                int yy = scanner.nextInt();
                if (xx < 0 || xx > GameBoard.BOARD_SIZE - 1 || yy < 0 || yy > GameBoard.BOARD_SIZE - 1) continue;
                while (true) {
                    System.out.println("Направление " + (j + 1) + "-го корабля (1:\\/; 2: <-; 3: /\\; 4: ->): ");
                    int z = scanner.nextInt();
                    z = z - 1;
                    if (z > -1 && z < 4) break;
                }
                z = ShipPlacement.getShipDirection(z, 2);
                if ((yy + z < GameBoard.BOARD_SIZE && yy + z > -1 && ShipPlacement.isHorizontal()) || 
                    (xx + z < GameBoard.BOARD_SIZE && xx + z > -1 && !ShipPlacement.isHorizontal())) break;
                else System.out.println("\nНевозможно расположить корабль \n");
            }
            z = z / 2;
            board[xx][yy] = 3;
            if (ShipPlacement.isHorizontal()) {
                board[xx][yy + z] = 3;
                board[xx][yy + 2 * z] = 3;
            } else {
                board[xx + z][yy] = 3;
                board[xx + 2 * z][yy] = 3;
            }
        }
    }
    
    private void placeFourDeckShip() {
        System.out.println("Введите координаты (через пробел)\nчетырехпалубных кораблей (сначала координаты по горизонтали)");
        GameBoard.printPlayerBoard(board, board);
        for (int j = 0; j < 1; j++) {
            while (true) {
                System.out.println((j + 1) + "-й корабль");
                int xx = scanner.nextInt();
                int yy = scanner.nextInt();
                if (xx < 0 || xx > GameBoard.BOARD_SIZE - 1 || yy < 0 || yy > GameBoard.BOARD_SIZE - 1) continue;
                while (true) {
                    System.out.println("Направление " + (j + 1) + "-го корабля (1:\\/; 2: <-; 3: /\\; 4: ->): ");
                    int z = scanner.nextInt();
                    z = z - 1;
                    if (z > -1 && z < 4) break;
                }
                z = ShipPlacement.getShipDirection(z, 3);
                if ((yy + z < GameBoard.BOARD_SIZE && yy + z > -1 && ShipPlacement.isHorizontal()) || 
                    (xx + z < GameBoard.BOARD_SIZE && xx + z > -1 && !ShipPlacement.isHorizontal())) break;
                else System.out.println("\nНевозможно расположить корабль \n");
            }
            z = z / 2;
            board[xx][yy] = 4;
            if (ShipPlacement.isHorizontal()) {
                board[xx][yy + z] = 4;
                board[xx][yy + 2 * z] = 4;
                board[xx][yy + 3 * z] = 4;
            } else {
                board[xx + z][yy] = 4;
                board[xx + 2 * z][yy] = 4;
                board[xx + 3 * z][yy] = 4;
            }
        }
    }
    
    public int[][] getBoard() {
        return board;
    }
} 