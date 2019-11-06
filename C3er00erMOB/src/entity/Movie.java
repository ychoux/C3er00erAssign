package entity;
import java.time.Duration;

public class Movie {
	
	private String movieTitle; // name
	private String synopsis; 
	private String director;
	private String cast;
	private String genre;
	private Duration time;
	private double overallRating;
	private int sales;
	private MovieStatus status;
	
	public Movie() {
		
	}
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
	public Duration getTime() {
		return time;
	}
	public void setTime(Duration time) {
		this.time = time;
	}
	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public void setCast(String cast) {
		this.cast = cast;
	}
	public void setOverallRating(double overallRating) {
		this.overallRating = overallRating;
	}
	public void setSales(int sales) {
		this.sales = sales;
	}
	public void setStatus(MovieStatus status) {
		this.status = status;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getMovieTitle() {
		return movieTitle;
	}
	public String getSynopsis() {
		return synopsis;
	}
	public String getDirector() {
		return director;
	}
	public String getCast() {
		return cast;
	}
	public double getOverallRating() {
		return overallRating;
	}
	public int getSales() {
		return sales;
	}
	public MovieStatus getStatus() {
		return status;
	}
	public String getGenre() {
		return genre;
	}
	
	
	
}
