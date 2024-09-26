import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Integer> arr = Arrays.asList(1, 3, 5, 2, 7, 8, 9, 232, 43, 65);

        // Cách 1
        List<Integer> clone1 = new ArrayList<>(arr);
        int sum1 = 0;
        for (Integer s : clone1) {
            sum1 += s;
        }
        System.out.println("\nCách 1 : " + sum1 + "\n");

        // Cách 2
        int sum2 = clone1.stream().mapToInt(Integer::intValue).sum();
        System.out.println("\nCách 2 : " + sum2 + "\n");

        // Cách 3
        int sum3 = clone1.stream().reduce(0, (s1, s2) -> (s1 + s2));
        System.out.println("\nCách 3 : " + sum3 + "\n");

    }
}
