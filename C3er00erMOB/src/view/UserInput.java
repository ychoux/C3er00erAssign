package view;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import controller.PrintMovieList;
import controller.ReviewController;
import entity.Movie;
import entity.Review;
public class UserInput {
	static String cvsSplitBy = ",";
	static String SplitBy =";";
	static String SplitByColon = ":";
	
	/*
	 * The function allow user to write review and give rating to the movie they
	 * select userReview is called to handle the processing and updating of reviews
	 * & rating
	 */
	public static void userInputReview(List<Movie> mList, List<Review> rList) {
		String rating, review, reviewtmp;
		int i=0;
		int select;
		System.out.println("Select Movie by its ID to review");
		for(Review r:rList) {
			System.out.print("ID: "+i);
			for(Movie m:mList) {
				if(rList.get(i).getMovieTitle().equals(m.getMovieTitle())) {
					System.out.println(" Movie: "+m.getMovieTitle());
					break;
				}
			}
			i++;
		}
		Scanner sc = new Scanner(System.in);
		try {
			select = sc.nextInt();
			for(Review r:rList) {
				if( rList.get(select).getMovieTitle().equals(r.getMovieTitle())) {
					System.out.println("Give Rating");
					sc.nextLine();
					rating = sc.nextLine();
					System.out.println("Give Review");
					reviewtmp = sc.nextLine();
					review = reviewtmp.replaceAll(cvsSplitBy, SplitByColon);
					ReviewController.userReview(rList, r.getMovieTitle(), rating, review);
					break;
				}
			}
		}
		catch(InputMismatchException e) {
			System.out.println("Invalid input try again please key in Movie ID");
		}
	}
	
	/*
	 * This function is to allow user to get movie details and reviews
	 * printMovieList and printReview is called to print the selected details
	 */
	
	public static void userGetMovie(List<Movie> mList, List<Review> rList) {
		int id, count;
		boolean check = true;
		while(check) {
			System.out.println("Select Movie ID to show movie detail");
			Scanner sc = new Scanner(System.in);
			count = 1;
			for(Movie m: mList) {
				System.out.println("ID: "+count+" Movie: "+m.getMovieTitle());
				count++;
			}
			try {
				id = (sc.nextInt()-1);
				if(id<(count-1) && id>=0) {
					PrintMovieList.printMovieList(mList, id);
					PrintMovieList.printReview(rList, id);
					check = false;
					break;
				}
				else {
					check = true;
					System.out.println("Invalid input try again");
				}
			}
			catch(InputMismatchException e) {
				System.out.println("Invalid input try again please key in Movie ID");
			}
		}
	}
	
	public static void userGetMovie(List<Movie> mList) {
		ReviewController file = new ReviewController();
		List<Review> rList = file.getReviewList();
		int id, count;
		boolean check = true;
		while(check) {
			System.out.println("Select Movie ID to show movie detail");
			Scanner sc = new Scanner(System.in);
			count = 1;
			for(Movie m: mList) {
				System.out.println("ID: "+count+" Movie: "+m.getMovieTitle());
				count++;
			}
			try {
				id = (sc.nextInt()-1);
				if(id<(count-1) && id>=0) {
					PrintMovieList.printMovieList(mList, id);
					PrintMovieList.printReview(rList, id);
					check = false;
					break;
				}
				else {
					check = true;
					System.out.println("Invalid input try again");
				}
			}
			catch(InputMismatchException e) {
				System.out.println("Invalid input try again please key in Movie ID");
			}
		}
	}
	
	public static void userGetReview(List<Movie> mList) {
		ReviewController file = new ReviewController();
		List<Review> rList = file.getReviewList();
		int id, count;
		boolean check = true;
		while(check) {
			System.out.println("Select Movie ID to show movie detail");
			Scanner sc = new Scanner(System.in);
			count = 1;
			for(Movie m: mList) {
				System.out.println("ID: "+count+" Movie: "+m.getMovieTitle());
				count++;
			}
			try {
				id = (sc.nextInt()-1);
				if(id<(count-1) && id>=0) {
					PrintMovieList.printReview(rList, id);
					check = false;
					break;
				}
				else {
					check = true;
					System.out.println("Invalid input try again");
				}
			}
			catch(InputMismatchException e) {
				System.out.println("Invalid input try again please key in Movie ID");
			}

			
		}
	}
}
