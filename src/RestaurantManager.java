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
    List<String> recordOld = new ArrayList<>();

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
        setRecordCount();
        setRecordOrder();
        try{
            Ops = new FileOutputStream(fileRecord);
            PrintStream PrintStream = new PrintStream(Ops);
            for(String PutOldOrder : recordOld){
                PrintStream.println(PutOldOrder);
            }
            PrintStream.printf("Order number : %s%n",orderCount);
            PrintStream.printf("%s : %s , %s : %s%n","Date",dateFormat.format(myDate.getTime()),"Time",timeFormat.format(myDate.getTime()));
            PrintStream.printf("+------ %s ------+-- %s --+-- %s --+%n", name[4], name[6], name[7]);
            for (int i = 0; i < food.size(); i++) {
                if (Amount[i] > 0) {
                    PrintStream.printf("|  %-16s|%6d   |   %7.1f |%n", food.get(i), Amount[i], totalPrice[i]);
                }
            }
            PrintStream.println("+----------------------------------------+");
            PrintStream.printf("|  %-26s|   %7.1f |%n", name[1], totalAll[0]);
            PrintStream.printf("|  %-26s|   %7.1f |%n", "Pay :", pay[0]);
            PrintStream.printf("|  %-26s|   %7s |%n", "", "");
            PrintStream.printf("|  %-26s|   %7.1f |%n", "Change :", pay[0]-totalAll[0]);
            PrintStream.println("+----------------------------------------+");

        } catch (FileNotFoundException e){
            System.out.println("Couldn't open output file " + fileRecord);
            System.exit(3);
        }
    }

    List<FoodManage> foodRead = new ArrayList<>();
    private final String fileName = "data/MenuFile.log";

    public void setMenu(){

       Scanner reader = new Scanner( getFile(fileName));
        while(reader.hasNextLine()) {
        String[] menu = reader.nextLine().replaceAll(" : ", "  ").split("  ");
        if (!menu[0].isEmpty()) {
            if (!menu[0].equals("##")) {
                foodRead.add(new FoodManage(menu[0], Double.parseDouble(menu[1])));
            }
          }

        }

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


