import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Economic Database Class
 * @version Friday, April 28, 2023
 * @author Ryfi
 * I used CHAT GPT to help with the MergeSort
 */
public class EconomicDatabase {
    private ArrayList<Country> database;
    private String[] categories;
    private double[] indices;
    private CountryComparator myComparator;
    private String primarySort, secondarySort;
    private boolean asc;
    private String[] primaryTerms = {"ID: Country ID", "NA: Country Name", "RE: Region", "EF: Economic Freedom Index", "IN: Specific Index"};
    private String[] secondaryTerms = {"JE: Judicial Effectiveness","GI: Government Integrity","TB: Tax Burden","GS: Government Spending",
    "FH: Fiscal Health","BF: Business Freedom","LF: Labor Freedom","MF: Monetary Freedom","TF: Trade Freedom","IF: Investment Freedom","FF: Financial Freedom"};

    /**
     * Constructor for EconomicDatabase class
     */
    public EconomicDatabase()   {
        database = new ArrayList<>();
        categories = new String[16];
        indices = new double[12];
        primarySort = "";
        secondarySort = "";
    }

    /**
     * Adds information from the file to a "database" ArrayList
     */
    public void populateDatabase() {
        String filename = "IEF_2023_data.txt";
        try {
            boolean firstLine = true;
            Scanner in = new Scanner(new File(filename));
            String[] line;
            while(in.hasNext()) {
                if (firstLine) {
                    categories = in.nextLine().split("\t");
                    firstLine = false;
                }
                else {
                    line = in.nextLine().split("\t");
                    database.add(new Country(Integer.parseInt(line[0]), line[1], line[2], line[3]));
                    indices[0] = Double.parseDouble(line[4]);
                    indices[1] = Double.parseDouble(line[5]);
                    indices[2] = Double.parseDouble(line[6]);
                    indices[3] = Double.parseDouble(line[7]);
                    indices[4] = Double.parseDouble(line[8]);
                    indices[5] = Double.parseDouble(line[9]);
                    indices[6] = Double.parseDouble(line[10]);
                    indices[7] = Double.parseDouble(line[11]);
                    indices[8] = Double.parseDouble(line[12]);
                    indices[9] = Double.parseDouble(line[13]);
                    indices[10] = Double.parseDouble(line[14]);
                    indices[11] = Double.parseDouble(line[15]);
                    database.get(database.size()-1).populateIndices(indices);
                }
            }
            in.close();
        }
        catch(Exception e)  {
            e.printStackTrace();
        }
    }

    /**
     * Method getSearchCriteria allows the user to select which search critera to use
     * @return true if search should happen and false if the user wants to quit
     */
    public boolean getSearchCriteria()  {
        String userIn;
        Scanner in = new Scanner(System.in);
        while(true) {
            System.out.println("+ Menu of search terms to use for sorting countries +");
            for(String term : primaryTerms)
                System.out.println("\t" + term);
            System.out.print("Enter your preferred sort by term or 'Q' to quit: ");
            userIn = in.nextLine();
            if(userIn.equalsIgnoreCase("q")) {
                in.close();
                return false;
            }
            primarySort = userIn.toUpperCase();
            if(!primarySort.equals("IN")) {
                // prompt for asc/dsc
                System.out.print("Enter 'A' to sort in ascending order or 'D' to sort in descending order: ");
                userIn = in.nextLine();
                asc = (userIn.equalsIgnoreCase("a")) ? true : false;
                for(String term : primaryTerms) {
                    if(term.indexOf(primarySort) != -1) {
                        primarySort = term.substring(4);    // for map retrieval
                        return true;
                    }
                }
                System.out.println("Invalid search term entered, try again.");
            }
            else {
                System.out.println("Search term selected specific index, please select the index value to display:");
                for(String term : secondaryTerms)
                    System.out.println("\t"+term);
                System.out.print("Enter your preferred sort by index or 'Q' to quit: ");
                userIn = in.nextLine();
                if(userIn.equalsIgnoreCase("q")) {
                    return false;
                }
                secondarySort = userIn.toUpperCase();
                System.out.print("Enter 'A' to sort in ascending order or 'D' to sort in descending order: ");
                userIn = in.nextLine();
                asc = (userIn.equalsIgnoreCase("a")) ? true : false;
                for(String term : secondaryTerms) {
                    if(term.indexOf(secondarySort) != -1) {
                        primarySort = "Specific Index";
                        secondarySort = term.substring(4); // for map retrieval
                        return true;
                    }
                }
                System.out.println("Invalid search term entered, try again.");
            }
        }
    }

    /**
     * Calls the merge function and sorts the Database
     */
    public void sortDB()    {
        CountryComparator comp = new CountryComparator(asc, primarySort, secondarySort);
        mergeSort(database, 0, database.size() - 1, comp);
    }
    private static void mergeSort(ArrayList<Country> arr, int l, int r, CountryComparator comp) {
        if (l < r) {
            int m = (l + r) / 2;
            mergeSort(arr, l, m, comp);
            mergeSort(arr, m + 1, r, comp);
            merge(arr, l, m, r, comp);
        }
    }

    private static void merge(ArrayList<Country> arr, int l, int m, int r, CountryComparator comp) {
        ArrayList<Country> left = new ArrayList<>();
        ArrayList<Country> right = new ArrayList<>();

        for (int i = l; i <= m; i++) {
            left.add(arr.get(i));
        }
        for (int i = m + 1; i <= r; i++) {
            right.add(arr.get(i));
        }

        int i = 0, j = 0, k = l;
        while (i < left.size() && j < right.size()) {
            if (comp.compare(left.get(i), right.get(j)) < 0) {
                arr.set(k, left.get(i));
                i++;
            } else {
                arr.set(k, right.get(j));
                j++;
            }
            k++;
        }
        while (i < left.size()) {
            arr.set(k, left.get(i));
            i++;
            k++;
        }
        while (j < right.size()) {
            arr.set(k, right.get(j));
            j++;
            k++;
        }
    }

    /**
     * Iterates through and prints the database out
     */
    public void printDatabase() {
        for(int i = 0; i < database.size(); i++) {
            database.get(i).setSecondaryCategory(secondarySort);
            System.out.println(database.get(i));
        }
    }

    /**
     * Main Method of class Economic Database
     * @param args as needed
     */
    public static void main(String[] args) {
        EconomicDatabase database = new EconomicDatabase();
        database.populateDatabase();
        System.out.println("*** Welcome to the World Economic Freedom Database ***");
        while (database.getSearchCriteria()) {
            database.sortDB();
            database.printDatabase();
        }
        System.out.println("Exiting the program");
    }
}
