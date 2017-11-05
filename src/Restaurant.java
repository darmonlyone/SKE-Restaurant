import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
* Main class for SKE Restaurarnt order taking application.
* It display a menu by using FoodManage class to manage a menu.
* Using RestaurantManger class for get menu and record receipt.
* When done, it prints a receipt and save receipt thought RestaurantManger class.
*
*  @author Manusporn Fukkham
*/

public class Restaurant {

	static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	static DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	static Date myDate = new Date(System.currentTimeMillis());

    static List<String> food = new ArrayList<>();
	static List<Double> foodPrice = new ArrayList<>();
	static Scanner sc = new Scanner(System.in);

	static String[] name = { "Wellcome to SKE restaurant", "Total", "Exit", "Bath", "Menu", "Cost", "Qty", "Price" };
	static int[] Amount;
	static double[] totalAll = new double[1];
	static double[] totalPrice;
	static double[] pay = new double[1];

	//set menu and food price
	private static void setResFood(){

	    RestaurantManager resManage = new RestaurantManager();
	    resManage.setMenu();
        for(FoodManage foodAddName : resManage.foodRead) {
            food.add(foodAddName.getFoodName());
        }
        for(FoodManage foodAddPrice : resManage.foodRead){
            foodPrice.add(foodAddPrice.getPrice());
        }
	}
	//print a menu
	private static void printMenu() {
		System.out.printf("********** %s **********%n", name[0]);
		System.out.printf("%5s%s%19s%s %n","", name[4],"", name[5]);
		System.out.printf("%4s-------%16s------ %n","","");
		for (int i = 0; i < food.size() ; i++) {
			System.out.printf("%d.) %-20s%6.1f\t%s.%n",i+1, food.get(i), foodPrice.get(i), name[3]);
		}
		System.out.printf("%n(%s) %-5s%n","P", "Print order");
		System.out.printf("(%s) %-5s%n","C", "Cancel order");
		System.out.printf("(%s) %-5s%n","M", "Menu");
		System.out.printf("(%s) %-5s%n","E", "Check out");
	}

	private static int getScanInt(String prompt) {
		System.out.print(prompt);
		return sc.nextInt();
	}

	private static String getScanString(String prompt) {
		System.out.print(prompt);
		return sc.nextLine();
	}

	private static int name(int a) {
		return a - 1;
	}

	//return total price
	private static int totals() {
		int result = 0;
		for (int i = 0; i <totalPrice.length ; i++) {
			result += totalPrice[i];
		}
		return result;
	}

	private static void printOrder(String ordername, int Amount) {
		System.out.printf("---> You have order %-15s = %6d%n", ordername, Amount);
	}

	private static void printCheck(String ordername, int Amount, double finalPrice) {
		System.out.printf("|  %-16s|%6d   |   %7.1f |%n", ordername, Amount, finalPrice);
	}

	private static void totalPriceChange() {
		for (int i = 0; i < foodPrice.size(); i++) {
			totalPrice[i] = Amount[i] * foodPrice.get(i);
		}
	}

	private static void priceTotal(String ipOrder) {
		totalPriceChange();
		totalAll[0] = totals();

		if (ipOrder.equalsIgnoreCase("P")) {
			System.out.printf("+------ %s ------+-- %s --+-- %s --+%n", name[4], name[6], name[7]);
			for (int i = 0; i < food.size(); i++) {
				if (Amount[i] > 0) {
					printCheck(food.get(i), Amount[i], totalPrice[i]);
				}
			}
			System.out.println("+----------------------------------------+");
			System.out.printf("|  %-26s|   %7.1f |%n", name[1], totalAll[0]);
			System.out.println("+----------------------------------------+");

		}
	}

	private static void printAmountOrder(int ipOrder) {
		if (ipOrder != food.size()+1 && ipOrder < food.size()+2 && ipOrder > 0) {
			int ipAmount = getScanInt("Enter Quantity: ");
			sc.nextLine();//Bug fix.
			for (int j = 0; j < food.size(); j++) {
				if (ipOrder == j + 1) {
					Amount[j] += ipAmount;
				}
			}

			System.out.printf("You order %d %s.%n", ipAmount, food.get(name(ipOrder)).toLowerCase());

			for (int i = 0; i < food.size(); i++) {
				if (Amount[i] > 0) {
					printOrder(food.get(i), Amount[i]);
				}
			}
		}
	}
	private static boolean isNumber(String ipOrder){
		try {
			Integer.parseInt(ipOrder);
			return true;
		} catch(NumberFormatException ex) {
			return false;
		}
	}

	private static void getOrder() {
		while (true) {
			String ipOrder = getScanString("\nEnter your order: ");

			if (ipOrder.equalsIgnoreCase("E")) {
				System.out.println("\nChecking out....");
				return;
			}
			else if (ipOrder.equalsIgnoreCase("M"))
				printMenu();
			else if (ipOrder.equalsIgnoreCase("P"));
			else if (ipOrder.equalsIgnoreCase("C"))
				getCancel();
			else if (isNumber(ipOrder)){
				if(Integer.parseInt(ipOrder) > food.size() || Integer.parseInt(ipOrder) < 1 )
					System.out.println("Try Again...");
				else printAmountOrder(Integer.parseInt(ipOrder));

			}
			else System.out.println("Try Again...");

			priceTotal(ipOrder);

		}
	}

	private static void getPay(double total) {
		System.out.println("\nYou need to getPay :" + total+" Bath.");

		while (true) {
			double payy = getScanInt("Put your cash: ");
			if (payy < total)
				System.out.println("Not enough ");
			else {
				pay[0] = payy;
				break;
			}

		}

		printReceipt(total);
	}
	private static void getCancel(){
		System.out.print("You have order = {");
		for (int i = 0; i < food.size(); i++) {
			if (Amount[i] > 0) {
				System.out.printf(" %s ", food.get(i));
			}
		}
		System.out.println("}");
		String cancel = getScanString("What menu you want to cancel (Write the name): ");
		if(food.contains(cancel)) {
			for (int i = 0; i < food.size(); i++) {
				if (cancel.equalsIgnoreCase(food.get(i))) {
					Amount[i] = 0;
					System.out.println("Done canceling");
					break;
				}
			}
		}else{
			System.out.println("Its not on the list");
		}


	}
	private static void printReceipt(double total){
        RestaurantManager resRecord = new RestaurantManager();
        resRecord.recordOrder();
        System.out.println("\n****SKE restaurant Receipt****");
		System.out.printf("Receipt No. %d%n" ,resRecord.getOrderCount());
		System.out.printf("%s : %s , %s : %s%n","Date",dateFormat.format(myDate.getTime()),"Time",timeFormat.format(myDate.getTime()));
		priceTotal("P");
		System.out.printf("|  %-26s|   %7.1f |%n", "Pay :", pay[0]);
		System.out.printf("|  %-26s|   %7s |%n", "", "");
		System.out.printf("|  %-26s|   %7.1f |%n", "Change :", pay[0]-total);
		System.out.println("+----------------------------------------+");
		System.out.printf("%n========= Thank you =========");
	}
	public static void main(String[] args) {
        setResFood();
		Amount = new int[foodPrice.size()];
		totalPrice = new double[foodPrice.size()];

		printMenu();
		getOrder();
		getPay(totalAll[0]);
	}
}
