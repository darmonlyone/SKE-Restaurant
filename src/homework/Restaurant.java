package homework;

import java.util.Scanner;

public class Restaurant {
	
	static Scanner sc = new Scanner(System.in);
	static int pizzaAmount=0, chickenAmount = 0, cokeAmount=0, total=0;
    public static void printMenu(){
    	 System.out.println("********** Wellcome to SKE restaurant **********");
        System.out.printf("\tMenu\t\t\t  Cost %n");
        System.out.printf("\t------\t\t\t  ----- %n");
        System.out.printf("1. )\tPizza\t\t\t%5d Bath.%n", 250);
        System.out.printf("2. )\tChickens\t\t%5d Bath.%n", 120);
        System.out.printf("3. )\tCoke\t\t\t%5d Bath.%n", 45);
        System.out.println("4. )\tTotal");
        System.out.println("5. )\tExit");
    }

	private static String name(int a) {
		if (a==1)return "pizza";
		if (a==2)return "chickens";
		if (a==3)return "coke";		
		return null;
	}
	
	private static int totals(int pizzaAmount, int chickenAmount, int cokeAmount) {
	
	return (pizzaAmount*250)+(chickenAmount*120)+(cokeAmount*45);
	}
	
	private static void why(){
		 while (true) {
	            System.out.print("\nEnter your order: ");
	            int ipOrder = sc.nextInt();
	            if (ipOrder>5||ipOrder<0)System.out.println("Try Again...");
	            if (ipOrder == 5)
	                break;
	            if (ipOrder != 4&&ipOrder<6&&ipOrder>0) {
	                System.out.print("Enter Quantity: ");
	                int ipAmount = sc.nextInt();
	                String name = name(ipOrder);
	                System.out.printf("You order %d %s%n",ipAmount,name);
	                if (ipOrder==1)pizzaAmount+=ipAmount;
	                if (ipOrder==2)chickenAmount+=ipAmount;
	                if (ipOrder==3)cokeAmount+=ipAmount;
	                if(pizzaAmount>0)System.out.printf("---> You Order Pizza   = %6d%n",pizzaAmount);
	                if(chickenAmount>0)System.out.printf("---> You Order Chicken = %6d%n",chickenAmount);
	                if(cokeAmount>0)System.out.printf("---> You Order Coke    = %6d%n",cokeAmount);
	            }
	            total = totals(pizzaAmount,chickenAmount,cokeAmount);
	            if (ipOrder == 4) {
	                System.out.println("+------ Menu ------+-- Qty --+-- Price --+");
	                if(pizzaAmount>0) System.out.printf("|  %-16s|%6d   |%7d    |%n", "Pizza", pizzaAmount, pizzaAmount*250);
	                if(chickenAmount>0) System.out.printf("|  %-16s|%6d   |%7d    |%n", "Chicken", chickenAmount, chickenAmount*120);
	                if(cokeAmount>0) System.out.printf("|  %-16s|%6d   |%7d    |%n", "Coke", cokeAmount, cokeAmount*45);
	                System.out.println("+----------------------------------------+");
	                System.out.printf("|  %-26s|%7d    |%n", "Total", total);
	                System.out.println("+----------------------------------------+");

	            }

	        }
	}
	
	public static void main(String[] args) {
     
        
        printMenu();
        why();
        System.out.println("You need to pay :"+total);
        System.out.print("Cash : ");
        int pay = sc.nextInt();
        System.out.println("\nChange :"+(pay-total));
        System.out.printf("%n======== Thank you =========");
    }


}