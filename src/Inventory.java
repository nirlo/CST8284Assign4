import java.util.*;
import java.io.*;

/************************************************************************************************
 * 
 * Nicholas Lockhart - CST8130 - Winter 2018
 * Purpose:  This class holds the data structure for the Inventory
 * ****updated to change inventory to ArrayList with Items in order and to handle reading/writing Items from/to files
 * Data members:  inventory: ArrayList<LinkedList<Item>> - array of Item objects 
 *      
 * Methods: addItem(Scanner):boolean - fills valid values for the data members in this class from the Scanner object (meant as keyboard - with prompts)
 *          addItemFromFile(Scanner): boolean - fills valid values for data members from a file
 *          toString(): String - sends contents of data members to a String
 *          alreadyExists(Item): int - the binary search method to see if itemCode in Item object exists in the array
 *                                      (returns index of where it is found or -1 if not found)
 *          findIndex(Item): int - returns index of where to add item using binary search 
 *          updateItem(Scanner): boolean - updates the quantity of the Item object
 *          writeToFile (FileWriter)": boolean - writes the arraylist to a file   
 *          
 * This class was created from the given solution for Assignment 2, created by Linda Crane.
 *
 ***********************************************************************************************/

public class Inventory {
	private ArrayList<LinkedList<Item>> inventory = new ArrayList<>(100);

	public Inventory () {
		for(int i = 0; i < inventory.size(); i++) {
			inventory.set(i, new LinkedList<Item>());
		}
	}

	public Inventory (int size) {
		if (size < 0) {
			size = 0;
		}
		inventory = new ArrayList<LinkedList<Item>>(size);
		for(int i = 0; i < inventory.size(); i++) {
			inventory.set(i, new LinkedList<Item>());
		}
	}

	public boolean addItem(Scanner keyboard) {

		Item newItem;
		String type = "";
		// which type of item to add?
		System.out.print ("Do you wish to add a purchased item (enter P/p) or manufactured (enter anything else)? ");
		if (keyboard.hasNext())
			type = keyboard.next();
		else return false;
		if (type.charAt(0) == 'P' || type.charAt(0) == 'p')
			newItem = new PurchasedItem();
		else 
			newItem = new ManufacturedItem();

		// fill newItem object with data,  if successful, add to array - if not already there
		if (newItem.addItem(keyboard)) {
			LinkedList<Item> list = inventory.get(newItem.hashCode());
			list.add(newItem);
			return true;
		} else 
			return false;
	}


	public boolean addItemFromFile (Scanner inFile) {

		Item newItem;
		String type = "";
		// which type of item to add?
		if (inFile.hasNext())
			type = inFile.next();
		else return false;
		if (type.charAt(0) == 'P' || type.charAt(0) == 'p')
			newItem = new PurchasedItem();
		else 
			newItem = new ManufacturedItem();

		// fill newItem object with data,  if successful, add to array - if not already there
		if (newItem.addItemFromFile(inFile)) {
			LinkedList<Item> list = inventory.get(newItem.hashCode());
			list.add(newItem);
			return true;
		} else 
			return false;
	}


	public String toString() {
		String temp = "Inventory: \n";
		for (int i = 0; i < inventory.size(); i++) {
			if(inventory.get(i).isEmpty())
				continue;
			for(Item j: inventory.get(i))
				temp+= j.toString() + "\n";
		}
		return temp;
	}
	
	public String searchForItem(Scanner kb) {
		Item item = new Item();
		item.inputCode(kb);
		int index = item.hashCode();
		LinkedList<Item> list = inventory.get(index);
		if(list.isEmpty())
			return "Item is not in the inventory...";
		for(Item i: list) {
			if(i.isEqual(item))
				return "Item is at " + index + " with the following contents: \n" + i.toString();
		}
		return "Item is not in inventory...";
	}

	public boolean updateItem(Scanner keyboard, boolean isBuying) {
		boolean isProcessed = false;
		Item item = new Item();
		int quantity = 0;

		if (!item.inputCode(keyboard)) {
			System.out.println ("Invalid code entered - can't continue");
			isProcessed = false;	
		} else {   // get valid quantity to update
			boolean isValid = false;
			// find inventory item for code
			int index = item.hashCode();
			if (inventory.get(index).isEmpty()) {
				System.out.println ("Code not found in inventory...");
				isProcessed = false;
			} else {
				for(Item i: inventory.get(index)) {
					if(i.isEqual(item)) {
						isValid = false;
						item = i;
						break;
					} else isValid = true;
				}
				if(isValid) {
					System.out.println("Code not found in inventory...");
					return isProcessed;
				}
				while (!isValid) {
					System.out.print ("Enter valid quantity : ");
					if (keyboard.hasNextInt()) {
						quantity = keyboard.nextInt();
						isValid = true;
					}
					if (!isBuying && quantity > 0)  // check that if we are selling - the quantity is negative - if not switch it
						quantity = -1 * quantity;
					isProcessed = item.update(quantity);
				} // end while
			}// end else

		}// end else
		return isProcessed;
	}

	public boolean writeToFile(FileWriter outFile) {
		try {
			for (int i=0; i < inventory.size(); i++) {
				if(inventory.get(i).isEmpty())
					continue;
				for(Item j: inventory.get(i)) {	
					outFile.append(j.writeItem() + " ");
				}
			}
			outFile.close();
		}
		catch (IOException e) {
			System.out.println ("Error writing to file....");
			return false;
		}
		return true;
	}




}
