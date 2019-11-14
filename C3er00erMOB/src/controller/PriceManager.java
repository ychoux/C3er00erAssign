package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import entity.Cinema.CinemaType;
import entity.Cineplex;
import entity.Slot;
import entity.Ticket.TicketType;


/**
 * The class use to manage the price of tickets, calculate price of tickets.
 * Used to manage the rates for different days, ticket type or cinema types.
 * Used to manage public holidays.
 * @author Yew Wei Chee
 *
 */
public class PriceManager {
	
	/**
	 * A List of public holidays
	 */
	private List<LocalDate> PublicHolidays = new ArrayList<LocalDate>();
	
	/**
	 * A dictionary of price rates
	 */
	private Dictionary<String, Double> rates = new Hashtable<String, Double>();
	
	/**
	 * The PriceManager object instance, used as a singleton
	 */
	private static PriceManager INSTANCE = new PriceManager();
	
	/**
	 * The path to the CSV file that stores all the public holidays
	 */

	private static final String HOLIDAYPATH = "src/data/PH.csv";
	//private static final String HOLIDAYPATH = "data/PH.csv";
	
	/**
	 * The path to the CSV file that stores all the ticket price rate
	 */
	private static final String RATEPATH = "src/data/rates.csv";
	//private static final String RATEPATH = "data/rates.csv";
	
	/**
	 * The DateTimeFormatter object that specifies how LocalDate object is formatted to string and vice versa
	 */
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	/**
	 * The private constructor of the class
	 * Loads all public holiday dates from the CSV file and store them in a List
	 * Loads all the rates from the CSV file and store them into a dictionary
	 */
	private PriceManager() {
		
		try {			
			BufferedReader br = new BufferedReader(new FileReader(HOLIDAYPATH));
			String line;			
			while ((line = br.readLine()) != null) {				
				try {
					this.PublicHolidays.add(LocalDate.parse(line, this.formatter));			
				}
				catch (Exception e) {
					System.out.println("Unable to retrieve public holiday information!");
				}				
			}			
			br.close();
			this.autoSave();			
		} 
		catch (IOException e) {
			System.out.println("Unable to retrieve public holiday information!");
		}
		
		try {			
			BufferedReader br = new BufferedReader(new FileReader(RATEPATH));
			String line;			
			while ((line = br.readLine()) != null) {				
				try {
					String[] row = line.split(",");
					this.rates.put(row[0], Double.parseDouble(row[1]));
				}
				catch (Exception e) {
					System.out.println("Unable to retrieve price rate information!");
				}				
			}			
			br.close();
			this.autoSave();			
		} 
		catch (IOException e) {
			System.out.println("Unable to retrieve price rate information!");
		}
		
	}
	
	/**
	 * The function to get the instance of PriceManager object
	 * @return	The PriceManager object
	 */
	public static PriceManager getInstance() {
		return INSTANCE;
	}
	
	/**
	 * The function to get the dictionary of Rates
	 * @return 	The Dictionary of Rates
	 */
	public Dictionary<String, Double> getRates() {
		return rates;
	}
	
	/**
	 * The function to get the list of Public Holidays
	 * @return 	The list of Public Holidays
	 */
	public List<LocalDate> getPH(){
		return PublicHolidays;
	}
	
	/**
	 * The function to see if a date is public holiday
	 * @param date	The date
	 * @return		Returns true if the date is a public holiday
	 */
	private boolean isPublicHoliday(LocalDate date) {
		if (this.PublicHolidays.contains(date))
			return true;
		return false;
	}
	
	/**
	 * The function used to add public holiday
	 * @param date	A LocalDate object
	 * @return		A boolean variable that indicates whether the operation is successful or not
	 * 				Returns false if the date existed before
	 */
	public boolean addPublicHoliday(LocalDate date) {
		if (!this.PublicHolidays.contains(date)) {
			this.PublicHolidays.add(date);
			if(this.saveToCSV())
				return true;
			else
				return false;
		}
		else 
			return false;
	}
	
	/**
	 * The function used to add public holiday
	 * @param date	A String object, should be in the format of dd-MM-yyyy
	 * @return		A boolean variable that indicates whether the operation is successful or not
	 * 				Returns false if the date existed before or the date format is wrong
	 */
	public boolean addPublicHoliday(String date) {
		LocalDate ldate;
		try {
			ldate = LocalDate.parse(date, this.formatter);
		}
		catch (DateTimeParseException e) {
			return false;
		}
		return addPublicHoliday(ldate);
	}
	
