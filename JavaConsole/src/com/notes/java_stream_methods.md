Dưới đây là danh sách đầy đủ tất cả các phương thức trong Stream của Java, kèm theo ví dụ cụ thể và các comment mô tả input và output.

### Tạo Stream

1. **`Stream.of(T... values)`**

   ```java
   // Input: "a", "b", "c"
   Stream<String> stream = Stream.of("a", "b", "c");
   // Output: Stream containing "a", "b", "c"
   ```

2. **`Collection.stream()`**

   ```java
   List<String> list = Arrays.asList("John", "Jane", "Alice");
   Stream<String> streamFromList = list.stream();
   // Output: Stream containing "John", "Jane", "Alice"
   ```

3. **`Arrays.stream(T[] array)`**

   ```java
   String[] array = {"A", "B", "C"};
   Stream<String> streamFromArray = Arrays.stream(array);
   // Output: Stream containing "A", "B", "C"
   ```

4. **`Files.lines(Path path)`**

   ```java
   Path path = Paths.get("file.txt");
   Stream<String> lines = Files.lines(path);
   // Output: Stream containing lines from the file
   ```

### Phép toán trung gian (Intermediate Operations)

1. **`filter(Predicate<? super T> predicate)`**

   ```java
   List<String> filtered = list.stream()
                                .filter(name -> name.startsWith("J"))
                                .collect(Collectors.toList());
   // Input: ["John", "Jane", "Alice"]
   // Output: ["John", "Jane"]
   ```

2. **`map(Function<? super T, ? extends R> mapper)`**

   ```java
   List<Integer> lengths = list.stream()
                                .map(String::length)
                                .collect(Collectors.toList());
   // Input: ["John", "Jane", "Alice"]
   // Output: [4, 4, 5]
   ```

3. **`flatMap(Function<? super T, ? extends Stream<? extends R>> mapper)`**

   ```java
   List<List<String>> listOfLists = Arrays.asList(Arrays.asList("a", "b"), Arrays.asList("c", "d"));
   List<String> flatList = listOfLists.stream()
                                       .flatMap(Collection::stream)
                                       .collect(Collectors.toList());
   // Input: [["a", "b"], ["c", "d"]]
   // Output: ["a", "b", "c", "d"]
   ```

4. **`sorted()`**

   ```java
   List<String> sortedList = list.stream()
                                  .sorted()
                                  .collect(Collectors.toList());
   // Input: ["John", "Jane", "Alice"]
   // Output: ["Alice", "Jane", "John"]
   ```

5. **`sorted(Comparator<? super T> comparator)`**

   ```java
   List<String> sortedByLength = list.stream()
                                      .sorted(Comparator.comparing(String::length))
                                      .collect(Collectors.toList());
   // Input: ["John", "Jane", "Alice"]
   // Output: ["John", "Jane", "Alice"]
   ```

6. **`distinct()`**

   ```java
   List<String> distinctList = Arrays.asList("a", "b", "a", "c").stream()
                                      .distinct()
                                      .collect(Collectors.toList());
   // Input: ["a", "b", "a", "c"]
   // Output: ["a", "b", "c"]
   ```

7. **`peek(Consumer<? super T> action)`**

   ```java
   list.stream()
       .peek(System.out::println)
       .filter(name -> name.startsWith("J"))
       .collect(Collectors.toList());
   // Input: ["John", "Jane", "Alice"]
   // Output: Prints "John" and "Jane" (while filtering)
   ```

8. **`limit(long maxSize)`**

   ```java
   List<String> limited = list.stream()
                               .limit(2)
                               .collect(Collectors.toList());
   // Input: ["John", "Jane", "Alice"]
   // Output: ["John", "Jane"]
   ```

9. **`skip(long n)`**

   ```java
   List<String> skipped = list.stream()
                               .skip(1)
                               .collect(Collectors.toList());
   // Input: ["John", "Jane", "Alice"]
   // Output: ["Jane", "Alice"]
   ```

### Phép toán kết thúc (Terminal Operations)

1. **`forEach(Consumer<? super T> action)`**

   ```java
   list.stream().forEach(System.out::println);
   // Input: ["John", "Jane", "Alice"]
   // Output: Prints each name on a new line
   ```

2. **`count()`**

   ```java
   long count = list.stream().count();
   // Input: ["John", "Jane", "Alice"]
   // Output: 3
   ```

3. **`collect(Collector<? super T, A, R> collector)`**

   ```java
   List<String> collectedList = list.stream()
                                     .collect(Collectors.toList());
   // Input: ["John", "Jane", "Alice"]
   // Output: ["John", "Jane", "Alice"]
   ```

