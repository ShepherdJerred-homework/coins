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
    }

    public static int max(int l, int r) {
        return l > r ? l : r;
    }

    public static int min(int l, int r) {
        return l < r ? l : r;
    }

    public static int solve(int[] coins, int startIndex, int endIndex, int[][] memo) {
        if (startIndex > endIndex) {
            return 0;
        } else if (memo[startIndex][endIndex] != 0) {
            return memo[startIndex][endIndex];
        } else {
            int start = coins[startIndex] + min(solve(coins, startIndex + 2, endIndex, memo), solve(coins, startIndex + 1, endIndex - 1, memo));
            int end = coins[endIndex] + min(solve(coins, startIndex + 1, endIndex - 1, memo), solve(coins, startIndex, endIndex - 2, memo));
            int max = max(start, end);
            memo[startIndex][endIndex] = max;
            return max;
        }
    }
}
