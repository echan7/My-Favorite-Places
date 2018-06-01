///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            My Favorite Places
// Files:            MyFavoritePlaces.java
//					 Database.java
//					 Place.java
// Semester:         (CS 302) Spring 2016
//
// Author:           Aaron Tze-Rue Tan
// Email:            atan24@wisc.edu
// CS Login:         aaront
// Lecturer's Name:  Jim Williams
// Lab Section:      313
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
//                   CHECK ASSIGNMENT PAGE TO see IF PAIR-PROGRAMMING IS ALLOWED
//                   If pair programming is allowed:
//                   1. Read PAIR-PROGRAMMING policy (in cs302 policy) 
//                   2. choose a partner wisely
//                   3. REGISTER THE TEAM BEFORE YOU WORK TOGETHER 
//                      a. one partner creates the team
//                      b. the other partner must join the team
//                   4. complete this section for each program file.
//
// Pair Partner:     Eric Jun Hong Chan
// Email:            echan7@wisc.edu
// CS Login:         echan
// Lecturer's Name:  Jim Williams
// Lab Section:      314
//
////////////////////////////////////////////////////////////////////////////////

import java.util.Scanner;
import java.io.*;
import java.net.URISyntaxException;
/**
 * This is the main class that will run the user interface function to allow the
 * user to choose different option from the menu "Add, Edit, Delete, Current, 
 * Read, Write and Quit" based on the user's favorite places. This program 
 * purpose is to store different amount of user's favorite places and have the
 * function to show and compare different favorite places in the list.
 * @author Eric
 *
 */
public class MyFavoritePlaces {
	
	/**
	 * This is the main method that will run the program. The methods functions
	 * will be further explain below. For starters, the main few lines of the
	 * program will, run the list in between will depends whether use has 
	 * input places in it. If there are no places stored in the list, program
	 * will can run the function "Add, Read and Quit", otherwise a different
	 * menu of "Add, Edit, Delete, Current, Read, Write and Quit" will 
	 * be displayed. The program will keep continue running until user use the
	 * Quit option from the menu. There will only be one scanner, one ArrayList
	 * and a boolean value declared throughout the entire program.
	 * @param args
	 */
	public static void main(String[] args) {
		//to store the arraylist of places
		Database database = new Database();
		//to keep track whether program should continue to run
		boolean runProgram = true;
		//one scanner to scan all userInput
		Scanner scnr = new Scanner(System.in);
		while(runProgram){
			//run initial few lines 
			System.out.println("My Favorite Places 2016");
			System.out.println("--------------------------");
			if(database.getSize()>0){
				//if there are places in the list, sort the places using method
				//by java Collection class
				database.sort();
			}
			//print the list alphabetically, if there is a currentplace set,
			//print the list according distance to currentplace
			System.out.print(database);
			System.out.print("--------------------------\n");

			//run the remaining software
			if(database.getSize() <1){
				System.out.print("A)dd R)ead Q)uit : ");
				String userInput = scnr.nextLine().toUpperCase();
				switch(userInput){
				case "A":{
					add(scnr, database);
					break;
				}
				case "R":{
					read(scnr, database);
					break;
				}
				case "Q":{
					//runProgram boolean value changed to false to stop program
					//from repeating. Did not turn this into a method because
					//boolean is a primitive type 
					System.out.println("Thank you for using My Favorite "
							+ "Places 2016!");
					runProgram = false;
					break;
				}
				default:{
					System.out.println("");
					continue;
				}
				}
			}
			else if(database.getSize()>0){
				System.out.print("A)dd S)how E)dit D)elete C)urrent R)ead "
						+ "W)rite"
						+ " Q)uit : ");
				String userInput = scnr.nextLine().toUpperCase();
				switch(userInput){
				case "A":{
					add(scnr, database);
					break;
				}
				case "S":{
					show(scnr, database);
					break;
				}
				case"E":{
					edit(scnr, database);
					break;
				}
				case"D":{
					delete(scnr, database);
					break;
				}
				case "R":{
					read(scnr, database);
					break;
				}
				case "W":{
					write(scnr, database);
					break;
				}
				case "C":{
					current(scnr, database);
					break;
				}
				case "Q":{
					System.out.println("Thank you for using My Favorite Places "
							+ "2016!");
					runProgram = false;
					break;
				}
				default:
					System.out.println("");
					continue;
				}				
			}

		}
		scnr.close();
	}

