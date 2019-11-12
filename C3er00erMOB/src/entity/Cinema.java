package entity;

import java.util.List;

public class Cinema implements Comparable<Cinema> {

	public static enum CinemaType {STANDARD_2D, STANDARD_3D, DELUXE, IMAX, LUXURY}
	private static final String seating_plan_directory = "src/data/seating plans/";
	
	private String cinemaID;
	private String cineplex_name;
	private CinemaType cinema_type;
	private SeatingPlan seating_plan;
	private String seating_plan_path;
	
	public Cinema(String cinemaID, String cineplex_name, CinemaType cinema_type, String seating_plan_ID) {
		this.cinemaID = cinemaID.toUpperCase();
		this.cineplex_name = cineplex_name.toUpperCase();
		this.cinema_type = cinema_type;
		this.seating_plan_path = Cinema.seating_plan_directory + seating_plan_ID.toUpperCase() + ".csv";
		this.seating_plan = new SeatingPlan(this.seating_plan_path, seating_plan_ID.toUpperCase());
	}

	public String getCinemaID() {
		return cinemaID;
	}

	public void setCinemaID(String cinemaID) {
		this.cinemaID = cinemaID.toUpperCase();
	}

	public String getCineplex_name() {
		return cineplex_name;
	}

	public void setCineplex_name(String cineplex_name) {
		this.cineplex_name = cineplex_name.toUpperCase();
	}

	public CinemaType getCinema_type() {
		return cinema_type;
	}

	public void setCinema_type(CinemaType cinema_type) {
		this.cinema_type = cinema_type;
	}

	public SeatingPlan getSeating_plan() {
		return seating_plan;
	}
	
	public String getSeating_plan_path() {
		return seating_plan_path;
	}
	
	public boolean containSeat(String seatID) {
		seatID = seatID.toUpperCase();
		return this.seating_plan.getSeatIDs().contains(seatID);
	}
	
	public boolean containSeat(List<String> seatIDs) {
		seatIDs.replaceAll(String::toUpperCase);
		return this.seating_plan.getSeatIDs().containsAll(seatIDs);
	}
	
	@Override
	public String toString() {
		return this.cineplex_name + " " + this.cinemaID;
	}
	
	@Override
	public int compareTo(Cinema c) {
		int i = this.cineplex_name.compareTo(c.cineplex_name);
		if (i != 0)
			return i;
		return this.cinemaID.compareTo(c.cinemaID);
	}
	
}
