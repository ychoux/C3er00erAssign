package controller;


import java.util.List;

import entity.Movie;

public class PrintMovieList {

	public static void printAllMovieList(){
		// TODO Auto-generated method stub
		MovieListController file = new MovieListController();
		List<Movie> aList = file.getMovieList();
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
			System.out.printf("Rating: %.2f\n",m.getOverallRating());
			System.out.println("Synopsis: "+m.getSynopsis());
			System.out.println(" ");
		}
		
	}
	public static void printMovieList(int id) {
		MovieListController file = new MovieListController();
		List<Movie> aList = file.getMovieList();
		String SplitBy = ";";
		for(Movie m : aList) {
			if(m.getId() == id) {
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
				System.out.printf("Rating: %.2f\n",m.getOverallRating());
				System.out.println("Synopsis: "+m.getSynopsis());
				System.out.println(" ");
				break;
			}
			
		}
	}
	

}
