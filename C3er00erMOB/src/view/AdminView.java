package view;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

import controller.*;
import entity.*;
import login.AccessLevel;
import login.AdminSession;

/**
 * This class is for selecting all Admin and Superadmin settings
 * 
 * @author
 *
 */
public class AdminView {

	/**
	 * This is the view page for movie settings
	 * 
	 * @param admSess Passes in an authenticated admin session
	 * @param choice  For recursion checking
	 */
	public static void movieSettings(AdminSession admSess, int choice) {
		int option = 0;
		Scanner sc = new Scanner(System.in);
		try {
			if (choice == 0) {
				System.out.println("====================");
				System.out.println("1. Create Movie");
				System.out.println("2. Update Movie Status");
				System.out.println("3. Manage Reviews");
				System.out.println("4. Show Top 5 Movies");
				System.out.println("5. Back");
				System.out.println("====================");
				System.out.print("Select task: ");
				option = sc.nextInt();
				if (!(option >= 1 && option <= 5)) {
					System.out.println("Invalid Choice! Try again.");
					movieSettings(admSess, 0);
				}
			} else {
				option = choice;
			}
		} catch (InputMismatchException e) {
			System.out.println("Invalid input.");
			movieSettings(admSess, 0);
		}

		MovieListController mlCon = new MovieListController();
		ReviewController rCon = new ReviewController();
		switch (option) {
		case 1:
			if (StaffMovieListController.staffAddMovie(mlCon.getMovieList(), rCon.getReviewList())) {
				System.out.println("Movie created!");
				LoggerController.getInstance().LogChangeEntry(admSess.getUsername(), "Movie Added.");
			} else {
				System.out.println("Failed to create movie!");
				LoggerController.getInstance().LogErrorEntry(admSess.getUsername(), "Failed to add Movie.");
			}
			break;
		case 2:
			if (StaffMovieListController.staffUpdateStatus(mlCon.getMovieList())) {
				System.out.println("Status updated!");
				LoggerController.getInstance().LogChangeEntry(admSess.getUsername(), "Movie Status Updated.");
			} else {
				System.out.println("Failed to update status!");
				LoggerController.getInstance().LogErrorEntry(admSess.getUsername(), "Fail to update movie status.");
			}
			break;
		case 3:
			reviewSettings(0);
			break;
		case 4:
			top5Movies(mlCon.getMovieList(), rCon.getReviewList());
			break;
		case 5:
			// clear garbage
			mlCon = null;
			rCon = null;
			return;
		}

		// clear garbage
		mlCon = null;
		rCon = null;

		movieSettings(admSess, 0);
	}

	/**
	 * This is the view page for cineplex settings
	 * 
	 * @param admSess Passes in an authenticated admin session
	 * @param choice  For recursion checking
	 */
	public static void cineplexSettings(AdminSession admSess, int choice) {
		int option = 0;
		Scanner sc = new Scanner(System.in);
		try {
			if (choice == 0) {
				System.out.println("====================");
				System.out.println("1. Create ShowTime");
				System.out.println("2. Back");
				System.out.println("====================");
				System.out.print("Select task: ");
				option = sc.nextInt();
				if (!(option >= 1 && option <= 2)) {
					System.out.println("Invalid Choice! Try again.");
					cineplexSettings(admSess, 0);
				}
			} else {
				option = choice;
			}
		} catch (InputMismatchException e) {
			System.out.println("Invalid input.");
			cineplexSettings(admSess, 0);
		}

		StaffSlotController ssCon = new StaffSlotController();
		MovieListController mlCon = new MovieListController();
		switch (option) {
		case 1:
			if (ssCon.staffAddSlot(mlCon.getMovieList())) {
				System.out.println("Showtime creation successful!");
				LoggerController.getInstance().LogChangeEntry(admSess.getUsername(), "Showtime creation successful.");
			} else {
				System.out.println("Showtime creation failed!");
				LoggerController.getInstance().LogErrorEntry(admSess.getUsername(), "Showtime creation failed.");
			}
			break;
		case 2:
			ssCon = null;
			mlCon = null;
			return;
		}
	}

