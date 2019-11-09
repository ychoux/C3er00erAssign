package view;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

import controller.*;
import entity.*;
import login.AccessLevel;
import login.AdminSession;

/**
 * The class to select all Admin and Superadmin settings 
 * @author 
 *
 */
public class AdminView {

	/**
	 * This is the view page for movie settings
	 * @param admSess 	Passes in an authenticated admin session
	 * @param choice 	For recursion checking
	 */
	public static void movieSettings(AdminSession admSess,int choice) {
		int option;
		Scanner sc = new Scanner (System.in);
		if(choice == 0) {
			System.out.println("====================");
			System.out.println("1. Create Movie");
			System.out.println("2. Update Movie Status");
			System.out.println("3. Manage Reviews");
			System.out.println("4. Back");
			System.out.println("====================");
			System.out.print("Select task: ");
			option = sc.nextInt();
			if(!(option >=1 && option <=4)) {
				System.out.println("Invalid Choice! Try again.");
				movieSettings(admSess,0);
			}
		}else {
			option=choice;
		}

		MovieListController mlCon=new MovieListController();
		ReviewController rCon = new ReviewController();
		switch(option) {
		case 1:
			if(StaffMovieListController.staffAddMovie(mlCon.getMovieList(),rCon.getReviewList())) {
				LoggerController.getInstance().LogChangeEntry(admSess.getUsername(), "Movie Added");
			}
			
			break;
		case 2:
			if(StaffMovieListController.staffUpdateStatus(mlCon.getMovieList())) {
				LoggerController.getInstance().LogChangeEntry(admSess.getUsername(), "Movie Status Updated");
			}
			break;
		case 3:
			reviewSettings(0);
			break;
		case 4:
			return;
		}
		movieSettings(admSess,0);
	}

	/**
	 *	This is the view page for cineplex settings
	 *	@param admSess 	Passes in an authenticated admin session
	 *	@param choice 	For recursion checking
	 */
	public static void cineplexSettings(AdminSession admSess,int choice) {
		int option;
		Scanner sc = new Scanner(System.in);
		if(choice == 0) {
			System.out.println("====================");
			System.out.println("1. Create ShowTime");
			System.out.println("2. Back");
			System.out.println("====================");
			System.out.print("Select task: ");
			option = sc.nextInt();
			if(!(option >=1 && option <=2)) {
				System.out.println("Invalid Choice! Try again.");
				cineplexSettings(admSess,0);
			}
		}else {
			option=choice;
		}
		StaffSlotController ssCon = new StaffSlotController();
		MovieListController mlCon=new MovieListController();
		switch(option) {
		case 1:
			LoggerController.getInstance().LogChangeEntry(admSess.getUsername(), "Show time added successfully.");
			ssCon.staffAddSlot(mlCon.getMovieList());
			break;
		case 2:
			return;
		}
	}

	/**
	 *	This is the view page for price settings
	 *	@param admSess	Passes in an authenticated admin session
	 *	@param choice 	For recursion checking
	 */
	public static void priceSettings(AdminSession admSess,int choice) {
		int option;
		Scanner sc = new Scanner(System.in);
		if(choice == 0) {
			System.out.println("====================");
			System.out.println("1. Manage Rates");
			System.out.println("2. Manage Public Holidays");
			System.out.println("3. Back");
			System.out.println("====================");
			System.out.print("Select task: ");
			option = sc.nextInt();
			if(!(option >=1 && option <=3)) {
				System.out.println("Invalid Choice! Try again.");
				priceSettings(admSess,0);
			}
		}else {
			option=choice;
		}

		switch(option){
		case 1:
			ratesSetting(admSess,0);
			break;
		case 2:
			phSetting(admSess,0);
			break;
		case 3:
			return;
		}
	}

