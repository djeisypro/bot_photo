package ru.tpu.javaEElabs;

public class ShipPlacement {
    private static boolean c = true;

    // Проверка возможности размещения корабля
    public static boolean canPlaceShip(int x, int y, int[][] board) {
        int i, j;
        boolean flag = true;
        for (i = x - 1; i <= x + 1; i++)
            for (j = y - 1; j <= y + 1; j++)
                if (i > -1 && i < GameBoard.BOARD_SIZE && j > -1 && j < GameBoard.BOARD_SIZE) {
                    if (board[i][j] != 0) {
                        flag = false;
                        break;
                    }
                }
        return flag;
    }

    // Определение направления корабля
    public static int getShipDirection(int z, int v) {
        int a = 0;
        switch (z) {
            case 0:
                a = v;
                c = false;
                break;
            case 1:
                a = -v;
                c = true;
                break;
            case 2:
                a = -v;
                c = false;
                break;
            case 3:
                a = v;
                c = true;
                break;
        }
        return a;
    }

    public static boolean isHorizontal() {
        return c;
    }
} 