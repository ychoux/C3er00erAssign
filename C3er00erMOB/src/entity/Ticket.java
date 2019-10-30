package entity;

import java.util.List;

import controller.SlotManager;

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
	
	public void printTicketDetails() {
		System.out.println("Ticket ID: " + this.ticketID);
		System.out.println("Movie Name: " + this.slot.getMovie_name());
		System.out.println("Showtime: " + this.slot.getShowtime().format(SlotManager.getInstance().getFormatter()));
		System.out.printf("Duration: %d hours %d minutes\n", this.slot.getDuration().toHoursPart(), this.slot.getDuration().toMinutesPart());
		System.out.println("Cinema: " + this.slot.getCinema().toString());
		System.out.println("Seats: " + this.seats);
	}
	
}
