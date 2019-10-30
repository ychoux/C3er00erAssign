package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import controller.SlotManager;
import entity.Slot;
import entity.Ticket;

/**
 * The class that manages all tickets and create ticket IDs
 * @author User
 *
 */
public class TicketManager {
	
	/**
	 * A dictionary of tickets, the key is the ticketID
	 */
	private Dictionary<String, Ticket> tickets = new Hashtable<String, Ticket>();
	
	/**
	 * The header of the CSV file, stored in a list of String
	 */
	private String[] header;
	
	/**
	 * The TicketManager object instance, used as a singleton
	 */
	private static TicketManager INSTANCE = new TicketManager();
	
	/**
	 * The path to the CSV file that stores all the cineplexes and cinema
	 */
	private static final String TICKETSPATH = "C3er00erMOB/src/data/tickets.csv";
	
	/**
	 * The private constructor of the class
	 * Loads all tickets from the CSV file and store them in a hashtable
	 */
	private TicketManager() {
		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(TICKETSPATH));
			String line = br.readLine();
			String[] row = line.split(",");
			this.header = row;
			
			while ((line = br.readLine()) != null) {
				
				try {
					row = line.split(",");
					String ticketID = row[0].toUpperCase();
					double price = Double.parseDouble(row[1]);
					String slotID = row[2].toUpperCase();
					List<String> seats = Arrays.asList(row[3].split("\\+"));
					this.tickets.put(ticketID, new Ticket(ticketID, price, SlotManager.getInstance().getSlot(slotID), seats));					
				}
				catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Unable to retrieve ticket information!");
				}
				
			}
			
			br.close();
			this.autoSave();
			
		} 
		catch (IOException e) {
			System.out.println("Unable to retrieve ticket information!");
		}
		
	}
	
	/**
	 * The function to get the instance of TicketManager object
	 * @return	The TicketManager object
	 */
	public static TicketManager getInstance() {
		return INSTANCE;
	}
	
	/**
	 * The function to add a new ticket
	 * Automatically change the availability of seats to not available after adding the ticket
	 * @param price		The price of ticket, in double
	 * @param slot		The slot
	 * @param seats		The seats booked, a list of seat IDs
	 * @return			A boolean variable indication whether the operation is successful or not
	 * 					Return false if seats passed in contains booked seats or has no seats
	 */
	public boolean addTicket(double price, String slotID, List<String> seats) {
		Slot slot = SlotManager.getInstance().getSlot(slotID.toUpperCase());
		for (String s: seats) {
			if (slot.getBookings().getBookedSeatsID().contains(s))
				return false;
		}
		seats.replaceAll(String::toUpperCase);
		Collections.sort(seats);
		try {
			String ticketID = slot.getSlotID() + seats.get(0);
			Ticket t = new Ticket(ticketID, price, slot, seats);
			this.tickets.put(ticketID, t);
			slot.getBookings().occupySeats(seats);
			return true;
		} 
		catch (IndexOutOfBoundsException e) {
			return false;
		}
	}
	
	/**
	 * The function to remove a ticket
	 * @param ticketID	The ticket ID to be removed
	 * @return			The removed ticket
	 */
	public Ticket removeTicket(String ticketID) {
		ticketID = ticketID.toUpperCase();
		try {
			Ticket t = this.tickets.remove(ticketID);
			return t;
		} 
		catch (NullPointerException e) {
			return null;
		}
	}
	
	/**
	 * The function to get a ticket based on its ticket ID
	 * @param ticketID	The ticket ID to be retrieved
	 * @return			The ticket object corresponds to the ticket ID
	 */
	public Ticket getTicket(String ticketID) {
		try {
			return this.tickets.get(ticketID.toUpperCase());
		} 
		catch (NullPointerException e) {
			return null;
		}
	}
	
	/**
	 * The function to get a list of tickets based on their ticket IDs
	 * @param ticketIDs	The list of ticket IDs to be retrieved
	 * @return			The list of ticket objects
	 */
	public List<Ticket> getTickets(List<String> ticketIDs) {
		List<Ticket> tickets = new ArrayList<Ticket>();
		Ticket ticket;
		for (String t: ticketIDs) {
			if ((ticket = this.getTicket(t.toUpperCase())) == null)
				continue;
			tickets.add(ticket);
		}
		return tickets;
	}
	
	/**
	 * The function to print ticket details
	 */
	public void printTicketDetails(String ticketID) {
		Ticket ticket = this.tickets.get(ticketID.toUpperCase());
		if (ticket == null) {
			System.out.println("Ticket ID not found!");
			return;
		}
		System.out.println("Ticket ID: " + ticket.getTicketID());
		System.out.println("Movie Name: " + ticket.getSlot().getMovie_name());
		System.out.println("Showtime: " + ticket.getSlot().getShowtime().format(SlotManager.getInstance().getFormatter()));
		System.out.printf("Duration: %d hours %d minutes\n", ticket.getSlot().getDuration().toHoursPart(), ticket.getSlot().getDuration().toMinutesPart());
		System.out.println("Cinema: " + ticket.getSlot().getCinema().toString());
		System.out.println("Seats: " + ticket.getSeats());
	}
	
	/**
	 * The function to save back all tickets to the CSV file
	 * @return	A boolean variable that indicates whether the operation is successful or not
	 */
	private boolean saveToCSV() {
		
		try {
			
			FileWriter csvWriter = new FileWriter(TICKETSPATH);
			csvWriter.append(String.join(",", this.header));
			csvWriter.append("\n");

            for (Ticket t: Collections.list(this.tickets.elements())) {
            	StringBuilder sb = new StringBuilder();
        		sb.append(t.getTicketID());
        		sb.append(',');
        		sb.append(t.getPrice());
        		sb.append(',');
        		sb.append(t.getSlot().getSlotID());
        		sb.append(',');
        		sb.append(String.join("+", t.getSeats()));
        		sb.append("+\n");
            	csvWriter.append(sb.toString());
            }
            
            csvWriter.flush();
            csvWriter.close();
			return true;
		}
		catch (IOException e) {
			return false;
		}
		
	}
	
	/**
	 * The function to automatically save all tickets back to 
	 * the CSV file upon exiting the application
	 */
	private void autoSave() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
	        public void run(){
	            saveToCSV();
	        }
	    });
	}

	/**
	 * The function to test TicketManager
	 * Not included in real application
	 * @param args
	 */
	public static void main(String[] args) {
		String[] seats = {"D12", "D14"};
		TicketManager.getInstance().addTicket(7.6, "m1s6", Arrays.asList(seats));
		TicketManager.getInstance().printTicketDetails("m1s6D12");
	}

}
