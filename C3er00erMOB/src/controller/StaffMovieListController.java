package controller;
import java.time.Duration;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import entity.Movie;
import entity.MovieStatus;
import entity.Review;

public class StaffMovieListController {
	static String cvsSplitBy = ",";
	static String SplitBy = ";";
	static String SplitByColon = ":";
	static int hour, min;
	/*
	 * This function allow staff to add movie into movielist
	 * addMovieList and addReviewList function will be called at the end of the 
	 * function to ensure that both movielist and review csv is updated
	 */
	
	public static void staffAddMovie(List<Movie> mList, List<Review> rList) {
		MovieStatus status = null;
		String name,synopsis, director, cast, genre, synopsistmp, directortmp, casttmp, genretmp, nametmp;
		int choice;
		Duration time = null;
		boolean check2 = true,check = true;
		double rating = 0.0;
		int sale = 0;
		System.out.println("Enter Movie Name");
		Scanner sc = new Scanner(System.in);
		nametmp = sc.nextLine();
		name = nametmp.replaceAll(cvsSplitBy, SplitByColon);
		System.out.println("Enter Movie Synopsis");
		synopsistmp = sc.nextLine();
		synopsis= synopsistmp.replaceAll(cvsSplitBy, SplitBy);
		System.out.println("Enter Director Name");
		directortmp = sc.nextLine();
		director = directortmp.replaceAll(cvsSplitBy, SplitBy);
		System.out.println("Enter Cast Name");
		casttmp = sc.nextLine();
		cast = casttmp.replaceAll(cvsSplitBy, SplitBy);
		System.out.println("Enter Movie Genres");
		genretmp = sc.nextLine();
		genre = genretmp.replaceAll(cvsSplitBy, SplitBy);
		System.out.println("Movie Duration");
		while(check) {
			while(check2) {
				System.out.println("How many Hours (0 to 24)");
				if(sc.hasNextInt()) {
					hour = sc.nextInt();
					System.out.println("How many Minuties (0 to 59)");
					if(sc.hasNextInt()) {
						min = sc.nextInt();
						check2 = false;
					}
					else {
						System.out.println("Invalid input for minutes");
						sc.nextLine();
						sc.nextLine();
					}
				}
				else {
					System.out.println("Invalid input for hour");
					sc.nextLine();
				}
			}
			if((0<=hour && hour<=24) && (0<=min && min<=59)) {
				time = Duration.parse("PT"+hour+"H"+min+"M");
				check = false;	}
				else {
					System.out.println("Enter time again in correct format");
					check2 = true;
					}
		}
		check = true;
		while(check) {
			try {
				System.out.println("Choose Movie Status");
				System.out.println("1: NOW_SHOWING");
				System.out.println("2: UP_COMING");
				choice = sc.nextInt();
				switch(choice) {
					case 1:{
						status = MovieStatus.NOW_SHOWING;
						check = false;
					}
					break;
					case 2:{
						status = MovieStatus.UP_COMING;
						check = false;
					}
					break;
				}
			}
			catch(InputMismatchException e) {
				System.out.println("Invalid input try again please key in Movie ID");
				sc.nextLine();
			}
		}
		
		//NAME	SYNOPSIS	DIRECTOR	CAST	GENRE	TIME	STATUS	SALES	RATING
		MovieListController.addMovieList(mList,name,synopsis,director,cast,genre, time, status ,sale,rating);
		ReviewController.addReviewList(rList, name);
		System.out.println("Movie added");
	}
	
	
	/*
	 * This function allow staff to delete movie from movielist At the end of the
	 * function delMovieList and delReviewList is called to ensure that both
	 * movielist and review csv is updated
	 * the id in the function is the positon of the object in the List of objects
	 */
	
	public static void staffDelMovie(List<Movie> mList, List<Review> rList) {
		int id, count;
		boolean check = true;
		while(check) {
			count =1;
			System.out.println("Select Movie ID to delete movie");
			
			for(Movie m: mList) {
				System.out.println("ID: "+ count+" Movie: "+ m.getMovieTitle());
				count++;
			}
			Scanner sc = new Scanner(System.in);
			try{
				id = (sc.nextInt()-1);
				if(id<(count-1) && id>=0) {
					MovieListController.delMovieList(mList, id);
					ReviewController.delReviewList(rList, id);
					System.out.println("Movie Deleted");
					check = false;
				}
				else {
					System.out.println("Invalid ID try again");
					check = true;
				}
			}
			catch (InputMismatchException e) {
				System.out.println("Invalid ID try again");
				sc.nextLine();
			}
		}
	}
	
}
