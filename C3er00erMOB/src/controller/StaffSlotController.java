package controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import entity.Cinema;
import entity.Cineplex;
import entity.Movie;

/**
 * This class is for showtime creation
 * @author 
 *
 */
public class StaffSlotController {

	/**
	 * The function adds a new slot time showing for a particular cineplex
	 * @param passes in a list of movies that are screening
	 */
	public boolean staffAddSlot(List<Movie> movieList) {
		Scanner sc = new Scanner(System.in);

		List<Cineplex> Cine = controller.CineplexManager.getInstance().getCineplexes();
		System.out.println("====================");
		for(int i = 0; i<Cine.size(); i++) {
			System.out.println(i+1+". "+Cine.get(i).getCineplex_name());
		}
		System.out.println("====================");
		System.out.print("Select Cineplex: ");
		Cineplex tmpcineplex = Cine.get(sc.nextInt()-1);
		System.out.println("====================");
		
		List<Cinema> tmpCinemaList = tmpcineplex.getCinemas();
		for(int k = 0;k<tmpCinemaList.size();k++) {
			System.out.println(k+1+". "+tmpCinemaList.get(k).getCinemaID());
		}
		System.out.println("====================");
		System.out.print("Select Cinema: ");
		Cinema tmpCinema = tmpCinemaList.get(sc.nextInt()-1);
		System.out.println("====================");

		for(int m = 0;m<movieList.size();m++) {
			System.out.println(m+1+". "+movieList.get(m).getMovieTitle());
		}
		System.out.println("====================");
		System.out.print("Select Movie: ");
		Movie tmpMovie = movieList.get(sc.nextInt()-1);
		System.out.println("====================");
		
		LocalDateTime showDateTime=null;
		
		System.out.print("Enter Showing Date [dd-MM-yyyy] : ");
		String showDate = sc.next();
		System.out.print("Enter Showing Time [HH:mm] : ");
		String showTime = sc.next();
		showDateTime = LocalDateTime.parse((showDate+" "+showTime), SlotManager.getInstance().getFormatter());
			
		int ranId = ThreadLocalRandom.current().nextInt(100, 999 + 1);
		String slotId = tmpMovie.getMovieTitle().substring(0,2).toUpperCase() 
				+ tmpCinema.getCinemaID().toUpperCase() 
				+ Integer.toString(ranId);
		
		return controller.SlotManager.getInstance().addSlot(slotId, showDateTime, tmpMovie.getTime(), tmpMovie.getMovieTitle(), tmpCinema);
	}

}
