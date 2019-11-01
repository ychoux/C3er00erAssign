import java.util.List;

import controller.MovieListController;
import controller.PrintMovieList;
import controller.ReviewController;
import entity.Movie;
import entity.Review;
import view.UserInputReview;

public class MovieListApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReviewController file = new ReviewController();
		List<Review> rList = file.getReviewList();
		MovieListController mfile = new MovieListController();
		List<Movie> mList = mfile.getMovieList();
		PrintMovieList.printAllMovieList();
		ReviewController.updateOverallRating();
		ReviewController.updateMovieListRating();
		PrintMovieList.printMovieList(2); // movie.getid()
		//ReviewController.userReview(rList, 2, "5", "Good movie"); //ReviewController.userReview(rList, movieid, user rating, user review);
		UserInputReview.userInputReview(rList, mList);
	}

}
