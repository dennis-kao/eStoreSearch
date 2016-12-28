package estoresearch;

/**
 * public class Electronics An Electronics class which stores productID, name,
 * price, year, and maker
 */
public class Electronics extends Product {

    private String maker;

    public Electronics(String newProductID, String newName, String newPrice, String newYear, String newMaker) throws Exception {

        super(newProductID, newName, newPrice, newYear);
        setMaker(newMaker);
    }

    public Electronics(String newProductID, String newName, String newPrice, String newYear) throws Exception {

        super(newProductID, newName, newPrice, newYear);
        setMaker("");
    }

    private void setMaker(String newMaker) {
        this.maker = newMaker;
    }

    public String getMaker() {
        return maker;
    }

    /**
     * equals Checks if two electronics are equal based on ID, price, name,
     * maker
     *
     * @param  obj - the electronic to be tested
     * @return true if electronics are equal
     */
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        } else {

            Electronics other = (Electronics) obj;

            if (!getProductID().equals(other.getProductID())) {
                return false;
            } else if (!getPrice().equals(other.getPrice())) {
                return false;
            } else if (!getName().equals(other.getName())) {
                return false;
            } else if (!getMaker().equals(other.getMaker())) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * toString Returns a string with an electronics name, ID, year, price,
     * maker
     *
     * @param 
     * @return String containing the information
     */
    @Override
    public String toString() {

        String electronicString = new String("Name: " + getName() + " ProductID: " + getProductID() + " Year: " + getYear() + " Price: " + getPrice() + " Maker: " + getMaker());

        return electronicString;
    }
}
