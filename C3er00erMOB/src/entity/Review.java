package entity;

public class Review {
	private int id;
	private String rating;
	private double overallRating;
	private String review;
	public Review() {
		
	}
	public Review(int id, String rating, double overallRating, String review) {
		super();
		this.id = id;
		this.rating = rating;
		this.overallRating = overallRating;
		this.review = review;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
