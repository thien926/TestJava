import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> arr = Arrays.asList("a", "a", "b", "c", "d", "b", "a", "f", "g", "h", "h", "h", "h", "h", "h",
                "h");
        String stringFind = "a";

        // Cách 1
        List<String> clone1 = new ArrayList<>(arr);
        long startTime1 = System.nanoTime();
        long count1 = clone1.stream().filter(s -> stringFind.equals(s)).count();
        long endTime1 = System.nanoTime();
        System.out.println("Cách 1: " + count1);
        System.out.println("time: " + (endTime1 - startTime1) + " ns\n");

        // Cách 2
        long startTime2 = System.nanoTime();
        int count2 = Collections.frequency(arr, stringFind);
        long endTime2 = System.nanoTime();
        System.out.println("Cách 2: " + count2);
        System.out.println("time: " + (endTime2 - startTime2) + " ns\n");
        
        // Cách 1: 3
        // time: 1656140 ns

        // Cách 2: 3
        // time: 34750 ns

    }
}


// import java.util.*;

// class Entity {
//     private String name;

//     public Entity(String name) {
//         this.name = name;
//     }

//     public String getName() {
//         return name;
//     }

//     @Override
//     public boolean equals(Object obj) {
//         if (this == obj)
//             return true;
//         if (!(obj instanceof Entity))
//             return false;
//         Entity entity = (Entity) obj;
//         return name.equals(entity.name);
//     }

//     @Override
//     public int hashCode() {
//         return Objects.hash(name);
//     }

//     @Override
//     public String toString() {
//         return name;
//     }
// }

// public class Main {
//     public static void main(String[] args) {
//         // Tạo danh sách các Entity
//         List<Entity> arr = Arrays.asList(
//                 new Entity("a"),
//                 new Entity("a"),
//                 new Entity("b"),
//                 new Entity("c"),
//                 new Entity("d"),
//                 new Entity("b"),
//                 new Entity("a"),
//                 new Entity("f"),
//                 new Entity("g"),
//                 new Entity("h"),
//                 new Entity("h"),
//                 new Entity("h"),
//                 new Entity("h"),
//                 new Entity("h"),
//                 new Entity("h"),
//                 new Entity("h"));

//         Entity entityFind = new Entity("a");

//         // Cách 1
//         List<Entity> clone1 = new ArrayList<>(arr);
//         long startTime1 = System.nanoTime();
//         long count1 = clone1.stream().filter(entity -> entityFind.equals(entity)).count();
//         long endTime1 = System.nanoTime();
//         System.out.println("Cách 1: " + count1);
//         System.out.println("time: " + (endTime1 - startTime1) + " ns\n");

//         // Cách 2
//         long startTime2 = System.nanoTime();
//         int count2 = Collections.frequency(arr, entityFind);
//         long endTime2 = System.nanoTime();
//         System.out.println("Cách 2: " + count2);
//         System.out.println("time: " + (endTime2 - startTime2) + " ns\n");

//         // Cách 1: 3
//         // time: 1674990 ns

//         // Cách 2: 3
//         // time: 31850 ns
//     }
// }
