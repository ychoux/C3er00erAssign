import java.io.Console;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import controller.AdminController;
import controller.LoggerController;
import login.AccessLevel;
import login.AdminSession;
import view.AdminView;

/**
 * The class to authenticate the admin/superadmin
 * @author 
 *
 */
public class adminApp {
	/**
	 * This function authenticate the admin/superadmin and select the necessary actions
	 */
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
			
		int failCount=0;

		while(true) {
			System.out.print("Enter Username: ");
			String username = sc.nextLine();	
			AdminSession admSess;
			
			Console console = System.console();
	        if (console == null) {
	            System.out.println("Couldn't get Console instance");
	            System.exit(0);
	        }
	        char[] passwordArray = console.readPassword("Enter Password: ");
	        //console.printf("Password entered was: %s%n", new String(passwordArray));
			admSess = AdminSession.createSession(username, new String(passwordArray));
			
			/**
			System.out.println("Enter Username: ");
			String username = sc.nextLine();	
			AdminSession admSess;
			if(!username.equals("1")) {
				System.out.println("Enter Password");
				String password= sc.nextLine();
				admSess = AdminSession.createSession(username, password);
			}
			else {
				System.out.println("Entering bypass.....");
				admSess= AdminSession.createSession("jy", "P@ssw0rd!");
			}**/
			

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
						System.out.println("5. Log Analysis");
						System.out.println("Exit [-1]");
						System.out.println("====================");
						try {
							System.out.print("Select task: ");
							choice = sc.nextInt();
							switch(choice) {
							case 1:
								AdminView.movieSettings(admSess,0);
								break;
							case 2:
								AdminView.cineplexSettings(admSess,0);
								break;
							case 3:
								AdminView.priceSettings(admSess,0);
								break;
							case 4:
								AdminView.userSettings(admSess,aCon);
								break;
							case 5:
								AdminView.logAnalysis(0);
								break;
							default:
								System.out.println("Invalid option. Try again.");
								break;
							case -1:
								System.out.println("Admin Signing Out...");
								return;
							}
						}catch(InputMismatchException e) {
							System.out.println("Invalid input!");
							sc.reset();
							sc.next();
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
