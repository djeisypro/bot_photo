package ru.tpu.javaEElabs;

public class ComputerPlayer {
    private int[][] board;
    
    public ComputerPlayer() {
        board = new int[GameBoard.BOARD_SIZE][GameBoard.BOARD_SIZE];
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
        placeFourDeckShip();
        placeThreeDeckShips();
        placeTwoDeckShips();
        placeOneDeckShips();
    }
    
    private void placeFourDeckShip() {
        int m = (int) (Math.random() * GameBoard.BOARD_SIZE);
        int n = (int) (Math.random() * GameBoard.BOARD_SIZE);
        while (true) {
            int z = ShipPlacement.getShipDirection((int) (Math.random() * 4), 3);
            if (((n + z < GameBoard.BOARD_SIZE && n + z > -1 && ShipPlacement.isHorizontal()) || 
                 (m + z < GameBoard.BOARD_SIZE && m + z > -1 && !ShipPlacement.isHorizontal())) && 
                (ShipPlacement.canPlaceShip(m, n, board))) {
                break;
            }
        }
        int z = ShipPlacement.getShipDirection((int) (Math.random() * 4), 3) / 2;
        board[m][n] = 4;
        if (ShipPlacement.isHorizontal()) {
            board[m][n + z] = 4;
            board[m][n + 2 * z] = 4;
            board[m][n + 3 * z] = 4;
        } else {
            board[m + z][n] = 4;
            board[m + 2 * z][n] = 4;
            board[m + 3 * z][n] = 4;
        }
    }
    
    private void placeThreeDeckShips() {
        for (int l = 1; l <= 2; l++) {
            int m = (int) (Math.random() * GameBoard.BOARD_SIZE);
            int n = (int) (Math.random() * GameBoard.BOARD_SIZE);
            while (true) {
                int z = ShipPlacement.getShipDirection((int) (Math.random() * 4), 2);
                if (((n + z < GameBoard.BOARD_SIZE && n + z > -1 && ShipPlacement.isHorizontal()) || 
                     (m + z < GameBoard.BOARD_SIZE && m + z > -1 && !ShipPlacement.isHorizontal())) && 
                    (ShipPlacement.canPlaceShip(m, n, board))) {
                    break;
                }
            }
            int z = ShipPlacement.getShipDirection((int) (Math.random() * 4), 2) / 2;
            board[m][n] = 3;
            if (ShipPlacement.isHorizontal()) {
                board[m][n + z] = 3;
                board[m][n + 2 * z] = 3;
            } else {
                board[m + z][n] = 3;
                board[m + 2 * z][n] = 3;
            }
        }
    }
    
    private void placeTwoDeckShips() {
        for (int l = 1; l <= 3; l++) {
            int m = (int) (Math.random() * GameBoard.BOARD_SIZE);
            int n = (int) (Math.random() * GameBoard.BOARD_SIZE);
            int m1 = m;
            int n1 = n;
            int z = ShipPlacement.getShipDirection((int) (Math.random() * 4), 1);
            if (ShipPlacement.isHorizontal()) {
                n1 = n1 + z;
            } else {
                m1 = m1 + z;
            }
            if (((n + z < GameBoard.BOARD_SIZE && n + z > -1 && ShipPlacement.isHorizontal()) || 
                 (m + z < GameBoard.BOARD_SIZE && m + z > -1 && !ShipPlacement.isHorizontal())) && 
                (ShipPlacement.canPlaceShip(m, n, board) && ShipPlacement.canPlaceShip(m1, n1, board))) {
                z = z / 2;
                board[m][n] = 2;
                board[m1][n1] = 2;
            }
        }
    }
    
    private void placeOneDeckShips() {
        for (int l = 1; l <= 4; l++) {
            int m = (int) (Math.random() * GameBoard.BOARD_SIZE);
            int n = (int) (Math.random() * GameBoard.BOARD_SIZE);
            if (ShipPlacement.canPlaceShip(m, n, board)) {
                board[m][n] = 1;
            }
        }
    }
    
    public int[][] getBoard() {
        return board;
    }
} 