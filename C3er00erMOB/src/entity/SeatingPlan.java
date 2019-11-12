package entity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SeatingPlan {
	
	private String planID;
	private List<List<String>> plan = new ArrayList<List<String>>();
	private List<String> seatIDs = new ArrayList<String>();
	
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
	
	public List<List<String>> getPlan() {
		return this.plan;
	}
	
	public List<String> getSeatIDs() {
		return this.seatIDs;
	}
	
	public String getPlanID() {
		return this.planID;
	}
	
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
