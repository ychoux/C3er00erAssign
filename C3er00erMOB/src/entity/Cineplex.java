package entity;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import entity.Cinema.CinemaType;

/**
 * The Cineplex class, represents a cineplex object in real life
 * @author Yew Wei Chee
 *
 */
public class Cineplex {

	/**
	 * The name of the cineplex
	 */
	private String cineplex_name;
	
	/**
	 * A dictionary (hashtable) that stores the cinemas in the cineplex, with keys as cinema IDs
	 */
	private Dictionary<String, Cinema> cinemas = new Hashtable<String, Cinema>();
	//private List<Movie> movies;
	
	/**
	 * The constructor of the cineplex
	 * @param cineplex_name The name of the cineplex
	 */
	public Cineplex(String cineplex_name) {
		this.cineplex_name = cineplex_name.toUpperCase();
	}

	/**
	 * The function to get the name of the cineplex
	 * @return The name of cineplex
	 */
	public String getCineplex_name() {
		return cineplex_name;
	}
	
	/**
	 * The function to set the name of the cineplex
	 * @param cineplex_name The name of cineplex
	 */
	public void setCineplex_name(String cineplex_name) {
		this.cineplex_name = cineplex_name.toUpperCase();
		for (Cinema c: Collections.list(this.cinemas.elements())) {
			c.setCineplex_name(this.cineplex_name);
		}
	}
	
	/**
	 * The function to get the list of cinemas in the cineplex
	 * @return The list of cinemas in the cineplex
	 */
	public List<Cinema> getCinemas() {
		return Collections.list(this.cinemas.elements());
	}
	
	/**
	 * The function to add a cinema into the cineplex
	 * @param cinemaID		The cinema ID
	 * @param cinema_type	The type of cinema
	 * @return				A boolean variable indicating whether the operation is successful or not
	 */
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
	
	/**
	 * The function to remove a cinema from the cineplex
	 * @param cinemaID	The cinema ID
	 * @return			The removed cinema object
	 */
	public Cinema removeCinema(String cinemaID) {
		cinemaID = cinemaID.toUpperCase();
		Cinema c = this.cinemas.get(cinemaID);
		if (c != null) {
			this.cinemas.remove(cinemaID);
		}
		return c;
	}
	
	/**
	 * The function to get a cinema object based on its cinema ID
	 * @param cinemaID 	The cinema ID
	 * @return			The cinema object
	 */
	public Cinema getCinema(String cinemaID) {
		try {
			return this.cinemas.get(cinemaID.toUpperCase());
		} 
		catch (NullPointerException e) {
			return null;
		}
	}

}
