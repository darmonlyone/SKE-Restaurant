import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RestaurantManager {
    static List<String> menuName = new ArrayList<>();
    static List<Double> menuPrice = new ArrayList<>();

    public static void getMenuItems(Scanner fileScanner,String [] menuNameList){

          if(!menuNameList[0].equals("##")) {
             menuName.add(menuNameList[0]);
           }

    }
    public static void getPrices(Scanner fileScanner, String [] menuNamePrice) {

            if (!menuNamePrice[0].equals("##")) {
                menuPrice.add(Double.parseDouble(menuNamePrice[1]));
            }

    }
    public static void recordOrder(int orderNumber, int[] order, double total){

    }
    static void setMenu(String filename){
        try {
            File theFile = new File(filename);
            Scanner fileScanner = new Scanner(theFile);
            while(fileScanner.hasNextLine()){
                String[] menu = fileScanner.nextLine().replaceAll(" : ","  ").split("  ");
                getMenuItems(fileScanner,menu);
                getPrices(fileScanner,menu);
            }


        } catch (FileNotFoundException e) {
            System.exit(0);
        }
    }

    public static void init() {
        String whereFile = "src/data/menuFile.txt";
        setMenu(whereFile);
    }
}


