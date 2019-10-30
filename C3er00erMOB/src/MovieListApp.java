import controller.PrintMovieList;
import controller.ReviewController;

public class MovieListApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PrintMovieList.printAllMovieList();
		ReviewController.updateOverallRating();
		ReviewController.updateMovieListRating();
		PrintMovieList.printMovieList(2); // movie.getid()
	}

}
