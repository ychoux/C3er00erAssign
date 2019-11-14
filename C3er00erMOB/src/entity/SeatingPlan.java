package entity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * The seating plan for a cinema
 * @author Yew Wei Chee
 *
 */
public class SeatingPlan {
	
	/**
	 * The seating plan ID, or the file name that stores the seating plan
	 */
	private String planID;
	
	/**
	 * The seating plan, which is a 2D list
	 */
	private List<List<String>> plan = new ArrayList<List<String>>();
	
	/**
	 * The list of seatIDs in the seating plan
	 */
	private List<String> seatIDs = new ArrayList<String>();
	
	/**
	 * The constructor of the seating plan
	 * @param path		The seating plan path
	 * @param planID	The seating plan ID
	 */
	public SeatingPlan(String path, String planID) {

		try {
			
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;
			
			while ((line = br.readLine()) != null) {
				line = line.toUpperCase();
				this.plan.add(Arrays.asList(line.split(",")));
			}
			this.planID = planID.toUpperCase();
			br.close();
			
			for (List<String> row: this.plan) {
				for (String s: row) {
					if (s.compareTo("") == 0)
						continue;
					seatIDs.add(s);
				}
			}
			
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("Seating plan not found, unable to create seating plan!");
		}
		
	}
	
	/**
	 * The function that returns the 2D array seating plan
	 * @return The 2D array seating plan
	 */
	public List<List<String>> getPlan() {
		return this.plan;
	}
	
	/**
	 * The function to get all the seatIDs in the seating plan
	 * @return	A list of seatIDs
	 */
	public List<String> getSeatIDs() {
		return this.seatIDs;
	}
	
	/**
	 * The function to get the plan ID
	 * @return The plan ID
	 */
	public String getPlanID() {
		return this.planID;
	}
	
	/**
	 * The function to print the seating plan
	 */
	public void printSeatingPlan() {
		
		boolean first = true;
		for (List<String> row: this.plan) {
			
			String boundary = new String();
			String seating = new String();
			boundary += "-";
			seating += "|";
			
			for (String seat: row) {
				if (seat.compareTo("") == 0) {
					boundary += "     -";
					seating += "     |";
					continue;
				}
				boundary += "------";
				seating += (" " + seat.toUpperCase() + " |");
			}
			
			if (first) {
				
				first = false;
				
				for (int i = 0; i < boundary.length(); i++) {
					System.out.print("=");
				}
				System.out.println();
				
				int mid = (boundary.length() - 6) / 2;
				for (int i = 0; i < mid; i++) {
					System.out.print(" ");
				}
				System.out.print("SCREEN");
				for (int i = 0; i < (boundary.length() - mid - 6); i++) {
					System.out.print(" ");
				}
				
				System.out.println();
				System.out.println();
				System.out.println(boundary);
			}
			
			System.out.println(seating);
			System.out.println(boundary);
			
		}
		
	}
	
	/**
	 * The function to print the seating plan with seat bookings
	 */
	public void printSeatingPlan(SeatBookings bookings) {
		
		boolean first = true;
		for (List<String> row: this.plan) {
			
			String boundary = new String();
			String seating = new String();
			boundary += "-";
			seating += "|";
			
			for (String seat: row) {
				if (seat.compareTo("") == 0) {
					boundary += "     -";
					seating += "     |";
					continue;
				}
				if (bookings.getBookings().get(seat.toUpperCase()).isavailable()) {
					boundary += "------";
					seating += (" " + seat.toUpperCase() + " |");
				}
				else {
					boundary += "------";
					seating += "  X  |";
				}
			}
			
			if (first) {
				
				first = false;
				
				for (int i = 0; i < boundary.length(); i++) {
					System.out.print("=");
				}
				System.out.println();
				
				int mid = (boundary.length() - 6) / 2;
				for (int i = 0; i < mid; i++) {
					System.out.print(" ");
				}
				System.out.print("SCREEN");
				for (int i = 0; i < (boundary.length() - mid - 6); i++) {
					System.out.print(" ");
				}
				
				System.out.println();
				System.out.println();
				System.out.println(boundary);
			}
			
			System.out.println(seating);
			System.out.println(boundary);
			
		}
		
	}

}
