package controller;


import java.time.Duration;
import java.util.List;

import entity.Movie;
import entity.MovieStatus;
import entity.Review;

/**
 * This class is to handle printing of movielist and review
 * @author
 *
 */
public class PrintMovieList {
	/**
	 * The seperator for array of string in csv
	 */
	static String SplitBy = ";";
	/**
	 * The csv seperator
	 */
	static String cvsSplitBy = ",";
	/**
	 * The seperator that represent "," when stored in csv
	 */
	static String SplitByColon = ":";
	
	
	/**
	 * This function is to print out all the movies from list of movie object
	 * @param mList		A list of movie object
	 */
	public static void printAllMovieList(List<Movie>mList){	
		int count,id;
		ReviewController file = new ReviewController();
		List<Review> rlist = file.getReviewList();
		id=0;
		for(Movie m : mList) {
			String synopsis, cast, genre, director;
			String directortmp = m.getDirector();
			String synoptmp = m.getSynopsis();
			String casttmp = m.getCast();
			String genretmp = m.getGenre();
			String[] rating;
			System.out.println("Movie: "+m.getMovieTitle());
			director = directortmp.replaceAll(SplitBy, cvsSplitBy);
			System.out.println("Director: "+director);
			cast = casttmp.replaceAll(SplitBy, cvsSplitBy);
			System.out.println("Cast: "+cast);
			genre = genretmp.replaceAll(SplitBy, cvsSplitBy);
			System.out.println("Genre: "+genre);
			System.out.printf("Duration: %d Hours %d Minutes \n", m.getTime().toHoursPart(), m.getTime().toMinutesPart());
			System.out.println("Status: "+m.getStatus());
			System.out.println("Sales: "+m.getSales());
			Review r =rlist.get(id);
			rating = r.getRating().split(SplitBy);
			count =0;
			for(String rates: rating) {
				count++;
			}
			if(count >1) {
				System.out.printf("Rating: %.2f\n",m.getOverallRating());
			}
			else {
				System.out.println("Rating: Nil");
			}
			synopsis = synoptmp.replaceAll(SplitBy, cvsSplitBy);
			System.out.println("Synopsis: "+synopsis);
			System.out.println("");
			id++;
		}
		
	}
	
	/**
	 * This function is to print the selected movie from the list of movie object
	 * @param mList		A list of movie object
	 * @param id		Index of selected movie from list of movie object
	 */
	public static void printMovieList(List<Movie>mList,int id) {
		int count;
		ReviewController file = new ReviewController();
		List<Review> rlist = file.getReviewList();
		for(Movie m : mList) {
			if( mList.get(id).getMovieTitle().equals(m.getMovieTitle())) {
				String synopsis, cast, genre,director;
				String synoptmp = m.getSynopsis();
				String casttmp = m.getCast();
				String genretmp = m.getGenre();
				String directortmp = m.getDirector();
				String[] rating;
				System.out.println("Movie: "+m.getMovieTitle());
				director = directortmp.replaceAll(SplitBy, cvsSplitBy);
				System.out.println("Director: "+director);
				cast = casttmp.replaceAll(SplitBy, cvsSplitBy);
				System.out.println("Cast: "+cast);
				genre = genretmp.replaceAll(SplitBy, cvsSplitBy);
				System.out.println("Genre: "+genre);
				System.out.printf("Duration: %d Hours %d Minutes \n", m.getTime().toHoursPart(), m.getTime().toMinutesPart());
				System.out.println("Status: "+m.getStatus());
				System.out.println("Sales: "+m.getSales());
				Review r =rlist.get(id);
				rating = r.getRating().split(SplitBy);
				count =0;
				for(String rates: rating) {
					count++;
				}
				if(count >1) {
					System.out.printf("Rating: %.2f\n",m.getOverallRating());
				}
				else {
					System.out.println("Rating: Nil");
				}
				synopsis = synoptmp.replaceAll(SplitBy, cvsSplitBy);
				System.out.println("Synopsis: "+synopsis);
				System.out.println("");
				break;
			}
		}
	}
	/**
	 * This function is to print the selected movie from the list of movie object
	 * @param m		Movie object
	 */
	public static void printMovieList(Movie m) {
			String synopsis, cast, genre, director;
			String synoptmp = m.getSynopsis();
			String casttmp = m.getCast();
			String genretmp = m.getGenre();
			String directortmp = m.getDirector();
			System.out.println("Movie: "+m.getMovieTitle());
			director = directortmp.replaceAll(SplitBy, cvsSplitBy);
			System.out.println("Director: "+director);
			cast = casttmp.replaceAll(SplitBy, cvsSplitBy);
			System.out.println("Cast: "+cast);
			genre = genretmp.replaceAll(SplitBy, cvsSplitBy);
			System.out.println("Genre: "+genre);
			System.out.printf("Duration: %d Hours %d Minutes \n", m.getTime().toHoursPart(), m.getTime().toMinutesPart());
			System.out.println("Status: "+m.getStatus());
			System.out.println("Sales: "+m.getSales());
			System.out.printf("Rating: %.2f\n",m.getOverallRating());
			synopsis = synoptmp.replaceAll(SplitBy, cvsSplitBy);
			System.out.println("Synopsis: "+synopsis);
	}
	
