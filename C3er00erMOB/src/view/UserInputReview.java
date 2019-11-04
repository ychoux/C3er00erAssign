package view;
import java.util.List;
import java.util.Scanner;

import controller.ReviewController;
import entity.Movie;
import entity.Review;
public class UserInputReview {
	
	public static void userInputReview(List<Review> rList, List<Movie> mList) {
		String rating, review;
		int i=0;
		System.out.println("Select Movie by its ID to review");
		for(Review r:rList) {
			System.out.print("ID: "+i);
			for(Movie m:mList) {

				if(rList.get(i).getMovieTitle().equals(m.getMovieTitle())) {
					System.out.println(" Movie: "+m.getMovieTitle());
					break;
				}
			}
			i++;
		}
		Scanner sc = new Scanner(System.in);
		int choice = sc.nextInt();
		for(Review r:rList) {
			if( rList.get(choice).getMovieTitle().equals(r.getMovieTitle())) {
				System.out.println("Give Rating");
				sc.nextLine();
				rating = sc.nextLine();
				System.out.println("Give Review");
				review = sc.nextLine();
				ReviewController.userReview(rList, r.getMovieTitle(), rating, review);
				break;
			}
		}
	}
}
