package entity;


public class Movie {
	
	private String movieTitle; // name
	private String synopsis; 
	private String director;
	private String cast;
	private String status;
	private String genre;
	private int id;
	private double overallRating;
	private int sales;
	
	public Movie() {
		
	}
	public Movie(int id,String movieTitle, String synopsis, String director, String cast, String genre,String status, 
			int sales,double overallRating) {
		this.id = id;
		this.movieTitle = movieTitle;
		this.synopsis = synopsis;
		this.director = director;
		this.cast = cast;
		this.genre = genre;
		this.status = status;
		this.sales = sales;
		this.overallRating = overallRating;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public void setStatus(String status) {
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
	public String getStatus() {
		return status;
	}
	public String getGenre() {
		return genre;
	}
	
	
	
}
