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


	private static Scanner sc = new Scanner(System.in);
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	private static DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	private static Date myDate = new Date(System.currentTimeMillis());
    private static String[] food;
	private static Double[] foodPrice;
	private static Integer[] quantity;
	private static Double[] totalPrice;
	private static double allPriceTotal,amountPay;


	static String[] name = { "Wellcome to SKE restaurant", "Total", "Exit", "Bath", "Menu", "Cost", "Qty", "Price" };

	//set menu and food price
	private static void setResFood(){
	    RestaurantManager resManage = new RestaurantManager();
	    resManage.setMenu();
        food = resManage.getMenuItem();
        foodPrice = resManage.getMenuPrice();
		quantity = new Integer[food.length];
		totalPrice = new Double[food.length];
		for (int i = 0; i < food.length ; i++) {
			quantity[i] = 0;
			totalPrice[i] = 0.00;
		}
		resManage.clearFoodRead();
	}

	//print a menu
	private static void printMenu() {
		System.out.printf("********** %s **********%n", name[0]);
		System.out.printf("%5s%s%19s%s %n","", name[4],"", name[5]);
		System.out.printf("%4s-------%16s------ %n","","");
		for (int i = 0; i < food.length ; i++) {
			if (food[i] == null)break;
			System.out.printf("%d.) %-20s%6.2f\t%s.%n",i+1, food[i], foodPrice[i], name[3]);
		}
		System.out.printf("%n(%s) %-5s%n","P", "Print order");
		System.out.printf("(%s) %-5s%n","C", "Cancel order");
		System.out.printf("(%s) %-5s%n","M", "Menu");
		System.out.printf("(%s) %-5s%n","E", "Check out");
		System.out.printf("(%s) %-5s%n","A", "Add New food on the menu (You can request only one time)");
	}

	private static int getScanInt(String prompt) {
		System.out.print(prompt);
		return sc.nextInt();
	}

	private static String getScanString(String prompt) {
		System.out.print(prompt);
		return sc.nextLine();
	}

	//return total price
	private static double totals() {
		double result = 0;
		for (int i = 0; i < totalPrice.length ; i++) {
			result += totalPrice[i];
		}
		return result;
	}

	private static void printOrder(String ordername, int Amount) {
		System.out.printf("---> You have order %-15s = %6d%n", ordername, Amount);
	}

	private static void printCheck(String ordername, int Amount, double finalPrice) {
		System.out.printf("|  %-16s|%7d  |   %11.2f |%n", ordername, Amount, finalPrice);
	}

	private static void totalPriceChange() {
		for (int i = 0; i < foodPrice.length; i++) {
			if (foodPrice[i] == null)break;
			totalPrice[i] = quantity[i]*foodPrice[i];
		}
	}

	private static void priceTotal(String ipOrder) {
		totalPriceChange();
		allPriceTotal = totals();
		if (ipOrder.equalsIgnoreCase("P")) {
			System.out.printf("+------ %s ------+-- %s --+---- %s ----+%n", name[4], name[6], name[7]);
			for (int i = 0; i < food.length; i++) {
				if (quantity[i] > 0) {
					printCheck(food[i], quantity[i], totalPrice[i]);
				}
			}
			System.out.println("+--------------------------------------------+");
			System.out.printf("|  %-26s|   %11.2f |%n", name[1], allPriceTotal);
			System.out.println("+--------------------------------------------+");

		}
	}

	private static void printAmountOrder(int ipOrder) {
			while (true) {
				String ipAmountString = getScanString("Enter Quantity: ");
				if (isNumber(ipAmountString)&&Integer.parseInt(ipAmountString) > 0 ) {
					int ipAmount = Integer.parseInt(ipAmountString);
					for (int j = 0; j < food.length; j++) {
						if (ipOrder == j + 1) {
							quantity[j] = quantity[j]+ipAmount;
						}
					}

					System.out.printf("You order %d %s.%n", ipAmount, food[ipOrder-1].toLowerCase());

					for (int i = 0; i < food.length; i++) {
						if (quantity[i] > 0) {
							printOrder(food[i], quantity[i]);
						}
					}
					break;
				} else {
					System.out.println("Try again ......");
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
	private static boolean isFloat(String ipOrder){
		try {
			Double.parseDouble(ipOrder);
			return true;
		} catch(NumberFormatException ex) {
			return false;
		}
	}
	private static void cantOrder(){
		System.out.println("You need to order some food first");
	}

	static boolean addRequest = true;

	private static void getOrder() {
		while (true) {
			String ipOrder = getScanString("\nEnter your order: ");

			if (ipOrder.equalsIgnoreCase("E")) {
				if ((food.length != 0)) {
					System.out.println("\nChecking out....");
					return;
					} else cantOrder();
				}
			else if (ipOrder.equalsIgnoreCase("M"))
				printMenu();
			//You can add new menu only one time
			else if (ipOrder.equalsIgnoreCase("A")) {
				if(addRequest) {
					addMenuRequest();
				}else{
					System.out.println("You already have request adding food");
				}
			}
				else if (ipOrder.equalsIgnoreCase("P")) {
					boolean isit = false;
				for (int i = 0; i <quantity.length ; i++) {
					if (quantity[i] != 0) {
						priceTotal(ipOrder);
						isit = true;
						break;
						}
					}if(!isit)cantOrder();
				}
				else if (ipOrder.equalsIgnoreCase("C")){
					if ((allPriceTotal != 0)) {
						getCancel();
					} else cantOrder();
				}
			else if (isNumber(ipOrder)){
				if(Integer.parseInt(ipOrder) > food.length || Integer.parseInt(ipOrder) < 1 )
					System.out.println("Try Again...");
				else printAmountOrder(Integer.parseInt(ipOrder));

			}
			else System.out.println("Try Again...");



		}
	}

	private static void setPay(double total) {
		System.out.println("\nYou need to pay :" + total+" Bath.");

		while (true) {
			double payy = getScanInt("Put your cash: ");
			if (payy < total)
				System.out.println("Not enough ");
			else {
				amountPay = payy;
				break;
			}

		}

		printReceipt(total);
	}
	private static void getCancel(){
		System.out.print("You have order = {");
		for (int i = 0; i < food.length; i++) {
			if (quantity[i] > 0) {
				System.out.printf(" %s ", food[i]);
			}
		}
		System.out.println("}");
		boolean isit = false;
		String cancel = getScanString("What menu you want to cancel (Write the name): ");
		for (int j = 0 ; j < food.length ; j++) {
			if (food[j]==null)break;
				if (food[j].toLowerCase().equalsIgnoreCase(cancel)) {
					for (int i = 0; i < food.length; i++) {
						if (cancel.equalsIgnoreCase(food[i].toLowerCase())) {
							quantity[i] = 0;
							System.out.println("Done canceling");
							isit = true;
							break;
						}
					}
					break;
				}

		}
			if (!isit){
			System.out.println("Its not on the list");
			return;}
	}
	private static void printReceipt(double total){
        RestaurantManager resRecord = new RestaurantManager();
        resRecord.recordOrder();
        System.out.println("\n****SKE restaurant Receipt****");
		System.out.printf("Receipt No. %d%n" ,resRecord.getOrderCount());
		System.out.printf("%s : %s , %s : %s%n","Date",dateFormat.format(myDate.getTime()),"Time",timeFormat.format(myDate.getTime()));
		priceTotal("P");
		System.out.printf("|  %-26s|   %11.2f |%n", "Pay :", amountPay);
		System.out.printf("|  %-26s|   %11s |%n", "", "");
		System.out.printf("|  %-26s|   %11.2f |%n", "Change :", amountPay-total);
		System.out.println("+--------------------------------------------+");
		System.out.printf("%n========= Thank you =========");
	}
	private static void addMenuRequest(){
		double addPrice;
		RestaurantManager addMenu = new RestaurantManager();
		String addFood = getScanString("What food you want to add to the menu:");
		String addPriceString = getScanString("What price is it:");
		if(isFloat(addPriceString)) {
			addPrice = Double.parseDouble(addPriceString);
			addRequest = false;
		}else{
			System.out.println("Pleas input number not string");
			System.out.println("Try again later...");
			return;
		}
		addMenu.addMenu(addFood,addPrice);
		food[food.length-1] = addFood;
		foodPrice[foodPrice.length-1] = Double.parseDouble(addPriceString);

		System.out.println("Done add menu");
	}
	public static void main(String[] args) {
        setResFood();
		printMenu();
		getOrder();
		setPay(allPriceTotal);
	}

	public static Date getMyDate() {
		return myDate;
	}

	public static DateFormat getDateFormat() {
		return dateFormat;
	}

	public static DateFormat getTimeFormat() {
		return timeFormat;
	}

	public static double getAllPriceTotal() {
		return allPriceTotal;
	}

	public static double getAmountPay() {
		return amountPay;
	}

	public static Double[] getFoodPrice() {
		return foodPrice;
	}

	public static Double[] getTotalPrice() {
		return totalPrice;
	}

	public static Integer[] getQuantity() {
		return quantity;
	}

	public static String[] getFood() {
		return food;
	}

}
