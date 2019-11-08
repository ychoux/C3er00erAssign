package controller;


import java.time.Duration;
import java.util.List;

import entity.Movie;
import entity.Review;

public class PrintMovieList {
	static String SplitBy = ";";
	static String cvsSplitBy = ",";
	static String SplitByColon = ":";
	static int count;
	/*
	 *  This function is to print out all the movies 
	 */
	
	public static void printAllMovieList(List<Movie>mList){	
		for(Movie m : mList) {
			String synopsis, cast, genre, director;
			String directortmp = m.getDirector();
			String synoptmp = m.getSynopsis();
			String casttmp = m.getCast();
			String genretmp = m.getGenre();
			System.out.println("Movie: "+m.getMovieTitle());
			director = directortmp.replaceAll(SplitBy, cvsSplitBy);
			System.out.println("Director: "+m.getDirector());
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
		
	}
	
	
	/*
	 *  This function is to print out selected movie from the movielist
	 */
	
	public static void printMovieList(List<Movie>mList,int id) {
		for(Movie m : mList) {
			if( mList.get(id).getMovieTitle().equals(m.getMovieTitle())) {
				String synopsis, cast, genre;
				String synoptmp = m.getSynopsis();
				String casttmp = m.getCast();
				String genretmp = m.getGenre();
				System.out.println("Movie: "+m.getMovieTitle());
				System.out.println("Director: "+m.getDirector());
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
				break;
			}
			
		}
	}
	
	public static void printAllReview(List<Review> rList) {
		for(Review r: rList) {
			String review;
			String reviewtmp = r.getReview();
			review = reviewtmp.replaceAll(SplitByColon, cvsSplitBy);
			String[] reviews = review.split(SplitBy);
			System.out.println("Reviews");
			count =1;
			for(String rev: reviews) {
				if(rev.equals("null")) {
					
				}
				else {
					System.out.println(count+". "+rev);
					count++;
				}
			}
		}
	}
	/*
	 *  This function is to print out reviews from the seleted movie title
	 *  id is object position
	 */
	
	public static void printReview(List<Review> rList, int id) {
		for(Review r: rList) {
			if(rList.get(id).getMovieTitle().equals(r.getMovieTitle())) {
				String review;
				String reviewtmp = r.getReview();
				review = reviewtmp.replaceAll(SplitByColon, cvsSplitBy);
				String[] reviews = review.split(SplitBy);
				System.out.println(r.getMovieTitle()+" Reviews");
				count = 1;
				for(String rev: reviews) {
					if(rev.equals("null")) {
						
					}
					else {
						System.out.println(count+". "+rev);
						count++;
					}
				}
				
				
			}
		}
	}
	
	/*
	 *  This function is to print out reviews from the seleted movie title
	 *  name is movie title 
	 */
	
	public static void printReview(List<Review> rList, String name) {
		for(Review r: rList) {
			if(r.getMovieTitle().equals(name)) {
				String review;
				String reviewtmp = r.getReview();
				review = reviewtmp.replaceAll(SplitByColon, cvsSplitBy);
				String[] reviews = review.split(SplitBy);
				System.out.println("Reviews");
				count =1;
				for(String rev: reviews) {
					if(rev.equals("null")) {
						
					}
					else {
						System.out.println(count+". "+rev);
						count++;
					}
				}
			}
		}
	}
	

}
