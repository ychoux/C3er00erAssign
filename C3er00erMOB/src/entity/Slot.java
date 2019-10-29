package entity;

import java.time.Duration;
import java.time.LocalDateTime;

public class Slot {

	private String slotID;
	private LocalDateTime showtime;
	private Duration duration;
	private String movie_name;
	private Cinema cinema;	
	private SeatBookings bookings;
	
	public Slot(String slotID, LocalDateTime showtime, Duration duration, String movie_name, Cinema cinema) {
		this.slotID = slotID.toUpperCase();
		this.showtime = showtime;
		this.duration = duration;		
		this.movie_name = movie_name;
		this.cinema = cinema;
		this.bookings = new SeatBookings(cinema.getSeating_plan(), slotID);
	}
	
	public String getSlotID() {
		return slotID;
	}

	public void setSlotID(String slotID) {
		this.slotID = slotID.toUpperCase();
		this.bookings.setSlotID(slotID.toUpperCase());
	}

	public LocalDateTime getShowtime() {
		return showtime;
	}

	public void setShowtime(LocalDateTime showtime) {
		this.showtime = showtime;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}
	
	public LocalDateTime getEndtime() {
		return this.showtime.plus(duration);
	}

	public String getMovie_name() {
		return movie_name;
	}

	public void setMovie_name(String movie_name) {
		this.movie_name = movie_name;
	}

	public Cinema getCinema() {
		return cinema;
	}

	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}

	public SeatBookings getBookings() {
		return bookings;
	}
	
}
