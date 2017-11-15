import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
* This class for taking or recording to log file.
* Using fileLoader and scanner to taking a menu and price from log file.
* Using fileOutputStream for record receipt from Restaurant class.
*
*/
public class RestaurantManager extends Restaurant{

    private final String fileRecord = "src/data/RecordOrder.log";
    private final String fileRecordIo = "data/RecordOrder.log";
    private int orderCount = 1;
    private List<String> recordOld = new ArrayList<>();

    public int getOrderCount() {
        return orderCount;
    }

    public void setRecordOrder(){
        Scanner reader = new Scanner(getFile(fileRecordIo));
        while(reader.hasNextLine()) {
                recordOld.add(reader.nextLine());
            }
        recordOld.add("");
    }
    public void setRecordCount() {
        Scanner reader = new Scanner(getFile(fileRecordIo));
        while (reader.hasNextLine()) {
            String[] recover = reader.nextLine().split("  ");
            if(recover[0].startsWith("Order")){
                this.orderCount++;
            }
        }
    }

    public void recordOrder(){
        OutputStream Ops = null;
        setRecordOrder();
        setRecordCount();
        try{
            Ops = new FileOutputStream(fileRecord);
            PrintStream PrintStream = new PrintStream(Ops);
            for(String PutOldOrder : recordOld){
                PrintStream.println(PutOldOrder);
            }
            PrintStream.printf("Order number : %s%n",orderCount);
            PrintStream.printf("%s : %s , %s : %s%n","Date",getDateFormat().format(getMyDate().getTime()),"Time",getTimeFormat().format(getMyDate().getTime()));
            PrintStream.printf("+------ %s ------+-- %s --+---- %s ----+%n", "Menu", "Qty", "Price");
            for (int i = 0; i < getFood().length; i++) {
                if (getQuantity()[i] > 0) {
                    PrintStream.printf("|  %-16s|%7d  |   %11.2f |%n", getFood()[i], getQuantity()[i], getTotalPrice()[i]);
                }
            }
            PrintStream.println("+--------------------------------------------+");
            PrintStream.printf("|  %-26s|   %11.2f |%n", "Total", getAllPriceTotal());
            PrintStream.printf("|  %-26s|   %11.2f |%n", "Pay :", getAmountPay());
            PrintStream.printf("|  %-26s|   %11s |%n", "", "");
            PrintStream.printf("|  %-26s|   %11.2f |%n", "Change :", getAmountPay() - getAllPriceTotal());
            PrintStream.println("+--------------------------------------------+");

        } catch (FileNotFoundException e){
            System.out.println("Couldn't open output file " + fileRecord);
            System.exit(3);
        }
    }

    private List<FoodManage> foodRead = new ArrayList<>();
    private final String fileNameIo = "data/MenuFile.log";
    private final String fileName = "src/data/MenuFile.log";

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

    private List<String> oldMenu =  new ArrayList<>();
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
                Ops = new FileOutputStream(fileName);
                PrintStream PrintStream = new PrintStream(Ops);
                for (String PutOldOrder : oldMenu) {
                    PrintStream.println(PutOldOrder);
                }
                PrintStream.printf("%s : %.2f",foodAdd,priceAdd);
            }catch (FileNotFoundException e){
                    System.out.println("Couldn't open output file " + fileName);
                    System.exit(3);
                }
                oldMenu.clear();
    }

    public String[] getMenuItem(){
            String[] item = new String[foodRead.size()+1];
            int i =0;
            for (FoodManage menuItems : foodRead){
                item[i] = menuItems.getFoodName();
                i++;
            }
            return item;
    }
    public Double[] getMenuPrice(){
        Double[] itemPrice = new Double[foodRead.size()+1];
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


