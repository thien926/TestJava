# Các phương thức của List trong Java

Dưới đây là danh sách các phương thức chính của interface `List` trong Java. Interface này được sử dụng để làm việc với các danh sách, và các lớp như `ArrayList`, `LinkedList` đều triển khai `List`.

## Các phương thức chính của `List`:

1. **`void add(E e)`**  
   Thêm phần tử vào cuối danh sách.

2. **`void add(int index, E element)`**  
   Thêm phần tử tại vị trí chỉ định trong danh sách.

3. **`boolean addAll(Collection<? extends E> c)`**  
   Thêm tất cả phần tử từ một collection vào danh sách.

4. **`boolean addAll(int index, Collection<? extends E> c)`**  
   Thêm tất cả phần tử từ một collection tại vị trí chỉ định.

5. **`void clear()`**  
   Xóa tất cả phần tử khỏi danh sách.

6. **`boolean contains(Object o)`**  
   Kiểm tra xem danh sách có chứa phần tử chỉ định hay không.

7. **`boolean containsAll(Collection<?> c)`**  
   Kiểm tra xem danh sách có chứa tất cả các phần tử từ một collection hay không.

8. **`E get(int index)`**  
   Trả về phần tử tại vị trí chỉ định trong danh sách.

9. **`int indexOf(Object o)`**  
   Trả về vị trí đầu tiên của phần tử chỉ định trong danh sách, hoặc -1 nếu phần tử không tồn tại.

10. **`boolean isEmpty()`**  
    Kiểm tra xem danh sách có rỗng hay không.

11. **`Iterator<E> iterator()`**  
    Trả về một iterator để duyệt qua danh sách.

12. **`int lastIndexOf(Object o)`**  
    Trả về vị trí cuối cùng của phần tử chỉ định trong danh sách, hoặc -1 nếu không tìm thấy.

13. **`ListIterator<E> listIterator()`**  
    Trả về một `ListIterator` để duyệt qua danh sách theo cả hai chiều (từ đầu đến cuối hoặc ngược lại).

14. **`ListIterator<E> listIterator(int index)`**  
    Trả về một `ListIterator` bắt đầu từ vị trí chỉ định.

15. **`E remove(int index)`**  
    Xóa phần tử tại vị trí chỉ định và trả về phần tử đã bị xóa.

16. **`boolean remove(Object o)`**  
    Xóa phần tử chỉ định nếu nó tồn tại trong danh sách.

17. **`boolean removeAll(Collection<?> c)`**  
    Xóa tất cả các phần tử trong danh sách có trong collection chỉ định.

18. **`boolean retainAll(Collection<?> c)`**  
    Chỉ giữ lại các phần tử trong danh sách có trong collection chỉ định.

19. **`E set(int index, E element)`**  
    Thay thế phần tử tại vị trí chỉ định bằng phần tử mới.

20. **`int size()`**  
    Trả về số lượng phần tử trong danh sách.

21. **`List<E> subList(int fromIndex, int toIndex)`**  
    Trả về một `List` con từ vị trí `fromIndex` đến `toIndex`.

22. **`Object[] toArray()`**  
    Trả về một mảng chứa tất cả các phần tử trong danh sách.

23. **`<T> T[] toArray(T[] a)`**  
    Trả về một mảng chứa tất cả các phần tử trong danh sách với kiểu dữ liệu chỉ định.

### Các phương thức từ Java 8 và Java 9:

24. **`void forEach(Consumer<? super E> action)`**  
    Thực hiện một hành động cho từng phần tử của danh sách.

25. **`void replaceAll(UnaryOperator<E> operator)`**  
    Thay thế từng phần tử của danh sách bằng kết quả của việc áp dụng `operator` cho phần tử đó.

26. **`void sort(Comparator<? super E> c)`**  
    Sắp xếp các phần tử của danh sách dựa trên `Comparator` cung cấp.

27. **`Spliterator<E> spliterator()`**  
    Trả về một `Spliterator` để duyệt qua danh sách theo kiểu phân tán.

28. **`default void removeIf(Predicate<? super E> filter)`**  
    Xóa tất cả các phần tử trong danh sách mà thỏa mãn điều kiện của `Predicate` chỉ định.

29. **`Stream<E> stream()`**  
    Trả về một `Stream` để thao tác với các phần tử trong danh sách theo kiểu stream (luồng).

30. **`Stream<E> parallelStream()`**  
    Trả về một `Stream` song song để thao tác với các phần tử trong danh sách.

31. **`default List<E> of(E... elements)`** *(Java 9)*  
    Tạo một danh sách không thay đổi từ các phần tử cho trước.

### Ví dụ sử dụng một vài phương thức:

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<String> fruits = new ArrayList<>();
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Orange");

        // Sử dụng forEach
        fruits.forEach(fruit -> System.out.println("Fruit: " + fruit));

        // Sử dụng replaceAll để viết hoa tất cả các phần tử
        fruits.replaceAll(String::toUpperCase);

        // In danh sách sau khi thay đổi
        fruits.forEach(System.out::println);

        // Sắp xếp danh sách theo chiều ngược lại
        fruits.sort(Comparator.reverseOrder());
        fruits.forEach(System.out::println);
    }
}
```