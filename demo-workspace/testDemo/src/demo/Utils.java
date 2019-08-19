package demo;

public class Utils {
	public static void printArray(Object[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i].toString() + " ");
		}
		System.out.println();
	}

	public static void printArray(Object[] arr, String separator) {
		if (arr.length > 0)
			System.out.print(arr[0].toString());
		for (int i = 1; i < arr.length; i++) {
			System.out.print(separator + arr[i].toString());
		}
		System.out.println();
	}
}
