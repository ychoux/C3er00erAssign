import controller.BookingController;
import controller.CusMovController;
import controller.CustomerController;
import controller.TicketManager;
import entity.MovieDetail;
import entity.SeatBookings;
import entity.Slot;

import java.util.List;
import java.util.Scanner;


public class customerApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<MovieDetail> movieDetailList;
        movieDetailList =  CusMovController.movieCSVRead();
        while(true) {
            System.out.println("\nChoose \n " +
                    "1. View user's detail\n" +
                    " 2. List Movie");
            int choice = sc.nextInt();
            if (choice == 1) {
                new CustomerController();
            } else if (choice == 2) {
                CusMovController.listMovie(movieDetailList);
                System.out.println("\nEnter the number of movie to see description: (others to quit)");
                int ch = sc.nextInt();
                CusMovController.viewMovieDetails(movieDetailList.get(ch - 1));
                System.out.println("Enter 1 to show the slots available");
                int ch1 = sc.nextInt();
                if(ch1==1){
                    List<Slot> result;
                    result = BookingController.getInstance().viewAvailableSlot(movieDetailList.get(ch1 - 1).getTitle());
                    System.out.println("Choose the slot you are interested in");
                    int ch2 = sc.nextInt();
                    Slot slot = result.get(ch2-1);
                    BookingController.getInstance().planofcineplex(slot);
                    System.out.println("Enter the seat you want to book");
                    String seats = sc.next();
                    SeatBookings sb = new SeatBookings(slot.getCinema().getSeating_plan(),seats);
                }
            }
            else {
            }
        }
    }
}
