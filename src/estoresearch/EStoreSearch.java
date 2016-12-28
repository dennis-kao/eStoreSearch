package estoresearch;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * EStoreSearch A store system which allows the user to input items and search
 * for them Product information is loaded and saved from a command-line
 * specified text file
 */
public class EStoreSearch {

    protected static HashMap<String, ArrayList<Integer>> hashIndex = new HashMap<>();
    protected static ArrayList<Product> products = new ArrayList<Product>();

    /**
     * isExistingID Checks if the productID appears in the two lists Returns
     * true if it does
     *
     * @param String productID
     * @param ArrayList<Book> books - first list
     * @param ArrayList<Electronics> electronics - second list
     * @return true if product is in list
     */
    public static boolean isExistingID(String productID, ArrayList<Product> products) {

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductID().equals(productID)) {
                return true;
            }
        }

        /*if both loops fail to find a matching product ID*/
        return false;
    }

    /**
     * isAllNumbers Checks if the string only contains number characters Returns
     * true if it does
     *
     * @param String tested - the string to be tested
     * @return true string only has digits
     */
    public static boolean isAllNumbers(String tested) {

        for (int j = 0; j < tested.length(); j++) {

            if (tested.charAt(j) == '0'
                    || tested.charAt(j) == '1'
                    || tested.charAt(j) == '2'
                    || tested.charAt(j) == '3'
                    || tested.charAt(j) == '4'
                    || tested.charAt(j) == '5'
                    || tested.charAt(j) == '6'
                    || tested.charAt(j) == '7'
                    || tested.charAt(j) == '8'
                    || tested.charAt(j) == '9'); else {
                return false;
            }
        }

        return true;
    }

    /**
     * validPrice Checks if the string contains at most one decimal place and
     * numbers Returns true if it does
     *
     * @param String tested - the string to be tested
     * @return true if the string is a price
     */
    public static boolean validPrice(String tested) {

        int decimalPoint = 0;

        for (int j = 0; j < tested.length(); j++) {

            if (tested.charAt(j) == '0'
                    || tested.charAt(j) == '1'
                    || tested.charAt(j) == '2'
                    || tested.charAt(j) == '3'
                    || tested.charAt(j) == '4'
                    || tested.charAt(j) == '5'
                    || tested.charAt(j) == '6'
                    || tested.charAt(j) == '7'
                    || tested.charAt(j) == '8'
                    || tested.charAt(j) == '9'); else if (tested.charAt(j) == '.') {
                if (decimalPoint == 0) {
                    decimalPoint = 1;
                } else if (decimalPoint == 1) {
                    return false;
                }
            } else {
                return false;
            }
        }

        return true;
    }

    /**
     * searchAndPrint Searches a for a product using a HashMap on the basis of
     * productID, keywords and year Prints out all matching products at the end
     *
     * @param String productID
     * @param String keywords
     * @param String time
     * @param ArrayList<Product> products
     * @param HashMap<String, ArrayList<Integer>> hashIndex
     * @return void
     */
    public static void searchAndPrint(String productID, String keywords, String time, ArrayList<Product> products, HashMap<String, ArrayList<Integer>> hashIndex) {

        String[] token;
        String[] nameToken;

        boolean match = false;

        ArrayList<Product> productList = new ArrayList<Product>();

        keywords = keywords.toLowerCase();

        if (keywords.isEmpty()) {
            productList = new ArrayList<Product>(products);
        } else {

            ArrayList<Integer> pastIndex = new ArrayList<Integer>();
            ArrayList<Integer> wordIndex = new ArrayList<Integer>();
            ArrayList<Integer> futureIndex = new ArrayList<Integer>();

            token = keywords.split("[ ]+");

            if (hashIndex.containsKey(token[0])) {
                pastIndex = new ArrayList<Integer>(hashIndex.get(token[0]));
            }

            for (int i = 1; i < token.length; i++) {

                futureIndex.clear();
                wordIndex.clear();

                if (hashIndex.containsKey(token[i])) {
                    wordIndex = new ArrayList<Integer>(hashIndex.get(token[i]));
                }

                for (int j = 0; j < products.size(); i++) {
                    if (wordIndex.contains(j) && pastIndex.contains(j)) {
                        futureIndex.add(j);
                    }
                }

                pastIndex = new ArrayList<Integer>(futureIndex);
            }

            for (int i = 0; i < pastIndex.size(); i++) {

                productList.add(products.get(pastIndex.get(i)));
            }
        }

        if (!productID.isEmpty()) {

            for (int i = productList.size() - 1; i >= 0; i--) {
                if (!productList.get(i).getProductID().equals(productID)) {
                    productList.remove(i);
                }
            }
        }

        if (!time.isEmpty()) {

            if (time.length() == 4) {

                for (int i = productList.size() - 1; i >= 0; i--) {
                    if (productList.get(i).getYear() != Integer.parseInt(time)) {
                        productList.remove(i);
                    }
                }
            } else if (time.length() == 5) {

                if (time.charAt(0) == '-') {

                    for (int i = productList.size() - 1; i >= 0; i--) {
                        if (productList.get(i).getYear() > Integer.parseInt(time.substring(1, 5))) {
                            productList.remove(i);
                        }
                    }
                } else if (time.charAt(4) == '-') {

                    for (int i = productList.size() - 1; i >= 0; i--) {
                        if (productList.get(i).getYear() < Integer.parseInt(time.substring(0, 4))) {
                            productList.remove(i);
                        }
                    }
                }
            } else if (time.length() == 9) {

                for (int i = productList.size() - 1; i >= 0; i--) {
                    if (productList.get(i).getYear() < Integer.parseInt(time.substring(0, 4)) || productList.get(i).getYear() > Integer.parseInt(time.substring(5, 9))) {
                        productList.remove(i);
                    }
                }
            }
        }

        mainWindow.log.append("Here are the matching items: \n");

        for (int i = 0; i < productList.size(); i++) {
            mainWindow.log.append(productList.get(i).toString() + "\n");
        }
    }

    /**
     * createIndex Clears a hashmap, then fills it in using keywords from the
     * names of a product list
     *
     * @param HashMap<String, ArrayList<Integer>> hashIndex
     * @param ArrayList<Product> products
     * @return void
     */
    public static void createIndex(HashMap<String, ArrayList<Integer>> hashIndex, ArrayList<Product> products) {

        String temp;
        StringTokenizer st;

        hashIndex.clear();

        for (int i = 0; i < products.size(); i++) {
            st = new StringTokenizer(products.get(i).getName());

            while (st.hasMoreTokens()) {
                temp = st.nextToken().toLowerCase();

                if (hashIndex.containsKey(temp)) {
                    hashIndex.get(temp).add(i);
                } else {
                    hashIndex.put(temp, new ArrayList<Integer>());
                    hashIndex.get(temp).add(i);
                }
            }
        }
    }
    
    /**
     * updateIndex - updates the index of a HashMap to reflect a new product
     *
     * @param HashMap<String, ArrayList<Integer>> hashIndex
     * @param Product product - new product to be added
     * @param int index - the index of the new item
     * @return void
     */
    public static void updateIndex(HashMap<String, ArrayList<Integer>> hashIndex, Product addMe, int index) {

        String temp;
        StringTokenizer st;

        st = new StringTokenizer(addMe.getName());

        while (st.hasMoreTokens()) {
            temp = st.nextToken().toLowerCase();

            if (hashIndex.containsKey(temp)) {
                hashIndex.get(temp).add(index);
            } else {
                hashIndex.put(temp, new ArrayList<Integer>());
                hashIndex.get(temp).add(index);
            }
        }
    }

    /**
     * main parses a file called "data.txt" in the same folder as the program
     * It initializes the database and launches the GUI
     * 
     * @param String[] args - arg[0] should be the path to the data file
     * @return void
     */
    public static void main(String[] args) {

        Scanner scannerObj = new Scanner(System.in);
        PrintWriter outputStream = null;
        Scanner StreamObject = null;

        /* used for menu navigation */
        String input;
        String oneorTwo;

        /* used to store input information a new item */
        String productID;
        String name;
        String year;
        String price;
        String author;
        String publisher;
        String maker;
        int validResponse;
        Book newb;
        Electronics newe;

        /* used for search */
        String keywords;
        String time;

        /*File IO */
        String inputFile = "data.txt";
        String productData;
        String line;

        mainWindow gui = new mainWindow();
        gui.setVisible(true);

        try {
            StreamObject = new Scanner(new FileInputStream(inputFile));

            while (StreamObject.hasNextLine()) {
                line = StreamObject.nextLine();

                if (line.equals("book")) {

                    productID = StreamObject.nextLine();
                    name = StreamObject.nextLine();
                    price = StreamObject.nextLine();
                    year = StreamObject.nextLine();
                    author = StreamObject.nextLine();
                    publisher = StreamObject.nextLine();

                    try {
                        products.add(new Book(productID, name, price, year, author, publisher));
                    } catch (Exception e) {
                        mainWindow.log.append(e.toString() + ": Could not parse a book. Skipping.");
                    }
                } else if (line.equals("electronics")) {

                    productID = StreamObject.nextLine();
                    name = StreamObject.nextLine();
                    price = StreamObject.nextLine();
                    year = StreamObject.nextLine();
                    maker = StreamObject.nextLine();

                    try {
                        products.add(new Electronics(productID, name, price, year, maker));
                    } catch (Exception e) {
                        mainWindow.log.append(e.toString() + ": Could not parse an electronic. Skipping.");
                    }
                }
            }

            if (!(products.isEmpty())) {
                createIndex(hashIndex, products);
            }
        } catch (FileNotFoundException e) {
            mainWindow.log.append("inputFile could not be found, or could not be opened");
            mainWindow.log.append("The system will create an empty list");
        }
    }
}
