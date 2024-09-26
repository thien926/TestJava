import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Integer> arr = Arrays.asList(1,3,5,2,7,8,9,232,43,65);

        // Cách 1
        List<Integer> clone1 = new ArrayList<>(arr);
        clone1 = clone1.stream().filter(s -> s % 2 == 0).collect(Collectors.toList());
        System.out.println("\nCách 1\n");
        clone1.forEach(System.out::println);

        // Cách 2
        List<Integer> clone2 = new ArrayList<>(arr);
        clone2.removeIf(s -> s % 2 != 0);
        System.out.println("\nCách 2\n");
        clone1.forEach(System.out::println);
    }
}
