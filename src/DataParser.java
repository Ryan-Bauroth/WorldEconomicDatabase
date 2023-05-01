import java.io.File;
import java.util.Scanner;

/**
 * DataParser - prints all the data for countries
 * @version Friday, April 28, 2023
 * @author Ryfi
 */
public class DataParser {
    /**
     * Adds all the countries from a file, splits them among tab, and then prints them all out
     */
    public static void giveMeAClue(){
        String[] line;
        try{
            Scanner in = new Scanner(new File("IEF_2023_data.txt"));
            line = in.nextLine().split("\t");
            in.close();
            for(String item : line){
                System.out.println(item);
            }
        }
        catch(Exception e){

        }
    }

    /**
     * Main method for DataParser class
     * @param args as needed
     */
    public static void main(String[] args) {
        giveMeAClue();
    }
}
