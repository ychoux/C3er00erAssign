import java.util.Scanner;

import controller.AdminController;
import login.AdminSession;


public class adminApp {
	public static void startAdmin() {
		Scanner sc = new Scanner(System.in);
		AdminSession.createSession("peter", "password");
		sc.close();
	}
}
