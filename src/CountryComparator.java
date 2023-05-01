import java.util.Comparator;

/**
 * Country comparision class
 * @version Friday, April 28, 2023
 * @author Ryfi
 */
public class CountryComparator implements Comparator<Country> {
    private boolean asc;
    private String primarySort, secondarySort;

    /**
     * Constructor of CountryComparator class
     * @param a
     * @param p
     * @param s
     */
    public CountryComparator(boolean a, String p, String s){
        asc = a;
        primarySort = p;
        secondarySort = s;
    }

    /**
     * defines what compare method of Country should be
     * @param one the first object to be compared.
     * @param two the second object to be compared.
     * @return difference between the two objects using whatever primary sort says
     */
    public int compare(Country one, Country two) {
        int diff = switch (primarySort) {
            case "Country ID" -> one.getID() - two.getID();
            case "Country Name" -> one.getCountryName().compareTo(two.getCountryName());
            case "Region" -> one.getRegion().compareTo(two.getRegion());
            case "Economic Freedom Index" ->
                    Double.compare(one.getEconomicFreedomIndex(), two.getEconomicFreedomIndex());
            default -> Double.compare(one.getIndexValue(secondarySort), two.getIndexValue(secondarySort));
        };
        return asc ? diff : -diff;
    }
}