	/**
	 *	This is the view page for public holiday settings
	 *	@param admSess 	Passes in an authenticated admin session
	 *	@param choice 	For recursion checking
	 */
	public static void phSetting(AdminSession admSess,int choice) {
		int option;
		Scanner sc = new Scanner(System.in);
		if(choice == 0) {
			System.out.println("====================");
			System.out.println("1. Show Public Holidays");
			System.out.println("2. Add Public Holidays");
			System.out.println("3. Back");
			System.out.println("====================");
			System.out.print("Select task: ");
			option = sc.nextInt();
			if(!(option >=1 && option <=3)) {
				System.out.println("Invalid Choice! Try again.");
				ratesSetting(admSess,0);
			}
		}else {
			option=choice;
		}

		switch(option){
		case 1:
			StaffPriceController.staffShowPh();
			break;
		case 2:
			if(StaffPriceController.staffAddPh()) {
				System.out.println("Public Holiday creation successfully.");
				LoggerController.getInstance().LogChangeEntry(admSess.getUsername(), "Public Holiday creation successfully.");
			}else {
				System.out.println("Public Holiday creation failed.");
				LoggerController.getInstance().LogChangeEntry(admSess.getUsername(), "Public Holiday creation failed.");
			}
			break;
		case 3:
			return;
		}
	}

	/**
	 *	This is the view page for rates settings
	 *	@param admSess 	Passes in an authenticated admin session
	 *	@param choice 	For recursion checking
	 */
	public static void ratesSetting(AdminSession admSess,int choice) {
		int option;
		Scanner sc = new Scanner(System.in);
		if(choice == 0) {
			System.out.println("====================");
			System.out.println("1. Show Rates");
			System.out.println("2. Create Rates");
			System.out.println("3. Update Rates");
			System.out.println("4. Back");
			System.out.println("====================");
			System.out.print("Select task: ");
			option = sc.nextInt();
			if(!(option >=1 && option <=4)) {
				System.out.println("Invalid Choice! Try again.");
				ratesSetting(admSess,0);
			}
		}else {
			option=choice;
		}
		switch(option){
		case 1:
			StaffPriceController.staffShowRates();
			break;
		case 2:
			if(StaffPriceController.staffAddRates()) {
				System.out.println("Rates creation successfully.");
				LoggerController.getInstance().LogChangeEntry(admSess.getUsername(), "Rates creation successfully.");
			}
			else {
				System.out.println("Rates creation failed.");
				LoggerController.getInstance().LogErrorEntry(admSess.getUsername(), "Rates creation failed.");
			}
			break;
		case 3:
			if(StaffPriceController.staffUpdateRates()) {
				System.out.println("Rates updated successfully.");
				LoggerController.getInstance().LogChangeEntry(admSess.getUsername(), "Rates updated successfully.");
			}
			else {
				System.out.println("Rates updated failed.");
				LoggerController.getInstance().LogErrorEntry(admSess.getUsername(), "Rates updated failed.");
			}
			break;
		case 4:
			return;
		}
	}

	/**
	 *	This is the view page for user settings
	 *	@param admSess 	Passes in an authenticated admin session
	 *	@param aCon		Passes in a admin controller to access methods for user settings.
	 */
	public static void userSettings(AdminSession admSess,AdminController aCon) {
		Scanner sc=new Scanner(System.in);
		System.out.println("1. Change Password");
		System.out.println("2. Create user");
		System.out.println("3. Delete user");
		System.out.println("4. Unlock user");
		System.out.println("5. Back");
		int choice = sc.nextInt();
		switch(choice) {
		case 1:
			if(aCon.updateAdminUserPassword(admSess)) {
				System.out.println("Password changed.");
				LoggerController.getInstance().LogSecurityEntry(admSess.getUsername(), "Password change successfully");
			}else {
				LoggerController.getInstance().LogErrorEntry(admSess.getUsername(), "Wrong password was used to change password");
			}
			break;
		case 2:
			if(admSess.getAccesslevel()== AccessLevel.SUPERADMIN) {
				if(aCon.makeAdminUser()) {
					LoggerController.getInstance().LogChangeEntry(admSess.getUsername(), "New user has been created");
					System.out.println("User created!");
				}else {
					LoggerController.getInstance().LogErrorEntry(admSess.getUsername(), "New user creation failed");
					System.out.println("Failed to create new user.");
				}
				break;
			}
			else {
				System.out.println("No permission");
				LoggerController.getInstance().LogErrorEntry(admSess.getUsername(), "Failed to create user due to lacking privileges.");
				break;
			}
		case 3:
			if(admSess.getAccesslevel() == AccessLevel.SUPERADMIN) {
				if(aCon.deleteAdminUser()) {
					LoggerController.getInstance().LogChangeEntry(admSess.getUsername(), "User removed successfully");
					System.out.println("User has been removed.");
				}else {
					LoggerController.getInstance().LogNormalEntry(admSess.getUsername(), "User removed failed.");
					System.out.println("Task abort..........");
				}
			}else {
				System.out.println("No permission");
			}
			break;
		case 4:
			if(admSess.getAccesslevel() == AccessLevel.SUPERADMIN) {
				if(aCon.unlockAdminUser()) {
					LoggerController.getInstance().LogSecurityEntry(admSess.getUsername(), "User has been unlocked successfully");
					System.out.println("User has been unlocked.");
				}
				else {
					LoggerController.getInstance().LogErrorEntry(admSess.getUsername(), "User was not unlocked");
					System.out.println("Update error occured.");
				}
			}else {
				System.out.println("No permission");
				LoggerController.getInstance().LogErrorEntry(admSess.getUsername(), "Failed to create user due to lacking privileges.");
			}
			break;

		case 5: 
			return;
		}
	}

