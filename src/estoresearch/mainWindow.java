package estoresearch;

import static estoresearch.EStoreSearch.isAllNumbers;
import static estoresearch.EStoreSearch.isExistingID;
import static estoresearch.EStoreSearch.products;
import static estoresearch.EStoreSearch.searchAndPrint;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Dennis
 */
public class mainWindow extends JFrame {

    String inputFile = "data.txt";
    String[] command = {"welcome", "add", "search", "quit"};
    String[] productType = {"Book", "Electronic"};

    /** log
     * log is the log messages box on the bottom of the GUI
     */
    protected static JTextArea log;
    private JComboBox productSelect;
    private JComboBox commandlist;
    private JTextField productIDField;
    private JTextField nameField;
    private JTextField priceField;
    private JTextField yearField;
    private JTextField publisherField;
    private JTextField makerField;
    private JPanel middleWelcomePanel;
    private JPanel middleAddPanel;
    private JPanel totalLogPanel;
    private JLabel title;
    private JButton add;
    private JButton reset;
    private JPanel typePanel;
    private JLabel nameLabel;
    private JLabel priceLabel; //also used for start year
    private JLabel yearLabel; //also used for end year
    private JPanel makerPanel;
    private JPanel publisherPanel;
    private JLabel productIDLabel;
    private JPanel pricePanel;
    private JPanel yearPanel;
    private JLabel makerLabel;

    /** saveOnExit
     * saves all information to the input file, then closes
     */
    private class saveOnExit extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            try {

                        PrintWriter outputStream = new PrintWriter(new FileOutputStream(inputFile));

                        for (int i = 0; i < EStoreSearch.products.size(); i++) {

                            if (EStoreSearch.products.get(i) instanceof Book) {

                                outputStream.println("book");
                                outputStream.println(EStoreSearch.products.get(i).getProductID());
                                outputStream.println(EStoreSearch.products.get(i).getName());
                                outputStream.println(EStoreSearch.products.get(i).getPrice());
                                outputStream.println(EStoreSearch.products.get(i).getYear());
                                outputStream.println(EStoreSearch.products.get(i).getAuthors());
                                outputStream.println(EStoreSearch.products.get(i).getPublisher());
                            } else {

                                outputStream.println("electronics");
                                outputStream.println(EStoreSearch.products.get(i).getProductID());
                                outputStream.println(EStoreSearch.products.get(i).getName());
                                outputStream.println(EStoreSearch.products.get(i).getPrice());
                                outputStream.println(EStoreSearch.products.get(i).getYear());
                                outputStream.println(EStoreSearch.products.get(i).getMaker());
                            }
                        }

                        outputStream.flush();
                        outputStream.close();
                    } catch (FileNotFoundException outputFileInvalid) {
                        log.append("Could not write to output file");
                    }

