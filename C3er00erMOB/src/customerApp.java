
import controller.CustomerController;
import controller.MovieListController;
import controller.ReviewController;
import entity.Customer;
import entity.Movie;
import entity.Review;
import view.BookingView;
import view.UserInput;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


/**
 * The class to manage functionality user wanted to use
 * @author kaishern
 */
public class customerApp {


    /**
     * The function to manage functionality user wanted to use
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String dummy = "";
        List<Movie> movieDetailListFiltered;
        MovieListController mlist = new MovieListController();
        List<Movie>movieDetailList = mlist.getMovieList();
        movieDetailListFiltered = MovieListController.readMovieShowing(movieDetailList);
        ReviewController file = new ReviewController();
		List<Review> reviewList = file.getReviewList();

		Customer cus = null;
		int chc = 0;
		while(true) {
            CustomerController cc = new CustomerController();
            System.out.println("Press 1 to Sign Up, 2 to Login");
            try {
                chc = sc.nextInt();
            } catch (InputMismatchException e) {
                if (sc.hasNext()) dummy = sc.next();
                System.out.println("Invalid Input");
            }

            if (chc == 1) {
                boolean valid_username = true;
                try{
                    dummy = sc.nextLine();
                    System.out.println("Enter your username");
                    String username = sc.nextLine();
                    for (Customer usr : cc.getCustomerlst()){
                        if(usr.getUsername().equals(username.toUpperCase())){
                            System.out.println("User exists, try another username");
                            valid_username = false;
                            break;
                        }
                    }
                    if (valid_username){
                        System.out.println("Enter you name");
                        String name = sc.nextLine();
                        System.out.println("Enter your email address");
                        String mail = sc.nextLine();
                        System.out.println("Enter you phone number");
                        int phone = sc.nextInt();
                        cc.addUser(username,name, mail, phone);
                        System.out.println("User added");
                    }
                }catch (InputMismatchException e){
                    if(sc.hasNext()) dummy = sc.nextLine();
                    System.out.println("Invalid input");
                }
            }
            else if (chc == 2) {
                try {
                    System.out.println("Enter your username");
                    if (sc.hasNext()) dummy = sc.nextLine();
                    String name = sc.nextLine();
                    System.out.println("Enter your phone number as verification");
                    int phone = sc.nextInt();
                    cus = cc.userVeri(name, phone);

                    if (cus == null) System.out.println("Wrong user name or phone number\n");
                    else {
                        System.out.println("Login successfully ");
                    }

                } catch (Exception e) {
                    if (sc.hasNext()) dummy = sc.nextLine();
                    System.out.println("Phone number cannot have alphabets, try again\n");
                }

                while (cus != null) {
                    System.out.println("\nChoose\n" +
                            " 1. View user's detail\n" +
                            " 2. List Movie (Movie Details & Book for Tickets)\n" +
                            " 3. Rate & Review Movies\n" +
                            " 4. View Movie Reviews\n" +
                            " 5. List Top 5 Movies\n" +
                            " 6. Switch user");
                    if(sc.hasNext()) dummy = sc.nextLine();
                    int choice = sc.nextInt();
                    switch (choice) {

                        case 1: {
                            cc.print();
                            break;
                        }

                        case 2: {
                            Customer updated_cus = BookingView.getInstance().booking_flow(movieDetailListFiltered, cus);
                            cc.update_user(updated_cus);
                            break;
                        }

                        case 3: {
                            UserInput.userInputReview(movieDetailList, reviewList);
				movieDetailList = mlist.getMovieList();
                            break;
                        }
                        case 4: {
                            UserInput.userGetReview(movieDetailList);
                            break;
                        }
                        case 5: {
                            UserInput.top5Movies(movieDetailList, reviewList);
                            break;
                        }
                        default: {
                            cus = null;
                            return;
                        }

                    }
                }
            }
            else {System.out.println("Invalid input");}
        }
    }
}
