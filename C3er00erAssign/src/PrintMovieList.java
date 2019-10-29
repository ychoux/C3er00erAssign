
import java.util.List;

public class PrintMovieList {

	public static void printMovieList(){
		// TODO Auto-generated method stub
		MovieListCSV file = new MovieListCSV();
		List<Movie> aList = file.getMovieList();
		Movie movelist = new Movie();
		String SplitBy = ";";
		for(Movie m : aList) {
			String[] cast = m.getCast().split(SplitBy);
			String[] genre = m.getGenre().split(SplitBy);
			System.out.println("Movie: "+m.getMovieTitle());
			System.out.println("Director: "+m.getDirector());
			System.out.print("Cast: ");
			for(String casts: cast)
			{
				System.out.print("["+casts+"] ");
			}
			System.out.println(" ");
			System.out.print("Genre: ");
			for(String genres: genre)
			{
				System.out.print("["+genres+"] ");
			}
			System.out.println(" ");
			System.out.println("Status: "+m.getStatus());
			System.out.println("Sales: "+m.getSales());
			System.out.println("Rating: "+m.getOverallRating());
			System.out.println("Synopsis: "+m.getSynopsis());
			
		}
		
	}

}
