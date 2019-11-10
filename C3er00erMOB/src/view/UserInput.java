package view;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;

import controller.PrintMovieList;
import controller.ReviewController;
import entity.Movie;
import entity.MovieStatus;
import entity.Review;
public class UserInput {
	static String cvsSplitBy = ",";
	static String SplitBy =";";
	static String SplitByColon = ":";
	static String rating;
	/*
	 * The function allow user to write review and give rating to the movie they
	 * select userReview is called to handle the processing and updating of reviews
	 * & rating
	 */
	public static void userInputReview(List<Movie> mList, List<Review> rList) {
		String review, reviewtmp;
		int choice = 0,i=1;
		double rate;
		boolean check = true, checkm = true;
		System.out.println("\nSelect Movie by its ID to review");
		for(Review r:rList) {
			System.out.print("ID: "+i);
			for(Movie m:mList) {

				if(rList.get((i-1)).getMovieTitle().equals(m.getMovieTitle())) {
					System.out.println(" Movie: "+m.getMovieTitle());
					break;
				}
			}
			i++;
		}
		Scanner sc = new Scanner(System.in);
		while(checkm) {
			if(sc.hasNextInt()) {
				choice = (sc.nextInt()-1);
				if(choice>=0 && choice<((i-1))) {
					checkm = false;
				}
				else {
					System.out.println("Enter valid movie ID");
					sc.nextLine();
				}
			}
			else {
				System.out.println("Enter valid movie ID");
				sc.nextLine();
			}
		}
		
		for(Review r:rList) {
			if( rList.get(choice).getMovieTitle().equals(r.getMovieTitle())) {
				System.out.println("Give Rating");
				sc.nextLine();
				while(check) {
					try {
						if(sc.hasNextDouble()) {
							rating = sc.nextLine();
							rate = Double.parseDouble(rating);
							if(rate>=0 && rate <=5) {
								check = false;
								break;
							}
							else {
								System.out.println("Enter Rating between 0 to 5");
							}
						}
						else {
							System.out.println("Enter Rating between 0 to 5");
							sc.nextLine();
						}	
					}catch(NumberFormatException e) {
						System.out.println("Enter Rating between 0 to 5");
					}					
				}
				System.out.println("Give Review");
				reviewtmp = sc.nextLine();
				review = reviewtmp.replaceAll(cvsSplitBy, SplitByColon);
				ReviewController.userReview(rList, r.getMovieTitle(), rating, review);
				break;
			}
		}
	}
	
	/*
	 * This function is to allow user to get movie details and reviews
	 * printMovieList and printReview is called to print the selected details
	 */
	
	
	// if nvr use will remove
	public static void userGetMovie(List<Movie> mList, List<Review> rList) {
		int id, count;
		System.out.println("\nSelect Movie ID to show movie detail");
		Scanner sc = new Scanner(System.in);
		count = 1;
		for(Movie m: mList) {
			System.out.println("ID: "+count+" Movie: "+m.getMovieTitle());
			count++;
		}
		id = sc.nextInt();
		PrintMovieList.printMovieList(mList, id);
		PrintMovieList.printReview(rList, id);
	}
	
	public static void userGetMovie(List<Movie> mList) {
		ReviewController file = new ReviewController();
		List<Review> rList = file.getReviewList();
		int id, count;
		boolean check = true;
		System.out.println("\nSelect Movie ID to show movie detail");
		Scanner sc = new Scanner(System.in);
		count = 1;
		for(Movie m: mList) {
			System.out.println("ID: "+count+" Movie: "+m.getMovieTitle());
			count++;
		}
		while(check) {
			if(sc.hasNextInt()) {
				id = (sc.nextInt()-1);
				if(id<(count-1)&& id>=0) {
					PrintMovieList.printMovieList(mList, id);
					PrintMovieList.printReview(rList, id);
					check = false;
				}
				else {
					System.out.println("Invalid Input");
					sc.nextLine();
				}
			}
			else {
				System.out.println("Invalid Input");
				sc.nextLine();
			}
		}
		
		
	}
	
	public static void userGetReview(List<Movie> mList) {
		ReviewController file = new ReviewController();
		List<Review> rList = file.getReviewList();
		int id, count;
		boolean check = true;
		System.out.println("\nSelect Movie ID to show movie detail");
		Scanner sc = new Scanner(System.in);
		count = 1;
		for(Movie m: mList) {
			System.out.println("ID: "+count+" Movie: "+m.getMovieTitle());
			count++;
		}
		while(check) {
			if(sc.hasNextInt()) {
				id = (sc.nextInt()-1);
				if(id<(count-1) && id>=0) {
					PrintMovieList.printMovieList(mList, id);
					PrintMovieList.printReview(rList, id);
					System.out.println();
					check = false;
				}
				else{
					System.out.println("Invalid Input");
					sc.nextLine();
				}
			}
			else {
				System.out.println("Invalid Input");
				sc.nextLine();
			}
		}
	}
	
	/**
	 * This is the view page for top 5 movies
	 * @param mList	A list of movies
	 */
	public static void top5Movies(List<Movie> mList, List<Review> rList) {
		DecimalFormat df = new DecimalFormat("0.0");
		Scanner sc = new Scanner(System.in);
		System.out.println("\n====================");
		System.out.println("1. Top 5 Movies by Sales: ");
		System.out.println("2. Top 5 Movies by Ratings");
		System.out.println("====================");
		System.out.print("Choice: ");
		int choice = sc.nextInt();
		if(choice<1 || choice >2) {
			System.out.println("Invalid Choice!");
			return;
		}
		
		switch (choice) {
			case 1:
				int j=1;
				TreeMap<Integer,String> movieList = new TreeMap<Integer,String>(Collections.reverseOrder());
				for (Movie m: mList) {
					if(!(m.getStatus().equals(MovieStatus.END_OF_SHOWING)||m.getStatus().equals(MovieStatus.UP_COMING))) {
						movieList.put(m.getSales(), m.getMovieTitle());
					}
				}
				System.out.println("=====Top 5 Movies by Sales=====");
				if(movieList.size()>5) {
					for(;j<6;j++) {
						System.out.println(j+". "+movieList.firstEntry().getValue()+" [Ticket Sales:"+movieList.firstEntry().getKey()+"]");
						movieList.remove(movieList.firstEntry().getKey());
					}
				}
				else {
					for (Entry<Integer, String> movie : movieList.entrySet()) {
					    System.out.println(j+". "+movie.getValue()+" [Ticket Sales:"+movie.getKey()+"]");
					    j++;
					}
				}
				break;
				
			case 2:
				TreeMap<Double,String> ratingList = new TreeMap<Double,String>(Collections.reverseOrder());
				for (Review r: rList) {
					String[] noOfratings = r.getRating().split(";");
					if(noOfratings.length>1) {
						ratingList.put(r.getOverallRating(),r.getMovieTitle());
					}
				}
				System.out.println("=====Top 5 Movies by Ratings=====");
				int i = 1;
				if(ratingList.size()>5) {
					for(;i<6;i++) {
						System.out.println(i+". "+ratingList.firstEntry().getValue()+" [Overall Rating:"+ratingList.firstEntry().getKey()+"]");
						ratingList.remove(ratingList.firstEntry().getKey());
					}
				}
				else {
					for (Entry<Double, String> movie : ratingList.entrySet()) {
					    System.out.println(i+". "+movie.getValue()+" [Overall Rating:"+df.format(movie.getKey())+"]");
					    i++;
					}
				}
				break;
		}
	}
}
