import controller.BookingController;
import controller.CusMovController;
import controller.CustomerController;
import entity.Movie;
import view.UserInput;

import java.util.List;
import java.util.Scanner;


public class customerApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Movie> movieDetailList;
        movieDetailList =  CusMovController.getInstance().movieCSVRead();
        while(true) {
            System.out.println("\nChoose\n " +
                    "1. View user's detail\n" +
                    " 2. List Movie\n" +
                    " 3. Movie review and rating\n"+
                    " 4. View Review");

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
                    UserInput.userGetMovie(movieDetailList);
                    break;
                }
                case 4:{
                	UserInput.userGetReview(movieDetailList);
                	break;
                }
            }
        }
    }
}
