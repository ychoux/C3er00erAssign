package entity;

/**
 * This Review class represent review entity
 * @author JunRong
 *
 */
public class Review {
	/**
	 * Movie name
	 */
	private String movieTitle;
	/**
	 * Movie rating
	 */
	private String rating;
	/**
	 * Movie overall rating
	 */
	private double overallRating;
	/**
	 * Movie reviews
	 */
	private String review;
	/**
	 * Default review constructor
	 */
	public Review() {
		
	}
	/**
	 * This is use to create new reivew objects
	 * @param movieTitle	Movie name
	 * @param rating		Movie rating
	 * @param overallRating	Movie overall rating
	 * @param review		Movie review
	 */
	public Review(String movieTitle, String rating, double overallRating, String review) {
		super();
		this.movieTitle = movieTitle;
		this.rating = rating;
		this.overallRating = overallRating;
		this.review = review;
	}

	/**
	 * This function is to get movie name
	 * @return	return movie name
	 */
	public String getMovieTitle() {
		return movieTitle;
	}
	/**
	 * This function is to set movie name
	 * @param name	Movie name
	 */
	public void setMovieTitle(String name) {
		this.movieTitle = name;
	}
	/**
	 * This function is to get movie rating
	 * @return	Movie rating
	 */
	public String getRating() {
		return rating;
	}
	/**
	 * This function is to set movie rating
	 * @param rating	Movie rating
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}
	/**
	 * This function is to get movie overall rating
	 * @return return Movie overall rating
	 */
	public double getOverallRating() {
		return overallRating;
	}
	/**
	 * This function is to set movie overall rating
	 * @param overallRating	Movie overall rating
	 */
	public void setOverallRating(double overallRating) {
		this.overallRating = overallRating;
	}
	/**
	 * This function is to get movie reviews
	 * @return	return movie reviews
	 */
	public String getReview() {
		return review;
	}
	/**
	 * This function is to set movie reviews
	 * @param review	movie reviews
	 */
	public void setReview(String review) {
		this.review = review;
	}
	
	
}