                    System.out.println("Program has been terminated. Good bye!");
                    System.exit(0);
        }
    }

   /** addListener
    * Handles the actions of the button "add" and "search"
    */
    private class addListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            if (commandlist.getSelectedItem().toString().equalsIgnoreCase("search")) {

                if (EStoreSearch.products.isEmpty()) {
                    log.append("Both lists are empty, nothing to search\n");
                    return;
                }

                if (!(productIDField.getText().isEmpty()) && productIDField.getText().length() != 6) {
                    log.append("Enter a 6 digit productID or an empty string\n");
                    return;
                }

                if (!priceField.getText().isEmpty() && !isAllNumbers(priceField.getText())) {
                    log.append("Enter only numbers in the year fields\n");
                    return;
                }

                if (!yearField.getText().isEmpty() && !isAllNumbers(yearField.getText())) {
                    log.append("Enter only numbers in the year fields\n");
                    return;
                }

                if (!priceField.getText().isEmpty() && !yearField.getText().isEmpty()) {

                    String time = priceField.getText() + "-" + yearField.getText();
                    searchAndPrint(productIDField.getText(), nameField.getText(), time, EStoreSearch.products, EStoreSearch.hashIndex);
                } else if (priceField.getText().isEmpty() && !yearField.getText().isEmpty()) {

                    //startYear is empty, endYear valid
                    String time = "-" + yearField.getText();
                    searchAndPrint(productIDField.getText(), nameField.getText(), time, EStoreSearch.products, EStoreSearch.hashIndex);
                } else if (!priceField.getText().isEmpty() && yearField.getText().isEmpty()) {
                    String time = priceField.getText() + "-";
                    searchAndPrint(productIDField.getText(), nameField.getText(), time, EStoreSearch.products, EStoreSearch.hashIndex);
                } else { //both years empty
                    searchAndPrint(productIDField.getText(), nameField.getText(), "", EStoreSearch.products, EStoreSearch.hashIndex);
                }
            } else if ((commandlist.getSelectedItem().toString().equalsIgnoreCase("add")) && (productSelect.getSelectedItem().toString().equalsIgnoreCase("Book"))) {

                if (isExistingID(productIDField.getText(), products)) {
                    log.append(productIDField.getText() + " already exists in the database\n");
                    return;
                }

                try {
                    EStoreSearch.products.add(new Book(productIDField.getText(), nameField.getText(), priceField.getText(), yearField.getText(), makerField.getText(), publisherField.getText()));
                    EStoreSearch.updateIndex(EStoreSearch.hashIndex, EStoreSearch.products.get(EStoreSearch.products.size() - 1), EStoreSearch.products.size() - 1);
                    log.append("Added the new book" + nameField.getText() + "!\n");

                } catch (Exception bookInvalid) {
                    log.append(bookInvalid.toString() + " - An invalid field was detected, could not parse book.\n");
                }
            } else if ((commandlist.getSelectedItem().toString().equalsIgnoreCase("add")) && (productSelect.getSelectedItem().toString().equalsIgnoreCase("Electronic"))) {

                if (isExistingID(productIDField.getText(), products)) {
                    log.append(productIDField.getText() + " already exists in the database\n");
                    return;
                }

                try {
                    EStoreSearch.products.add(new Electronics(productIDField.getText(), nameField.getText(), priceField.getText(), yearField.getText(), makerField.getText()));
                    EStoreSearch.updateIndex(EStoreSearch.hashIndex, EStoreSearch.products.get(EStoreSearch.products.size() - 1), EStoreSearch.products.size() - 1);
                    log.append("Added the new electronic" + nameField.getText() + "!\n");
                } catch (Exception electronicInvalid) {
                    log.append(electronicInvalid.toString() + " - An invalid field was detected, could not parse electronic.\n");
                }
            }
        }
    }

    /** resetListener
    * Clears the text fields of the current page
    */
    private class resetListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            if (commandlist.getSelectedItem().toString().equalsIgnoreCase("search")) {
                productIDField.setText("");
                nameField.setText("");
                priceField.setText("");
                yearField.setText("");
            } else if ((productSelect.getSelectedItem().toString().equalsIgnoreCase("Book")) && (commandlist.getSelectedItem().toString().equalsIgnoreCase("add"))) {
                productIDField.setText("");
                nameField.setText("");
                priceField.setText("");
                yearField.setText("");
                makerField.setText("");
                publisherField.setText("");
            } else if ((productSelect.getSelectedItem().toString().equalsIgnoreCase("Electronic")) && (commandlist.getSelectedItem().toString().equalsIgnoreCase("add"))) {
                productIDField.setText("");
                nameField.setText("");
                priceField.setText("");
                yearField.setText("");
                makerField.setText("");
            }
        }
    }

    /** commandListener
    * Changes the UI based on the selected field of JComboBox commandList
    */
    private class commandListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String option = commandlist.getSelectedItem().toString();

            switch (option) {

                case "welcome":
                    middleAddPanel.setVisible(false);
                    remove(middleAddPanel);
                    totalLogPanel.setVisible(false);
                    middleWelcomePanel.setVisible(true);
                    add(middleWelcomePanel, BorderLayout.CENTER);
                    revalidate();
                    repaint();
                    break;
                case "search":
                    typePanel.setVisible(false);
                    yearLabel.setVisible(true);
                    priceLabel.setVisible(true);
                    makerPanel.setVisible(false);
                    publisherPanel.setVisible(false);
                    priceLabel.setText("     Start Year:          ");
                    yearLabel.setText("     End Year:           ");
                    title.setText("  Searching products");
                    add.setText("Search");
                    nameLabel.setText("     Name Keywords:");
                    productIDLabel.setText("     ProductID:         ");
                    middleWelcomePanel.setVisible(false);
                    remove(middleWelcomePanel);
                    totalLogPanel.setVisible(true);
                    middleAddPanel.setVisible(true);
                    add(middleAddPanel, BorderLayout.CENTER);
                    revalidate();
                    repaint();
                    break;
                case "add":
                    productSelect.setSelectedIndex(0);
                    typePanel.setVisible(true);
                    makerPanel.setVisible(true);
                    publisherPanel.setVisible(true);
                    add.setText("Add");
                    title.setText(" Adding a product");
                    nameLabel.setText("     Name:       ");
                    productIDLabel.setText("     ProductID:");
                    priceLabel.setText("     Price:        ");
                    yearLabel.setText("     Year:         ");
                    makerLabel.setText("     Author:     ");
                    middleWelcomePanel.setVisible(false);
                    remove(middleWelcomePanel);
                    totalLogPanel.setVisible(true);
                    middleAddPanel.setVisible(true);
                    add(middleAddPanel, BorderLayout.CENTER);
                    revalidate();
                    repaint();
                    break;
                case "quit":
                    try {

                        PrintWriter outputStream = new PrintWriter(new FileOutputStream(inputFile));

                        for (int i = 0; i < EStoreSearch.products.size(); i++) {

                            if (EStoreSearch.products.get(i) instanceof Book) {

                                outputStream.println("book");
                                outputStream.println(EStoreSearch.products.get(i).getProductID());
                                outputStream.println(EStoreSearch.products.get(i).getName());
                                outputStream.println(EStoreSearch.products.get(i).getPrice());
                                outputStream.println(EStoreSearch.products.get(i).getYear());
                                outputStream.println(EStoreSearch.products.get(i).getAuthors());
                                outputStream.println(EStoreSearch.products.get(i).getPublisher());
                            } else {

                                outputStream.println("electronics");
                                outputStream.println(EStoreSearch.products.get(i).getProductID());
                                outputStream.println(EStoreSearch.products.get(i).getName());
                                outputStream.println(EStoreSearch.products.get(i).getPrice());
                                outputStream.println(EStoreSearch.products.get(i).getYear());
                                outputStream.println(EStoreSearch.products.get(i).getMaker());
                            }
                        }

                        outputStream.flush();
                        outputStream.close();
                    } catch (FileNotFoundException outputFileInvalid) {
                        log.append("Could not write to output file");
                    }

                    System.out.println("Program has been terminated. Good bye!");
                    System.exit(0);
            }
        }
    }

    /** productListener
    * Changes the addPanel UI based on the selected field of JComboBox productList
    */
    private class productListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String product = productSelect.getSelectedItem().toString();

            if (product.equalsIgnoreCase("book")) {
                typePanel.setVisible(true);
                makerPanel.setVisible(true);
                publisherPanel.setVisible(true);
                title.setText(" Adding a product");
                nameLabel.setText("     Name:       ");
                productIDLabel.setText("     ProductID:");
                priceLabel.setText("     Price:        ");
                yearLabel.setText("     Year:         ");
                makerLabel.setText("     Author:     ");
                revalidate();
                repaint();
            } else {
                typePanel.setVisible(true);
                makerPanel.setVisible(true);
                publisherPanel.setVisible(false);
                title.setText(" Adding a product");
                nameLabel.setText("     Name:       ");
                productIDLabel.setText("     ProductID:");
                priceLabel.setText("     Price:        ");
                yearLabel.setText("     Year:         ");
                makerLabel.setText("     Maker:      ");
                revalidate();
                repaint();
            }
        }
    }

    /** mainWindow
     * Initializes the variables declared on the top of this block
     * Starts the screen off on the welcome page
     */
    public mainWindow() {
        super();

        setSize(550, 550);
        setResizable(false);
        setTitle("eStore");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new saveOnExit());
        setLayout(new BorderLayout());

        commandlist = new JComboBox(command);
        commandlist.addActionListener(new commandListener());

        JLabel welcomeMsg1 = new JLabel("  Welcome to eStore");
        welcomeMsg1.setFont(new Font("Serif", Font.PLAIN, 19));

        JLabel welcomeMsg2 = new JLabel("  Choose a command from the " + '"' + "Commands" + '"' + " menu above for");
        welcomeMsg2.setFont(new Font("Serif", Font.PLAIN, 19));

        JLabel welcomeMsg3 = new JLabel("  adding a product, searching products, or quitting the program");
        welcomeMsg3.setFont(new Font("Serif", Font.PLAIN, 19));

        middleWelcomePanel = new JPanel();
        middleWelcomePanel.setLayout(new GridLayout(18, 1));
        middleWelcomePanel.add(new JLabel(" "));
        middleWelcomePanel.add(new JLabel(" "));
        middleWelcomePanel.add(new JLabel(" "));
        middleWelcomePanel.add(new JLabel(" "));
        middleWelcomePanel.add(new JLabel(" "));
        middleWelcomePanel.add(new JLabel(" "));
        middleWelcomePanel.add(welcomeMsg1);
        middleWelcomePanel.add(new JLabel(" "));
        middleWelcomePanel.add(welcomeMsg2);
        middleWelcomePanel.add(welcomeMsg3);

        middleAddPanel = new JPanel();
        middleAddPanel.setLayout(new BoxLayout(middleAddPanel, BoxLayout.X_AXIS));

        JPanel addPanel = new JPanel();
        addPanel.setLayout(new GridLayout(10, 1));
        middleAddPanel.add(addPanel);

        JPanel buttonPanel1 = new JPanel();
        buttonPanel1.setLayout(new GridLayout(8, 1));
        buttonPanel1.add(new JLabel(" "));
        buttonPanel1.add(new JLabel(" "));
        JPanel resizeAdd = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel resizeReset = new JPanel(new FlowLayout(FlowLayout.CENTER));
        add = new JButton("Add");
        add.addActionListener(new addListener());
        reset = new JButton("Reset");
        reset.addActionListener(new resetListener());
        resizeAdd.add(add);
        resizeReset.add(reset);
        buttonPanel1.add(resizeReset);
        buttonPanel1.add(new JLabel(" "));
        buttonPanel1.add(new JLabel(" "));
        buttonPanel1.add(resizeAdd);
        buttonPanel1.add(new JLabel(" "));
        buttonPanel1.add(new JLabel(" "));
        middleAddPanel.add(buttonPanel1);

        title = new JLabel();
        title.setFont(new Font("Serif", Font.PLAIN, 19));

        addPanel.add(title, BorderLayout.NORTH);

        typePanel = new JPanel();
        JLabel typeLabel = new JLabel("     Type:        ", JLabel.LEFT);
        productSelect = new JComboBox(productType);
        typePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        typePanel.add(typeLabel);
        typePanel.add(productSelect);
        productSelect.setSelectedIndex(1);
        addPanel.add(typePanel);

