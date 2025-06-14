package ru.tpu.javaEElabs;

public class GameBoard {
    static final int BOARD_SIZE = 10;
    
    // Вывод поля машины
    public static void printMachineBoard(int[][] A, int[][] B) {
        System.out.println("Поле машины");
        System.out.print("  ");
        for (int j = 0; j < BOARD_SIZE; j++) {
            System.out.print(" " + j + " ");
        }
        System.out.println();
        for (int p = 0; p < BOARD_SIZE; p++) {
            System.out.print(p + " ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (A[p][j] < 10) System.out.print(" " + A[p][j] + " ");
                else System.out.print(A[p][j] + " ");
            }
            System.out.println();
        }
        System.out.println("                                               ");
    }

    // Вывод поля второго игрока
    public static void printSecondPlayerBoard(int[][] C, int[][] B) {
        System.out.println("Поле второго игрока");
        System.out.print("  ");
        for (int j = 0; j < BOARD_SIZE; j++) {
            System.out.print(" " + j + " ");
        }
        System.out.println();
        for (int p = 0; p < BOARD_SIZE; p++) {
            System.out.print(p + " ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (C[p][j] < 10) System.out.print(" " + C[p][j] + " ");
                else System.out.print(C[p][j] + " ");
            }
            System.out.println();
        }
        System.out.println("                                               ");
    }

    // Вывод поля игрока
    public static void printPlayerBoard(int[][] A, int[][] B) {
        System.out.println("Поле игрока");
        System.out.print("  ");
        for (int j = 0; j < BOARD_SIZE; j++) {
            System.out.print(" " + j + " ");
        }
        System.out.println();
        for (int l = 0; l < BOARD_SIZE; l++) {
            System.out.print(l + " ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (B[l][j] < 10) System.out.print(" " + B[l][j] + " ");
                else System.out.print(B[l][j] + " ");
            }
            System.out.println();
        }
        System.out.println("                                               ");
    }
} 