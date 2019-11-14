package entity;

import java.util.List;

/**
 * The Cinema class, which represents the cinema in real life.
 * Consists of several attributes that defines a cinema.
 * @author Yew Wei Chee
 *
 */
public class Cinema implements Comparable<Cinema> {

	/**
	 * An enumeration of cinema types
	 * @author Yew Wei Chee
	 *
	 */
	public static enum CinemaType {STANDARD_2D, STANDARD_3D, DELUXE, IMAX, LUXURY}
	
	/**
	 * The path to seating plans directory
	 */
	private static final String seating_plan_directory = "src/data/seating plans/";
	
	/**
	 * The cinema ID
	 */
	private String cinemaID;
	
	/**
	 * The name of cineplex in which that cinema belongs to
	 */
	private String cineplex_name;
	
	/**
	 * The type of cinema
	 */
	private CinemaType cinema_type;
	
	/**
	 * The seating plan of the cinema
	 */
	private SeatingPlan seating_plan;
	
	/**
	 * The path to the seating plan CSV file of the cinema
	 */
	private String seating_plan_path;
	
	/**
	 * The constructor of cinema object
	 * @param cinemaID 			The cinema ID
	 * @param cineplex_name 	The name of cineplex in which the cinema belongs to
	 * @param cinema_type 		The type of cinema
	 * @param seating_plan_ID 	The seating plan ID of the cinema (file name)
	 */
	public Cinema(String cinemaID, String cineplex_name, CinemaType cinema_type, String seating_plan_ID) {
		this.cinemaID = cinemaID.toUpperCase();
		this.cineplex_name = cineplex_name.toUpperCase();
		this.cinema_type = cinema_type;
		this.seating_plan_path = Cinema.seating_plan_directory + seating_plan_ID.toUpperCase() + ".csv";
		this.seating_plan = new SeatingPlan(this.seating_plan_path, seating_plan_ID.toUpperCase());
	}

	/**
	 * The function to get the ID of the cinema
	 * @return	The cinema ID
	 */
	public String getCinemaID() {
		return cinemaID;
	}

	/**
	 * The function to set the cinema ID
	 * @param cinemaID The cinema ID
	 */
	public void setCinemaID(String cinemaID) {
		this.cinemaID = cinemaID.toUpperCase();
	}

	/**
	 * The function to get the cineplex name in which the cinema belongs to
	 * @return The name of cineplex in which the cinema belongs to
	 */
	public String getCineplex_name() {
		return cineplex_name;
	}

	/**
	 * The function to set the cineplex name in which the cinema belongs to
	 * @param cineplex_name The name of cineplex in which the cinema belongs to
	 */
	public void setCineplex_name(String cineplex_name) {
		this.cineplex_name = cineplex_name.toUpperCase();
	}

	/**
	 * The function to get the type of the cinema
	 * @return The type of the cinema
	 */
	public CinemaType getCinema_type() {
		return cinema_type;
	}

	/**
	 * The function to set the type of the cinema
	 * @param cinema_type The type of the cinema
	 */
	public void setCinema_type(CinemaType cinema_type) {
		this.cinema_type = cinema_type;
	}

	/**
	 * The function to get the seating plan of a cinema
	 * @return The seating plan of the cinema
	 */
	public SeatingPlan getSeating_plan() {
		return seating_plan;
	}
	
	/**
	 * The function to get the path to the seating plan of the cinema
	 * @return The path to the seating plan of the cinema
	 */
	public String getSeating_plan_path() {
		return seating_plan_path;
	}
	
	/**
	 * The function to check if the cinema has a seat with the seatID
	 * @param seatID The seatID of a seat
	 * @return	A boolean variable, if the seat is in the cinema, returns True, else return False
	 */
	public boolean containSeat(String seatID) {
		seatID = seatID.toUpperCase();
		return this.seating_plan.getSeatIDs().contains(seatID);
	}
	
	/**
	 * The function to check if the cinema has all the seats in a list of seatIDs
	 * @param seatIDs The list of seatIDs
	 * @return A boolean variable, if the all the seats are in the cinema, returns True, else return False
	 */
	public boolean containSeat(List<String> seatIDs) {
		seatIDs.replaceAll(String::toUpperCase);
		return this.seating_plan.getSeatIDs().containsAll(seatIDs);
	}
	
	/**
	 * The function to get the cinema full name
	 */
	@Override
	public String toString() {
		return this.cineplex_name + " " + this.cinemaID;
	}
	
	/**
	 * The function to compare 2 cinemas
	 */
	@Override
	public int compareTo(Cinema c) {
		int i = this.cineplex_name.compareTo(c.cineplex_name);
		if (i != 0)
			return i;
		return this.cinemaID.compareTo(c.cinemaID);
	}
	
}