//            commandlist.setActionListener(); //actionListener should modify visibility
        JPanel productIDPanel = new JPanel();
        productIDLabel = new JLabel("     ProductID:", JLabel.LEFT);
        productIDField = new JTextField(6);
        productIDPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        productIDPanel.add(productIDLabel);
        productIDPanel.add(productIDField);
        addPanel.add(productIDPanel);

        JPanel namePanel = new JPanel();
        nameLabel = new JLabel("     Name:       ", JLabel.LEFT);
        nameField = new JTextField(20);
        namePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        addPanel.add(namePanel);

        pricePanel = new JPanel();
        priceLabel = new JLabel("     Price:        ", JLabel.LEFT);
        priceField = new JTextField(5);
        pricePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        pricePanel.add(priceLabel);
        pricePanel.add(priceField);
        addPanel.add(pricePanel);

        JPanel yearPanel = new JPanel();
        yearLabel = new JLabel("     Year:         ", JLabel.LEFT);
        yearField = new JTextField(5);
        yearPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        yearPanel.add(yearLabel);
        yearPanel.add(yearField);
        addPanel.add(yearPanel);

        makerPanel = new JPanel();
        makerLabel = new JLabel("     Maker:      ", JLabel.LEFT);
        makerField = new JTextField(20);
        makerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        makerPanel.add(makerLabel);
        makerPanel.add(makerField);
        addPanel.add(makerPanel);

        publisherPanel = new JPanel();
        JLabel publisherLabel = new JLabel("     Publisher: ", JLabel.LEFT);
        publisherField = new JTextField(20);
        publisherPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        publisherPanel.add(publisherLabel);
        publisherPanel.add(publisherField);
        addPanel.add(publisherPanel);
        publisherPanel.setVisible(false);

        log = new JTextArea(10, 10);
        log.setMargin(new Insets(5, 5, 5, 5));
        log.setEditable(false);

        JScrollPane scrolledText = new JScrollPane(log);
        scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        totalLogPanel = new JPanel();
        totalLogPanel.setLayout(new BorderLayout());

        totalLogPanel.add(new JLabel(" Messages"), BorderLayout.NORTH);
        totalLogPanel.add(new JLabel("  "), BorderLayout.EAST);
        totalLogPanel.add(new JLabel("  "), BorderLayout.WEST);
        totalLogPanel.add(scrolledText, BorderLayout.CENTER);
        totalLogPanel.add(new JLabel("  "), BorderLayout.SOUTH);
        totalLogPanel.setVisible(false);

        productSelect.addActionListener(new productListener());

        add(commandlist, BorderLayout.NORTH);
        add(middleWelcomePanel, BorderLayout.CENTER);
        add(totalLogPanel, BorderLayout.SOUTH);
    }
}
