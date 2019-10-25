import java.util.Scanner;

import login.AdminSession;


public class adminApp {
	public static void startAdmin() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Username: ");
		String username=sc.nextLine();
		System.out.println("Enter Password");
		String password=sc.nextLine();

		if(AdminSession.createSession(username, password) != null) {

		}
		sc.close();
	}

}
