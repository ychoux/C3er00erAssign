package entity;

public class Review {
	private String movieTitle;
	private String rating;
	private double overallRating;
	private String review;
	public Review() {
		
	}
	public Review(String movieTitle, String rating, double overallRating, String review) {
		super();
		this.movieTitle = movieTitle;
		this.rating = rating;
		this.overallRating = overallRating;
		this.review = review;
	}

	public String getMovieTitle() {
		return movieTitle;
	}
	public void setMovieTitle(String name) {
		this.movieTitle = name;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public double getOverallRating() {
		return overallRating;
	}
	public void setOverallRating(double overallRating) {
		this.overallRating = overallRating;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	
	
}
