import controller.*;
import entity.Customer;
import entity.Movie;
import entity.Review;
import view.UserInput;

import java.util.List;
import java.util.Scanner;


public class customerApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String dummy = "";
        List<Movie> movieDetailListFiltered;
        MovieListController mlist = new MovieListController();
        List<Movie>movieDetailList = mlist.getMovieList();
        movieDetailListFiltered = MovieListController.readMovieShowing(movieDetailList);
        ReviewController file = new ReviewController();
		List<Review> reviewList = file.getReviewList();

		CustomerController cc = new CustomerController();
		Customer cus = null;
		while(true){
		    try{
                System.out.println("Enter your name");
                String name = sc.next();
                System.out.println("Enter your phone number as verification");
                int phone = sc.nextInt();
                cus = cc.userVeri(name,phone);

                if (cus == null)System.out.println("Wrong user name or password\n");
                else{System.out.println("Login successfully ");}

		    }catch (Exception e){
		        if (sc.hasNext()) dummy = sc.next();
		        System.out.println("User does not exist, try again\n");}

            while(cus != null) {
                System.out.println("\nChoose\n" +
                        " 1. View user's detail\n" +
                        " 2. List Movie (Movie Details & Book for Tickets)\n" +
                        " 3. Rate & Review Movies\n" +
                        " 4. View Movie Reviews\n" +
                        " 5. List Top 5 Movies\n"+
                        " 6. Switch user");

                int choice = sc.nextInt();
                switch (choice) {

                    case 1 :
                    {
                        cc.print();
                        break;
                    }

                    case 2:{
                        Customer updated_cus = BookingController.getInstance().booking_flow(movieDetailListFiltered,cus);
                        cc.update_user(updated_cus);
                        break;}

                    case  3:{
                        UserInput.userInputReview(movieDetailList, reviewList);
                        break;
                    }
                    case 4:{
                        UserInput.userGetReview(movieDetailList);
                        break;
                    }
                    case 5: {
                        UserInput.top5Movies(movieDetailList, reviewList);
                        break;
                    }
                    default: {cus = null;break;}

                }
            }
        }
    }
}
