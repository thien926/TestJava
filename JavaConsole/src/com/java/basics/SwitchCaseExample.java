package com.java.basics;

public class SwitchCaseExample {
	public static void switchExample() {
		int number = 20;
		switch (number) {
		case 10:
			System.out.println("10");
			break;
		case 20:
			System.out.println("20");
			break;
		case 30:
			System.out.println("30");
			break;
		default:
			System.out.println("Not in 10, 20 or 30");
		}
	}

	/*
	 * Summary: separate by commas
	 */
	public static void switchByCommasExample(int year, int month) {
		int numDays = 0;

		switch (month) {
			case 1, 3, 5, 7, 8, 10, 12 -> {
				numDays = 31;
			}
			case 4, 6, 9, 11 -> {
				numDays = 30;
			}
			case 2 -> {
				if (((year % 4 == 0) && !(year % 100 == 0)) || (year % 400 == 0))
					numDays = 29;
				else
					numDays = 28;
			}
			default -> {
				System.out.println("Invalid month.");
	
			}
		}
		System.out.println("Number of Days = " + numDays);
	}

	/*
	 * Switch case with yield
	 */
	public static void switchWithYieldExample(int year, int month) {
		int numDays = switch (month) {
			case 1, 3, 5, 7, 8, 10, 12 -> 31;
			case 4, 6, 9, 11 -> 30;
			case 2 -> {
				if (java.time.Year.of(year).isLeap()) {
					yield 29;
				} else {
					yield 28;
				}
			}
			default -> {
				yield 0;
			}
		};
		System.out.println(numDays != 0 ? "Number of Days = " + numDays : "Invalid month.");
	}

	/*
	 * Cách sử dụng switch với điều kiện lớn hơn hoặc bằng
	 */
//	public static void switchGreaterThanExample() {
//		int number = 20;
//		switch (true) {
//		case (number < 10):
//			System.out.println("10");
//			break;
//		default:
//			System.out.println("Not in 10, 20 or 30");
//		}
//	}

	public static void main(String[] args) {
//		switchExample();
//		switchByCommasExample(2018, 9);
		switchWithYieldExample(2018, 9);
	}
}
