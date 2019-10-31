package view;
import java.util.List;
import java.util.Scanner;

import controller.ReviewController;
import entity.Movie;
import entity.Review;
public class UserInputReview {
	
	public static void userInputReview(List<Review> rList, List<Movie> mList) {
		String rating, review;
		System.out.println("Select Movie by its ID to review");
		for(Review r:rList) {
			System.out.print("ID: "+r.getId());
			for(Movie m:mList) {
				if(r.getId() == m.getId()) {
					System.out.println(" Movie: "+m.getMovieTitle());
					break;
				}
			}
		}
		Scanner sc = new Scanner(System.in);
		int choice = sc.nextInt();
		for(Review r:rList) {
			if(choice == r.getId()) {
				System.out.println("Give Rating");
				sc.nextLine();
				rating = sc.nextLine();
				System.out.println(" rating: "+rating);
				System.out.println("Give Review");
				review = sc.nextLine();
				System.out.println(" review: "+review);
				ReviewController.userReview(rList, r.getId(), rating, review);
				break;
			}
		}
	}
}
