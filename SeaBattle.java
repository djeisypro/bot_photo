package ru.tpu.javaEElabs;

import java.util.Scanner;
import java.util.*;
import java.lang.*;

public class SeaBattle {
    static int num = 10;
    static int[][] A = new int[num][num];
    static int[][] B = new int[num][num];
    static int[][] C = new int[num][num];
    static boolean c = true;

    // Вывод поля машины
    static void poleM(int[][] A, int[][] B) {
        System.out.println("Поле машины");
        System.out.print("  ");
        for (int j = 0; j < num; j++) {
            System.out.print(" " + j + " ");
        }
        System.out.println();
        for (int p = 0; p < num; p++) {
            System.out.print(p + " ");
            for (int j = 0; j < num; j++) {
                if (A[p][j] < 10) System.out.print(" " + A[p][j] + " ");
                else System.out.print(A[p][j] + " ");
            }
            System.out.println();
        }
        System.out.println("                                               ");
    }

    // Вывод поля второго игрока
    static void poleP2(int[][] C, int[][] B) {
        System.out.println("Поле второго игрока");
        System.out.print("  ");
        for (int j = 0; j < num; j++) {
            System.out.print(" " + j + " ");
        }
        System.out.println();
        for (int p = 0; p < num; p++) {
            System.out.print(p + " ");
            for (int j = 0; j < num; j++) {
                if (C[p][j] < 10) System.out.print(" " + C[p][j] + " ");
                else System.out.print(C[p][j] + " ");
            }
            System.out.println();
        }
        System.out.println("                                               ");
    }

    // Вывод поля игрока
    static void poleP(int[][] A, int[][] B) {
        System.out.println("Поле игрока");
        System.out.print("  ");
        for (int j = 0; j < num; j++) {
            System.out.print(" " + j + " ");
        }
        System.out.println();
        for (int l = 0; l < num; l++) {
            System.out.print(l + " ");
            for (int j = 0; j < num; j++) {
                if (B[l][j] < 10) System.out.print(" " + B[l][j] + " ");
                else System.out.print(B[l][j] + " ");
            }
            System.out.println();
        }
        System.out.println("                                               ");
    }

    // Проверка возможности размещения корабля
    static boolean prov(int x, int y, int[][] C) {
        int i, j;
        boolean flag = true;
        for (i = x - 1; i <= x + 1; i++)
            for (j = y - 1; j <= y + 1; j++)
                if (i > -1 && i < num && j > -1 && j < num) {
                    if (C[i][j] != 0) {
                        flag = false;
                        break;
                    }
                }
        return flag;
    }

