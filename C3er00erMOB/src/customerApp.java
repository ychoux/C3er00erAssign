import controller.CusMovController;
import controller.CustomerController;
import entity.MovieDetail;

import java.util.List;
import java.util.Scanner;


public class customerApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<MovieDetail> movieDetailList;
        movieDetailList =  CusMovController.movieCSVRead();
        while(true) {
            System.out.println("\n\nChoose \n " +
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
            }
            else {
            }
        }
    }
}
