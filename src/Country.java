import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * Object Constructor for Country
 * @author JCochran tbh but I made an edit so all credit goes to me (of course)
 * @version Friday, April 28, 2023
 */
public class Country implements Comparable<Country> {
    private int ID;
    private String countryName, webName;
    private String region;
    private HashMap<String, Double> categoryIndices;
    private double economicFreedomIndex;
    private String secondaryCategory;

    /**
     * Constructor for the Country Class
     * @param id
     * @param name
     * @param wName
     * @param region
     */
    public Country(int id, String name, String wName, String region)    {
        this.ID = id;
        this.countryName = name;
        this.webName = wName;
        this.region = region;
        this.secondaryCategory = "";    // populate this with a sub-category for display
        categoryIndices = new HashMap<>();
        categoryIndices.put("Property Rights", 0.0);
        categoryIndices.put("Judicial Effectiveness", 0.0);
        categoryIndices.put("Government Integrity", 0.0);
        categoryIndices.put("Tax Burden", 0.0);
        categoryIndices.put("Government Spending", 0.0);
        categoryIndices.put("Fiscal Health", 0.0);
        categoryIndices.put("Business Freedom", 0.0);
        categoryIndices.put("Labor Freedom", 0.0);
        categoryIndices.put("Monetary Freedom", 0.0);
        categoryIndices.put("Trade Freedom", 0.0);
        categoryIndices.put("Investment Freedom", 0.0);
        categoryIndices.put("Financial Freedom", 0.0);
    }

    /**
     * Assigns the key values to the map
     * @param values
     */
    public void populateIndices(double[] values)    {
        int i = 0;
        double sum = 0.;
        for(String key : categoryIndices.keySet())  {
            sum += values[i];
            categoryIndices.put(key, values[i]);
            i++;
        }
        // calculate economic freedom index
        economicFreedomIndex = sum/categoryIndices.size();
    }

    /**
     * Sets the secondary category
     * @param sTerm
     */
    public void setSecondaryCategory(String sTerm)  {
        secondaryCategory = sTerm;
    }

    /**
     * toString method defined for Country Object
     * @return
     */
    public String toString()    {
        DecimalFormat df = new DecimalFormat("0.00");
        String asString = ID + ": " + countryName + " | Region: " + region +
                " | Economic Freedom Index: " + df.format(economicFreedomIndex);
        if(!secondaryCategory.equals(""))
            asString += " | " + secondaryCategory + ": " + categoryIndices.get(secondaryCategory);
        return asString;
    }

    /**
     * Compares two countries EFI's
     * @param other the object to be compared.
     * @return the difference between the two compared EFI's
     */
    public int compareTo(Country other) {
        Double myEFI = economicFreedomIndex;
        Double otherEFI = other.economicFreedomIndex;
        return myEFI.compareTo(otherEFI);
    }

    /**
     * Gets EFI
     * @return EFI of the country
     */
    public double getEconomicFreedomIndex() {
        return economicFreedomIndex;
    }

    /**
     * Gets the index value of the country
     * @param indexName
     * @return index value
     */
    public double getIndexValue(String indexName)   {
        return categoryIndices.get(indexName);
    }

    /**
     * gets the ID of the country
     * @return the ID of the country
     */
    public int getID() {
        return ID;
    }

    /**
     * Gets the name of the country
     * @return the name of the country
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Gets the region of the country
     * @return the region of the country
     */
    public String getRegion() {
        return region;
    }
}
