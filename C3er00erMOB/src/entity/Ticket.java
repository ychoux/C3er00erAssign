package entity;

import java.time.LocalDateTime;

import entity.Cinema.CinemaType;

public class Ticket {

	public enum TicketType {ADULT, CHILD, STUDENT, SENIOR_CITIZEN, DISABLED};
	
	private String ticketID;
	private LocalDateTime showtime;
	private String movie_name;
	private TicketType ticket_type;
	private CinemaType cinema_type;	
	private double price;
	private String cineplex_name;
	private String cinemaID;
	private String seatID;
	
	public Ticket(String ticketID, LocalDateTime showtime, String movie_name, TicketType ticket_type,
			CinemaType cinema_type, double price, String cineplex_name, String cinemaID, String seatID) {
		this.ticketID = ticketID.toUpperCase();
		this.showtime = showtime;
		this.movie_name = movie_name;
		this.ticket_type = ticket_type;
		this.cinema_type = cinema_type;
		this.price = price;
		this.cineplex_name = cineplex_name.toUpperCase();
		this.cinemaID = cinemaID.toUpperCase();
		this.seatID = seatID.toUpperCase();
	}

	public String getTicketID() {
		return ticketID;
	}

	public LocalDateTime getShowtime() {
		return showtime;
	}

	public String getMovie_name() {
		return movie_name;
	}

	public TicketType getTicket_type() {
		return ticket_type;
	}

	public CinemaType getCinema_type() {
		return cinema_type;
	}

	public double getPrice() {
		return price;
	}

	public String getCineplex_name() {
		return cineplex_name;
	}

	public String getCinemaID() {
		return cinemaID;
	}

	public String getSeatID() {
		return seatID;
	}

}
