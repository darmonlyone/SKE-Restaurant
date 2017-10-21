import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileOpen {
    static List<String> menuName = new ArrayList<>();
    static List<Integer> menuPrice = new ArrayList<>();
    public static void openFile()  {
        try {
            File theFile = new File("menuFile.txt");
            Scanner fileScanner = new Scanner(theFile);
            while(fileScanner.hasNextLine()) {
                String[] menu = fileScanner.nextLine().replaceAll(" : ","  ").split("  ");
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
