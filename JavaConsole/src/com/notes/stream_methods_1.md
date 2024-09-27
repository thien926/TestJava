Dưới đây là danh sách đầy đủ các phương thức trong Stream của Java, kèm theo giải thích ý nghĩa, ví dụ cụ thể và các comment mô tả input và output.

### Tạo Stream

1. **`Stream.of(T... values)`**
   - **Ý nghĩa**: Tạo một Stream từ một số giá trị đã cho.
   ```java
   // Input: "a", "b", "c"
   Stream<String> stream = Stream.of("a", "b", "c");
   // Output: Stream containing "a", "b", "c"
   ```

2. **`Collection.stream()`**
   - **Ý nghĩa**: Tạo một Stream từ một Collection (ví dụ: List, Set).
   ```java
   List<String> list = Arrays.asList("John", "Jane", "Alice");
   Stream<String> streamFromList = list.stream();
   // Output: Stream containing "John", "Jane", "Alice"
   ```

3. **`Arrays.stream(T[] array)`**
   - **Ý nghĩa**: Tạo một Stream từ một mảng.
   ```java
   String[] array = {"A", "B", "C"};
   Stream<String> streamFromArray = Arrays.stream(array);
   // Output: Stream containing "A", "B", "C"
   ```

4. **`Files.lines(Path path)`**
   - **Ý nghĩa**: Tạo một Stream chứa các dòng trong một tệp tin.
   ```java
   Path path = Paths.get("file.txt");
   Stream<String> lines = Files.lines(path);
   // Output: Stream containing lines from the file
   ```

### Phép toán trung gian (Intermediate Operations)

1. **`filter(Predicate<? super T> predicate)`**
   - **Ý nghĩa**: Lọc các phần tử trong Stream dựa trên điều kiện được xác định bởi Predicate.
   ```java
   List<String> filtered = list.stream()
                                .filter(name -> name.startsWith("J"))
                                .collect(Collectors.toList());
   // Input: ["John", "Jane", "Alice"]
   // Output: ["John", "Jane"]
   ```

2. **`map(Function<? super T, ? extends R> mapper)`**
   - **Ý nghĩa**: Áp dụng một hàm cho mỗi phần tử trong Stream và trả về một Stream mới chứa kết quả.
   ```java
   List<Integer> lengths = list.stream()
                                .map(String::length)
                                .collect(Collectors.toList());
   // Input: ["John", "Jane", "Alice"]
   // Output: [4, 4, 5]
   ```

3. **`flatMap(Function<? super T, ? extends Stream<? extends R>> mapper)`**
   - **Ý nghĩa**: Áp dụng một hàm cho mỗi phần tử và "phẳng" kết quả, trả về một Stream mới.
   ```java
   List<List<String>> listOfLists = Arrays.asList(Arrays.asList("a", "b"), Arrays.asList("c", "d"));
   List<String> flatList = listOfLists.stream()
                                       .flatMap(Collection::stream)
                                       .collect(Collectors.toList());
   // Input: [["a", "b"], ["c", "d"]]
   // Output: ["a", "b", "c", "d"]
   ```

4. **`sorted()`**
   - **Ý nghĩa**: Sắp xếp các phần tử trong Stream theo thứ tự tự nhiên.
   ```java
   List<String> sortedList = list.stream()
                                  .sorted()
                                  .collect(Collectors.toList());
   // Input: ["John", "Jane", "Alice"]
   // Output: ["Alice", "Jane", "John"]
   ```

5. **`sorted(Comparator<? super T> comparator)`**
   - **Ý nghĩa**: Sắp xếp các phần tử trong Stream theo Comparator cung cấp.
   ```java
   List<String> sortedByLength = list.stream()
                                      .sorted(Comparator.comparing(String::length))
                                      .collect(Collectors.toList());
   // Input: ["John", "Jane", "Alice"]
   // Output: ["John", "Jane", "Alice"]
   ```

6. **`distinct()`**
   - **Ý nghĩa**: Loại bỏ các phần tử trùng lặp trong Stream.
   ```java
   List<String> distinctList = Arrays.asList("a", "b", "a", "c").stream()
                                      .distinct()
                                      .collect(Collectors.toList());
   // Input: ["a", "b", "a", "c"]
   // Output: ["a", "b", "c"]
   ```

7. **`peek(Consumer<? super T> action)`**
   - **Ý nghĩa**: Thực hiện một hành động trên từng phần tử trong Stream mà không thay đổi Stream.
   ```java
   list.stream()
       .peek(System.out::println)
       .filter(name -> name.startsWith("J"))
       .collect(Collectors.toList());
   // Input: ["John", "Jane", "Alice"]
   // Output: Prints "John" and "Jane" (while filtering)
   ```

8. **`limit(long maxSize)`**
   - **Ý nghĩa**: Giới hạn số lượng phần tử trong Stream.
   ```java
   List<String> limited = list.stream()
                               .limit(2)
                               .collect(Collectors.toList());
   // Input: ["John", "Jane", "Alice"]
   // Output: ["John", "Jane"]
   ```