	/**
	 * This is the view page for price settings
	 * 
	 * @param admSess Passes in an authenticated admin session
	 * @param choice  For recursion checking
	 */
	public static void priceSettings(AdminSession admSess, int choice) {
		int option = 0;
		Scanner sc = new Scanner(System.in);
		try {
			if (choice == 0) {
				System.out.println("====================");
				System.out.println("1. Manage Rates");
				System.out.println("2. Manage Public Holidays");
				System.out.println("3. Back");
				System.out.println("====================");
				System.out.print("Select task: ");
				option = sc.nextInt();
				if (!(option >= 1 && option <= 3)) {
					System.out.println("Invalid Choice! Try again.");
					priceSettings(admSess, 0);
				}
			} else {
				option = choice;
			}
		} catch (InputMismatchException e) {
			System.out.println("Invalid input.");
			priceSettings(admSess, 0);
		}

		switch (option) {
		case 1:
			ratesSetting(admSess, 0);
			break;
		case 2:
			phSetting(admSess, 0);
			break;
		case 3:
			return;
		}
	}

	/**
	 * This is the view page for public holiday settings
	 * 
	 * @param admSess Passes in an authenticated admin session
	 * @param choice  For recursion checking
	 */
	public static void phSetting(AdminSession admSess, int choice) {
		int option = 0;
		Scanner sc = new Scanner(System.in);
		try {
			if (choice == 0) {
				System.out.println("====================");
				System.out.println("1. Show Public Holidays");
				System.out.println("2. Add Public Holidays");
				System.out.println("3. Back");
				System.out.println("====================");
				System.out.print("Select task: ");
				option = sc.nextInt();
				if (!(option >= 1 && option <= 3)) {
					System.out.println("Invalid Choice! Try again.");
					ratesSetting(admSess, 0);
				}
			} else {
				option = choice;
			}
		} catch (InputMismatchException e) {
			System.out.println("Invalid input.");
			phSetting(admSess, 0);
		}
		
		switch (option) {
		case 1:
			StaffPriceController.staffShowPh();
			break;
		case 2:
			if (StaffPriceController.staffAddPh()) {
				System.out.println("Public Holiday creation successful!");
				LoggerController.getInstance().LogChangeEntry(admSess.getUsername(),
						"Public Holiday creation successful.");
			} else {
				System.out.println("Public Holiday creation failed!");
				LoggerController.getInstance().LogChangeEntry(admSess.getUsername(), "Public Holiday creation failed.");
			}
			break;
		case 3:
			return;
		}
	}

	/**
	 * This is the view page for rates settings
	 * 
	 * @param admSess Passes in an authenticated admin session
	 * @param choice  For recursion checking
	 */
	public static void ratesSetting(AdminSession admSess, int choice) {
		int option;
		Scanner sc = new Scanner(System.in);
		if (choice == 0) {
			System.out.println("====================");
			System.out.println("1. Show Rates");
			System.out.println("2. Create Rates");
			System.out.println("3. Update Rates");
			System.out.println("4. Back");
			System.out.println("====================");
			System.out.print("Select task: ");
			option = sc.nextInt();
			if (!(option >= 1 && option <= 4)) {
				System.out.println("Invalid Choice! Try again.");
				ratesSetting(admSess, 0);
			}
		} else {
			option = choice;
		}
		switch (option) {
		case 1:
			StaffPriceController.staffShowRates();
			break;
		case 2:
			if (StaffPriceController.staffAddRates()) {
				System.out.println("Rates creation successful!");
				LoggerController.getInstance().LogChangeEntry(admSess.getUsername(), "Rates creation successful.");
			} else {
				System.out.println("Rates creation failed!");
				LoggerController.getInstance().LogErrorEntry(admSess.getUsername(), "Rates creation failed.");
			}
			break;
		case 3:
			if (StaffPriceController.staffUpdateRates()) {
				System.out.println("Rates updated successfully!");
				LoggerController.getInstance().LogChangeEntry(admSess.getUsername(), "Rates updated successfully.");
			} else {
				System.out.println("Rates updated failed!");
				LoggerController.getInstance().LogErrorEntry(admSess.getUsername(), "Rates updated failed.");
			}
			break;
		case 4:
			return;
		}
	}

