package entity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The Ticket class, which represents the ticket in real life
 * @author Yew Wei Chee
 *
 */
public class Ticket {

	/**
	 * The enumeration of ticket types
	 * @author Yew Wei Chee
	 *
	 */
	public enum TicketType {ADULT, CHILD, STUDENT, SENIOR_CITIZEN, DISABLED};
	
	/**
	 * The ticket ID, or the transaction ID
	 */
	private String ticketID;
	
	/**
	 * The price of the ticket
	 */
	private double price;
	
	/**
	 * The slot in which the ticket is booked for
	 */
	private Slot slot;
	
	/**
	 * The list of seats booked when buying the ticket
	 */
	private List<String> seats;
	
	/**
	 * The list of ticket type for every seats bought
	 */
	private List<TicketType> type;
	
	/**
	 * The constructor for the ticket object
	 * @param ticketID	The ticket ID
	 * @param price		The price of the ticket
	 * @param slot		The slot in which the ticket is booked for
	 * @param seats		The list of seats booked when buying the ticket
	 * @param type		The list of ticket type for every seats bought
	 */
	public Ticket(String ticketID, double price, Slot slot, List<String> seats, List<TicketType> type) {
		this.ticketID = ticketID.toUpperCase();
		this.price = price;
		this.slot = slot;
		this.seats = seats;
		this.type = type;
	}

	/**
	 * The function to get the ticket ID
	 * @return The ticket ID
	 */
	public String getTicketID() {
		return this.ticketID;
	}

	/**
	 * The function to get the price of ticket
	 * @return The price of ticket
	 */
	public double getPrice() {
		return this.price;
	}

	/**
	 * The function to get the slot in which the ticket is booked for
	 * @return The slot in which the ticket is booked for
	 */
	public Slot getSlot() {
		return this.slot;
	}
	
	/**The function to get the list of seats booked when buying the ticket
	 * @return The list of seats booked when buying the ticket
	 */
	public List<String> getSeats() {
		return this.seats;
	}
	
	/**The function to get the list of ticket type for every seats bought
	 * @return The list of ticket type for every seats bought
	 */
	public List<TicketType> getType() {
		return this.type;
	}
	
	/**The function to get the list of ticket type for every seats bought in a list of string
	 * @return The list of string of ticket type for every seats bought
	 */
	public List<String> getTypeString() {
		return this.type.stream().map(TicketType::name).collect(Collectors.toList());
	}
	
}
