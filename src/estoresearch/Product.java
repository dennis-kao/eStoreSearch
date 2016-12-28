package estoresearch;

/**
 * public class Product A Product class which stores productID, name, price,
 * year
 */
public abstract class Product {

    private String productID; //only digits, length 6
    private String name;
    private String price;
    private int year; //4 digits, 1000 to 9999

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

    public Product(String newProductID, String newName, String newPrice, String newYear) throws Exception {

        int year;

        if (newYear.isEmpty()) {
            throw new Exception("emptyYear");
        }
        if (!newYear.matches("[0-9]+")) {
            throw new Exception("notAllNumYear");
        }

        year = Integer.parseInt(newYear);

        if (newName.isEmpty()) {
            throw new Exception("emptyName");
        }
        if (year > 9999) {
            throw new Exception("yearOver9999");
        }
        if (year < 1000) {
            throw new Exception("yearUnder1000");
        }
        if (newProductID.isEmpty()) {
            throw new Exception("emptyProductID");
        }

        if (!newProductID.matches("[0-9]+")) {
            throw new Exception("notAllNumID"); //checks if string is all numbers
        }
        if (newProductID.length() != 6) {
            throw new Exception("not6CharID");
        }

        if (newPrice.isEmpty()) {
            throw new Exception("emptyPrice");
        }
        if (validPrice(newPrice) == false) {
            throw new Exception("invalidPrice");
        }

        setProductID(newProductID);
        setName(newName);
        setPrice(newPrice);
        setYear(year);
    }

    public Product(Product aProduct) throws Exception {
        this(aProduct.getProductID(), aProduct.getName(), aProduct.getPrice(), Integer.toString(aProduct.getYear()));
    }

    //product, name, price, year, maker
    //getters
    public int getYear() {
        return year;
    }

    public String getProductID() {
        return productID;
    }

    public String getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getMaker() {
        return "";
    }

    public String getAuthors() {
        return "";
    }

    public String getPublisher() {
        return "";
    }

    //setters
    private void setYear(int newYear) {
        this.year = newYear;
    }

    private void setProductID(String newProductID) {
        this.productID = newProductID;
    }

    private void setPrice(String newPrice) {
        this.price = newPrice;
    }

    private void setName(String newName) {
        this.name = newName;
    }

    /**
     * equals Checks if two products are equal on the basis that their ID, price
     * and name are equal
     *
     * @param  obj - the product to be tested
     * @return true if products are equal
     */
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        } else {

            Product other = (Product) obj;

            if (!(this.getProductID().equals(other.getProductID()))) {
                return false;
            } else if (!(this.getPrice().equals(other.getPrice()))) {
                return false;
            } else if (!(this.getName().equals(other.getName()))) {
                return false;
            } else {
                return true;
            }
        }
    }
}
