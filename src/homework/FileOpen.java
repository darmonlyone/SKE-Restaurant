package homework;
import java.io.*;
import java.util.*;

public class FileOpen {
    static List<String> menuName = new ArrayList<>();
    static List<Integer> menuPrice = new ArrayList<>();
    public static void openFile()  {
        try {
            File theFile = new File("menuFile.txt");
            Scanner fileScanner = new Scanner(theFile);
            while(fileScanner.hasNextLine()) {
                String[] menu = fileScanner.nextLine().split("  ");
                if(!menu[0].equals("##")) {
                    menuName.add(menu[0]);
                    menuPrice.add(Integer.parseInt(menu[1]));
                }
            }
        } catch (FileNotFoundException e) {
            System.exit(0);
        }

    }

}
