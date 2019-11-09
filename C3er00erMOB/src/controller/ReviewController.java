package controller;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entity.Movie;
import entity.Review;

public class ReviewController {
	static String REVIEWFILE = "src/data/review.csv";
	static String cvsSplitBy = ",";
	static String SplitBy =";";
	
	
	/* 
	 * This function is to get data from review csv file into review objects 
	 */
	public List<Review>getReviewList(){
	List<Review>reviewList = new ArrayList<>();
	BufferedReader br = null;
	String line ="";
	Review reviewtmp;
	try {
		br = new BufferedReader(new FileReader(REVIEWFILE));
		while((line = br.readLine()) !=null) {
			String[] reviewcsv = line.split(cvsSplitBy);
			if(!reviewcsv[0].equals("NAME")) {
				//string name, string rating, double overallRating, String review
				//NAME	RATING	OVERALLRATING	REVIEW
				reviewtmp = new Review(reviewcsv[0],reviewcsv[1],Double.parseDouble(reviewcsv[2]),reviewcsv[3]);
				reviewList.add(reviewtmp);
			}
		}
	}catch (IOException e) {
		e.printStackTrace();
	}
	return reviewList;
	}
	
	/* 
	 * This function is to allow user to add their reviews
	 * updateMovieListRating , updateOverallRating and updateReviewCSV is called to ensure that
	 * the rating in review csv and overall rating in both movielist and review csv is updated
	 */
	
	public static void userReview(List<Review> rList, String name, String rating, String review) {
		String ratingtmp, reviewtmp;
		for(Review r: rList) {
			if(r.getMovieTitle().equals(name)) {
				ratingtmp = r.getRating();
				ratingtmp = ratingtmp+SplitBy+rating;
				reviewtmp = r.getReview();
				reviewtmp = reviewtmp+SplitBy+review;
				r.setRating(ratingtmp);
				r.setReview(reviewtmp);
				break;
			}
		}
		updateOverallRating(rList);
		updateMovieListRating(rList);
		updateReviewCSV(rList);
	}
	
	/* 
	 * This function is to allow staff to delete review from review csv
	 * updateReviewCSV is called to ensure csv updated
	 */
	
	public static void delReviewList(List<Review> rList, int id) {
		rList.remove(id);
		updateReviewCSV(rList);
	}
	
	/* 
	 * This function is to allow staff to add movie title into review csv so userReview will be able to work
	 * updateReviewCSV is called to ensure csv updated
	 */
	
	public static void addReviewList(List<Review> rList, String name) {
		Review reviewtmp;
		reviewtmp = new Review();
		reviewtmp.setMovieTitle(name);
		rList.add(reviewtmp);
		updateReviewCSV(rList);
	}
	
	/* 
	 * This function is a hard update on csv overallrating, only used the rating in the csv file
	 * if less than 2 rating overall rating will be 0
	 * updateReviewCSV is called to ensure csv updated
	 */
	
	public static void updateOverallRating() {
		double rates, totalrating;
		int count;

		ReviewController file = new ReviewController();
		List<Review> rList = file.getReviewList();
		String SplitBy = ";";
		for(Review r: rList)
		{
			count =0;
			totalrating =0;
			try {
				String[] rating = r.getRating().split(SplitBy);
				for(String ratings: rating)
				{
					if(ratings.equals("null")|| ratings.equals("null")) {
						totalrating +=0;
					}
					else {
						rates = Double.parseDouble(ratings);
						totalrating += rates;
						count++;
					}
				}
			}
			catch(NullPointerException e) {
			}
			if(count>=2) {
				totalrating /= count;
				r.setOverallRating(totalrating);
			}
			else {
				r.setOverallRating(0.0);
			}
		}
		updateReviewCSV(rList);
	}
	
	/* 
	 * This function is to allow update on overall rating in review csv, rating is get from review objects
	 * if less than 2 rating overall rating will be 0
	 * updateReviewCSV is called to ensure csv updated
	 */
	public static void updateOverallRating(List<Review> rList) {
		double rates, totalrating;
		int count;
		String SplitBy = ";";
		for(Review r: rList)
		{
			count =0;
			totalrating =0;
			try {
				String[] rating = r.getRating().split(SplitBy);
				for(String ratings: rating)
				{
					if(ratings.equals("null")|| ratings.equals("")) {
						totalrating +=0;
					}
					else {
						rates = Double.parseDouble(ratings);
						totalrating += rates;
						count++;
					}
					
				}
			}
			catch(NullPointerException e) {
			}
			if(count>=2) {
				totalrating /= count;
				r.setOverallRating(totalrating);
			}
			else {
				r.setOverallRating(0.0);
			}
		}
		updateReviewCSV(rList);
	}
	
	/* 
	 * This function is a hard update on movielist overall rating, rating is taken strange from review csv
	 * updateMovieListCSV is called to ensure csv updated
	 */
	
	public static void updateMovieListRating() {
		ReviewController file = new ReviewController();
		List<Review> rList = file.getReviewList();
		MovieListController mfile = new MovieListController();
		List<Movie> mList = mfile.getMovieList();
		for(Movie m: mList) {
			for(Review r: rList)
			{
				if(m.getMovieTitle() == r.getMovieTitle()) {
					m.setOverallRating(r.getOverallRating());
					break;
				}
			}
		}
		MovieListController.updateMovieListCSV(mList);
		
	}
	
	/* 
	 * This function is to allow update on movielist csv directly from review objects
	 * updateMovieListCSV is called to ensure csv updated
	 */
	
	public static void updateMovieListRating(List<Review> rList) {
		MovieListController mfile = new MovieListController();
		List<Movie> mList = mfile.getMovieList();
		for(Movie m: mList) {
			for(Review r: rList)
			{
				if(m.getMovieTitle().equals(r.getMovieTitle())) {
					m.setOverallRating(r.getOverallRating());
					break;
				}
			}
		}
		MovieListController.updateMovieListCSV(mList);
		
	}
	
	/* 
	 * This function is to allow live update on movielist csv and movielist object of overall rating from review objects
	 * updateMovieListCSV is called to ensure csv updated
	 */
	
	public static void updateMovieListRating(List<Movie> mList, List<Review> rList) {
		for(Movie m: mList) {
			for(Review r: rList)
			{
				if(m.getMovieTitle().equals(r.getMovieTitle())) {
					m.setOverallRating(r.getOverallRating());
					break;
				}
			}
		}
		MovieListController.updateMovieListCSV(mList);
		
	}
	
	
	/* 
	 * This function is to allow update on review csv
	 */
	
	
	public static void updateReviewCSV(List<Review> rList) {
		FileWriter csvWriter;
		String SplitBy = ",";

		try {
			csvWriter = new FileWriter(REVIEWFILE);
			csvWriter.append("NAME");
			csvWriter.append(SplitBy);
			csvWriter.append("RATING");
			csvWriter.append(SplitBy);
			csvWriter.append("OVERALLRATING");
			csvWriter.append(SplitBy);
			csvWriter.append("REVIEW");
			csvWriter.append("\n");

			for (Review reviewtmp : rList) {
				StringBuilder sb = new StringBuilder();
				sb.append(reviewtmp.getMovieTitle());
				sb.append(SplitBy);
				sb.append(reviewtmp.getRating());
				sb.append(SplitBy);
				sb.append(reviewtmp.getOverallRating());
				sb.append(SplitBy);
				sb.append(reviewtmp.getReview());
				sb.append(SplitBy);
				sb.append('\n');
				csvWriter.append(sb.toString());
			}

			csvWriter.flush();
			csvWriter.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

}
