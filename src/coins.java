// coins
// Jerred Shepherd

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class coins {
    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File("coins.in");
        File outputFile = new File("coins.out");

        Scanner scanner = new Scanner(inputFile);
        PrintWriter printWriter = new PrintWriter(outputFile);

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.equals("0")) {
                break;
            }

            Deque<Integer> coins = Stream.of(line)
                    .map(s -> s.split(" "))
                    .flatMap(Arrays::stream)
                    .map(Integer::valueOf)
                    .collect(Collectors.toCollection(ArrayDeque::new));

            System.out.println(coins);
        }
    }
}
