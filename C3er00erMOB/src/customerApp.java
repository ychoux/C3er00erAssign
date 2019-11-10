import controller.BookingController;
import controller.CusMovController;
import controller.CustomerController;
import controller.ReviewController;
import entity.Movie;
import entity.Review;
import view.UserInput;

import java.util.List;
import java.util.Scanner;


public class customerApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Movie> movieDetailList;
        movieDetailList =  CusMovController.getInstance().movieCSVRead();
        ReviewController file = new ReviewController();
		List<Review> reviewList = file.getReviewList();
        while(true) {
            System.out.println("\nChoose\n" +
                    " 1. View user's detail\n" +
                    " 2. List Movie (Movie Details & Book for Tickets)\n" +
                    " 3. Rate & Review Movies\n" +
                    " 4. View Movie Reviews\n" +
                    " 5. List Top 5 Movies");

            int choice = sc.nextInt();
            switch (choice) {

                case 1 :
                {
                    CustomerController.getInstance().print();
                    break;
                }

                case 2:{
                    BookingController.getInstance().booking_flow(movieDetailList);
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

            }
        }
    }
}
