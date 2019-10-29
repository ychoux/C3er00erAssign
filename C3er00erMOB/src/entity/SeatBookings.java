package entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class SeatBookings {

	private String planID;
	private String slotID;
	private Dictionary<String, Seat> bookings = new Hashtable<String, Seat>();
	
	public SeatBookings(SeatingPlan plan, String slotID) {
		
		this.planID = plan.getPlanID();
		this.slotID = slotID.toUpperCase();
		
		for (List<String> row: plan.getPlan()) {
			for (String s: row) {
				if (s.compareTo("") == 0)
					continue;
				bookings.put(s.toUpperCase(), new Seat(s.toUpperCase(), true));
			}
		}
		
	}
	
	public SeatBookings(SeatingPlan plan, String slotID, List<String> booked_seats) {

		this.planID = plan.getPlanID();
		this.slotID = slotID.toUpperCase();
		
		for (List<String> row: plan.getPlan()) {
			for (String s: row) {
				if (s.compareTo("") == 0)
					continue;
				bookings.put(s.toUpperCase(), new Seat(s.toUpperCase(), true));
			}
		}
		
		this.occupySeats(booked_seats);
		
	}
	
	public SeatBookings(SeatingPlan plan, String slotID, String[] booked_seats) {
		
		this.planID = plan.getPlanID();
		this.slotID = slotID.toUpperCase();
		
		for (List<String> row: plan.getPlan()) {
			for (String s: row) {
				if (s.compareTo("") == 0)
					continue;
				bookings.put(s.toUpperCase(), new Seat(s.toUpperCase(), true));
			}
		}
		
		this.occupySeats(booked_seats);
		
	}
	
	public String getPlanID() {
		return planID;
	}
	
	public String getSlotID() {
		return slotID;
	}
	
	public void setSlotID(String slotID) {
		this.slotID = slotID.toUpperCase();
	}
	
	public Dictionary<String, Seat> getBookings() {
		return this.bookings;
	}
	
	public boolean occupySeats(List<String> seatID) {
		seatID.replaceAll(String::toUpperCase);
		if (!Collections.list(this.bookings.keys()).containsAll(seatID))
			return false;
		for (String ID: seatID) {
			this.bookings.get(ID).setavailable(false);
		}
		return true;
	}
	
	public boolean occupySeats(String[] seatID) {
		return this.occupySeats(Arrays.asList(seatID));
	}
	
	public boolean freeSeats(List<String> seatID) {
		seatID.replaceAll(String::toUpperCase);
		if (!Collections.list(this.bookings.keys()).containsAll(seatID))
			return false;
		for (String ID: seatID) {
			this.bookings.get(ID).setavailable(true);
		}
		return true;
	}
	
	public boolean freeSeats(String[] seatID) {
		return this.freeSeats(Arrays.asList(seatID));
	}
	
	public List<String> getBookedSeatsID() {
		List<String> seats = new ArrayList<String>();
		for (Seat s: Collections.list(this.bookings.elements()))
			if (!s.isavailable())
				seats.add(s.getID());
		return seats;
	}
	
	public List<String> getAvailableSeatsID() {
		List<String> seats = new ArrayList<String>();
		for (Seat s: Collections.list(this.bookings.elements()))
			if (s.isavailable())
				seats.add(s.getID());
		return seats;
	}
	
	public static void main(String[] args) {
//		SeatBookings s = new SeatBookings(CineplexManager.getInstance().getCineplex("star @ bugis").getCinema("a").getSeating_plan(), "haha");
//		String[] seats = {"E11", "E12", "E13", "F11", "F12", "F10", "F13"};
//		s.occupySeats(seats);
//		System.out.println(String.join(",", s.getBookedSeatsID()));
	}
	
}
