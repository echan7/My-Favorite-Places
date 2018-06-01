///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            My Favorite Places
// Files:            Place.java
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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Now, lets create the classes to manage the places in memory. Create an 
 * instantiable class that contains name, address, latitude and longitude 
 * fields for a place. This class will contain appropriate constructors, 
 * accessor (getter) and mutator (setter) methods. This class will override the 
 * Object classs equals method. When comparing places the equals method will 
 * return true when the name and address are the same.
 * In milestone 3, we will extend this project to gather latitude and 
 * longitude data using Googles Geocoding API. Prior to then, you can 
 * look up latitude and longitude for places using Google maps as shown here: 
 * http://www.wikihow.com/Get-Longitude-and-Latitude-from-Google-Maps.
 * @author Eric
 *
 */
public class Place implements Comparable<Place>{
	private static Place currentPlace;
	private String name;
	private String address;
	private double latitude;
	private double longitude;

	//initialzide the name, address, latitude and longitude when a new Place
	//is declared
	public Place(String name, String address, double latitude, double longitude)
	{
		this.name = name;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
	}


	//create accessor
	//to get the name, address, latitude and longtitude for show method
	public String getDetails(){
		return this.name+"\n"+this.address+"\n"+latitude+","+longitude;
	}
	
	//to get the name of this place
	public String getName(){
		return this.name;
	}

	//to ge the address of this place
	public String getAddress(){
		return this.address;
	}

	//to get the latitude of this place
	public double getLatitude(){
		return this.latitude;
	}

	//to get longitude of this place
	public double getLongitude(){
		return this.longitude;
	}

	//method to set the name of the place to the parameter passed
	public void setName(String newName){
		name = newName;
	}
	
	//method to set the address of this place to the parameter passed
	public void setAddress(String newAddress){
		address = newAddress;
	}
	
	//method to set the latitude of this place to the parameter passed
	public void setLatitude(double newLatitude){
		latitude = newLatitude;
	}
	
	//method to set the longitude of this place to the parameter passed
	public void setLongitude(double newLongitude){
		longitude = newLongitude;
	}

	/**
	 * return true when two places with same name and address are the same
	 */
	public boolean equals(Object Obj){
		Place placeCheck = (Place) Obj;
		if (name.equals(placeCheck.getName()) && 
				address.equals(placeCheck.getAddress())){
			return true;
		}
		return false;
	}

	/**
	 * Write a method in the place class that returns a 
	 * URL based on the places address, 
	 * latitude and longitude. The main program, when showing place details, 
	 * will call 
	 * this method to obtain the URL. Note the URL is simply a string with a 
	 * specific format. An example URL is:
	 * https://www.google.com/maps/place/702+S+Randall+Ave,+Madison,+WI+53715
	 * /@43.0606104,-89.410035,17z/
	 * @throws UnsupportedEncodingException 
	 */
	public String urlEncoders() throws UnsupportedEncodingException{
		String constant = "https://www.google.com/maps/place/";
		String returnValue = URLEncoder.encode(address, "UTF-8");
		String total = constant+returnValue+"/@"+latitude+","+longitude+",17z/";
		return total;
	}

	//method to get the distance of this place and the currentplace using
	//methods provided by instructor
	public double getDistance(){
		double distance = Geocoding.distance(currentPlace.latitude, 
				currentPlace.longitude, this.latitude, this.longitude);
		return distance;
	}

	//method to get the currentplace
	public static Place getCurrentPlace() {
		return currentPlace;
	}

	//method to set the current place to the parameter passed
	public static void setCurrentPlace(Place currentPlace) {
		Place.currentPlace = currentPlace;
	}

	/**
	 * In the place class, implement the Comparable interface. The Comparable 
	 * interface requires the compareTo method to be implemented. If there 
	 * is not a current place set (the value is null), then the compareTo method 
	 * should compare names so that the list is sorted alphabetically. If there
	 * is a current place set then the compareTo method should compare places by 
	 * their distance from the current place. For example: is one place instance 
	 * (this) closer to current place then the other place instance (passed in 
	 * as a parameter to the compareTo method).
	 * @param comparedPlace
	 * @return int
	 */
	@Override
	public int compareTo(Place comparedPlace) {
		// TODO Auto-generated method stub
		if(Place.getCurrentPlace() != null){
			double distance2 = comparedPlace.getDistance();
			double distance = this.getDistance();
			if (distance > distance2){
				return 1;
			}
			else if(distance == distance2){
				return 0;
			}
			else{
				return -1;
			}
		}
		else{
			return this.name.compareTo(comparedPlace.getName());		
		}
	}
}