	/**
	 * This function is to print out all Movie name from the list of movie object
	 * @param movieList		A list of movie object
	 */
	public static void MovieTitle(List<Movie> movieList){
        int i =1;
        for (Movie m : movieList){
    		System.out.println((i++) + ": " + m.getMovieTitle());   
        }
    }

	
	/**
	 * This function is to print out reviews from the selected movie title
	 * @param rList		A list of review object
	 * @param id		selected object index
	 */
	public static void printReview(List<Review> rList, int id) {
		int count, count2;
		for(Review r: rList) {
			if(rList.get(id).getMovieTitle().equals(r.getMovieTitle())) {
				String review;
				String reviewtmp = r.getReview();
				review = reviewtmp.replaceAll(SplitByColon, cvsSplitBy);
				String[] ratings = r.getRating().split(SplitBy);
				String[] reviews = review.split(SplitBy);
				System.out.println(r.getMovieTitle()+" Reviews");
				count = 0;
				for(int i=1; i<ratings.length;i++) {
					
					if(ratings[i].equals("null")) {
						System.out.print((count+1)+". NIL");
					}
					else if(ratings[i].equals("")) {
						System.out.print((count+1)+". ");
						System.out.print("NIL,");
					}
					else {
						System.out.print((count+1)+". ");
						System.out.print(ratings[i]+"/5,");
						
					}
					count++;
					count2 =0;
						for(int k=1; k<reviews.length;k++) {
							if((count-1) == count2) {
								if(reviews[i].equals("null")) {
									System.out.println(",NIL");
								}
								else if(reviews[i].equals("")){
									System.out.println("NIL");
									break;
								}
								else {
									System.out.println(reviews[i]);
									break;
								}
							}
							count2++;
						}
				}	
			}
		}
	}
	
//	/*
//	 *  This function is to print out reviews from the seleted movie title
//	 *  name is movie title 
//	 */
//	// will remove if not used
//	public static void printReview(List<Review> rList, String name) {
//		for(Review r: rList) {
//			if(r.getMovieTitle().equals(name)) {
//				String review;
//				String reviewtmp = r.getReview();
//				review = reviewtmp.replaceAll(SplitByColon, cvsSplitBy);
//				String[] reviews = review.split(SplitBy);
//				System.out.println("Reviews");
//				for(String rev: reviews) {
//					if(rev.equals("null")) {
//						
//					}
//					else {
//						System.out.println(rev);
//					}
//				}
//			}
//		}
//	}
	
}
