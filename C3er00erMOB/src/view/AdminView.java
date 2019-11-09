package view;

import java.util.Base64;
import java.util.List;
import java.util.Scanner;

import controller.*;
import entity.*;
import login.AccessLevel;
import login.AdminSession;
import login.SecurityFunc;

public class AdminView {

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
			StaffMovieListController.staffAddMovie(mlCon.getMovieList(),rCon.getReviewList());
			LoggerController.getInstance().LogChangeEntry(admSess.getUsername(), "Movie Added");
			break;
		case 2:
			StaffMovieListController.staffUpdateStatus(mlCon.getMovieList());
			LoggerController.getInstance().LogChangeEntry(admSess.getUsername(), "Movie Status Updated");
			break;
		case 3:
			reviewSettings(0);
			break;
		case 4:
			return;
		}
	}

	public static void cineplexSettings(int choice) {
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
				cineplexSettings(0);
			}
		}else {
			option=choice;
		}
		StaffSlotController ssCon = new StaffSlotController();
		MovieListController mlCon=new MovieListController();
		switch(option) {
		case 1:
			ssCon.staffAddSlot(mlCon.getMovieList());
			break;
		case 2:
			return;
		}
	}

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
				break;
			case 3:
				return;
		}
	}
	
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
			
			break;
		case 3:
			if(StaffPriceController.staffUpdateRates()) {
				System.out.println("Rates updated successfully.");
				LoggerController.getInstance().LogChangeEntry(admSess.getUsername(), "Rates updated successfully.");
			}
			else {
				System.out.println("Rates updated failed.");
				LoggerController.getInstance().LogChangeEntry(admSess.getUsername(), "Rates updated failed.");
			}
			break;
		case 4:
			return;
	}
	}
	
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
			for(;;) {
				System.out.println("Enter current password: ");
				String currpassword = sc.next();
				Admin adtmp=aCon.getAdminUser(admSess.getUsername());
				if(admSess.authenticatePassword(currpassword,adtmp)) {
					for(;;) {
						System.out.println("Enter new password: ");
						String newpassword = sc.next();
						System.out.println("Confirm new password: ");
						String newcfmpassword = sc.next();
						if(newpassword.equals(newcfmpassword)) {
							// Generate new salt
							String newSalt = Base64.getEncoder().encodeToString(SecurityFunc.getNextSalt());

							// Newly hashed password
							String newPassword= SecurityFunc.hash(newpassword.toCharArray(),Base64.getDecoder().decode(newSalt));

							// update admin user password & salt
							adtmp.password = newPassword;
							adtmp.salt = newSalt;

							List<Admin> adminList=aCon.getAdminUsers();
							for(Admin a : adminList) {
								if(a.username.equals(admSess.getUsername())) {
									a.password = newPassword;
									a.salt = newSalt;
									break;
								}
							}

							// Update csv file
							aCon.updateAdminCSV(adminList);
							System.out.println("Password updated!");
							break;
						}else {
							System.out.println("Passwords did not match!");
							System.out.println("Please try again.");
						}
					}
					break;
				}else {
					System.out.println("Passwords did not match!");
					System.out.println("Please try again.");
				}
			}
		case 2:
			if(admSess.getAccesslevel() == AccessLevel.ADMIN || admSess.getAccesslevel()== AccessLevel.SUPERADMIN) {
				System.out.println("Enter username: ");
				String uname=sc.next();
				System.out.println("Enter accesslevel(0-2)");
				int accesslevel=sc.nextInt();
				System.out.println("Enter password");
				String password=sc.next();
				Admin adtmp=aCon.makeAdminUser(uname, password, accesslevel);

				List<Admin> adminList=aCon.getAdminUsers();
				adminList.add(adtmp);
				aCon.updateAdminCSV(adminList);
				System.out.println("User created!");
				break;
			}
			else {
				System.out.println("No permission");
				break;
			}
		case 3:
			if(admSess.getAccesslevel() == AccessLevel.SUPERADMIN) {
				List<Admin> adminList=aCon.getAdminUsers();
				for(Admin a : adminList) {
					aCon.printUserDetails(a);
				}
				System.out.println("Choose user to remove: ");
				String removeUsername = sc.next();
				System.out.println("Are you sure? (Y/N)");
				char cfm = sc.next().charAt(0);
				if(cfm == 'Y' || cfm == 'y'){
					aCon.deleteAdminUser(removeUsername);
					System.out.println("User has been removed.");
				}else {
					System.out.println("Task abort..........");
				}
			}else {
				System.out.println("No permission");
			}
			break;
		case 4:
			if(admSess.getAccesslevel() == AccessLevel.SUPERADMIN) {

			}else {
				System.out.println("No permission");
			}
			break;

		case 5: 
			int pick = -1;
			while(true) {
				System.out.println("====================");
				System.out.println("1. Create/Update/Remove Movie Listing ");
				System.out.println("2. Create/Update/Remove cinema showtimes");
				System.out.println("3. User Settings");
				System.out.println("====================");
				pick = sc.nextInt();
				switch(pick) {
				case 1:
					break;
				case 2:
					break;
				case 3:
					userSettings(admSess,aCon);
					break;
				}
				if(pick == -1){
					break;
				}
				break;
			}
		}
	}

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
				cineplexSettings(0);
			}
		}else {
			option=choice;
		}
		ReviewController rCon = new ReviewController();
		List<Review> rList = rCon.getReviewList();
		switch(option) {
		case 1:
			for(Review r : rList) {
				System.out.print(r.getMovieTitle()+" ");
				System.out.print(r.getRating()+" ");
				System.out.print(r.getOverallRating()+" ");
				System.out.println(r.getReview()+" ");
				System.out.println();
			}
			break;
		case 2:
			System.out.println("Top 5 Movies");
			
			break;
		case 3:
			return;
		}
	}
	
}
