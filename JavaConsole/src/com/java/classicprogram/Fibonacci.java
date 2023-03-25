package com.java.classicprogram;

public class Fibonacci {
	public static int recursive(int n) {
		if(n == 0 || n == 1) return n;
		return recursive(n-2) + recursive(n-1);
	} 
	
	public static String nonRecursive(int n) {
		StringBuilder res = new StringBuilder("0 1 ");
		int f0 = 0, f1 = 1, fn;
		for(int i = 2; i < n; ++i) {
			fn = f0 + f1;
			res.append(Integer.toString(fn) + " ");
			f0 = f1; f1 = fn;
		}
		return res.toString();
	}
	
	public static void main(String[] args) {
		int n = 20;
		for(int i = 0; i < n; ++i) {
			System.out.print(recursive(i) + " ");
		}
		System.out.println();
		System.out.println(nonRecursive(n));
	}
}
