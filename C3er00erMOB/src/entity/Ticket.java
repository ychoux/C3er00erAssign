package entity;

import java.util.List;
import java.util.stream.Collectors;

public class Ticket {

	public enum TicketType {ADULT, CHILD, STUDENT, SENIOR_CITIZEN, DISABLED};
	
	private String ticketID;
	private double price;
	private Slot slot;
	private List<String> seats;
	private List<TicketType> type;
	
	public Ticket(String ticketID, double price, Slot slot, List<String> seats, List<TicketType> type) {
		this.ticketID = ticketID.toUpperCase();
		this.price = price;
		this.slot = slot;
		this.seats = seats;
		this.type = type;
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
	
	public List<TicketType> getType() {
		return this.type;
	}
	
	public List<String> getTypeString() {
		return this.type.stream().map(TicketType::name).collect(Collectors.toList());
	}
	
}
