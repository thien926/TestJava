package com.java.classicprogram;

import java.math.BigInteger;
import java.util.Scanner;

public class BigIntExample {
	public static void main(String args[]) {
        try (Scanner in = new Scanner(System.in)) {
			String N = in.nextLine();
			BigInteger res = new BigInteger(N);

			System.out.println(res.mod(new BigInteger("4")).equals(new BigInteger("0")) ? "AAAAAAAAAA!!!" : "Ok");
		}
    }
}
