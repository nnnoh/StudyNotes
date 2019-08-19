package demo;

import java.util.Scanner;

import demo.grammar.ArrayOpe;

public class Exercise {
	public static void main(String[] args) {
		int[] arr = { 17, 32, 14, 67, 2, 25, 78, 24, 72, 10, 55, 63 };
		ArrayOpe.printArray(arr);
		quickSort(arr, 0, arr.length);
		ArrayOpe.printArray(arr);
	}

	public static void printMultiplicationTable() {
		for (Integer a = 1; a <= 9; a++) {
			for (int b = 1; b <= a; b++) {
				System.out.print(a.toString() + "x" + b + "=" + (a * b) + " ");
			}
			System.out.println();
		}
		for (Integer a = 9; a >= 1; a--) {
			for (int b = 1; b <= a; b++) {
				System.out.print(a.toString() + "x" + b + "=" + (a * b) + " ");
			}
			System.out.println();
		}
	}

	public static void bubbleSort() {
		Scanner scan = new Scanner(System.in);
		int n;
		while (true) {
			n = scan.nextInt();
			if (n == 0)
				break;
			int arr[] = new int[n];
			for (int i = 0; i < n; i++) {
				arr[i] = scan.nextInt();
			}
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < n - i; j++) {
					if (arr[j - 1] > arr[j]) {
						int t = arr[j];
						arr[j] = arr[j - 1];
						arr[j - 1] = t;
					}
				}
			}
			for (int i = 0; i < n; i++) {
				System.out.print(arr[i] + " ");
			}
			System.out.println();
		}
		scan.close();
	}

	public static void quickSort(int[] arr, int start, int end) {
		if (start == end)
			return;
		int p = partition(arr, start, end);
		quickSort(arr, start, p);
		quickSort(arr, p + 1, end);
//		System.out.println();
	}

	public static int partition(int[] arr, int start, int end) {
		int x = arr[start];
		int i = start, j = end;
		while (i < j) {
			while (++i < j && arr[i] < x)
				;
			while (start < --j && arr[j] > x)
				;
			if (i < j) {
				int t = arr[j];
				arr[j] = arr[i];
				arr[i] = t;
			}
		}
		if (start != j) {
			arr[start] = arr[j];
			arr[j] = x;
		}
//		System.out.println(start + "-" + end + ":" + x + "->[" + j + "]");
//		ArrayOpe.printArray(arr);
		return j;
	}
}
