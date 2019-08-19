package demo;

import java.util.*;

public class QuestionTest {
	public static void main(String[] args) {
		System.out.println(Long.MAX_VALUE);
		scanTest();
	}

	public static void scanTest() {
		Scanner scan = new Scanner(System.in);
		while (scan.hasNextInt()) {
			System.out.println(scan.nextInt());
		}
		scan.close();
	}

	public static void stringTest() {
		String a = "saff";
		String b = "saff";
		String c = new String("saff");
		System.out.println(a == b);
		System.out.println(a == c);
		System.out.println(a.equals(b));
		System.out.println(a.equals(c));

		String s1 = "ab";
		String s2 = "abc";
		String s3 = s1 + "c";
		String s4 = "a" + "b" + "c";
		System.out.println(s4 == s2); // true
		System.out.println(s3 == s2); // false
		System.out.println(s3.equals(s2)); // true
	}

	public void block() {
		int i = 0;
		{
			int a = 1;
			System.out.println(i);
		}
	}

	protected void finalize() {
	}
	
	public static void equalsTest() {
		Integer a = 1;
		Integer b = 2;
		Integer c = 3;
		Integer d = 3;
		Integer e = 321;
		Integer f = 321;
		Long g = 3L;
		Long h = 2L;

		System.out.println(c == d);
		System.out.println(e == f);
		System.out.println(c == (a + b));
		System.out.println(c.equals(a + b));
		System.out.println(g == (a + b));
		System.out.println(g.equals(a + b));
		System.out.println(g.equals(a + h));
	}

}
