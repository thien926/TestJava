package com.java.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamExample {
	public static void streamForeach() {
		List<Integer> list = new ArrayList<>();
		Random r = new Random();
		for (int i = 0; i < 10; ++i) {
			list.add(r.nextInt(10));
		}

//		list.forEach(System.out::println);
		list.forEach(e -> {
			System.out.print(e + " ");
		});

		System.out.println();
		System.out.print("Loại bỏ trùng lặp : ");
		list.stream().distinct().forEach(e -> {
			System.out.print(e + " ");
		});
		System.out.println();
		System.out.println("Count : " + list.stream().filter(e -> e > 5).count());
		System.out.println("Min : " + list.stream().min((a, b) -> a - b).get());
		System.out.println("Max : " + list.stream().max((a, b) -> a - b).get());
		System.out.println("Sum : " + list.stream().mapToInt(i -> i).sum());
//		System.out.println("Sum : " + list.stream().mapToInt(Integer::intValue).sum());
//		System.out.println("Sum : " + list.stream().mapToInt(i -> i.intValue()).sum());
		System.out.println(
				"So khớp với tất cả các phần tử có chia hết cho 3 : " + list.stream().allMatch(e -> e % 3 == 0));

		// Collect :
		// https://www.digitalocean.com/community/tutorials/java-stream-collect-method-examples
		System.out.println(
				"Các số chia hết cho 3: " + list.stream().filter(e -> e % 3 == 0).collect(Collectors.toList()));

		// takeWhile = filter + collect
		System.out.print("Các số chia hết cho 3: ");
		list.stream().takeWhile(e -> e % 3 == 0).forEach(x -> System.out.print(x + " "));

		System.out.print("\nfindAny: ");
		list.stream().filter(e -> e % 3 == 0).findAny().ifPresent(e -> System.out.print(e + " "));
		System.out.println();

		System.out.print("findFirst: ");
		list.stream().filter(e -> e % 3 == 0).findFirst().ifPresent(e -> System.out.print(e + " "));
		System.out.println();

		System.out.println("skip and limit: " + list.stream().skip(1).limit(5).collect(Collectors.toList()));

		list = list.parallelStream().map(e -> e + 3).collect(Collectors.toList());
		System.out.println(list);
	}

	// Collect :
	// https://www.digitalocean.com/community/tutorials/java-stream-collect-method-examples
	public static void streamCollectToMap() {
		List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);

		Map<Integer, String> mapOddNumbers = numbers.parallelStream().filter(x -> x % 2 != 0)
				.collect(Collectors.toMap(Function.identity(), x -> String.valueOf(x)));
		System.out.println(mapOddNumbers); // {1=1, 3=3, 5=5}
	}

	/*
	 * Chúng ta có thể sử dụng flatMap() để tạo một luồng từ luồng danh sách. Hãy
	 * cùng xem một ví dụ đơn giản để xóa nghi ngờ này.
	 */
	public static void streamFlatMap() {
		Stream<List<String>> namesOriginalList = Stream.of(Arrays.asList("Pankaj"), Arrays.asList("David", "Lisa"),
				Arrays.asList("Amit"));
		// flat the stream from List<String> to String stream
		Stream<String> flatStream = namesOriginalList.flatMap(strList -> strList.stream());

		flatStream.forEach(System.out::println);
	}

	/*
	 * Chúng ta có thể sử dụng reduce() để thực hiện giảm các yếu tố của luồng, sử
	 * dụng hàm tích lũy kết hợp và trả về tùy chọn. Hãy để xem cách chúng ta có thể
	 * sử dụng nó nhân các số nguyên trong một luồng.
	 */
	public static void streamReduce() {
		Stream<Integer> numbers = Stream.of(1, 2, 3, 4, 5);

		Optional<Integer> intOptional = numbers.reduce((i, j) -> {
			return i + j;
		});
		if (intOptional.isPresent())
			System.out.println("Multiplication = " + intOptional.get()); // 15
	}

	public static void statefulParallelStream() {
		List<Integer> ss = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
		List<Integer> result = new ArrayList<Integer>();

		Stream<Integer> stream = ss.parallelStream();

		stream.map(s -> {
			synchronized (result) {
				if (result.size() < 10) {
					result.add(s);
				}
			}
			return s;
		}).forEach(e -> {
		});
		System.out.println(result);
	}

	public static void streamMapConvertingLowercaseToUppercase() {
		System.out.println("The stream after applying " + "the function is : ");
		List<String> list = Arrays.asList("geeks", "gfg", "g", "e", "e", "k", "s");
		
		List<String> answer = list.stream().map(String::toUpperCase).collect(Collectors.toList());

		System.out.println(answer);
	}
	
	public static void convertStringToListCharacter() {
		String string = "testingString";
		List<Character> list = string.chars().mapToObj(i -> Character.toUpperCase((char)i)).collect(Collectors.toList());
		System.out.println(list);
	}

	// Stream : https://www.digitalocean.com/community/tutorials/java-8-stream
	public static void main(String[] args) {
//		streamForeach();
//		streamCollectToMap();
//		streamFlatMap();
//		streamReduce();
//		statefulParallelStream();
//		streamMapConvertingLowercaseToUppercase();
		convertStringToListCharacter();
	}
}
