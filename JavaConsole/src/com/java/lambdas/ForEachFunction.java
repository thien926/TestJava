package com.java.lambdas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ForEachFunction {
	/* 
	 * Lambda with Filter  
	 */
	public static void lambdaWithFilterForeach() {
		List<Integer> list = new ArrayList<>();
		Random r = new Random();
		for(int i = 0; i < 10; ++i) {
			list.add(r.nextInt(20));
		}
		
//		list.forEach(System.out::println);
		list.forEach(e -> {
			System.out.print(e + " ");
		});
		
		System.out.println();
		System.out.println("Count : " + list.stream().filter(e -> e > 10).count());
		System.out.println("Min : " + list.stream().min((a, b) -> a - b).get());
		System.out.println("Max : " + list.stream().max((a, b) -> a - b).get());
	}
	
	public static void main(String[] args) {
		lambdaWithFilterForeach();
    }
}
