public class WATER {
    public static void main(String[] args) {
        int[][] A = new int[5][5];
        
        // Заполнение массива
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                A[i][j] = i + j;
            }
        }
        
        // Вывод массива
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                if(A[i][j] < 10) {
                    System.out.print(" " + A[i][j] + " ");
                } else {
                    System.out.print(A[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
} 