    // Определение направления корабля
    static int storona(int z, int v) {
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

    // Игра против человека
    public static void chelovek() {
        Scanner in = new Scanner(System.in);
        int x = 0, y = 0, x1 = 0, y1 = 0, z = 0, aa = 0, xx = 0, yy = 0, xx1 = 0, yy1 = 0, xx2 = 0, yy2 = 0;
        int a = 0;
        System.out.println("Заполнение поля 1-м игроком");
        
        // Размещение однопалубных кораблей
        System.out.println("Введите координаты (через пробел)\nоднопалубных кораблей (сначала координаты по горизонтали)");
        poleP(A, B);
        for (int i = 0; i < 4; i++) {
            while (true) {
                System.out.println((i + 1) + "-й корабль");
                xx = in.nextInt();
                yy = in.nextInt();
                if (xx < 0 || xx > num - 1 || yy < 0 || yy > num - 1) continue;
                if (prov(xx, yy, B)) break;
                else
                    System.out.println("\nНевозможно расположить корабль \n");
            }
            B[xx][yy] = 1;
        }

        // Размещение двухпалубных кораблей
        System.out.println("Введите координаты (через пробел)\nдвухпалубных кораблей (сначала координаты по горизонтали)");
        poleP(A, B);
        for (int i = 0; i <= 2; i++) {
            while (true) {
                System.out.println((i + 1) + "-й корабль ");
                xx = in.nextInt();
                yy = in.nextInt();
                if (xx < 0 || xx > num - 1 || yy < 0 || yy > num - 1) continue;
                xx1 = xx;
                yy1 = yy;
                while (true) {
                    System.out.println("Направление " + (i + 1) + "-го корабля (1:\\/; 2: <-; 3: /\\; 4: ->): ");
                    z = in.nextInt();
                    z = z - 1;
                    if (z > -1 && z < 4) break;
                }
                z = storona(z, 1);
                if (c) yy1 = yy1 + z;
                else
                    xx1 = xx1 + z;
                if (((yy + z < 10 && yy + z > -1 && c == true) || (xx + z < 10 && xx + z > -1 && c == false)) && (prov(xx, yy, B) && prov(xx1, yy1, B)))
                    break;
                else System.out.println("\nНевозможно расположить корабль \n");
            }
            z = z / 2;
            B[xx][yy] = 2;
            B[xx1][yy1] = 2;
        }

        // Размещение трехпалубных кораблей
        System.out.println("Введите координаты (через пробел)\nтрехпалубных кораблей (сначала координаты по горизонтали)");
        poleP(A, B);
        for (int j = 0; j < 2; j++) {
            while (true) {
                System.out.println((j + 1) + "-й корабль");
                xx = in.nextInt();
                yy = in.nextInt();
                if (xx < 0 || xx > num - 1 || yy < 0 || yy > num - 1) continue;
                while (true) {
                    System.out.println("Направление " + (j + 1) + "-го корабля (1:\\/; 2: <-; 3: /\\; 4: ->): ");
                    z = in.nextInt();
                    z = z - 1;
                    if (z > -1 && z < 4) break;
                }
                z = storona(z, 2);
                if ((yy + z < 10 && yy + z > -1 && c == true) || (xx + z < 10 && xx + z > -1 && c == false)) break;
                else System.out.println("\nНевозможно расположить корабль \n");
            }
            z = z / 2;
            B[xx][yy] = 3;
            if (c) {
                B[xx][yy + z] = 3;
                B[xx][yy + 2 * z] = 3;
            } else {
                B[xx + z][yy] = 3;
                B[xx + 2 * z][yy] = 3;
            }
        }

        // Размещение четырехпалубных кораблей
        System.out.println("Введите координаты (через пробел)\nчетырехпалубных кораблей (сначала координаты по горизонтали)");
        poleP(A, B);
        for (int j = 0; j < 1; j++) {
            while (true) {
                System.out.println((j + 1) + "-й корабль");
                xx = in.nextInt();
                yy = in.nextInt();
                if (xx < 0 || xx > num - 1 || yy < 0 || yy > num - 1) continue;
                while (true) {
                    System.out.println("Направление " + (j + 1) + "-го корабля (1:\\/; 2: <-; 3: /\\; 4: ->): ");
                    z = in.nextInt();
                    z = z - 1;
                    if (z > -1 && z < 4) break;
                }
                z = storona(z, 3);
                if ((yy + z < 10 && yy + z > -1 && c == true) || (xx + z < 10 && xx + z > -1 && c == false)) break;
                else System.out.println("\nНевозможно расположить корабль \n");
            }
            z = z / 2;
            B[xx][yy] = 4;
            if (c) {
                B[xx][yy + z] = 4;
                B[xx][yy + 2 * z] = 4;
                B[xx][yy + 3 * z] = 4;
            } else {
                B[xx + z][yy] = 4;
                B[xx + 2 * z][yy] = 4;
                B[xx + 3 * z][yy] = 4;
            }
        }
    }

    // Игра против компьютера
    public static void mashine() {
        Scanner in = new Scanner(System.in);
        boolean c = false;
        int m = 0, n = 0, m1 = 0, n1 = 0, x = 0, y = 0, x1 = 0, y1 = 0, z = 0, aa = 0, n2 = 0, m2 = 0, xx = 0, yy = 0, xx1 = 0, yy1 = 0;
        int a = 0;

        // Размещение четырехпалубного корабля
        for (int l = 1; l <= 1; l++) {
            m = (int) (Math.random() * num);
            n = (int) (Math.random() * num);
            while (true) {
                z = storona((int) (Math.random() * 4), 3);
                if (((n + z < 10 && n + z > -1 && c == true) || (m + z < 10 && m + z > -1 && c == false)) && (prov(m, n, A) && prov(m1, n1, A)))
                    break;
            }
            z = z / 2;
            A[m][n] = 4;
            if (c) {
                A[m][n + z] = 4;
                A[m][n + 2 * z] = 4;
                A[m][n + 3 * z] = 4;
            } else {
                A[m + z][n] = 4;
                A[m + 2 * z][n] = 4;
                A[m + 3 * z][n] = 4;
            }
        }

        // Размещение трехпалубных кораблей
        for (int l = 1; l <= 2; l++) {
            m = (int) (Math.random() * num);
            n = (int) (Math.random() * num);
            while (true) {
                z = storona((int) (Math.random() * 4), 2);
                if (((n + z < 10 && n + z > -1 && c == true) || (m + z < 10 && m + z > -1 && c == false)) && (prov(m, n, A) && prov(m1, n1, A)))
                    break;
            }
            z = z / 2;
            A[m][n] = 3;
            if (c) {
                A[m][n + z] = 3;
                A[m][n + 2 * z] = 3;
            } else {
                A[m + z][n] = 3;
                A[m + 2 * z][n] = 3;
            }
        }

        // Размещение двухпалубных кораблей
        for (int l = 1; l <= 3; l++) {
            while (true) {
                m = (int) (Math.random() * num);
                n = (int) (Math.random() * num);
                m1 = m;
                n1 = n;
                z = storona((int) (Math.random() * 4), 1);
                if (c) {
                    n1 = n1 + z;
                } else {
                    m1 = m1 + z;
                }
                if (((n + z < 10 && n + z > -1 && c == true) || (m + z < 10 && m + z > -1 && c == false)) && (prov(m, n, A) && prov(m1, n1, A)))
                    break;
            }
            z = z / 2;
            A[m][n] = 2;
            A[m1][n1] = 2;
        }

        // Размещение однопалубных кораблей
        for (int l = 1; l <= 4; l++) {
            while (true) {
                m = (int) (Math.random() * num);
                n = (int) (Math.random() * num);
                if (prov(m, n, A)) break;
            }
            A[m][n] = 1;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        
        // Инициализация поля
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                A[i][j] = 0;
            }
        }

        System.out.println("Выберите вариант игры \n(0 - машина, 1 - человек) ");
        int h = in.nextInt();
        switch (h) {
            case 0:
                mashine();
                break;
            case 1:
                chelovek();
                break;
            default:
                break;
        }

        // Основной игровой цикл
        poleP(A, B);
        System.out.println("Игра началась!");
        int number_mashine = 0, number_chelovek = 0, number_vistmash = 0, number_vistchel = 0;

        while (number_mashine < 4 || number_chelovek < 4 || number_vistmash <= num * num || number_vistchel <= num * num) {
            // Ход игрока
            for (int i = 0; i < 1; i++) {
                System.out.println("Введите координаты куда будете стрелять, " + (i + 1) + " выстрел\n(сначала координаты по горизонтали)");
                int q = in.nextInt();
                int w = in.nextInt();
                number_vistchel += 1;
                if (A[q][w] != 0 && A[q][w] != 8 && A[q][w] != 9) {
                    System.out.println("Попадание!");
                    A[q][w] = 9;
                    number_chelovek += 1;
                } else {
                    System.out.println("Мимо");
                    A[q][w] = 8;
                    continue;
                }
            }

            // Ход компьютера
            for (int l = 0; ; l++) {
                m = (int) (Math.random() * num);
                n = (int) (Math.random() * num);
                if (B[m][n] != 8 && B[m][n] != 9) break;
            }
            number_vistmash += 1;
            System.out.println("Теперь очередь машины");
            if (B[m][n] != 0 && B[m][n] != 8 && B[m][n] != 9) {
                if (prov(m, n, B)) break;
                System.out.println("Машина попадает!");
                B[m][n] = 9;
                poleP(A, B);
                number_mashine += 1;
            } else {
                System.out.println("Машина не попала");
                if (B[m][n] != 8 && B[m][n] != 9) B[m][n] = 8;
                poleP(A, B);
                continue;
            }

            // Проверка окончания игры
            if (number_mashine >= 4) {
                System.out.println("Игра окончена! Победитель машина!!!");
                System.exit(0);
            }
            if (number_chelovek >= 4) {
                System.out.println("Игра окончена! Победитель игрок!!!");
                System.exit(0);
            }
            if (number_vistmash >= num * num) {
                System.out.println("Игра окончена! Израсходовано количество выстрелов равное числу клеток(для машины)");
                break;
            }
            if (number_vistchel >= num * num) {
                System.out.println("Игра окончена! Израсходовано количество выстрелов равное числу клеток(для человека)");
                break;
            }
        }
        System.out.println("number_vistchel = " + number_vistchel + "\nnumber_vistmash = " + number_vistmash);
    }
} 