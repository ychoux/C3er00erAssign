package entity;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * The Slot class, represents a showtime in real life
 * @author Yew Wei Chee
 *
 */
public class Slot {

	/**
	 * The slot ID
	 */
	private String slotID;
	
	/**
	 * The showtime, a LocalDateTime object
	 */
	private LocalDateTime showtime;
	
	/**
	 * The duration, a Duration object
	 */
	private Duration duration;
	
	/**
	 * The name of the movie
	 */
	private String movie_name;
	
	/**
	 * The cinema
	 */
	private Cinema cinema;	
	
	/**
	 * The seat bookings
	 */
	private SeatBookings bookings;
	
	/**
	 * The constructor for a slot
	 * @param slotID		The slot ID
	 * @param showtime		The showtime
	 * @param duration		The duration
	 * @param movie_name	The name of movie
	 * @param cinema		The cinema
	 */
	public Slot(String slotID, LocalDateTime showtime, Duration duration, String movie_name, Cinema cinema) {
		this.slotID = slotID.toUpperCase();
		this.showtime = showtime;
		this.duration = duration;		
		this.movie_name = movie_name;
		this.cinema = cinema;
		this.bookings = new SeatBookings(cinema.getSeating_plan(), slotID);
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
		this.bookings.setSlotID(slotID.toUpperCase());
	}

	/**
	 * The function to get the showtime
	 * @return	The showtime, a LocalDateTime object
	 */
	public LocalDateTime getShowtime() {
		return showtime;
	}

	/**
	 * The function to set the showtime
	 * @param showtime The showtime, a LocalDateTime object
	 */
	public void setShowtime(LocalDateTime showtime) {
		this.showtime = showtime;
	}

	/**
	 * The function to get the duration
	 * @return	The duration, a Duration object
	 */
	public Duration getDuration() {
		return duration;
	}

	/**
	 * The function to set the duration
	 * @param duration The duration, a Duration object
	 */
	public void setDuration(Duration duration) {
		this.duration = duration;
	}
	
	/**
	 * The function to get the endtime
	 * @return The endtime, a LocalDateTime object
	 */
	public LocalDateTime getEndtime() {
		return this.showtime.plus(duration);
	}

	/**
	 * The function to get the name of movie
	 * @return The name of movie
	 */
	public String getMovie_name() {
		return movie_name;
	}

	/**
	 * The function to set the name of movie
	 * @param movie_name The name of movie
	 */
	public void setMovie_name(String movie_name) {
		this.movie_name = movie_name;
	}

	/**
	 * The function to get the cinema
	 * @return The cinema
	 */
	public Cinema getCinema() {
		return cinema;
	}

	/**
	 * The function to set the cinema
	 * @param cinema The cinema
	 */
	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}

	/**
	 * The function to get the seat bookings
	 * @return The seat bookings
	 */
	public SeatBookings getBookings() {
		return bookings;
	}
	
}
