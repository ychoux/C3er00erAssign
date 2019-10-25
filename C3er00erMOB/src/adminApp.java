import java.util.Scanner;

import login.AdminSession;


public class adminApp {
	public static void startAdmin() {
		Scanner sc = new Scanner(System.in);
		for(int i=0;i<=3;) {
			System.out.println("Enter Username: ");
			String username=sc.nextLine();
			System.out.println("Enter Password");
			String password=sc.nextLine();

			if(AdminSession.createSession(username, password) != null) {

			}else {
				if(i!=2) {
					i++;
				}else {
					System.out.println("Please contact admin!");
					break;
				}
			}
		}

		sc.close();
	}
}
