package demo.grammar;

import java.util.Scanner;

public class LoopBranch {
	public static void main(String[] args) {
		printPrime();
		do {
			printDays();
			printWeek();
		} while (true);
	}

//	2、使用各种循环（for,while,do…while）结构完成相应demo。例如：打印200~500之间的素数。
//	3、使用分支结构(if..else和switch)完成一个根据学生成绩计算等级的算法demo。例如：根据输入的月份，判断该月份有多少天
//	4、随意输入一个数字1-7，判断是周几，输入其他数字提示输入错误。（switch语句写）

	public static void printPrime() {
		int start = 200, end = 500;
		System.out.print(start + "~" + end + "之间的素数：");
		for (int i = start; i <= end; i++) {
			int j = 2;
			while (j < Math.sqrt(i)) {
				if (i % j == 0)
					break;
				j++;
			}
			if (j > Math.sqrt(i)) {
				System.out.print(i + " ");
			}
		}
		System.out.println();
	}

	public static void printDays() {
		System.out.print("month：");
		Scanner scan = new Scanner(System.in);
		int month = scan.nextInt();
		scan.close();
		if (month > 12 || month < 1)
			System.out.println("输入错误");
		else if (month == 2) {
			System.out.println("28 or 29");
		} else {
			if (month > 7) {
				month++;
			}
			System.out.println(month % 2 == 1 ? 31 : 30);
		}
	}

	public static void printWeek() {
		System.out.print("number:");
		Scanner scan = new Scanner(System.in);
		int number = scan.nextInt();
		scan.close();
		switch (number) {
		case 1:
			System.out.println("周一");
			break;
		case 2:
			System.out.println("周二");
			break;
		case 3:
			System.out.println("周三");
			break;
		case 4:
			System.out.println("周四");
			break;
		case 5:
			System.out.println("周五");
			break;
		case 6:
			System.out.println("周六");
			break;
		case 7:
			System.out.println("周日");
			break;
		default:
			System.out.println("输入错误");
			break;
		}
	}
}
