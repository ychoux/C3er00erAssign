import controller.MovieListController;
import controller.PrintMovieList;
import controller.ReviewController;
import entity.Movie;
import entity.Review;
import view.UserInput;

import java.util.List;

public class MovieListApp {

	public static void review_cus() {
		// TODO Auto-generated method stub
		ReviewController file = new ReviewController();
		List<Review> rList = file.getReviewList();
		MovieListController mfile = new MovieListController();
		List<Movie> mList = mfile.getMovieList();
		
		/*
		 * //for staff
		 * 
		 * // just call this function will perform addmovielist
		 * StaffMovieListController.staffAddMovie(mList,rList); 
		 * // this function allow the staff to just delete the selected movie
		 * StaffMovieListController.staffDelMovie(mList, rList); 
		 * // this function allow the staff to add movie into movielist 
		 * MovieListController.addMovieList(mList, name, synopsis, director, cast, genre, time, status, sale, rating);
		 * 
		 * // this function allow the staff to delete movie from the movielist
		 * MovieListController.delMovieList(mList, id);
		 * 
		 * 
		 * //for customer
		 * 
		 * 
		 * // hard update directly from csv 
		 * ReviewController.updateMovieListRating();
		 * //hard update on movielist csv from review objects
		 * ReviewController.updateMovieListRating(rList); 
		 * // update on movielist csv and movie objects from review objects
		 * ReviewController.updateMovieListRating(mList, rList); 
		 * // hard update on overall rating on review csv 
		 * ReviewController.updateOverallRating(); 
		 * //update overall rating on review csv from review objects
		 * ReviewController.updateOverallRating(rList); 
		 * // update review csv file (IMPORTANT) 
		 * ReviewController.updateReviewCSV(rList); 
		 * // allow user to review on the movie //name = movie name /rating = movie rating /review = reviews on the movie 
		 * // this is a function for userInputReview
		 * ReviewController.userReview(rList, name, rating, review); 
		 * //this function allow user to select movie and write reviews and rating
		 * UserInput.userInputReview(mList, rList); 
		 * // this function allow user to select their movie and print the movie detail and reviews
		 * UserInput.userGetMovie(mList, rList); 
		 * // this function is to update the movie csv file (IMPORTANT) 
		 * MovieListController.updateMovieListCSV(mList); 
		 * // this function is to print all movie from the movielist
		 * PrintMovieList.printAllMovieList(mList); 
		 * // this function is to print selected movie from the movielist is called by userGetMovie
		 * PrintMovieList.printMovieList(mList, id); 
		 * // this function is to print selected review by object index is called by userGetMovie
		 * PrintMovieList.printReview(rList, id);
		 *  // this function is to print selected review by object name is called by userinputReview
		 * PrintMovieList.printReview(rList, name);
		 */
		
		ReviewController.updateOverallRating(rList);
		ReviewController.updateMovieListRating(mList, rList);
		PrintMovieList.printMovieList(mList,2); // movie.getid()
		ReviewController.userReview(rList, "this movie", "5", "Good movie"); //ReviewController.userReview(rList, movieid, user rating, user review);
		//MovieListController.delMovieList(mList, 0); // this will remove the whole selected movielist
		//MovieListController.addMovieList(mList, "red cliff", "synopsis", "director", "cast", "genre", null, null, 4, 5.5);
		//
		//PrintMovieList.printReview(rList, 10);
		UserInput.userInputReview(mList,rList);
	}
	

}
