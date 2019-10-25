import java.util.Scanner;

import controller.AdminController;
import login.AdminSession;


public class adminApp {
	public static void startAdmin() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Username: ");
		String username=sc.nextLine();
		System.out.println("Enter Password");
		String password=sc.nextLine();
		System.out.println("Enter AccessLevel");
		int accesslevel=sc.nextInt();
		AdminController adminCon=new AdminController();
		adminCon.makeAdminUser(username, password, accesslevel);
		AdminSession.createSession(username, password);
		
		sc.close();
	}
}
