package controller;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entity.Movie;
import entity.Review;

/**
 * The class for all action related to Reviews
 * @author
 *
 */
public class ReviewController {
	/**
	 * Path to the csv file that store review data
	 */
	//static String REVIEWFILE = "src/data/review.csv";
	static String REVIEWFILE = "data/review.csv";
	
	/**
	 * The seperator in csv file
	 */
	static String cvsSplitBy = ",";
	/**
	 * The seperator for array of string in csv
	 */
	static String SplitBy =";";
	
	/**
	 * The seperator for first entry in csv
	 */
	static String firstEntry =" ;";
	
	/* 
	 * This function is to get data from review csv file into review objects 
	 */
	/**
	 * This function is to fetch review data from review.csv into list of review object
	 * @return	return list of review object
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
		

	/**
	 * This function is to allow user to add their reviews
	 * @param rList		A list of review object
	 * @param name		Movie name
	 * @param rating	Movie rating from user
	 * @param review	Movie review from user
	 * @return			return true if successfully update review in review csv and movielist csv
	 */
	public static boolean userReview(List<Review> rList, String name, String rating, String review) {
		String ratingtmp, reviewtmp;
		for(Review r: rList) {
			if(r.getMovieTitle().equals(name)) {
				ratingtmp = r.getRating();
				ratingtmp = ratingtmp+rating+SplitBy;
				reviewtmp = r.getReview();
				reviewtmp = reviewtmp+review+firstEntry;
				r.setRating(ratingtmp);
				r.setReview(reviewtmp);
				break;
			}
		}
		return (updateReviewCSV(rList) && updateMovieListRating(rList) && updateOverallRating(rList));
	}

	
	/**
	 * This function is to allow staff to delete review in review csv when movie is removed
	 * @param rList		A list of review object
	 * @param id		Selected review index in the list of object
	 * @return			return true if successfully update review in review csv
	 */
	// will remove if not used
	public static boolean delReviewList(List<Review> rList, int id) {
		rList.remove(id);
		return updateReviewCSV(rList);
	}
	
	/**
	 * This function is to allow staff to add review in review csv when new movie is added
	 * @param rList		A list of review object
	 * @param name		Movie name
	 * @return		return true if successfully update review in review csv
	 */
	public static boolean addReviewList(List<Review> rList, String name) {
		Review reviewtmp;
		reviewtmp = new Review();
		reviewtmp.setMovieTitle(name);
		reviewtmp.setRating(" ;");
		reviewtmp.setReview(" ;");
		rList.add(reviewtmp);
		return updateReviewCSV(rList);
	}
	

	/**
	 * This function allow update of all overallrating in review csv
	 * @return		return true if successfully update overallrating in review csv
	 */
	
	public static boolean updateOverallRating() {
		double rates, totalrating;
		int count;

		ReviewController file = new ReviewController();
		List<Review> rList = file.getReviewList();
		for(Review r: rList)
		{
			count =0;
			totalrating =0;
			String[] rating = r.getRating().split(SplitBy);
			for(String ratings: rating)
			{
				rates = Double.parseDouble(ratings);
				totalrating += rates;
				count++;
			}
				totalrating /= count;
				r.setOverallRating(totalrating);
		}
		return updateReviewCSV(rList);
	}

	/**
	 * This function allow update of overallrating in review csv from list of review object
	 * @param rList		A list of review object
	 * @return			return true if successfully update overallrating in review csv
	 */
	
	public static boolean updateOverallRating(List<Review> rList) {
		double rates, totalrating;
		int count;
		String SplitBy = ";";
		for(Review r: rList)
		{
			count =0;
			totalrating =0;
			String[] rating = r.getRating().split(SplitBy);
			for(String ratings: rating)
			{
				if(!ratings.isBlank()) {
					rates = Double.parseDouble(ratings);
					count++;
				}
				else {
					rates = 0;
				}
				totalrating += rates;
			}

			totalrating /= count;
			if(count == 0) {
				r.setOverallRating(0);
			}
			else {
				r.setOverallRating(totalrating);
			}
		}
		return updateReviewCSV(rList);
	}
	
	/**
	 * This function allow update of overallrating in movielist csv directly from review csv
	 * @return		return true if successfully update overallrating in movielist csv
	 */
	public static boolean updateMovieListRating() {
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
		return MovieListController.updateMovieListCSV(mList);
		
	}
	
	/**
	 * This function allow update of overallrating in movielist csv directly from list of review object
	 * @param rList		A list of review object
	 * @return			return true if successfully update overallrating in movielist csv
	 */
	public static boolean updateMovieListRating(List<Review> rList) {
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
		return MovieListController.updateMovieListCSV(mList);
		
	}

	/**
	 * This function allow update of overallrating in list of movie object and movielist csv from list of review object
	 * @param mList		A list of movie object
	 * @param rList		A list of review object
	 * @return			return true if successfully update overallrating in movielist csv
	 */
	public static boolean updateMovieListRating(List<Movie> mList, List<Review> rList) {
		for(Movie m: mList) {
			for(Review r: rList)
			{
				if(m.getMovieTitle().equals(r.getMovieTitle())) {
					m.setOverallRating(r.getOverallRating());
					break;
				}
			}
		}
		return MovieListController.updateMovieListCSV(mList);
		
	}
	
	
	/* 
	 * This function is to allow update on review csv
	 */
	
	/**
	 * This function update review csv from list of review object
	 * @param rList		A list of review object
	 * @return			return true if successfully update review csv
	 */
	public static boolean updateReviewCSV(List<Review> rList) {
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
			return true;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}
	

}
