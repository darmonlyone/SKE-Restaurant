//import java.io.File;
//import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class RestaurantManager extends Restaurant{

    public void recordOrder(int orderNumber, int order, double total){

    }

    List<FoodManage> foodRead = new ArrayList<>();
    private final String filename = "data/menuFile.txt";

    public void setMenu(){

        ClassLoader loader = RestaurantManager.class.getClassLoader();


        InputStream in = loader.getResourceAsStream( filename );
        if (in == null) {
            System.out.println("Could not access file "+filename);
            return;
        }
        Scanner reader = new Scanner( in );
        while(reader.hasNextLine()) {
        String[] menu = reader.nextLine().replaceAll(" : ", "  ").split("  ");
        if (!menu[0].isEmpty()) {
            if (!menu[0].equals("##")) {
                foodRead.add(new FoodManage(menu[0], Double.parseDouble(menu[1])));
            }
        }
    }


        /*
        for me remembering to use in another way
        */
//    try {
//    File theFile = new File(filename);
//    Scanner fileScanner = new Scanner(theFile);
//    while(fileScanner.hasNextLine()) {
//        String[] menu = fileScanner.nextLine().replaceAll(" : ", "  ").split("  ");
//        if (!menu[0].isEmpty()) {
//            if (!menu[0].equals("##")) {
//                foodRead.add(new FoodManage(menu[0], Double.parseDouble(menu[1])));
//            }
//        }
//    }
//    }catch (FileNotFoundException e) {
//    System.out.println("cannot find file");
//    System.exit(10);
//}




    }

}


