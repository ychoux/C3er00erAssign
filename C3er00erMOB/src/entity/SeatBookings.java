package entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

/**
 * The seat booking class, stores all the seat bookings (availability) for a slot
 * @author Yew Wei Chee
 *
 */
public class SeatBookings {

	/**
	 * The seating plan ID
	 */
	private String planID;
	
	/**
	 * The slot ID
	 */
	private String slotID;
	
	/**
	 * The dictionary (hashtable) of seat bookings, key as seatID and value as Seat object
	 */
	private Dictionary<String, Seat> bookings = new Hashtable<String, Seat>();
	
	/**
	 * The constructor for seat bookings
	 * @param plan		The seating plan
	 * @param slotID	The slot ID
	 */
	public SeatBookings(SeatingPlan plan, String slotID) {
		
		this.planID = plan.getPlanID();
		this.slotID = slotID.toUpperCase();

		for (String s: plan.getSeatIDs()) {
			bookings.put(s.toUpperCase(), new Seat(s.toUpperCase(), true));
		}
		
	}
	
	/**
	 * The constructor for seat bookings
	 * @param plan			The seating plan
	 * @param slotID		The slot ID
	 * @param booked_seats	The booked seats, a list of seat IDs
	 */
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
	
	/**
	 * The constructor for seat bookings
	 * @param plan			The seating plan
	 * @param slotID		The slot ID
	 * @param booked_seats	The booked seats, a array of seat IDs
	 */
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
	
	/**
	 * The function to get the plan ID
	 * @return	The plan ID
	 */
	public String getPlanID() {
		return planID;
	}
	
	/**
	 * The function to get the slot ID
	 * @return The slot ID
	 */
	public String getSlotID() {
		return slotID;
	}
	
	/**
	 * The function to set the slot ID
	 * @param slotID The slot ID
	 */
	public void setSlotID(String slotID) {
		this.slotID = slotID.toUpperCase();
	}
	
	/**
	 * The function to get the seat bookings hashtable
	 * @return The seat bookings hashtable
	 */
	public Dictionary<String, Seat> getBookings() {
		return this.bookings;
	}
	
	/**
	 * The function to change all the seats in the list of seatIDs to occupied (booked)
	 * @param seatID 	The list of seat IDs
	 * @return			A boolean variable that indicates whether the operation is successful or not
	 * 					Return false if the seat ID is not in the seating plan
	 */
	public boolean occupySeats(List<String> seatID) {
		seatID.replaceAll(String::toUpperCase);
		if (!Collections.list(this.bookings.keys()).containsAll(seatID))
			return false;
		for (String ID: seatID) {
			this.bookings.get(ID).setavailable(false);
		}
		return true;
	}
	
	/**
	 * The function to change all the seats in the list of seatIDs to occupied (booked)
	 * @param seatID 	The array of seat IDs
	 * @return			A boolean variable that indicates whether the operation is successful or not
	 */
	public boolean occupySeats(String[] seatID) {
		return this.occupySeats(Arrays.asList(seatID));
	}
	
	/**
	 * The function to change all the seats in the list of seatIDs to not occupied (available)
	 * @param seatID	The array of seat IDs
	 * @return			A boolean variable that indicates whether the operation is successful or not
	 * 					Return false if the seat ID is not in the seating plan
	 */
	public boolean freeSeats(List<String> seatID) {
		seatID.replaceAll(String::toUpperCase);
		if (!Collections.list(this.bookings.keys()).containsAll(seatID))
			return false;
		for (String ID: seatID) {
			this.bookings.get(ID).setavailable(true);
		}
		return true;
	}
	
	/**
	 * The function to change all the seats in the list of seatIDs to not occupied (available)
	 * @param seatID	The array of seat IDs
	 * @return			A boolean variable that indicates whether the operation is successful or not
	 */
	public boolean freeSeats(String[] seatID) {
		return this.freeSeats(Arrays.asList(seatID));
	}
	
	/**
	 * The function to get all booked seats
	 * @return	The list of booked seats ID
	 */
	public List<String> getBookedSeatsID() {
		List<String> seats = new ArrayList<String>();
		for (Seat s: Collections.list(this.bookings.elements()))
			if (!s.isavailable())
				seats.add(s.getID());
		Collections.sort(seats);
		return seats;
	}
	
	/**
	 * The function to get all available seats
	 * @return	The list of available seats ID
	 */
	public List<String> getAvailableSeatsID() {
		List<String> seats = new ArrayList<String>();
		for (Seat s: Collections.list(this.bookings.elements()))
			if (s.isavailable())
				seats.add(s.getID());
		Collections.sort(seats);
		return seats;
	}
	
}
