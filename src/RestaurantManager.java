import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
* This class for taking or recording to log file.
* Using fileLoader and scanner to taking a menu and price from log file.
* Using fileOutputStream for record receipt from Restaurant class.
*
* @author Manusporn Fukkham
*/
public class RestaurantManager {

    private List<String> oldMenu =  new ArrayList<>();
    private List<String> recordReceipt = new ArrayList<>();
    private List<FoodManage> foodRead = new ArrayList<>();
    private final String fileOutputStreamName = "src/data/MenuFile.log";
    private final String fileOutputStreamRecord = "src/data/RecordOrder.log";
    private final String fileRecordIo = "data/RecordOrder.log";
    private final String fileNameIo = "data/MenuFile.log";
    private int orderCount = 1;

    public int getOrderCount() {
        return orderCount;
    }

    public void setCountOrder() {
        Scanner reader = new Scanner(getFile(fileRecordIo));
        while (reader.hasNextLine()) {
            String[] recover = reader.nextLine().split("  ");
            if(recover[0].startsWith("Order")){
                this.orderCount++;
            }
        }
        reader.close();
    }

    public void recordOrder(String[] food, int[] quantity , double[] totalPrice, double allPriceTotal , double amountPay ){
        setCountOrder();

        OutputStream Ops = null;
        Scanner reader = new Scanner(getFile(fileRecordIo));
        while(reader.hasNextLine()) {
            recordReceipt.add(reader.nextLine());
        }
        recordReceipt.add("");
        reader.close();

        try{
            Ops = new FileOutputStream(fileOutputStreamRecord);
            PrintStream PrintStream = new PrintStream(Ops);
            for(String PutOldOrder : recordReceipt){
                PrintStream.println(PutOldOrder);
            }
            PrintStream.printf("Order number : %s%n",orderCount);
            PrintStream.printf("%s : %s , %s : %s%n","Date", Restaurant.getDateFormat().format(Restaurant.getMyDate().getTime()),"Time", Restaurant.getTimeFormat().format(Restaurant.getMyDate().getTime()));
            PrintStream.printf("+------ %s ------+-- %s --+---- %s ----+%n", "Menu", "Qty", "Price");
            for (int i = 0; i < food.length; i++) {
                if (quantity[i] > 0) {
                    PrintStream.printf("|  %-16s|%7d  |   %11.2f |%n", food[i], quantity[i], totalPrice[i]);
                }
            }
            PrintStream.println("+--------------------------------------------+");
            PrintStream.printf("|  %-26s|   %11.2f |%n", "Total", allPriceTotal);
            PrintStream.printf("|  %-26s|   %11.2f |%n", "Pay :", amountPay);
            PrintStream.printf("|  %-26s|   %11s |%n", "", "");
            PrintStream.printf("|  %-26s|   %11.2f |%n", "Change :", amountPay - allPriceTotal);
            PrintStream.println("+--------------------------------------------+");

        } catch (FileNotFoundException e){
            System.out.println("Couldn't open output file " + fileOutputStreamRecord);
            System.exit(3);
        }
    }

    public void clearFoodRead(){
        foodRead.clear();
    }

    public void setMenu() {

        Scanner reader = new Scanner(getFile(fileNameIo));
        while (reader.hasNextLine()) {
            String[] menu = reader.nextLine().replaceAll(" : ", "  ").split("  ");
            if (!menu[0].isEmpty()) {
                if (!menu[0].equals("##")) {
                    foodRead.add(new FoodManage(menu[0], Double.parseDouble(menu[1])));
                }
            }

        }
        reader.close();
    }



    public void setAddmenu(){
        Scanner reader = new Scanner(getFile(fileNameIo));
        while(reader.hasNextLine()) {
            oldMenu.add(reader.nextLine());
        }
        reader.close();

    }
        public void addMenu(String foodAdd, double priceAdd){
        setAddmenu();

            OutputStream Ops = null;
            try {
                Ops = new FileOutputStream(fileOutputStreamName);
                PrintStream PrintStream = new PrintStream(Ops);
                for (String PutOldOrder : oldMenu) {
                    PrintStream.println(PutOldOrder);
                }
                PrintStream.printf("%s : %.2f",foodAdd,priceAdd);
            }catch (FileNotFoundException e){
                    System.out.println("Couldn't open output file " + fileOutputStreamName);
                    System.exit(3);
                }
                oldMenu.clear();
    }

    public String[] getMenuItems(){
            String[] item = new String[foodRead.size()+1];
            int i =0;
            for (FoodManage menuItems : foodRead){
                item[i] = menuItems.getFoodName();
                i++;
            }
            return item;
    }
    public double[] getPrices(){
        double[] itemPrice = new double[foodRead.size()+1];
        int i = 0;
        for (FoodManage menuPrice : foodRead){
            itemPrice[i] = menuPrice.getPrice();
            i++;
        }
        return itemPrice;
    }

    public InputStream getFile(String file) {
        ClassLoader loader = RestaurantManager.class.getClassLoader();
        InputStream iS = loader.getResourceAsStream(file);
        if (iS == null) {
            System.err.println("Could not find resource " + file);
            System.exit(2);
        }
        return iS;
    }
}


