package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import entity.Cinema;
import entity.Slot;

/**
 * The class that is used to manage slots
 * @author 
 *
 */
public class SlotManager {

	/**
	 * An array of all existing slots
	 */
	private List<Slot> slots = new ArrayList<Slot>();
	
	/**
	 * The header of CSV file, stored as an array of String
	 */
	private String[] header;
	
	/**
	 * The instance of SlotManager object, used as a singleton
	 */
	private static SlotManager INSTANCE = new SlotManager();
	
	/**
	 * The path to the CSV that stores all the slots
	 */
	private static final String SLOTSPATH = "/src/data/slots.csv";
	
	/**
	 * The DateTimeFormatter object that specifies how LocalDateTime object is formatted to string and vice versa
	 */
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	
	/**
	 * The private constructor of the class
	 * Loads all available slots from the CSV file and store them in a hashtable
	 */
	private SlotManager() {
		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(SLOTSPATH));
			String line = br.readLine();
			String[] row = line.split(",");
			this.header = row;
			
			while ((line = br.readLine()) != null) {
				
				try {
					row = line.split(",");
					String slotID = row[0].toUpperCase();
					LocalDateTime showtime = LocalDateTime.parse(row[1], this.formatter);
					Duration duration = Duration.parse(row[2].toUpperCase());
					String movie_name = row[3];
					String cineplex_name = row[4].toUpperCase();
					String cinemaID = row[5].toUpperCase();
					List<String> booked_seats = Arrays.asList(row[6].split("\\+"));
					this.addSlot(slotID, showtime, duration, movie_name, 
							CineplexManager.getInstance().getCineplex(cineplex_name).getCinema(cinemaID), 
							booked_seats);					
				}
				catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Unable to retrieve slot information!");
				}
				
			}
			
			br.close();
			this.autoSave();
			
		} 
		catch (IOException e) {
			System.out.println("Unable to retrieve slot information!");
		}
		
	}
	
	/**
	 * The function to get the instance of SlotManager object
	 * @return	The SlotManager object
	 */
	public static SlotManager getInstance() {
		return INSTANCE;
	}
	
	/**
	 * The function to get the DateTimeFormatter object
	 * @return	The DateTimeFormatter object
	 */
	public DateTimeFormatter getFormatter() {
		return formatter;
	}

	
	/**
	 * The function used to add an available slot
	 * @param slotID		The ID of the slot, a String
	 * @param showtime		The showtime of the slot, a LocalDateTime object
	 * @param duration		The duration of the slot, a Duration object
	 * @param movie_name	The movie name of the slot, a String
	 * @param cinema		The cinema of the slot, a Cinema object
	 * @return				A boolean variable indication whether the operation is successful or not
	 * 						Return false if the slot ID already exists or there is a clash of slots
	 */
	public boolean addSlot(String slotID, LocalDateTime showtime, Duration duration, String movie_name, Cinema cinema) {
		
		slotID = slotID.toUpperCase();
		if (this.getSlot(slotID) != null)
			return false;
		
		for (Slot s: this.getCinemaSlots(cinema.getCineplex_name(), cinema.getCinemaID(), showtime.toLocalDate())) {
			if (checkClash(s.getShowtime(), s.getShowtime().plus(s.getDuration()), showtime, showtime.plus(duration)))
				return false;
		}
		slots.add(new Slot(slotID.toUpperCase(), showtime, duration, movie_name, cinema));
		slots.sort(Comparator.comparing(Slot::getCinema).thenComparing(Slot::getShowtime));
		return true;
		
	}
	
	/**
	 * The function used to add an available slot
	 * @param slotID		The ID of the slot, a String
	 * @param showtime		The showtime of the slot, a LocalDateTime object
	 * @param duration		The duration of the slot, a Duration object
	 * @param movie_name	The movie name of the slot, a String
	 * @param cinema		The cinema of the slot, a Cinema object
	 * @param booked_seats	The list of seat ID that has already been booked for that slot
	 * @return				A boolean variable indication whether the operation is successful or not
	 * 						Return false if the slot ID already exists or there is a clash of slots
	 */
	public boolean addSlot(String slotID, LocalDateTime showtime, Duration duration, 
			String movie_name, Cinema cinema, List<String> booked_seats) {
		
		slotID = slotID.toUpperCase();
		if (this.getSlot(slotID) != null)
			return false;
		
		booked_seats.replaceAll(String::toUpperCase);
		if (addSlot(slotID, showtime, duration, movie_name, cinema)) {
			this.getSlot(slotID).getBookings().occupySeats(booked_seats);
		}
		return false;
		
	}

	/**
	 * The function used to check whether there is a clash between 2 slots
	 * @param s1	The showtime of the 1st slot
	 * @param e1	The endtime of the 1st slot
	 * @param s2	The showtime of the 2nd slot
	 * @param e2	The endtime of the 2nd slot
	 * @return		A boolean variable indication whether there is a clash between the 2 slots
	 */
	private boolean checkClash(LocalDateTime s1, LocalDateTime e1, LocalDateTime s2, LocalDateTime e2) {
		if (s1.isAfter(e2) | s2.isAfter(e1))
			return false;
		else return true;
	}
	
	/**
	 * The function that returns all slots
	 * @return	A list of all slotss
	 */
	public List<Slot> getAllSlots() {
		return this.slots;
	}
	
	/**
	 * The function to get a slot based on its slot ID
	 * @param slotID	The slot ID
	 * @return			The Slot object correspond to the slot ID passed in
	 */
	public Slot getSlot(String slotID) {
		
		slotID = slotID.toUpperCase();
		for (Slot s: this.slots)
			if (s.getSlotID().compareTo(slotID) == 0)
				return s;
		return null;
		
	}
	
	/**
	 * The function to filter all slots that uses a specific cinema
	 * @param cineplex_name	The cineplex name
	 * @param cinemaID		The cinema ID
	 * @return				A list of all slots that uses the cinema
	 */
	public List<Slot> getCinemaSlots(String cineplex_name, String cinemaID) {
		
		cineplex_name = cineplex_name.toUpperCase();
		cinemaID = cinemaID.toUpperCase();
		List<Slot> result = new ArrayList<Slot>();
		for (Slot s: this.slots)
			if (s.getCinema().getCineplex_name().compareTo(cineplex_name) == 0 
					& s.getCinema().getCinemaID().compareTo(cinemaID) == 0) {
				result.add(s);
			}
		result.sort(Comparator.comparing(Slot::getShowtime));
		return result;
		
	}

	/**
	 * The function to filter all slots that uses a specific cinema at a specific date
	 * @param cineplex_name	The cineplex name
	 * @param cinemaID		The cinema ID
	 * @param date			The date, a LocalDate object
	 * @return				A list of all slots that uses the cinema on that date
	 */
	public List<Slot> getCinemaSlots(String cineplex_name, String cinemaID, LocalDate date) {
		
		cinemaID = cinemaID.toUpperCase();
		List<Slot> result = new ArrayList<Slot>();		
		for (Slot s: this.slots) {
			if (s.getCinema().getCineplex_name().compareTo(cineplex_name) == 0 
					& s.getCinema().getCinemaID().compareTo(cinemaID) == 0 
					& s.getShowtime().toLocalDate().compareTo(date) == 0) {
				result.add(s);
			}
		}		
		result.sort(Comparator.comparing(Slot::getShowtime));
		return result;
	}
	
	/**
	 * The function to filter all slots that plays a specific movie at a specific date
	 * @param movie				The movie name
	 * @param date				The date, a LocalDate object
	 * @return					All slots that plays the specific movie at the specific date
	 */
	public List<Slot> getMovieSlots(String movie, LocalDate date) {
		
		movie = movie.toUpperCase();
		List<Slot> result = new ArrayList<Slot>();		
		for (Slot s: this.slots) {
			if (s.getMovie_name().toUpperCase().compareTo(movie) == 0 
					& s.getShowtime().toLocalDate().compareTo(date) == 0) {
				result.add(s);
			}
		}
		result.sort(Comparator.comparing(Slot::getShowtime));
		return result;
		
	}
	
	/**
	 * The function to filter all slots that plays a specific movie at a specific cineplex
	 * @param cineplex_name		The name of the cineplex
	 * @param movie				The movie name
	 * @return					All slots that plays the specific movie
	 */
	public List<Slot> getMovieSlots(String cineplex_name, String movie) {
		
		movie = movie.toUpperCase();
		cineplex_name = cineplex_name.toUpperCase();
		List<Slot> result = new ArrayList<Slot>();		
		for (Slot s: this.slots) {
			if (s.getCinema().getCineplex_name().compareTo(cineplex_name) == 0 
					& s.getMovie_name().toUpperCase().compareTo(movie) == 0) {
				result.add(s);
			}
		}
		result.sort(Comparator.comparing(Slot::getShowtime));
		return result;
		
	}
	
	/**
	 * The function to filter all slots that plays a specific movie
	 * @param movie				The movie name
	 * @return					All slots that plays the specific movie
	 */
	public List<Slot> getMovieSlots(String movie) {
		
		movie = movie.toUpperCase();
		List<Slot> result = new ArrayList<Slot>();		
		for (Slot s: this.slots) {
			if (s.getMovie_name().toUpperCase().compareTo(movie) == 0) {
				result.add(s);
			}
		}
		result.sort(Comparator.comparing(Slot::getShowtime));
		return result;
		
	}
	
	/**
	 * The function to filter all slots that plays a specific movie at a specific cineplex at a specific date
	 * @param cineplex_name		The name of the cineplex
	 * @param movie				The movie name
	 * @param date				The date, a LocalDate object
	 * @return					All slots that plays the specific movie at the specific date
	 */
	public List<Slot> getMovieSlots(String cineplex_name, String movie, LocalDate date) {
		
		cineplex_name = cineplex_name.toUpperCase();
		movie = movie.toUpperCase();
		List<Slot> result = new ArrayList<Slot>();		
		for (Slot s: this.slots) {
			if (s.getCinema().getCineplex_name().compareTo(cineplex_name) == 0 
					& s.getMovie_name().toUpperCase().compareTo(movie) == 0 
					& s.getShowtime().toLocalDate().compareTo(date) == 0) {
				result.add(s);
			}
		}
		result.sort(Comparator.comparing(Slot::getShowtime));
		return result;
		
	}
	
	/**
	 * The function to save back all slots to the CSV file
	 * @return	A boolean variable that indicates whether the operation is successful or not
	 */
	private boolean saveToCSV() {
		
		try {
			
			FileWriter csvWriter = new FileWriter(SLOTSPATH);
			csvWriter.append(String.join(",", this.header));
			csvWriter.append("\n");

            for (Slot s: this.slots) {
            	StringBuilder sb = new StringBuilder();
        		sb.append(s.getSlotID());
        		sb.append(',');
        		sb.append(s.getShowtime().format(formatter));
        		sb.append(',');
        		sb.append(s.getDuration().toString());
        		sb.append(',');
        		sb.append(s.getMovie_name());
        		sb.append(',');
        		sb.append(s.getCinema().getCineplex_name());
        		sb.append(',');
        		sb.append(s.getCinema().getCinemaID());
        		sb.append(',');
        		sb.append(String.join("+", s.getBookings().getBookedSeatsID()));
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
	 * The function to automatically save all slots back to 
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
	 * The function used to test the SlotManager class
	 * Not used in the real application
	 * @param args
	 */
	public static void main(String[] args) {

//		SlotManager.getInstance().addSlot("JOK0001", LocalDateTime.parse("27-10-2019 04:30PM", SlotManager.getInstance().formatter), Duration.parse("PT2H4M"), "Joker", CineplexManager.getInstance().getCineplex("star @ bugis").getCinema("bu2"));
//		SlotManager.getInstance().addSlot("JOK0002", LocalDateTime.parse("27-10-2019 07:00PM", SlotManager.getInstance().formatter), Duration.parse("PT2H4M"), "Joker", CineplexManager.getInstance().getCineplex("star @ bugis").getCinema("bu5"));
//		SlotManager.getInstance().addSlot("JOK0003", LocalDateTime.parse("27-10-2019 12:00PM", SlotManager.getInstance().formatter), Duration.parse("PT2H4M"), "Joker", CineplexManager.getInstance().getCineplex("star @ bugis").getCinema("bu2"));
//		SlotManager.getInstance().addSlot("TOY0001", LocalDateTime.parse("27-10-2019 10:00AM", SlotManager.getInstance().formatter), Duration.parse("PT2H30M"), "Toy Story 4", CineplexManager.getInstance().getCineplex("star @ ang mo kio").getCinema("am5"));
//		SlotManager.getInstance().addSlot("TOY0002", LocalDateTime.parse("27-10-2019 05:30PM", SlotManager.getInstance().formatter), Duration.parse("PT2H30M"), "Toy Story 4", CineplexManager.getInstance().getCineplex("star @ bugis").getCinema("bu1"));
//		SlotManager.getInstance().addSlot("MAL0001", LocalDateTime.parse("27-10-2019 04:30PM", SlotManager.getInstance().formatter), Duration.parse("PT2H40M"), "Maleficent: Mistress of Evil", CineplexManager.getInstance().getCineplex("star @ ang mo kio").getCinema("am2"));
//		SlotManager.getInstance().addSlot("MAL0002", LocalDateTime.parse("27-10-2019 07:00PM", SlotManager.getInstance().formatter), Duration.parse("PT2H40M"), "Maleficent: Mistress of Evil", CineplexManager.getInstance().getCineplex("star @ ang mo kio").getCinema("am5"));
//		SlotManager.getInstance().addSlot("MAL0003", LocalDateTime.parse("27-10-2019 12:00PM", SlotManager.getInstance().formatter), Duration.parse("PT2H40M"), "Maleficent: Mistress of Evil", CineplexManager.getInstance().getCineplex("star @ jurong east").getCinema("je2"));
//		SlotManager.getInstance().addSlot("TER0001", LocalDateTime.parse("27-10-2019 10:00AM", SlotManager.getInstance().formatter), Duration.parse("PT2H15M"), "Terminator: Dark Fate", CineplexManager.getInstance().getCineplex("star @ bugis").getCinema("bu5"));
//		SlotManager.getInstance().addSlot("TER0002", LocalDateTime.parse("27-10-2019 05:30PM", SlotManager.getInstance().formatter), Duration.parse("PT2H15M"), "Terminator: Dark Fate", CineplexManager.getInstance().getCineplex("star @ ang mo kio").getCinema("am1"));
//		SlotManager.getInstance().addSlot("TER0003", LocalDateTime.parse("27-10-2019 05:30PM", SlotManager.getInstance().formatter), Duration.parse("PT2H15M"), "Terminator: Dark Fate", CineplexManager.getInstance().getCineplex("star @ jurong east").getCinema("je1"));
		
 		for (Slot s: SlotManager.getInstance().slots) {
			System.out.print(s.getSlotID() + "\t");
			System.out.print(s.getShowtime().format(SlotManager.getInstance().formatter) + "\t");
			System.out.print(s.getEndtime().format(SlotManager.getInstance().formatter) + "\t");
			System.out.printf("%-25s\t", s.getMovie_name());
			System.out.printf("%-20s\t", s.getCinema().getCineplex_name());
			System.out.print(s.getCinema().getCinemaID() + "\t");
			System.out.printf("%-11s\t", s.getCinema().getCinema_type());
			System.out.print(s.getBookings().getBookedSeatsID());
			System.out.println();
		}
		
	}
	
}
