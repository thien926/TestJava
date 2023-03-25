package com.java.operators;

public class OperatorExample {
	public static boolean xorExample() {
		boolean a = false, b = false, c = true;
		return a ^ b ^ c;
	}
	
	public static int dichChuyenBit() {
		int a = 5;
		return 5 << 1; // 5 = 101, dich tat ca cac bit chuyen sang trai 1 don vi => 1010 = 10
	}
	
	public static boolean toanTuAnd() {
		return false & false;
	}
	
	public static void main(String[] args) {
//		System.out.println("XOR: " + xorExample());
//		System.out.println("dichChuyenBit: " + dichChuyenBit());
		System.out.println("toanTuAnd: " + toanTuAnd());
	}
}
 								