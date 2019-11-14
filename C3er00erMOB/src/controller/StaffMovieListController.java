package controller;
import java.time.Duration;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import entity.Movie;
import entity.MovieStatus;
import entity.Review;

/**
 * This class is for CRUD functions related to Movies
 * @author 
 *
 */
public class StaffMovieListController {
	static String cvsSplitBy = ",";
	static String SplitBy = ";";
	static String SplitByColon = ":";

	/**
	 * This function allow staff to add movie into movielist
	 * addMovieList and addReviewList function will be called at the end of the 
	 * function to ensure that both movielist and review csv is updated
	 * @param mList	A list of movies
	 * @param rList	A list of reviews
	 * @return 		A boolean variable that indicates whether the operation is successful or not
	 */
	public static boolean staffAddMovie(List<Movie> mList, List<Review> rList) {
		MovieStatus status = null;
		String name,synopsis, director, cast, genre, synopsistmp, directortmp, casttmp, genretmp, nametmp;
		int hr, min, choice;
		Duration time = null;
		boolean check = true;
		double rating = 0.0;
		int sale = 0;
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter Movie Name: ");
		nametmp = sc.nextLine();
		if(mList.stream().filter(o -> o.getMovieTitle().equalsIgnoreCase(nametmp)).findFirst().isPresent()) {
			System.out.println("Movie Already Exists!");
			return false;
		}
		name = nametmp.replaceAll(cvsSplitBy, SplitByColon);
		System.out.print("Enter Movie Synopsis: ");
		synopsistmp = sc.nextLine();
		synopsis= synopsistmp.replaceAll(cvsSplitBy, SplitBy);
		System.out.print("Enter Director Name: ");
		directortmp = sc.nextLine();
		director = directortmp.replaceAll(cvsSplitBy, SplitBy);
		System.out.print("Enter Cast Name: ");
		casttmp = sc.nextLine();
		cast = casttmp.replaceAll(cvsSplitBy, SplitBy);
		System.out.print("Enter Movie Genres: ");
		genretmp = sc.nextLine();
		genre = genretmp.replaceAll(cvsSplitBy, SplitBy);
		System.out.println("Movie Duration");
		while(check) {
			try {
				System.out.print("How many Hours (0 to 24): ");
				hr = sc.nextInt();
				System.out.print("How many Minutes (0 to 59): ");
				min = sc.nextInt();
				if((0<=hr && hr<=24) && (0<=min && min<=59)) {
					time = Duration.parse("PT"+hr+"H"+min+"M");
					check = false;	
				}
				else {
					System.out.println("Enter time again in correct format!");	
				}
			}catch(InputMismatchException e) {
				System.out.println("Invalid input.");
				sc.reset();
				sc.next();
			}
		}
		System.out.println("Choose Movie Status");
		System.out.println("1: NOW_SHOWING");
		System.out.println("2: UP_COMING");
		choice = sc.nextInt();
		switch(choice) {
		case 1:{
			status = MovieStatus.NOW_SHOWING;
			break;
		}
		case 2:{
			status = MovieStatus.UP_COMING;
			break;
		}
		}

		//NAME	SYNOPSIS	DIRECTOR	CAST	GENRE	TIME	STATUS	SALES	RATING
		if(MovieListController.addMovieList(mList,name,synopsis,director,cast,genre, time, status ,sale,rating) && 
				ReviewController.addReviewList(rList, name))
			return true;
		return false;
	}

	/**
	 * This function allows staff to update the status of the movie
	 * @param mList	A list of movies
	 * @return 		A boolean variable that indicates whether the operation is successful or not
	 */
	public static boolean staffUpdateStatus(List<Movie> mList) {
		Scanner sc = new Scanner(System.in);
		for(int i=0; i<mList.size(); i++) {
			System.out.println(i+1 +". " + mList.get(i).getMovieTitle()+" ["+mList.get(i).getStatus().toString()+"]");
		}
		System.out.print("Choose Movie: ");
		int choice = sc.nextInt()-1;
		if(choice >= 0 && choice < mList.size()) {
			System.out.println("Pick status to update: ");
			System.out.println("1: NOW_SHOWING");
			System.out.println("2: END_OF_SHOWING");
			while(true) {
				try {
					System.out.println("Choose Status: ");
					int status = sc.nextInt();
					switch(status) {
						case 1:
							if(mList.get(choice).getStatus() == MovieStatus.NOW_SHOWING) {
								System.out.println("Movie is already NOW_SHOWING!");
								return false;
							}
							mList.get(choice).setStatus(MovieStatus.NOW_SHOWING);
							break;
							
						case 2:
							if(mList.get(choice).getStatus() == MovieStatus.END_OF_SHOWING) {
								System.out.println("Movie is already END_OF_SHOWING!");
								return false;
							}
							mList.get(choice).setStatus(MovieStatus.END_OF_SHOWING);
							break;
							
						default:
							System.out.println("Invalid option!");
							return false;
					}

					return MovieListController.updateMovieListCSV(mList);
				}catch(InputMismatchException e) {
					System.out.println("Invalid input.");
					sc.reset();
					sc.next();
				}
			}
		}
		else{
			System.out.println("Invalid movie choice!");
			System.out.println("====================");
			staffUpdateStatus(mList);
		}
		return false;
	}
}
