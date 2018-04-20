import java.util.Scanner;
/************************************************************************************************
 * 
 * Linda Crane - CST8130 - Winter 2018
 * Purpose:  This class is the superclass for the inventory array elements
 * *** updated to handle reading/writing from/to file
 * Data members:  itemCode: int - holds a numeric code for the item
 *           	  itemName: String
 *           	  itemQuantityInStock: int - 
 *           	  itemPrice: float
 * Methods: addItem(Scanner):boolean - fills valid values for the data members in this class from the Scanner object (with prompts)
 *          addItemFromFile (Scanner): boolean - fills valid values from files (no prompts)
 *          toString(): String - sends contents of data members to a String
 *          update(int):boolean - updates the quantityInStock by the amount (quantity cannot be <0 so does not do update if this would result)
 *          isEqual(Item): boolean - returns true/false based on itemCode in parameter object and object being acted on being equal
 *          isGreater(Item): boolean - returns true/false based on itemCode in parameter object and object being acted on....
 *          inputCode (Scanner): boolean - fills object itemCode from Scanner
 *          writeItem () : String - the format of the object for writing to a file
 *
 ***********************************************************************************************/

public class Item {
	private int itemCode;
	private String itemName = new String();
	private int itemQuantityInStock;
	private float itemPrice;
	
	public Item() {
	}
	
	public boolean addItem(Scanner keyboard) {
		boolean isValid = false;
		
		// loop to read in itemCode
		inputCode(keyboard);
		
		// read in itemName
		System.out.print ("Enter the name for the item: ");
		itemName = keyboard.next();
		
		// loop to read in itemQuantityInStock
		while (!isValid) { 
			System.out.print ("Enter the quantity for the item: ");
			if (keyboard.hasNextInt()) {
				itemQuantityInStock = keyboard.nextInt();
				if (itemQuantityInStock > 0)
					break;  // we can leave this loop - we have read a good itemQuantityInStock
			} 			
			// bad data
			else keyboard.next();   // read past this data - don't need to store it - it's garbage
			System.out.println("Invalid quantity...please enter integer greater than 0");	
		} // end while
		
		// loop to read in itemPrice
		while (!isValid) { 
			System.out.print ("Enter the price of the item: ");
			if (keyboard.hasNextFloat()) {
				itemPrice = keyboard.nextFloat();
				if (itemPrice > 0)
					break;  // we can leave this loop - we have read a good itemCost
			} 
				// bad data
			else keyboard.next();   // read past this data - don't need to store it - it's garbage
			System.out.println("Invalid price...please enter float greater than 0");	
		} // end while
		
		return true;  // successfully read all the fields
	} // end method
	
	public boolean addItemFromFile(Scanner inFile) {
		
		// read in itemCode
		inputCode(inFile);
		
		// read in itemName
		itemName = inFile.next();
		
		// read in itemQuantityInStock
		if (inFile.hasNextInt()) {
			itemQuantityInStock = inFile.nextInt();
			if (itemQuantityInStock <= 0)
				return false;  
		} 			
		// bad data
		else return false;  
		
		// read in itemPrice
		if (inFile.hasNextFloat()) {
			itemPrice = inFile.nextFloat();
				if (itemPrice <= 0)
					return false;  
		} 
		// bad data
		else return false;   // read past this data - don't need to store it - it's garbage
		
		return true;  // successfully read all the fields
	} // end
	
	public String toString() {
		return "Item: " + itemCode + " "+ itemName + " "+ itemQuantityInStock + " price: $" + itemPrice;
	}
	
	public boolean update (int quantity) {
		if (itemQuantityInStock + quantity < 0)
			return false;  // can't process
		itemQuantityInStock += quantity;
		return true;
	}
	
	public boolean isEqual (Item item) {
		return (itemCode == item.itemCode);
	}
	
	public boolean isGreater (Item item) {
		return (itemCode > item.itemCode);
	}
	
	public boolean inputCode(Scanner keyboard) {
		boolean isValid = false;
		
		while (!isValid) {
			System.out.print ("Enter valid item code: ");
			if (keyboard.hasNextInt()) {
				itemCode = keyboard.nextInt();
				if (itemCode > 0) 
					isValid = true;
				else {
					System.out.println ("Invalid...reenter:");
				}
			}
			else  {
				keyboard.next();   // read past this data - don't need to store it - it's garbage
				System.out.println("Invalid quantity...please enter integer greater than 0");
			}
		}
		return isValid;
	}
	
	public String writeItem() {
		return itemCode + " " + itemName + " " + itemQuantityInStock + " " + itemPrice;
	}
	
	public int hashCode() {
		return itemCode % 100;
	}
	
} // end class
