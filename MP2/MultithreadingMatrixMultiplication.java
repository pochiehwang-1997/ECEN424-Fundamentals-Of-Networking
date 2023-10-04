import java.util.Arrays;
import java.util.Random;

public class MultithreadingMatrixMultiplication extends Thread {
    private int[][] arr1;
    private int[][] arr2;
    private int[][] result;
    private int threadNum;

    public MultithreadingMatrixMultiplication(int[][] arr1, int[][] arr2, int threadNum, int[][] result) {
        this.arr1 = arr1;
        this.arr2 = arr2;
        this.threadNum = threadNum;
        this.result = result;
    }

    public void run() {
        System.out.println("Now running thread" + this.threadNum);
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 20; col++) {
                for (int i = 0; i < 20; i++) {
                    this.result[this.threadNum * 4 + row][col] += arr1[this.threadNum * 4 + row][i] * arr2[i][col];
                }
            }
        }
    }

    public int[][] getResult() {
        return this.result;
    }

    public static void main(String[] args) {
        // Create input array 1
        Random rand = new Random();
        int[][] arr1 = new int[20][20];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                arr1[i][j] = rand.nextInt(10);
            }
        }
        System.out.println("Input array 1:");
        System.out.println(Arrays.deepToString(arr1));

        // Create input array 2
        int[][] arr2 = new int[20][20];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                arr2[i][j] = rand.nextInt(10);
            }
        }
        System.out.println("Input array 2:");
        System.out.println(Arrays.deepToString(arr2));

        // Create result and thread array
        int[][] ans = new int[20][20];
        MultithreadingMatrixMultiplication[] threads = new MultithreadingMatrixMultiplication[5];

        // Matrix multiplication
        for (int i = 0; i < 5; i++) {
            threads[i] = new MultithreadingMatrixMultiplication(arr1, arr2, i, ans);
            threads[i].start();
        }

        // Wait until every thread is finished
        for (int i = 0; i < 5; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Print out result
        System.out.println("Result of multithreading:");
        System.out.println(Arrays.deepToString(ans));

        // Calculate the result by single thread to validate the result of
        // multithreading
        int[][] singleThreadResult = new int[20][20];
        for (int row = 0; row < 20; row++) {
            for (int col = 0; col < 20; col++) {
                for (int i = 0; i < 20; i++) {
                    singleThreadResult[row][col] += arr1[row][i] * arr2[i][col];
                }
            }
        }
        System.out.println("Result of singlethreading:");
        System.out.println(Arrays.deepToString(singleThreadResult));

        // Check if the result is correct
        System.out.println("Result of singlethreading is equal to result of multithreading: "
                + String.valueOf(Arrays.deepEquals(ans, singleThreadResult)));

    }
}
