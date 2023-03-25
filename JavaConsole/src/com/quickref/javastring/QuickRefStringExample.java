package com.quickref.javastring;

public class QuickRefStringExample {
	
	public static void initialString() {
		String str1 = "value"; 
		String str2 = new String("value");
		String str3 = String.valueOf(123);
		System.out.println(str1 + " " + str2 + " " + str3);
	}
	
	public static void functionInString() {
		String s1 = new String("QuickRef"); 
		String s2 = new String("QuickRef"); 

//		s1 == s2          // false
//		s1.equals(s2)     // true

		"AB".equalsIgnoreCase("ab");  // true
		
		/*=======================================*/
		
		String str = "Abcd";

		str.toUpperCase();     // ABCD
		str.toLowerCase();     // abcd
		str.concat("#");       // Abcd#
		str.replace("b", "-"); // A-cd

		"  abc ".trim();       // abc
		"ab".toCharArray();    // {'a', 'b'}
		
		/* ========================================== */
		
		str = "abcd";

		str.charAt(2);       // c
		str.indexOf("a");     // 0
		str.indexOf("z");     // -1
		str.length();        // 4
		str.toString();      // abcd
		str.substring(2);    // cd
		str.substring(2,3);  // c
		str.contains("c");   // true
		str.endsWith("d");   // true
		str.startsWith("a"); // true
		str.isEmpty();       // false

	}
	
	public static void main(String[] args) {
		initialString();
	}
}
