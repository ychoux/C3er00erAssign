
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The class to run the whole application
 * @author 
 *
 */
public class mainApp {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("====================");
		System.out.println("1. Customer");
		System.out.println("2. Admin");
		System.out.println("====================");
		System.out.print("User: ");
		try {
			int choice=sc.nextInt();
			switch(choice) {
			case 1:
				System.out.println("Entering Customer mode...........");
				customerApp.main(null);
				break;
			case 2:
				System.out.println("Entering Admin mode...........");
				adminApp.main(null);
				break;
			}
		} catch (InputMismatchException e) {
			System.out.println("Invalid input.");
			main(null);
		}
		sc.close();
	}
}
