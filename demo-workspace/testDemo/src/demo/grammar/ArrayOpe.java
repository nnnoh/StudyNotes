package demo.grammar;

import java.util.Arrays;
import java.util.Scanner;

public class ArrayOpe {
	public static void main(String[] args) {

//		int[] arr = { 17, 32, 14, 67, 2, 25, 78, 24, 72, 10, 55, 63 };
		
		int[] arr = new int[0];  
		System.out.println("Max: " + getMax(arr));

		int[] tarr = Arrays.copyOf(arr, arr.length);

		selectSort(tarr);
		printArray(tarr);
		// printArray(arr);
		
		tarr = Arrays.copyOf(arr, arr.length);
		bubbleSort(tarr);
		printArray(tarr);

		System.out.println("Index of 14: " + halfSearch(tarr, 14));
		System.out.println("Index of 78: " + halfSearch(tarr, 78));
		System.out.println("Index of 26: " + halfSearch(tarr, 26));

		reverseArray(tarr);
		printArray(tarr);
	}

	/**
	 * 取最大值
	 * @param arr
	 * @return
	 */
	public static int getMax(int[] arr) {
		if (arr.length == 0) // arr == null
			return -1;
		int max = arr[0];
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] > max)
				max = arr[i];
		}
		return max;
	}

	public static void selectSort(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			int max = 0;
			int j = 0;
			for (; j < arr.length - i; j++) {
				if (arr[max] < arr[j])
					max = j;
			}
			if (max != j - 1) {
				int t = arr[j - 1];
				arr[j - 1] = arr[max];
				arr[max] = t;
			}
		}
	}

	public static void bubbleSort(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 1; j < arr.length - i; j++) {
				if (arr[j - 1] > arr[j]) {
					int t = arr[j];
					arr[j] = arr[j - 1];
					arr[j - 1] = t;
				}
			}
		}
	}

	public static int halfSearch(int[] arr, int key) {
		int min, mid, max;
		min = 0;
		mid = arr.length / 2;
		max = arr.length;
		while (min < max) {
			if (arr[mid] == key)
				return mid + 1;
			else if (arr[mid] > key) {
				max = mid;
				mid = (min + max) / 2;
			} else {
				min = mid + 1;
				mid = (min + max) / 2;
			}
		}
		return -1;
	}

	public static int[] getInputArray() {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = scan.nextInt();
		}
		scan.close();
		return arr;
	}

	public static void reverseArray(int[] arr) {
		for (int i = 0, j = arr.length - 1; i < j; i++, j--) {
			int t = arr[i];
			arr[i] = arr[j];
			arr[j] = t;
		}
	}

	public static void printArray(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
}
