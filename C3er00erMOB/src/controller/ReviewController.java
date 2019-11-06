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
	String cvsSplitBy = ",";
	
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
				//int id, string rating, double overallRating, String review
				reviewtmp = new Review(reviewcsv[0],reviewcsv[1],Double.parseDouble(reviewcsv[2]),reviewcsv[3]);
				reviewList.add(reviewtmp);
			}
		}
	}catch (IOException e) {
		e.printStackTrace();
	}
	return reviewList;
	}
	public static void userReview(List<Review> rList, String name, String rating, String review) {
		String ratingtmp, reviewtmp;
		for(Review r: rList) {
			if(r.getMovieTitle().equals(name)) {
				ratingtmp = r.getRating();
				ratingtmp = ratingtmp+";"+rating;
				reviewtmp = r.getReview();
				reviewtmp = reviewtmp+";"+review;
				r.setRating(ratingtmp);
				r.setReview(reviewtmp);
				break;
			}
		}
		updateOverallRating(rList);
		updateReviewCSV(rList);
		updateMovieListRating(rList);
	}
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
		updateReviewCSV(rList);
	}
	public static void updateOverallRating(List<Review> rList) {
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
				rates = Double.parseDouble(ratings);
				totalrating += rates;
				count++;
			}
			totalrating /= count;
			r.setOverallRating(totalrating);
			
		}
		updateReviewCSV(rList);
	}
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