	/**
	 * The add method allows the user to add places manually from the program
	 * the user will be prompt to input the name and address and it will 
	 * automatically search the address in Google map and stored the place if 
	 * it is a valid address. The method will use the scanner and list of places
	 * declared as a parameter so that there are only one scanner and list in 
	 * the program.
	 * @param scnr
	 * @param database
	 */
	public static void add(Scanner scnr, Database database){
		//prompt user for place name
		System.out.print("Enter the name: ");
		String nameInput = scnr.nextLine();
		//prompt user for address
		System.out.print("Enter the address: ");
		String addressInput = scnr.nextLine();
		//declared variables to store the address, latitude and longitude found
		String addressSaved = "";
		double latitudeSaved = 0;
		double longitudeSaved = 0;
		/**
		 * to try and catch whether address input is valid when searching for 
		 * the place in google map, throw IndexOutOfBoundsException if place is
		 * not found or IOException with invalid data
		 */
		try {
			//use GResponse provided by instructor to search for the place 
			//online
			GResponse placeSearch = 
					GeocodeResponse.parse(Geocoding.find(addressInput));
			//saved the address, latitude and longitude found
			addressSaved = placeSearch.getFormattedAddress();
			latitudeSaved = placeSearch.getLatitude();
			longitudeSaved = placeSearch.getLongitude();
			//declare a new variable to store the new place
			Place placeSaved = new Place
					(nameInput, addressSaved, latitudeSaved, longitudeSaved);
			//use the saved variable to check whether the place already exist
			//in the list, if not, add the place to the list
			if(!(database.placeExistence(placeSaved))){
				database.add(placeSaved);
			//otherwise, warn the user	
			}else{
				System.out.println("Place " + placeSaved.getName() + 
						" already in list.");
			}
	    //to check for IOException error
		} catch (IOException e) {
			e.printStackTrace();
		//to check for IndexOutOfBoundsException error	
		} catch (IndexOutOfBoundsException e){
			System.out.println("place not found using address: "+addressInput);
			System.out.print("Press Enter to continue.");
			scnr.nextLine();
		}
		System.out.println("");
	}

	/**
	 * This method read the folder the program currently is in and prompt the 
	 * user which .mfp files containing list of places to scan. The user can 
	 * also specify which folder to search for other mfp files with proper 
	 * input. Then the program check whether the places are already in the list,
	 * if it is not, then add the places, otherwise, warn the user. This method
	 * uses the scanner and list of places from the main program as the 
	 * parameter to keep only one scanner and list throughout the program.
	 * @param scnr
	 * @param database
	 */
	public static void read(Scanner scnr, Database database){
		//Uses file object from java File class, "." signify the folder program
	    //currently is in
		File folder = new File(".");
		//show the user list of available files
		System.out.println("Available Files: ");
		//if file ends with .mfp, show the user
		for ( File file : folder.listFiles()) {
			if(file.getName().endsWith(".mfp")){
				System.out.println("	"+file.getName());
			}
		}
		System.out.println("");
		//prompt the user to enter the filename to scan
		System.out.print("Enter filename: ");
		//saved the filename input
		String fileInput = scnr.nextLine();
		//try and catch the error FileNotFoundException when running readFile
		//method from database class
		try {
			database.readFile(fileInput);		
		}catch(FileNotFoundException e) 
		{System.out.println("Unable to read from file: " + fileInput);}
		System.out.println("");
	}
	
