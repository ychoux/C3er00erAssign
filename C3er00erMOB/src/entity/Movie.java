package entity;
import java.time.Duration;

/**
 * The Movie class represents movie entity
 * @author Jun Rong
 *
 */
public class Movie {
	
	/**
	 *  Movie name
	 */
	private String movieTitle;
	/**
	 * Movie synopsis
	 */
	private String synopsis; 
	/**
	 * Movie director
	 */
	private String director;
	/**
	 * Movie cast
	 */
	private String cast;
	/**
	 * Movie genre
	 */
	private String genre;
	/**
	 * Movie duration
	 */
	private Duration time;
	/**
	 * Movie overall rating
	 */
	private double overallRating;
	/**
	 * Movie total sales
	 */
	private int sales;
	/**
	 * Movie status
	 */
	private MovieStatus status;
	
	/**
	 * Default Movie constructor
	 */
	public Movie() {
		
	}
	/**
	 * This is used to create new Movie object
	 * @param movieTitle	This is movie name
	 * @param synopsis		This is movie synopsis
	 * @param director		This is movie directors
	 * @param cast			This is movie casts
	 * @param genre			This is movie genres
	 * @param time			This is movie duration
	 * @param status		This is movie status
	 * @param sales			This is movie sales
	 * @param overallRating	This is movie overall rating
	 */
	public Movie(String movieTitle, String synopsis, String director, String cast, String genre, Duration time
			,MovieStatus status, int sales,double overallRating) {
		this.movieTitle = movieTitle;
		this.synopsis = synopsis;
		this.director = director;
		this.cast = cast;
		this.genre = genre;
		this.time = time;
		this.status = status;
		this.sales = sales;
		this.overallRating = overallRating;
	}
	/**
	 * This function is to get movie duration
	 * @return	return Movie duration
	 */
	public Duration getTime() {
		return time;
	}
	/**
	 * This function is to set movie duration
	 * @param time	Movie duration
	 */
	public void setTime(Duration time) {
		this.time = time;
	}
	/**
	 * 	This function is to set Movie name
	 * @param movieTitle	Movie name
	 */
	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}
	/**
	 * This function is to set Movie synopsis
	 * @param synopsis	Movie synopsis
	 */
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	/**
	 * This function is to get Movie director
	 * @param director	Movie director
	 */
	public void setDirector(String director) {
		this.director = director;
	}
	/**
	 * This function is to set Movie cast
	 * @param cast	Movie cast
	 */
	public void setCast(String cast) {
		this.cast = cast;
	}
	/**
	 * This function is to set movie overall rating
	 * @param overallRating	Movie overall rating
	 */
	public void setOverallRating(double overallRating) {
		this.overallRating = overallRating;
	}
	/**
	 * This function is to set movie total sales
	 * @param sales	Movie sales
	 */
	public void setSales(int sales) {
		this.sales = sales;
	}
	/**
	 * This function is to set movie status
	 * @param status	Movie status
	 */
	public void setStatus(MovieStatus status) {
		this.status = status;
	}
	/**
	 * This function is to set movie genres
	 * @param genre	Movie genres
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}
	/**
	 * This function is to get movie name
	 * @return	return movie name
	 */
	public String getMovieTitle() {
		return movieTitle;
	}
	/**
	 * This function is to get movie synopsis
	 * @return	return movie synopsis
	 */
	public String getSynopsis() {
		return synopsis;
	}
	/**
	 * This function is to get movie directors
	 * @return	return movie director
	 */
	public String getDirector() {
		return director;
	}
	/**
	 * This function is to get movie casts
	 * @return	return movie cast
	 */
	public String getCast() {
		return cast;
	}
	/**
	 * This function is to get movie overall rating
	 * @return	return movie overall rating
	 */
	public double getOverallRating() {
		return overallRating;
	}
	/**
	 * This function is to get movie total sales
	 * @return	return movie total sales
	 */
	public int getSales() {
		return sales;
	}
	/**
	 * This function is to get movie status
	 * @return	return movie status
	 */
	public MovieStatus getStatus() {
		return status;
	}
	/**
	 * This function is to get movie genres
	 * @return	return movie genres
	 */
	public String getGenre() {
		return genre;
	}
}