	/**
	 * The function used to calculate ticket price
	 * @param type	Type of ticket
	 * @param slot	Slot of ticket
	 * @return		Price of ticket
	 */
	public double calculatePrice(TicketType type, Slot slot) {
		
		boolean isPH = this.isPublicHoliday(slot.getShowtime().toLocalDate());
		DayOfWeek day = slot.getShowtime().getDayOfWeek();
		CinemaType cinema = slot.getCinema().getCinema_type();
		
		double price = this.rates.get(cinema.toString());
		
		if (isPH)
			price = price * this.rates.get("PUBLIC_HOLIDAY");
		else if (day == DayOfWeek.SATURDAY | day == DayOfWeek.SUNDAY)
			price = price * this.rates.get(day.toString());
				
		return (double) Math.round(price * this.rates.get(type.toString()) * 10) / 10;
		
	}
	
	/**
	 * The function used to change the cinema price
	 * @param cinema	The type of cinema
	 * @param price		The new price
	 * @return			A boolean variable that indicates whether the operation is successful or not
	 */
	public boolean updateCinemaPrice(CinemaType cinema, double price) {
		try {
			this.rates.remove(cinema.toString());
			this.rates.put(cinema.toString(), price);
			return true;
		} 
		catch (Exception e) {
			this.rates.put(cinema.toString(), price);
			return false;
		}
	}
	
	/**
	 * The function used to update the ticket rate
	 * @param ticket	The ticket type
	 * @param rate		The new rate for the ticket
	 * @return			A boolean variable that indicates whether the operation is successful or not
	 */
	public boolean updateTicketRate(TicketType ticket, double rate) {
		try {
			this.rates.remove(ticket.toString());
			this.rates.put(ticket.toString(), rate);
			return true;
		} 
		catch (Exception e) {
			this.rates.put(ticket.toString(), rate);
			return false;
		}
	}
	
	/**
	 * The function used to update the Saturday ticket rate
	 * @param rate	The new rate of Saturday
	 * @return		A boolean variable that indicates whether the operation is successful or not
	 */
	public boolean updateSaturdayRate(double rate) {
		try {
			this.rates.remove(DayOfWeek.SATURDAY.toString());
			this.rates.put(DayOfWeek.SATURDAY.toString(), rate);
			return true;
		} 
		catch (Exception e) {
			this.rates.put(DayOfWeek.SATURDAY.toString(), rate);
			return false;
		}
	}
	
	/**
	 * The function used to update the Sunday ticket rate
	 * @param rate	The new rate of Sunday
	 * @return		A boolean variable that indicates whether the operation is successful or not
	 */
	public boolean updateSundayRate(double rate) {
		try {
			this.rates.remove(DayOfWeek.SUNDAY.toString());
			this.rates.put(DayOfWeek.SUNDAY.toString(), rate);
			return true;
		} 
		catch (Exception e) {
			this.rates.put(DayOfWeek.SUNDAY.toString(), rate);
			return false;
		}
	}
	
	/**
	 * The function used to update the public holiday ticket rate
	 * @param rate	The new rate of public holiday
	 * @return		A boolean variable that indicates whether the operation is successful or not
	 */
	public boolean updatePHRate(double rate) {
		try {
			this.rates.remove("PUBLIC_HOLIDAY");
			this.rates.put("PUBLIC_HOLIDAY", rate);
			return true;
		} 
		catch (Exception e) {
			this.rates.put("PUBLIC_HOLIDAY", rate);
			return false;
		}
	}

	/**
	 * The function to save back all public holidays to the CSV file
	 * And also save back all price rates to CSV file.
	 * @return	A boolean variable that indicates whether the operation is successful or not
	 */
	private boolean saveToCSV() {
		
		try {			
			FileWriter csvWriter = new FileWriter(HOLIDAYPATH);			
            for (LocalDate d: this.PublicHolidays) {
            	csvWriter.append(d.format(this.formatter) + "\n");
            }            
            csvWriter.flush();
            csvWriter.close();
		}
		catch (IOException e) {
			return false;
		}
		
		try {			
			FileWriter csvWriter = new FileWriter(RATEPATH);			
            for (String k: Collections.list(this.rates.keys())) {
            	StringBuilder sb = new StringBuilder();
            	sb.append(k);
            	sb.append(",");
            	sb.append(this.rates.get(k));
            	sb.append("\n");
        		csvWriter.append(sb);
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
	 * The function to automatically save all public holiday back to 
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
		
		for (Slot s: SlotManager.getInstance().getAllSlots()) {
			System.out.println(s.getSlotID());
			System.out.println(s.getCinema().getCinema_type());
			System.out.println(s.getShowtime().format(PriceManager.getInstance().formatter));
			System.out.println("Adult: " + PriceManager.getInstance().calculatePrice(TicketType.ADULT, s));
			System.out.println("Child: " + PriceManager.getInstance().calculatePrice(TicketType.CHILD, s));
			System.out.println("Student: " + PriceManager.getInstance().calculatePrice(TicketType.STUDENT, s));
			System.out.println("Senior Citizen: " + PriceManager.getInstance().calculatePrice(TicketType.SENIOR_CITIZEN, s));
			System.out.println("Disabled: " + PriceManager.getInstance().calculatePrice(TicketType.DISABLED, s));
			System.out.println();
		}
		
	}

}
