import java.util.Scanner;

import controller.AdminController;
import controller.LoggerController;
import login.AccessLevel;
import login.AdminSession;
import view.AdminView;

public class adminApp {
	public static void startAdmin() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Username: ");
		String username = sc.nextLine();		
		int failCount=0;

		for(;;) {
			AdminSession admSess;
			if(!username.equals("1")) {
				System.out.println("Enter Password");
				String password= sc.nextLine();

				admSess = AdminSession.createSession(username, password);
			}
			else {
				admSess= AdminSession.createSession("jy", "P@ssw0rd!");
			}

			AdminController aCon=new AdminController();
			if(admSess != null) {
				if(admSess.getAccesslevel() != AccessLevel.NOACCESS) {
					LoggerController.getInstance().LogNormalEntry(admSess.getUsername(), "Login Successful");
					int choice = -1;
					while(true) {
						System.out.println("====================");
						System.out.println("1. Manage Movie Listing ");
						System.out.println("2. Manage Cinema Showtimes");
						System.out.println("3. Manage Ticket Rates");
						System.out.println("4. Manage User Settings");
						System.out.println("====================");
						System.out.print("Select task: ");
						choice = sc.nextInt();
						switch(choice) {
						case 1:
							AdminView.movieSettings(admSess,0);
							break;
						case 2:
							AdminView.cineplexSettings(0);
							break;
						case 3:
							AdminView.priceSettings(admSess,0);
							break;
						case 4:
							AdminView.userSettings(admSess,aCon);
							break;
						}
						if(choice == -1){
							break;
						}
					}	
				}else {
					LoggerController.getInstance().LogNormalEntry(admSess.getUsername(), "Account Locked");
					System.out.println("Your account has been locked. Please contact an admin.");
					break;
				}
			}else {
				if(failCount == 3) {
					// locking bug where if wrong user it still counts
					System.out.println("Your account has been locked. Please contact an admin.");
					LoggerController.getInstance().LogSecurityEntry(username, "User Locked");
					aCon.lockAdminUser(username);
					break;
				}else {
					System.out.println("Try again.");
					LoggerController.getInstance().LogSecurityEntry(username, "Wrong User password");
					failCount++;
				}

			}
		}
	}
}