	/**
	 * This is the view page for user settings
	 * 
	 * @param admSess Passes in an authenticated admin session
	 * @param aCon    Passes in a admin controller to access methods for user
	 *                settings.
	 */
	public static void userSettings(AdminSession admSess, AdminController aCon) {
		Scanner sc = new Scanner(System.in);
		System.out.println("1. Change Password");
		System.out.println("2. Create user");
		System.out.println("3. Delete user");
		System.out.println("4. Unlock user");
		System.out.println("5. Back");
		int choice = sc.nextInt();
		switch (choice) {
		case 1:
			if (aCon.updateAdminUserPassword(admSess)) {
				System.out.println("Password changed.");
				LoggerController.getInstance().LogSecurityEntry(admSess.getUsername(), "Password change successfully");
			} else {
				LoggerController.getInstance().LogErrorEntry(admSess.getUsername(),
						"Wrong password was used to change password");
			}
			break;
		case 2:
			if (admSess.getAccesslevel() == AccessLevel.SUPERADMIN) {
				if (aCon.makeAdminUser()) {
					LoggerController.getInstance().LogChangeEntry(admSess.getUsername(), "New user has been created");
					System.out.println("User created!");
				} else {
					LoggerController.getInstance().LogErrorEntry(admSess.getUsername(), "New user creation failed");
					System.out.println("Failed to create new user.");
				}
				break;
			} else {
				System.out.println("No permission");
				LoggerController.getInstance().LogErrorEntry(admSess.getUsername(),
						"Failed to create user due to lacking privileges.");
				break;
			}
		case 3:
			if (admSess.getAccesslevel() == AccessLevel.SUPERADMIN) {
				if (aCon.deleteAdminUser()) {
					LoggerController.getInstance().LogChangeEntry(admSess.getUsername(), "User removed successfully");
					System.out.println("User has been removed.");
				} else {
					LoggerController.getInstance().LogNormalEntry(admSess.getUsername(), "User removed failed.");
					System.out.println("Task abort..........");
				}
			} else {
				System.out.println("No permission");
			}
			break;
		case 4:
			if (admSess.getAccesslevel() == AccessLevel.SUPERADMIN) {
				if (aCon.unlockAdminUser()) {
					LoggerController.getInstance().LogSecurityEntry(admSess.getUsername(),
							"User has been unlocked successfully");
					System.out.println("User has been unlocked.");
				} else {
					LoggerController.getInstance().LogErrorEntry(admSess.getUsername(), "User was not unlocked");
					System.out.println("Update error occured.");
				}
			} else {
				System.out.println("No permission");
				LoggerController.getInstance().LogErrorEntry(admSess.getUsername(),
						"Failed to create user due to lacking privileges.");
			}
			break;

		case 5:
			return;
		}
	}

	/**
	 * This is the view page for review settings
	 * 
	 * @param choice For recursion checking
	 */
	public static void reviewSettings(int choice) {
		DecimalFormat df = new DecimalFormat("0.0");
		int option = 0;
		Scanner sc = new Scanner(System.in);
		try {
			if (choice == 0) {
				System.out.println("====================");
				System.out.println("1. Show Movie Reviews & Ratings");
				System.out.println("2. Show Overall Ratings");
				System.out.println("3. Back");
				System.out.println("====================");
				System.out.print("Select task: ");
				option = sc.nextInt();
				if (!(option >= 1 && option <= 3)) {
					System.out.println("Invalid Choice! Try again.");
					reviewSettings(0);
				}
			} else {
				option = choice;
			}
		} catch (InputMismatchException e) {
			System.out.println("Invalid input.");
			reviewSettings(0);
		}

		ReviewController rCon = new ReviewController();
		List<Review> rList = rCon.getReviewList();

		switch (option) {
		case 1:
			for (Review r : rList) {
				System.out.println("--------" + r.getMovieTitle() + "--------");
				String[] noOfratings = r.getRating().split(";");

				if (noOfratings.length > 0) {
					System.out.println("All Ratings: " + Arrays.toString(noOfratings));
					if (noOfratings.length < 2) {
						System.out.println("Overall Rating: [NA]");
					} else {
						System.out.println("Overall Rating: [" + df.format(r.getOverallRating()) + "]");
					}
				} else {
					System.out.println("All Ratings: [No Ratings]");
					System.out.println("Overall Rating: [No Overall Rating]");
				}
				String[] noOfreviews = r.getReview().split(";");
				for(int i=0; i<noOfreviews.length;i++) {
					if(noOfreviews[i].isEmpty())
						noOfreviews[i]="NA";
				}
				
				if (noOfreviews.length > 0) {
					System.out.println("Reviews: " + Arrays.toString(noOfreviews));
				} else {
					System.out.println("Reviews: [No Reviews]");
				}
				System.out.println("");
			}
			break;
		case 2:
			System.out.println("=====Overall Ratings=====");
			int i = 1;
			for (Review r : rList) {
				String[] noOfratings = r.getRating().split(";");
				if(noOfratings.length<2) {
					System.out.println(i + ". " + r.getMovieTitle() + " [Overall ratings: NA]");
				}
				else {
					System.out.println(i + ". " + r.getMovieTitle() + " [Overall ratings:" + df.format(r.getOverallRating()) + "]");
				}
				i++;
			}
			break;
		case 3:
			return;
		}
	}