	/**
	 * This is the view page for review settings
	 * @param choice	For recursion checking
	 */
	public static void reviewSettings(int choice) {
		int option;
		Scanner sc = new Scanner (System.in);
		if(choice == 0) {
			System.out.println("====================");
			System.out.println("1. Show Reviews");
			System.out.println("2. View Analytics");
			System.out.println("3. Back");
			System.out.println("====================");
			System.out.print("Select task: ");
			option = sc.nextInt();
			if(!(option >=1 && option <=3)) {
				System.out.println("Invalid Choice! Try again.");
				reviewSettings(0);
			}
		}else {
			option=choice;
		}
		ReviewController rCon = new ReviewController();
		List<Review> rList = rCon.getReviewList();
		switch(option) {
		case 1:
			for(Review r : rList) {
				System.out.println(r.getMovieTitle());
				String[] noOfratings = r.getRating().split(";");
				System.out.println("All Ratings: "+Arrays.toString(noOfratings));
				System.out.println("Overall Rating: ["+r.getOverallRating()+"]"); 
				String[] noOfreviews = r.getReview().split(";");
				System.out.println("Reviews: "+Arrays.toString(noOfreviews));
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
			System.out.println("=====Top 5 Movies=====");
			int i = 1;
			if(ratingList.size()>5) {
				for(;i<6;i++) {
					System.out.println(i+". "+ratingList.firstEntry().getValue()+" [Overall Rating:"+ratingList.firstEntry().getKey()+"]");
					ratingList.remove(ratingList.firstEntry().getKey());
				}
			}
			else {
				for (Entry<Double, String> movie : ratingList.entrySet()) {
				    System.out.println(i+". "+movie.getValue()+" [Overall Rating:"+movie.getKey()+"]");
				}
			}
			break;
		case 3:
			return;
		}
	}


	/**
	 *	This is the view page for user settings
	 *	@param choice 	For recursion checking
	 */
	public static void logAnalysis(int choice) {
		int option;
		Scanner sc = new Scanner (System.in);
		if(choice == 0) {
			System.out.println("====================");
			System.out.println("1. Show logs");
			System.out.println("2. Search logs");
			System.out.println("3. Back");
			System.out.println("====================");
			System.out.print("Select task: ");
			option = sc.nextInt();
			if(!(option >=1 && option <=3)) {
				System.out.println("Invalid Choice! Try again.");
				logAnalysis(0);
			}
		}else {
			option=choice;
		}

		switch(option) {
		case 1:
			List<Log> logList = LoggerController.getInstance().getLogList();
			for(Log l : logList) {
				System.out.println(l.username + " , " +l.description + " , "+l.loglvl + " , "+l.datetime);
			}
			break;
		case 2:
			while(true) {
				System.out.println("====================");
				System.out.println("[-1 to exit]");
				System.out.println("Enter keyword: ");
				String keyword=sc.next();

				if(!keyword.equalsIgnoreCase("-1")) {
					List<Log> fLogList = LoggerController.getInstance().getLogList(keyword);
					for(Log l : fLogList) {
						System.out.println(l.username + " , " +l.description + " , "+l.loglvl + " , "+l.datetime);
					}
				}else {
					break;
				}
			}
			break;
		}
	}
}