9. **`skip(long n)`**
   - **Ý nghĩa**: Bỏ qua n phần tử đầu tiên trong Stream.
   ```java
   List<String> skipped = list.stream()
                               .skip(1)
                               .collect(Collectors.toList());
   // Input: ["John", "Jane", "Alice"]
   // Output: ["Jane", "Alice"]
   ```

### Phép toán kết thúc (Terminal Operations)

1. **`forEach(Consumer<? super T> action)`**
   - **Ý nghĩa**: Thực hiện một hành động cho mỗi phần tử trong Stream.
   ```java
   list.stream().forEach(System.out::println);
   // Input: ["John", "Jane", "Alice"]
   // Output: Prints each name on a new line
   ```

2. **`count()`**
   - **Ý nghĩa**: Đếm số lượng phần tử trong Stream.
   ```java
   long count = list.stream().count();
   // Input: ["John", "Jane", "Alice"]
   // Output: 3
   ```

3. **`collect(Collector<? super T, A, R> collector)`**
   - **Ý nghĩa**: Thu thập các phần tử trong Stream thành một Collection hoặc dạng khác.
   ```java
   List<String> collectedList = list.stream()
                                     .collect(Collectors.toList());
   // Input: ["John", "Jane", "Alice"]
   // Output: ["John", "Jane", "Alice"]
   ```

4. **`reduce(T identity, BiFunction<T, ? super U, T> accumulator)`**
   - **Ý nghĩa**: Thực hiện phép toán giảm dần trên các phần tử trong Stream.
   ```java
   String concatenated = list.stream()
                              .reduce("", (s1, s2) -> s1 + s2);
   // Input: ["John", "Jane", "Alice"]
   // Output: "JohnJaneAlice"
   ```

5. **`reduce(BinaryOperator<T> accumulator)`**
   - **Ý nghĩa**: Thực hiện phép toán giảm dần mà không cần giá trị khởi tạo.
   ```java
   Optional<String> maxLength = list.stream()
                                     .reduce((s1, s2) -> s1.length() > s2.length() ? s1 : s2);
   // Input: ["John", "Jane", "Alice"]
   // Output: "Alice"
   ```

6. **`min(Comparator<? super T> comparator)`**
   - **Ý nghĩa**: Tìm phần tử nhỏ nhất trong Stream theo Comparator cung cấp.
   ```java
   Optional<String> min = list.stream()
                               .min(Comparator.naturalOrder());
   // Input: ["John", "Jane", "Alice"]
   // Output: "Alice"
   ```

7. **`max(Comparator<? super T> comparator)`**
   - **Ý nghĩa**: Tìm phần tử lớn nhất trong Stream theo Comparator cung cấp.
   ```java
   Optional<String> max = list.stream()
                               .max(Comparator.naturalOrder());
   // Input: ["John", "Jane", "Alice"]
   // Output: "John"
   ```

8. **`anyMatch(Predicate<? super T> predicate)`**
   - **Ý nghĩa**: Kiểm tra xem có bất kỳ phần tử nào thỏa mãn điều kiện không.
   ```java
   boolean hasJohn = list.stream().anyMatch(name -> name.equals("John"));
   // Input: ["John", "Jane", "Alice"]
   // Output: true
   ```

9. **`allMatch(Predicate<? super T> predicate)`**
   - **Ý nghĩa**: Kiểm tra xem tất cả các phần tử có thỏa mãn điều kiện không.
   ```java
   boolean allStartWithA = list.stream().allMatch(name -> name.startsWith("A"));
   // Input: ["John", "Jane", "Alice"]
   // Output: false
   ```

10. **`noneMatch(Predicate<? super T> predicate)`**
    - **Ý nghĩa**: Kiểm tra xem không có phần tử nào thỏa mãn điều kiện

 không.
    ```java
    boolean noneStartWithZ = list.stream().noneMatch(name -> name.startsWith("Z"));
    // Input: ["John", "Jane", "Alice"]
    // Output: true
    ```

11. **`findFirst()`**
    - **Ý nghĩa**: Tìm phần tử đầu tiên trong Stream.
    ```java
    Optional<String> first = list.stream().findFirst();
    // Input: ["John", "Jane", "Alice"]
    // Output: "John"
    ```

12. **`findAny()`**
    - **Ý nghĩa**: Tìm bất kỳ phần tử nào trong Stream.
    ```java
    Optional<String> any = list.stream().findAny();
    // Input: ["John", "Jane", "Alice"]
    // Output: "John" or "Jane" or "Alice" (bất kỳ phần tử nào)
    ```

### Kết luận

Trên đây là danh sách đầy đủ các phương thức trong Stream của Java, kèm theo ý nghĩa, ví dụ cụ thể và các comment mô tả input và output. Nếu bạn có thắc mắc hay cần thêm thông tin chi tiết về từng phương thức, hãy cho tôi biết!