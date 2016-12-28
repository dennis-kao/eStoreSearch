package estoresearch;

/**
 * public class Book
 * A Book class which stores productID, name, price, year author and publisher
 */
public class Book extends Product {

    private String authors;
    private String publisher;

    public Book (String newProductID, String newName, String newPrice, String newYear, String newAuthors, String newPublisher) throws Exception{
        
        super(newProductID, newName, newPrice, newYear);
        setAuthors(newAuthors);
        setPublisher(newPublisher);
    }

    public Book (String newProductID, String newName, String newPrice, String newYear) throws Exception{

        super(newProductID, newName, newPrice, newYear);
        setAuthors("");
        setPublisher("");
    }

    public String getAuthors() {

        if ("".equals(this.authors)) return "";
        else return authors;
    }

    public String getPublisher() {

        if ("".equals(this.publisher)) return "";
        else return publisher;
    }

    //setters

    private void setAuthors(String newAuthors) {
        this.authors = newAuthors;
    }

    private void setPublisher(String newPublisher) {
        this.publisher = newPublisher;
    }

    /**
    * equals - Compares two books for equality, returns true if equal
    * @param  obj - the book to be compared with
    * @return         true if books are equal
    */
    @Override
    public boolean equals (Object obj) {

        if (obj == null) return false;
        else {

            Book other = (Book) obj;

            if (!getProductID().equals(other.getProductID())) return false;
            else if (!getPrice().equals(other.getPrice())) return false;
            else if (!getName().equals(other.getName())) return false;
            else if (!getAuthors().equals(other.getAuthors())) return false;
            else if (!getPublisher().equals(other.getPublisher())) return false;
            else return true;
        }
    }

    /**
     * toString
     * Returns a string with a book's name, ID, year, price, authors, publishers
     * @param  
     * @return String containing the information
     */
    @Override
    public String toString () {

        String bookString = new String("Name: " + getName() + " ProductID: " + getProductID() + " Price: " + getPrice() + " Year: " + getYear() + " Authors: " + getAuthors() + " Publishers: " + getPublisher());

        return bookString;
    }
}
