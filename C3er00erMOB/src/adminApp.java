import java.util.Scanner;

import controller.AdminController;
import login.AdminSession;
import view.AdminView;


public class adminApp {
	public static void startAdmin() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Username: ");
		String username = sc.nextLine();
		System.out.println("Enter Password");
		String password= sc.nextLine();

		AdminSession admSess = AdminSession.createSession(username, password);
		if(admSess != null) {
			AdminController aCon=new AdminController();
			int choice = -1;
			while(choice == -1) {
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
			}			
		}
		sc.close();
	}

}