	/**
	 * This method allow the user to see the name, address, latitude, longitude
	 * and URL of a particular place stored. When a place to show is chosen,
	 * a browser will also open up to show the place to the user in google map.
	 * This method uses the scanner and list of places from the main program 
	 * as the parameter to keep only one scanner and list throughout the entire
	 * program
	 * @param scnr
	 * @param database
	 */
	public static void show(Scanner scnr, Database database){
		//prompt user to choose which place to show
		System.out.print("Enter number of place to Show: ");
		//variable declared to store userInput
		int intInput = 0;
		//check whether if user input a correct integer value, otherwise, tell
		//the user it is an invalid value
		if(scnr.hasNextInt()){
			intInput = scnr.nextInt();
			//try and catch the error IndexOutOfBoundsException meaning user
			//has input an incorrect integer value
			try{
				//use the show method from database to show the name, address
				//latitude and longitude
				database.show(intInput);
				//to declare a variable to store URL string
				String uRL="";
				//try and catch whether the address use can be encoded into URL
				//string using method from Database and Place class, throws
				//UnsupportedEncodingException
				try {
					uRL = database.getPlace(intInput).urlEncoders();
					//print the URL encoded
					System.out.println(uRL);
					//use try and catch whether the URL used can open the 
					//the browser in google map through the method in Geocoding
					//class provided by instructor, throws IOException and
					//URLSyntaxException
					try {
						Geocoding.openBrowser(uRL);
						System.out.print("Press Enter to continue.");
						scnr.nextLine();
						scnr.nextLine();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (URISyntaxException e) {
						e.printStackTrace();
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}catch (IndexOutOfBoundsException e){
				System.out.println("Invalid value: "+intInput);
				scnr.nextLine();
			}
		}
		else {
			String input = scnr.nextLine();
			System.out.println("Invalid value: "+input);
		}
		System.out.println("");
	}

	/**
	 * This method allows the user to edit a particular place stored with
	 * new name and address input. Like the Add method, the program will look
	 * up google again whether the new place can be stored, otherwise the place
	 * will retain the same name and address. This method uses the scanner 
	 * and list of places from the main method as parameter to keep only one 
	 * scanner and list throughout the entire program.
	 * @param scnr
	 * @param database
	 */
	public static void edit(Scanner scnr, Database database){
		//prompt user to choose which place to edit
		System.out.print("Enter number of place to Edit: ");
		//variable to store the integer input by the user
		int intInput = 0;
		//if userInput is an integer, store the value into the variable,
		//otherwise, warn the user
		if(scnr.hasNextInt()){
			intInput = scnr.nextInt();
			//try and catch whether the user has input a valid Integer value
			//otherwise, throws IndexOutOfBoundException and warn the user
			try{
				//uses the method to get the place user want to edit from
				//Database class
				Place placeToEdit = database.getPlace(intInput);
				System.out.println("Current name: "+placeToEdit.getName());
				//prompt user to enter new name
				System.out.print("Enter a new name: ");
				scnr.nextLine();
				String newName = scnr.nextLine();
				//prompt user to enter new address
				System.out.println("Current address: "+
						placeToEdit.getAddress());
				System.out.print("Enter a new address: ");
				String newAddress = scnr.nextLine();
				//variables declared to stored the new place
				String addressSaved = "";
				double latitudeSaved = 0;
				double longitudeSaved = 0;
				//like Add method, try and catch whether the adressInput can 
				//be found on Google maps, otherwise warn the user, throws
				//IOException and IndexOutOfBoundsException
				try{
					//use method provided by instructor to look up the address
					GResponse placeSearch = 
							GeocodeResponse.parse(Geocoding.find(newAddress));
					//saved the place found
					addressSaved = placeSearch.getFormattedAddress();
					latitudeSaved = placeSearch.getLatitude();
					longitudeSaved = placeSearch.getLongitude();
					//edit the new name, address, latitude and longitude
					placeToEdit.setName(newName);
					placeToEdit.setAddress(addressSaved);
					placeToEdit.setLatitude(latitudeSaved);
					placeToEdit.setLongitude(longitudeSaved);
				}
				catch(IOException e){
					e.printStackTrace();
				}
				catch (IndexOutOfBoundsException e){
					System.out.println("place not found using address: "+
				addressSaved);
					System.out.print("Press Enter to continue.");
					scnr.nextLine();
				}
			}catch (IndexOutOfBoundsException e){
				System.out.println("Invalid value: "+ intInput);
				scnr.nextLine();
			}
		}
		else {
			String input = scnr.nextLine();
			System.out.println("Invalid value: "+input);
		}
		System.out.println("");
	}

	/**
	 * This method allows the user to delete any places from the list, the 
	 * program remove the place, check whether the place still exist in the 
	 * the list then confirm it. If the place deleted is the currentPlace,
	 * sort the list again alphabetically. This method uses the scanner and 
	 * list of places from the main method as the parameter to keep only one
	 * scanner and list throughout the entire program
	 * @param scnr
	 * @param database
	 */
	public static void delete(Scanner scnr, Database database){
		//prompt the user which place to delete
		System.out.print("Enter number of place to Delete: ");
		//declared variable to store user input of Integer
		int intInput = 0;
		//check whether user has input an integer value, otherwise, warn the 
		//user
		if(scnr.hasNextInt()){
			intInput = scnr.nextInt();
			//try and catch whether the user has input a valid integer value
			//if it is not, warn the user. Throws IndexOutOfBoundsException.
			try{
				//stored the place that user wanted to delete
				Place placeCheck = database.getPlace(intInput);
				//remove the place
				database.remove(intInput);
				//check whether the place has been deleted
				if(database.placeExistence(placeCheck) == false){
					System.out.println(placeCheck.getName() + " deleted.");
				}
				//do this if there is a currentplace in the list, otherwise, the 
				//program will crash
				if(Place.getCurrentPlace()!=null){
					//if the place user wanted to delete is the current place
					//set the currentplace to null
					if(Place.getCurrentPlace().equals(placeCheck)){
						Place.setCurrentPlace(null);
					}
				}
				System.out.print("Press Enter to continue.");
				scnr.nextLine();
				scnr.nextLine();
			}catch (IndexOutOfBoundsException e){
				System.out.println("Invalid value: "+ intInput);
				scnr.nextLine();
			}
		}
		else {
			String input = scnr.nextLine();
			System.out.println("Invalid value: "+input);
		}
		System.out.println("");
	}

	/**
	 * This method writes the current list into a new file and store the file
	 * in the same folder program was stored. This method uses the scanner and
	 * list of places from the main method to keep only one scanner and 
	 * list throughout the entire program.
	 * @param scnr
	 * @param database
	 */
	public static void write(Scanner scnr, Database database){
		//uses method from java File class
		File folder = new File (".");
		//show the names of files the folder currently have, show onlt if the 
		//files ends with .mfp
		System.out.println("Current files: ");
		for(File file : folder.listFiles()){
			if(file.getName().endsWith("mfp")){
				System.out.println("	"+file.getName());
			}
		}
		System.out.println("");
		//ask the user for the new file name
		System.out.print("Enter filename: ");
		String newFileName = scnr.nextLine();
		//try and catch whether the new file can be written using the new file
		//name, otherwise, warn the user. Throws FilesNotFoundException
		try{
			database.writeFile(newFileName);
		}catch(FileNotFoundException e){
			System.out.println("Unable to write to file: " + newFileName);
		} 
		System.out.println("");
	}

	/**
	 * This method allows the user to set one of the places in the list to be
	 * the current place. The current place will be stored and the program will
	 * rerun and sort the places with the distance from current place. This 
	 * method uses the scanner and list of places from the main method as 
	 * parameter to keep only one scanner and lsit throughout the entire 
	 * program
	 * @param scnr
	 * @param database
	 */
	public static void current(Scanner scnr, Database database){
		//prompt the user which place to be set to current place
		System.out.print("Enter number of place to be Current place: ");
		//variable to store the integer input of user
		int intInput = 0;
		//if userInput is not an integer, warn the user
		if(scnr.hasNextInt()){
			intInput = scnr.nextInt();
			//try and catch whether the integer value input by the user is 
			//valid, otherwise, warn the user. Throws IndexOutOfBoundsException
			try{
				//get the place user wanted to be current place and set the current
				//place to it
				Place.setCurrentPlace(database.getPlace(intInput));
				//check if the current place is the same as the place user choose
				//show the user it is set as the current place
				if(Place.getCurrentPlace().equals(database.getPlace(intInput))){
					System.out.println
					(database.getPlace(intInput).getName()+" "
							+ "set as Current place.");
				}
				System.out.print("Press Enter to continue.");
				scnr.nextLine();
				scnr.nextLine();
			}catch (IndexOutOfBoundsException e){
				System.out.println("Invalid value: "+intInput);
				scnr.nextLine();
			}
		}
		else {
			String input = scnr.nextLine();
			System.out.println("Invalid value: "+input);
		}
		System.out.println("");
	}
}
