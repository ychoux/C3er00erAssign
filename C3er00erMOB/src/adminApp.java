import java.util.Scanner;

import controller.AdminController;
import entity.Admin;
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
			System.out.println("Enter Password");
			String password= sc.nextLine();

			AdminSession admSess = AdminSession.createSession(username, password);

			AdminController aCon=new AdminController();
			if(admSess != null) {
				if(admSess.getAccesslevel() != AccessLevel.NOACCESS) {
					int choice = -1;
					while(true) {
						System.out.println("====================");
						System.out.println("1. Create/Update/Remove Movie Listing ");
						System.out.println("2. Create/Update/Remove cinema showtimes");
						System.out.println("3. User Settings");
						System.out.println("====================");
						choice = sc.nextInt();
						switch(choice) {
						case 1:
							break;
						case 2:
							break;
						case 3:
							AdminView.userSettings(admSess,aCon);
							break;
						}
						if(choice == -1){
							break;
						}
					}	
				}else {
					System.out.println("Your account has been locked. Please contact an admin.");
				}
			}else {
				if(failCount == 3) {
					// locking bug where if wrong user it still counts
					System.out.println("Your account has been locked. Please contact an admin.");
					aCon.lockAdminUser(username);
					break;
				}else {
					System.out.println("Try again.");
					failCount++;
				}

			}
		}
	}
}
