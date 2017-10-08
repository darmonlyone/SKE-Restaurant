package homework;

import java.util.Scanner;

public class Restaurant {

	static Scanner sc = new Scanner(System.in);

	static String[] ordername = { "Pizza", "Chicken", "Coke" };
	static String[] name = { "Wellcome to SKE restaurant", "Total", "Exit", "Bath", "Menu", "Cost", "Qty", "Price" };
	static int[] price = { 250, 120, 45 };
	static int[] Amount = new int[3];
	static int[] totalAll = new int[1];
	static int[] totalPrice = new int[3];
	static int[] pay = new int[1];

	public static void printMenu() {
		System.out.printf("********** %s **********%n", name[0]);
		System.out.printf("\t%s\t\t\t  %s %n", name[4], name[5]);
		System.out.printf("\t------\t\t\t  ----- %n");
		System.out.printf("1. )\t%s\t\t\t%5d %s.%n", ordername[0], price[0], name[3]);
		System.out.printf("2. )\t%s \t\t%5d %s.%n", ordername[1], price[1], name[3]);
		System.out.printf("3. )\t%s\t\t\t%5d %s.%n", ordername[2], price[2], name[3]);
		System.out.printf("4. )\t%s%n", name[1]);
		System.out.printf("5. )\t%s%n", name[2]);
	}

	public static int getScanInt(String prompt) {
		System.out.print(prompt);
		return sc.nextInt();
	}

	private static int name(int a) {
		return a - 1;
	}

	private static int totals() {

		return totalPrice[0] + totalPrice[1] + totalPrice[2];
	}

	private static void printOrder(String ordername, int Amount) {
		System.out.printf("---> You Order %s\t= %6d%n", ordername, Amount);
	}

	private static void printCheck(String ordername, int Amount, int finalPrice) {
		System.out.printf("|  %-16s|%6d   |%7d    |%n", ordername, Amount, finalPrice);
	}

	private static void totalPrice() {
		for (int i = 0; i < ordername.length; i++) {
			totalPrice[i] = Amount[i] * price[i];
		}
	}

	private static void printTotal(int ipOrder) {
		totalPrice();
		totalAll[0] = totals();

		if (ipOrder == 4) {
			System.out.printf("+------ %s ------+-- %s --+-- %s --+%n", name[4], name[6], name[7]);
			for (int i = 0; i < ordername.length; i++) {
				if (Amount[i] > 0) {
					printCheck(ordername[i], Amount[i], totalPrice[i]);
				}
			}
			System.out.println("+----------------------------------------+");
			System.out.printf("|  %-26s|%7d    |%n", name[1], totalAll[0]);
			System.out.println("+----------------------------------------+");

		}
	}

	private static void printAmountOrder(int ipOrder) {
		if (ipOrder != 4 && ipOrder < 6 && ipOrder > 0) {
			int ipAmount = getScanInt("Enter Quantity: ");
			for (int j = 0; j < ordername.length; j++) {
				if (ipOrder == j + 1) {
					Amount[j] += ipAmount;
				}
			}

			System.out.printf("You order %d %s.%n", ipAmount, ordername[name(ipOrder)].toLowerCase());

			for (int i = 0; i < ordername.length; i++) {
				if (Amount[i] > 0) {
					printOrder(ordername[i], Amount[i]);
				}
			}
		}
	}

	private static void Order() {
		while (true) {
			int ipOrder = getScanInt("\nEnter your order: ");
			if (ipOrder > 5 || ipOrder < 0)
				System.out.println("Try Again...");
			if (ipOrder == 5)
				break;
			printAmountOrder(ipOrder);
			printTotal(ipOrder);

		}
	}

	public static void pay(int total) {
		System.out.println("You need to pay :" + total);

		while (true) {
			int payy = getScanInt("Put your cash: ");
			if (payy < total)
				System.out.println("Not enough ");
			else {
				pay[0] = payy;
				break;
			}

		}

		System.out.println("\nChange :" + (pay[0] - total));
		System.out.printf("%n======== Thank you =========");
	}

	public static void main(String[] args) {

		printMenu();
		Order();
		pay(totalAll[0]);
	}
}
