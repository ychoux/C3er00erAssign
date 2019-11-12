package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

import controller.SlotManager;
import entity.Slot;
import entity.Ticket;
import entity.Ticket.TicketType;

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
	private static final String TICKETSPATH = "src/data/tickets.csv";
	
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
					List<TicketType> types = Arrays.asList(row[4].split("\\+")).stream().map(TicketType::valueOf).collect(Collectors.toList());;
					this.tickets.put(ticketID, new Ticket(ticketID, price, SlotManager.getInstance().getSlot(slotID), seats, types));					
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
	 * Automatically generate a ticket ID using the format: XXXXXXyyyyMMddHHmmss where:
	 * 		- XXXXXX is the slot ID
	 * 		- yyyy is the year
	 * 		- MM is the month
	 * 		- dd is the day
	 * 		- HH is the hour
	 * 		- mm is the minute
	 * 		- ss is the second
	 * Automatically change the availability of seats to not available after adding the ticket
	 * @param price		The price of ticket, in double
	 * @param slot		The slot
	 * @param seats		The seats booked, a list of seat IDs
	 * @param types		The type of tickets, a list of TicketType
	 * 					If 3 adult tickets are booked, the list should be [ADULT, ADULT, ADULT]
	 * @return			A boolean variable indication whether the operation is successful or not
	 * 					Return false if seats passed in contains booked seats or has no seats
	 * 					Return false if ticket ID already exists
	 * 					Return false if number seats is not equal to number of ticket types
	 */
	public String addTicket(String slotID, List<String> seats, List<TicketType> types) {
		
		if (seats.isEmpty())
			return null;
		
		if (seats.size() != types.size())
			return null;
		
		Slot slot = SlotManager.getInstance().getSlot(slotID.toUpperCase());
		for (String s: seats) {
			if (slot.getBookings().getBookedSeatsID().contains(s))
				return null;
		}
		
		seats.replaceAll(String::toUpperCase);
		Collections.sort(seats);
		String ticketID = slot.getSlotID() + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		
		if (this.tickets.get(ticketID) != null)
			return null;
		
		if (!slot.getBookings().occupySeats(seats))
			return null;
		
		double price = 0;
		for (TicketType type: types) {
			price += PriceManager.getInstance().calculatePrice(type, slot);
		}
		
		Ticket t = new Ticket(ticketID, price, slot, seats, types);
		this.tickets.put(ticketID, t);
		this.saveToCSV();
		SlotManager.getInstance().saveToCSV();
		return ticketID;
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
			Slot s = t.getSlot();
			if (s != null) {
				s.getBookings().freeSeats(t.getSeats());
				SlotManager.getInstance().saveToCSV();
			}
			this.saveToCSV();
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
	 * @param ticketID	The ticketID of the ticket
	 */
	public void printTicketDetails(String ticketID) {
		if (ticketID == null) {
			System.out.println("Ticket ID is null!");
			return;
		}		
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
		
		System.out.print("Tickets: ");
		for (TicketType type: TicketType.values()) {
			int occurence = Collections.frequency(ticket.getType(), type);
			if (occurence > 0)
				System.out.print(occurence + " " + type + " ");
		}
		System.out.println();
		System.out.println("Total Price: " + ticket.getPrice());
		
	}
	
	/**
	 * The function to print ticket details, used to print confirmation before payment
	 * @param slot			The slot booked
	 * @param seats			The seats booked
	 * @param ticketType	The list of ticket types chosen
	 */
	public void printTicketDetails(Slot slot, List<String> seats, List<TicketType> ticketType) {
		System.out.println("Movie Name: " + slot.getMovie_name());
		System.out.println("Showtime: " + slot.getShowtime().format(SlotManager.getInstance().getFormatter()));
		System.out.printf("Duration: %d hours %d minutes\n", slot.getDuration().toHoursPart(), slot.getDuration().toMinutesPart());
		System.out.println("Cinema: " + slot.getCinema().toString());
		System.out.println("Seats: " + seats);
		
		System.out.print("Tickets: ");
		for (TicketType type: TicketType.values()) {
			int occurence = Collections.frequency(ticketType, type);
			if (occurence > 0)
				System.out.print(occurence + " " + type + " ");
		}
		System.out.println();
		
		double price = 0;
		for (TicketType type: ticketType) {
			price += PriceManager.getInstance().calculatePrice(type, slot);
		}
		System.out.println("Total Price: " + price);
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
        		sb.append("+,");
        		sb.append(String.join("+", t.getTypeString()));
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
		
		for (Ticket t: Collections.list(TicketManager.getInstance().tickets.elements())) {
			TicketManager.getInstance().printTicketDetails(t.getTicketID());
			System.out.println();
		}
		
		TicketManager.getInstance().printTicketDetails(SlotManager.getInstance().getSlot("TOY0001"), Arrays.asList("E03", "E04", "E05"), Arrays.asList(TicketType.ADULT, TicketType.ADULT, TicketType.ADULT));
	}

}
