/**
 * Dennis Kao (Nov 30, 2016)
 */

======================================
Features
======================================
This program manages the inventory of a store. A user can add an electronic or a book.
Each item must have a productID (6 digits), a name and a year (1000 - 9999). An attempt to enter an incorrect value will print out an error message.

There is a search feature which allows users to specify the productID, year or range of years and keywords which can be used to search the name. Years can be searched from a range or a single year. Only specifying start year will cause the program to search for all products after that year. Only specifying end year will cause the program to search for all products before that year.

Keywords should be separated by spaces.

======================================
Compilation and execution
======================================
Navigate to the root directory (/eStoreSearch) and execute these lines of code:

Compile line: 
javac estoresearch/product.java estoresearch/book.java estoresearch/electronics.java estoresearch/estoresearch.java estoresearch/mainWindow.java

Run line: 
java estoresearch.EStoreSearch

The program reads from and writes to a file named “data.txt” which is in the same folder
as the program. This file must be present for regular execution. The user may wish to
replace this data file with something else, and in that case, he/she should rename that
file to “data.txt” and place it in the root directory.

======================================
Assumptions and Limitations
======================================
productID is only 6 digits long
Year search can only use 4 digit numbers
Price is either an integer or a decimal number

The data file needs to follow the specified format:

If a product is a book, then the product information should be written as follows:
book
productID
name
price
year
authors
publisher

If a product is an electronic, then the product information should be written as follows:
electronics
productID
name
price
year
make

There should be no spaces between each item in the text file. If a field is empty then there should be an empty line where the information should be.

A sample file, data.txt, has been provided.

======================================
Test Plan
======================================

Case 1:
1. Enter in a new electronic
2. Enter 123456 as the product ID
3. Enter in a new book
4. Enter 123456 as the product ID
Result: program will not add an item with the same ID

Case 2:
1. Enter in a new electronic
2. Enter -asdf as the product ID
Result: program will not accept chars in the product ID

Case 3:
1. Start the program
2. Try to conduct a search
Result: program will not search when lists are empty

Case 4:
1. Enter in a new electronic
2. Enter 0300 as the year
Result: program will not add a year starting with 0

Case 5:
1. Enter in a new electronic or book
2. Try not entering values for year, productID or name
Result: program will not add the item and will go back to the main menu

Case 6:
1. Add an arbitrary item
2. Conduct a search using 0300 as the year
Result: program will not search a year starting with 0

Case 7:
1. Enter in a new electronic
2. Conduct a search using a number with more than 4 digits or less than 4 digits as the year
Result: program will not accept the year value

Case 8:
1. Enter in a new electronic
2. Enter 123456 as the product ID
3. Conduct a search with productID = 123456 and all other fields blank
Result: program shows the details of the newly added electronic

Case 9:
1. Enter in a new electronic
2. Enter 123456 as the product ID, name = “MacBook air dennis kao”
3. Conduct a search with productID = 123456 and keywords = “DeNNis”
Result: program shows the details of the newly added electronic

Case 10:
1. Enter in a new electronic
2. Enter 123456 as the product ID, name = “MacBook air dennis kao”
3. Conduct a search with productID = 123456 and keywords = “DeNNis air”
Result: program shows the details of the newly added electronic

Case 11:
1. Using the provided data file (data.txt) conduct a search using the following field:
keywords = bubble
Result: no products should show up as there are no “bubble” products
