package com.java.basics;

public class ForExample {
	/* Vòng lặp for cải tiến */
	public static void forEachExample(int[] arr) {
		for (int i : arr) {
			System.out.print(i + "\t");
		}
		System.out.println();
	}

	/*
	 * Vòng lặp for gán nhãn 
	 * Chúng ta có để đặt tên cho mỗi vòng lặp for bằng cách
	 * gán nhãn trước vòng lặp for. Điều này rất hữu dụng khi chúng ta muốn
	 * thoát/tiếp tục(break/continues) chạy vòng lặp for.
	 */
	public static void labeledForExample() {
		aa: for (int i = 1; i <= 3; i++) {
            bb: for (int j = 1; j <= 3; j++) {
				if (i == 2 && j == 2) {
                    break aa;
                }
                System.out.println(i + " " + j);
            }
        }
	}

	public static void main(String[] args) {
		int arr[] = { 12, 23, 44, 56, 78 };
//		forEachExample(arr);
		labeledForExample();
	}
}
