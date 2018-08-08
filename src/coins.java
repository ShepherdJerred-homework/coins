// coins
// Jerred Shepherd

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

public class coins {
    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File("coins.in");
        File outputFile = new File("coins.out");

        Scanner scanner = new Scanner(inputFile);
        PrintWriter printWriter = new PrintWriter(outputFile);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.equals("0")) {
                break;
            }

            int[] coins = Stream.of(line)
                    .map(s -> s.split(" "))
                    .flatMap(Arrays::stream)
                    .skip(1)
                    .mapToInt(Integer::parseInt)
                    .toArray();

            int[][] memo = new int[coins.length][coins.length];

            int opt = solve(coins, 0, coins.length - 1, memo);

            System.out.println(opt);
            printWriter.println(opt);
        }

        printWriter.close();
    }

    public static int max(int l, int r) {
        return l > r ? l : r;
    }

    public static int min(int l, int r) {
        return l < r ? l : r;
    }

    /*
     * This algorithm will look between the bounds of two indexes and return the minimum points you can receive assuming
     * the opponent plays optimally
     */
    public static int solve(int[] coins, int startIndex, int endIndex, int[][] memo) {
        if (startIndex > endIndex) {
            return 0;
        }

        if (memo[startIndex][endIndex] != 0) {
            return memo[startIndex][endIndex];
        } else {
            // Assume we take the coin to the left and our opponent takes the next coin to the left
            int ll = solve(coins, startIndex + 2, endIndex, memo);
            // Assume we take the coin to the left and our opponent takes the coin to the right
            int lr = solve(coins, startIndex + 1, endIndex - 1, memo);

            // We will add the coin we took, along with the minimum of the two left moves
            // We take the minimum because our opponent is also playing optimally
            int left = coins[startIndex] + min(ll, lr);

            // Assume we take the coin to the right and our opponent takes the next coin to the right
            int rr = solve(coins, startIndex, endIndex - 2, memo);
            // Assume we take the coin to the right and our opponent takes the coin to the left
            int rl = solve(coins, startIndex + 1, endIndex - 1, memo);

            // We will add the coin we took, along with the minimum of the two right moves
            // We take the minimum because our opponent is also playing optimally
            int right = coins[endIndex] + min(rr, rl);

            // Get the move the will ultimately yield more coins between the left and right move
            int max = max(left, right);
            memo[startIndex][endIndex] = max;
            return max;
        }
    }
}