	/**
	 * This is the view page for top 5 movies
	 * 
	 * @param mList A list of movies
	 * @param rList A list of reviews
	 */
	private static void top5Movies(List<Movie> mList, List<Review> rList) {
		DecimalFormat df = new DecimalFormat("0.0");
		Scanner sc = new Scanner(System.in);
		int choice = 0;
		try {
			System.out.println("====================");
			System.out.println("1. Top 5 Movies by Sales: ");
			System.out.println("2. Top 5 Movies by Ratings");
			System.out.println("====================");
			System.out.print("Choice: ");
			choice = sc.nextInt();
			if (choice < 1 || choice > 2) {
				System.out.println("Invalid Choice!");
				top5Movies(mList, rList);
			}
		} catch (InputMismatchException e) {
			System.out.println("Invalid input.");
			sc.reset();
			sc.next();
			top5Movies(mList, rList);
		}

		switch (choice) {
		case 1:
			int j = 1;
			TreeMap<Integer, String> movieList = new TreeMap<Integer, String>(Collections.reverseOrder());
			for (Movie m : mList) {
				if (!(m.getStatus().equals(MovieStatus.END_OF_SHOWING)
						|| m.getStatus().equals(MovieStatus.UP_COMING))) {
					movieList.put(m.getSales(), m.getMovieTitle());
				}
			}
			System.out.println("=====Top 5 Movies by Sales=====");
			if (movieList.size() > 5) {
				for (; j < 6; j++) {
					System.out.println(j + ". " + movieList.firstEntry().getValue() + " [Ticket Sales:"
							+ movieList.firstEntry().getKey() + "]");
					movieList.remove(movieList.firstEntry().getKey());
				}
			} else {
				for (Entry<Integer, String> movie : movieList.entrySet()) {
					System.out.println(j + ". " + movie.getValue() + " [Ticket Sales:" + movie.getKey() + "]");
					j++;
				}
			}
			break;

		case 2:
			TreeMap<Double, String> ratingList = new TreeMap<Double, String>(Collections.reverseOrder());
			for (Review r : rList) {
				String[] noOfratings = r.getRating().split(";");
				if (noOfratings.length > 1) {
					ratingList.put(r.getOverallRating(), r.getMovieTitle());
				}
			}
			System.out.println("=====Top 5 Movies by Ratings=====");
			int i = 1;
			if (ratingList.size() > 5) {
				for (; i < 6; i++) {
					System.out.println(i + ". " + ratingList.firstEntry().getValue() + " [Overall Rating:"
							+ df.format(ratingList.firstEntry().getKey()) + "]");
					ratingList.remove(ratingList.firstEntry().getKey());
				}
			} else {
				for (Entry<Double, String> movie : ratingList.entrySet()) {
					System.out.println(
							i + ". " + movie.getValue() + " [Overall Rating:" + df.format(movie.getKey()) + "]");
					i++;
				}
			}
			break;
		}
	}

	/**
	 * This is the view page for user settings
	 * 
	 * @param choice For recursion checking
	 */
	public static void logAnalysis(int choice) {
		int option;
		Scanner sc = new Scanner(System.in);
		if (choice == 0) {
			System.out.println("====================");
			System.out.println("1. Show logs");
			System.out.println("2. Search logs");
			System.out.println("3. Back");
			System.out.println("====================");
			System.out.print("Select task: ");
			option = sc.nextInt();
			if (!(option >= 1 && option <= 3)) {
				System.out.println("Invalid Choice! Try again.");
				logAnalysis(0);
			}
		} else {
			option = choice;
		}

		switch (option) {
		case 1:
			List<Log> logList = LoggerController.getInstance().getLogList();
			for (Log l : logList) {
				System.out.println(l.username + " , " + l.description + " , " + l.loglvl + " , " + l.datetime);
			}
			break;
		case 2:
			while (true) {
				System.out.println("====================");
				System.out.println("[-1 to exit]");
				System.out.println("Enter keyword: ");
				String keyword = sc.next();

				if (!keyword.equalsIgnoreCase("-1")) {
					List<Log> fLogList = LoggerController.getInstance().getLogList(keyword);
					for (Log l : fLogList) {
						System.out.println(l.username + " , " + l.description + " , " + l.loglvl + " , " + l.datetime);
					}
				} else {
					break;
				}
			}
			break;
		}
	}
}