4. **`reduce(T identity, BiFunction<T, ? super U, T> accumulator)`**

   ```java
   String concatenated = list.stream()
                              .reduce("", (s1, s2) -> s1 + s2);
   // Input: ["John", "Jane", "Alice"]
   // Output: "JohnJaneAlice"
   ```

5. **`reduce(BinaryOperator<T> accumulator)`**

   ```java
   Optional<String> maxLength = list.stream()
                                     .reduce((s1, s2) -> s1.length() > s2.length() ? s1 : s2);
   // Input: ["John", "Jane", "Alice"]
   // Output: "Alice"
   ```

6. **`min(Comparator<? super T> comparator)`**

   ```java
   Optional<String> min = list.stream()
                               .min(Comparator.naturalOrder());
   // Input: ["John", "Jane", "Alice"]
   // Output: "Alice"
   ```

7. **`max(Comparator<? super T> comparator)`**

   ```java
   Optional<String> max = list.stream()
                               .max(Comparator.naturalOrder());
   // Input: ["John", "Jane", "Alice"]
   // Output: "John"
   ```

8. **`anyMatch(Predicate<? super T> predicate)`**

   ```java
   boolean hasJohn = list.stream().anyMatch(name -> name.equals("John"));
   // Input: ["John", "Jane", "Alice"]
   // Output: true
   ```

9. **`allMatch(Predicate<? super T> predicate)`**

   ```java
   boolean allStartWithA = list.stream().allMatch(name -> name.startsWith("A"));
   // Input: ["John", "Jane", "Alice"]
   // Output: false
   ```

10. **`noneMatch(Predicate<? super T> predicate)`**

    ```java
    boolean noneStartsWithZ = list.stream().noneMatch(name -> name.startsWith("Z"));
    // Input: ["John", "Jane", "Alice"]
    // Output: true
    ```

11. **`findFirst()`**

    ```java
    Optional<String> first = list.stream().findFirst();
    // Input: ["John", "Jane", "Alice"]
    // Output: "John"
    ```

12. **`findAny()`**

    ```java
    Optional<String> any = list.stream().findAny();
    // Input: ["John", "Jane", "Alice"]
    // Output: "John" or "Jane" or "Alice" (bất kỳ)
    ```

13. **`toArray()`**

    ```java
    String[] array = list.stream().toArray(String[]::new);
    // Input: ["John", "Jane", "Alice"]
    // Output: ["John", "Jane", "Alice"]
    ```

### Các phương thức khác trong Stream

1. **`boxed()`**

   ```java
   IntStream intStream = IntStream.range(1, 5);
   Stream<Integer> boxedStream = intStream.boxed();
   // Input: 1, 2, 3, 4
   // Output: Stream containing 1, 2, 3, 4
   ```

2. **`mapToInt(ToIntFunction<? super T> mapper)`**

   ```java
   List<String> numberStrings = Arrays.asList("1", "2", "3");
   IntStream intStream = numberStrings.stream().mapToInt(Integer::parseInt);
   // Input: ["1", "2", "3"]
   // Output: IntStream containing 1, 2, 3
   ```

3. **`mapToDouble(ToDoubleFunction<? super T> mapper)`**

   ```java
   List<String> decimalStrings = Arrays.asList("1.0", "2.5", "3.3");
   DoubleStream doubleStream = decimalStrings.stream().mapToDouble(Double::parseDouble);
   // Input: ["1.0", "2.5", "3.3"]
   // Output: DoubleStream containing 1.0, 2.5, 3.3
   ```

4. **`mapToLong(ToLongFunction<? super T>

 mapper)`**

   ```java
   List<String> longStrings = Arrays.asList("1", "2", "3");
   LongStream longStream = longStrings.stream().mapToLong(Long::parseLong);
   // Input: ["1", "2", "3"]
   // Output: LongStream containing 1, 2, 3
   ```

5. **`flatMapToInt`**, **`flatMapToDouble`**, **`flatMapToLong`**

   ```java
   List<List<String>> lists = Arrays.asList(Arrays.asList("1", "2"), Arrays.asList("3", "4"));
   IntStream flatIntStream = lists.stream()
                                   .flatMapToInt(list -> list.stream().mapToInt(Integer::parseInt));
   // Input: [["1", "2"], ["3", "4"]]
   // Output: IntStream containing 1, 2, 3, 4
   ```

### Kết luận

Trên đây là danh sách các phương thức của Stream trong Java cùng với ví dụ và comment mô tả cho mỗi phương thức. Nếu bạn có thắc mắc hoặc cần thêm thông tin chi tiết về từng phương thức, hãy cho tôi biết!