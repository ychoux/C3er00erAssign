package entity;

import java.util.List;

public class Ticket {

	public enum TicketType {ADULT, CHILD, STUDENT, SENIOR_CITIZEN, DISABLED};
	
	private String ticketID;
	private double price;
	private Slot slot;
	private List<String> seats;
	
	public Ticket(String ticketID, double price, Slot slot, List<String> seats) {
		this.ticketID = ticketID.toUpperCase();
		this.price = price;
		this.slot = slot;
		this.seats = seats;
	}

	public String getTicketID() {
		return this.ticketID;
	}

	public double getPrice() {
		return this.price;
	}

	public Slot getSlot() {
		return this.slot;
	}
	
	public List<String> getSeats() {
		return this.seats;
	}
	
}
