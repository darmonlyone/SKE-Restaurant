import java.util.Scanner;

public class Restaurant {

	static RestaurantManager OpenFile = new RestaurantManager();

	static Scanner sc = new Scanner(System.in);

	static String[] name = { "Wellcome to SKE restaurant", "Total", "Exit", "Bath", "Menu", "Cost", "Qty", "Price" };
	static int[] Amount;
	static double[] totalAll = new double[1];
	static double[] totalPrice;
	static double[] pay = new double[1];

	public static void printMenu() {
		System.out.printf("********** %s **********%n", name[0]);
		System.out.printf("%5s%s%19s%s %n","", name[4],"", name[5]);
		System.out.printf("%4s-------%16s------ %n","","");
		for (int i = 0; i < OpenFile.menuName.size() ; i++) {
			System.out.printf("%d.) %-20s%6.1f\t%s.%n",i+1, OpenFile.menuName.get(i), OpenFile.menuPrice.get(i), name[3]);
		}
		System.out.printf("%d.) %-5s%n",OpenFile.menuName.size()+1, name[1]);
		System.out.printf("%d.) %-5s%n",OpenFile.menuName.size()+2, name[4]);
		System.out.printf("%d.) %-5s%n",OpenFile.menuName.size()+3, name[2]);
	}

	public static int getScanInt(String prompt) {
		System.out.print(prompt);
		return sc.nextInt();
	}

	private static int name(int a) {
		return a - 1;
	}

	private static int totals() {
		int result = 0;
		for (int i = 0; i <totalPrice.length ; i++) {
			result += totalPrice[i];
		}
		return result;
	}

	private static void printOrder(String ordername, int Amount) {
		System.out.printf("---> You Order %-15s = %6d%n", ordername, Amount);
	}

	private static void printCheck(String ordername, int Amount, double finalPrice) {
		System.out.printf("|  %-16s|%6d   |   %6.1f |%n", ordername, Amount, finalPrice);
	}

	private static void totalPriceChange() {
		for (int i = 0; i < OpenFile.menuName.size(); i++) {
			totalPrice[i] = Amount[i] * OpenFile.menuPrice.get(i);
		}
	}

	private static void printTotal(int ipOrder) {
		totalPriceChange();
		totalAll[0] = totals();

		if (ipOrder == OpenFile.menuName.size()+1) {
			System.out.printf("+------ %s ------+-- %s --+-- %s --+%n", name[4], name[6], name[7]);
			for (int i = 0; i < OpenFile.menuName.size(); i++) {
				if (Amount[i] > 0) {
					printCheck(OpenFile.menuName.get(i), Amount[i], totalPrice[i]);
				}
			}
			System.out.println("+----------------------------------------+");
			System.out.printf("|  %-26s|   %6.1f |%n", name[1], totalAll[0]);
			System.out.println("+----------------------------------------+");

		}
	}

	private static void printAmountOrder(int ipOrder) {
		if (ipOrder != OpenFile.menuName.size()+1 && ipOrder < OpenFile.menuName.size()+2 && ipOrder > 0) {
			int ipAmount = getScanInt("Enter Quantity: ");
			for (int j = 0; j < OpenFile.menuName.size(); j++) {
				if (ipOrder == j + 1) {
					Amount[j] += ipAmount;
				}
			}

			System.out.printf("You order %d %s.%n", ipAmount, OpenFile.menuName.get(name(ipOrder)).toLowerCase());

			for (int i = 0; i < OpenFile.menuName.size(); i++) {
				if (Amount[i] > 0) {
					printOrder(OpenFile.menuName.get(i), Amount[i]);
				}
			}
		}
	}

	private static void Order() {
		while (true) {
			int ipOrder = getScanInt("\nEnter your order number: ");
			if (ipOrder > OpenFile.menuName.size()+3 || ipOrder < 1)
				System.out.println("Try Again...");
			if (ipOrder == OpenFile.menuName.size()+3)
				break;
			if (ipOrder == OpenFile.menuName.size()+2)
				printMenu();
			printAmountOrder(ipOrder);
			printTotal(ipOrder);

		}
	}

	public static void pay(double total) {
		System.out.println("\nYou need to pay :" + total+" Bath.");

		while (true) {
			double payy = getScanInt("Put your cash: ");
			if (payy < total)
				System.out.println("Not enough ");
			else {
				pay[0] = payy;
				break;
			}

		}

		System.out.println("\nChange :" + (pay[0] - total+" Bath."));
		System.out.printf("%n========= Thank you =========");
	}

	public static void main(String[] args) {
		OpenFile.init();

		Amount = new int[OpenFile.menuPrice.size()];
		totalPrice = new double[OpenFile.menuPrice.size()];

		printMenu();
		Order();
		pay(totalAll[0]);
	}
}
