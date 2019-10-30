package entity;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import entity.Cinema.CinemaType;

public class Cineplex {

	private String cineplex_name;
	private Dictionary<String, Cinema> cinemas = new Hashtable<String, Cinema>();
	//private List<Movie> movies;
	
	public Cineplex(String cineplex_name) {
		this.cineplex_name = cineplex_name.toUpperCase();
	}

	public String getCineplex_name() {
		return cineplex_name;
	}
	
	public void setCineplex_name(String cineplex_name) {
		this.cineplex_name = cineplex_name.toUpperCase();
		for (Cinema c: Collections.list(this.cinemas.elements())) {
			c.setCineplex_name(this.cineplex_name);
		}
	}
	
	public List<Cinema> getCinemas() {
		return Collections.list(this.cinemas.elements());
	}
	
	public boolean addCinema(String cinemaID, CinemaType cinema_type) {
		cinemaID = cinemaID.toUpperCase();
		if (this.cinemas.get(cinemaID) != null) {
			return false;
		}
		this.cinemas.put(cinemaID, 
				new Cinema(cinemaID, this.cineplex_name, cinema_type, (this.cineplex_name + " " + cinemaID))
				);
		return true;
	}
	
	public Cinema removeCinema(String cinemaID) {
		cinemaID = cinemaID.toUpperCase();
		Cinema c = this.cinemas.get(cinemaID);
		if (c != null) {
			this.cinemas.remove(cinemaID);
		}
		return c;
	}
	
	public Cinema getCinema(String cinemaID) {
		try {
			return this.cinemas.get(cinemaID.toUpperCase());
		} 
		catch (NullPointerException e) {
			return null;
		}
	}

}
