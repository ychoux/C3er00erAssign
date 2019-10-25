import java.util.Scanner;

public class mainApp {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("User:");
		System.out.println("1. Customer");
		System.out.println("2. Admin");
		int choice=sc.nextInt();
		switch(choice) {
			case 1:
				System.out.println("BLah");
				break;
			case 2:
				System.out.println("Entering Admin mode...........");
				adminApp.startAdmin();
				break;
		}
		sc.close();
	}
}
