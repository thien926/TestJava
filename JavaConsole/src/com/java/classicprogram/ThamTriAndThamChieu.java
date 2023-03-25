package com.java.classicprogram;

import java.util.concurrent.atomic.AtomicInteger;

public class ThamTriAndThamChieu {
	// Tham trị
	public static void passByValue(int n) {
		n = n + 10;
	}
	
	// Tham chiếu
	public static void passByReference(AtomicInteger n) {
		n.set(n.get() + 10);
	}
	
	public static void main(String[] args) {
		int n = 100;
		passByValue(100);
		System.out.println("Tham trị: " + n);
		
		AtomicInteger q = new AtomicInteger(100);
		passByReference(q);
		System.out.println("Tham chiếu: " + q);
	}
}
