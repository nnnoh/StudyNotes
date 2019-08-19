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

//	2��ʹ�ø���ѭ����for,while,do��while���ṹ�����Ӧdemo�����磺��ӡ200~500֮���������
//	3��ʹ�÷�֧�ṹ(if..else��switch)���һ������ѧ���ɼ�����ȼ����㷨demo�����磺����������·ݣ��жϸ��·��ж�����
//	4����������һ������1-7���ж����ܼ�����������������ʾ������󡣣�switch���д��

	public static void printPrime() {
		int start = 200, end = 500;
		System.out.print(start + "~" + end + "֮���������");
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
		System.out.print("month��");
		Scanner scan = new Scanner(System.in);
		int month = scan.nextInt();
		scan.close();
		if (month > 12 || month < 1)
			System.out.println("�������");
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
			System.out.println("��һ");
			break;
		case 2:
			System.out.println("�ܶ�");
			break;
		case 3:
			System.out.println("����");
			break;
		case 4:
			System.out.println("����");
			break;
		case 5:
			System.out.println("����");
			break;
		case 6:
			System.out.println("����");
			break;
		case 7:
			System.out.println("����");
			break;
		default:
			System.out.println("�������");
			break;
		}
	}
}
