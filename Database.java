///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            My Favorite Places
// Files:            Database.java
// Semester:         CS302 Spring 2016
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
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;
import java.util.Collections;
import java.text.DecimalFormat;
/**
 * Create another class that will manage a list of instances of the place 
 * class using an ArrayList. This class will have methods to add and remove 
 * places, retrieve a place, determine whether a place already exists in the 
 * list, return the number of places in the list
 * @author Eric
 *
 */
public class Database {
	//create an arraylist to store a list of places
	private ArrayList<Place> places;
	//initialize the arraylist when a new database is declared
	public Database(){
		places = new ArrayList<>();
	}
	/**
	 * a to string method to print the list of places when the database 
	 * is called to print out
	 */
	public String toString(){
		String placeToString = "";
		//a constant "No places loaded" is printed when there is no place in the
		//list
		if(places.size() <1){
			placeToString = "No places loaded.\n";
		}
		//if there is more than one place, first check whether the currentPlace
		//place is null, otherwise print first line as distance from 
		//currentPlace
		else if(places.size() > 0){
			if(Place.getCurrentPlace()!=null){
				System.out.println("distance from "+
						Place.getCurrentPlace().getName());
			}
			//print the places line by line as specified by the format
			for(int i =0;i<places.size();i++){
				placeToString += i+1+") "+ places.get(i).getName();
				//if current place is not null, print the distance as well
				//after the name of place
				if(Place.getCurrentPlace()!=null){
					//format the distance to 2 decimal place using DecimalFormat
					//class from java
					DecimalFormat round2 = new DecimalFormat("0.00");
					//check if distance is not NaN otherwise, print NaN miles
					//as the distance next to the name
					Double distance = new Double(places.get(i).getDistance());
					if(distance.isNaN()){
						placeToString += 
								" ("+places.get(i).getDistance()+" miles)";
					}else{
						placeToString += 
								" ("+round2.format(places.get(i).getDistance())
								+" miles)";
					}
				}
				placeToString +="\n";
			}
		}
		return placeToString;
	}

	/**
	 * method to show the address, latitude and longitude when it is called
	 * using getter method from Place class
	 * @param number
	 */
	public void show(int number){
		System.out.println(places.get(number-1).getDetails());
	}

	/**
	 * method to add the place passed through the parameter to the list
	 * @param place
	 */
	public void add(Place place) {
		places.add(place);	
	}

	/**
	 * method to remove the place number passed through the parameter from the
	 * list
	 * @param index
	 */
	public void remove(int index){
		places.remove(index-1);
	}

	/**
	 * method to get the place when the number representing the place is passed
	 * as the parameter
	 * @param index
	 * @return
	 */
	public Place getPlace(int index){
		return places.get(index-1);
	}

	/**
	 * method to check whether the place passed throught the parameter is still
	 * in the list
	 * @param placeCheck
	 * @return
	 */
	public boolean placeExistence(Place placeCheck){
		for(int i=0; i<places.size();i++){
			if(places.get(i).equals(placeCheck)){
				return true;
			}
		}
		return false;
	}

	/**
	 * method to return the size of the list
	 * @return
	 */
	public int getSize(){
		return places.size();
	}
	
	/**
	 * This method corresponds to the read option of the main method. This 
	 * method check the filename passed through can be found and read. If 
	 * the places in the file is not in the list, add it to the list, otherwise
	 * warn the user
	 * @param Filename
	 * @throws FileNotFoundException
	 */
	public void readFile(String Filename) throws FileNotFoundException{
		//uses java File class to read the file
		File fileToRead = new File(Filename);
		//create a new scanner to read the files
		try(Scanner fileScanner = new Scanner(fileToRead)){
			//for every line, split the name, address, latitude and longitude
			//and stored into the list
			while(fileScanner.hasNextLine()){
				String[] newPlaces = fileScanner.nextLine().split(";");
				String newName = newPlaces[0];
				String newAddress = newPlaces[1];
				double newLatitude = Double.parseDouble(newPlaces[2]);
				double newLongitude = Double.parseDouble(newPlaces[3]);
				Place newPlace = new Place(newName, newAddress, newLatitude,
						newLongitude);

				if(!(this.placeExistence(newPlace))){
					places.add(newPlace);
				}
				else {
					System.out.println("Place " + newName + 
							" already in list.");
				}
			}
		}

	}

	/**
	 * This method corresponds to the write method of the main method. The 
	 * method create a new file and store the currentlist into it as specified 
	 * by the format
	 * @param Filename
	 * @throws FileNotFoundException
	 */
	public void writeFile(String Filename) throws FileNotFoundException{
		//uses java File class to create a new File
		File fileToWrite = new File(Filename);
		//uses java PrintWriter class to write the file into
		PrintWriter writer = new PrintWriter(fileToWrite);
		String newText = "";
		//for every place, saved it into a line of string and stored into the 
		//the file with name, address, latitude and longitude separated by
		//semicolon ";". Then move the next line for the next place
		for(int i =0; i<places.size();i++){
			String nameToAdd = places.get(i).getName();
			String addressToAdd = places.get(i).getAddress();
			double latitudeToAdd = places.get(i).getLatitude();
			double longitudeToAdd = places.get(i).getLongitude();
			newText += nameToAdd + ";" + addressToAdd + ";" + latitudeToAdd + 
					";" +longitudeToAdd+"\n";
		}
		writer.write(newText);
		writer.flush();
		writer.close();
	}

	//sort the places using java Collection class for aphabetical order and 
	//the overrided compareTo method in Place class
	public void sort(){
		Collections.sort(places);
	}
}
