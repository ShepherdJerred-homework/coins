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

            int sum = Arrays.stream(coins).sum();

//            int opt = findMaxValue(coins, 0, coins.length - 1, 0);

            int valueChoosingLeft = findMaxValue(coins, 1, coins.length - 1, 0);
            int valueChoosingRight = findMaxValue(coins, 0, coins.length - 2, 0);

            int betterChoice = valueChoosingLeft > valueChoosingRight ? valueChoosingLeft : valueChoosingRight;

            System.out.println(Arrays.toString(coins));
            System.out.println(String.format("l: %s\nr: %s", valueChoosingLeft, valueChoosingRight));
//            System.out.println(betterChoice);
//            System.out.println(opt);
        }
    }

    public static int findMaxValue(int[] coins, int startIndex, int endIndex, int acc) {
//        System.out.println(String.format("start: %s, end: %s", startIndex, endIndex));
        if (startIndex == endIndex || startIndex + 1 == endIndex) {
            if (coins[startIndex] > coins[endIndex]) {
                return acc + coins[startIndex];
            } else {
                return acc + coins[endIndex];
            }
        } else {
            int bestMove = 0;

            // cases


            // I take left, they take right
            int lr = findMaxValue(coins, startIndex + 1, endIndex - 1, acc + coins[startIndex]);
            bestMove = lr > bestMove ? lr : bestMove;

            // I take left, they take left
            int ll = findMaxValue(coins, startIndex + 2, endIndex, acc + coins[startIndex]);
            bestMove = ll > bestMove ? ll : bestMove;

            // I take right, they take left
            int rl = findMaxValue(coins, startIndex + 1, endIndex - 1, acc + coins[endIndex]);
            bestMove = rl > bestMove ? rl : bestMove;

            // I take right, they take right
            int rr = findMaxValue(coins, startIndex, endIndex - 2, acc + coins[endIndex]);
            bestMove = rr > bestMove ? rr : bestMove;

            return bestMove;
        }
    }
}
