package controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

import entity.Cinema;
import entity.Cineplex;
import entity.Movie;
import entity.MovieStatus;

/**
 * This class is for showtime creation
 * @author JIAYING
 *
 */
public class StaffSlotController {

	/**
	 * The function adds a new slot time showing for a particular cineplex
	 * @param movieList	Passes in a list of movies that are screening
	 * @return	Returns a boolean to see if the slot was created
	 */
	public boolean staffAddSlot(List<Movie> movieList) {
		Scanner sc = new Scanner(System.in);

		Cineplex tmpcineplex;
		while (true) {
			try {
				List<Cineplex> Cine = controller.CineplexManager.getInstance().getCineplexes();
				System.out.println("====================");
				for (int i = 0; i < Cine.size(); i++) {
					System.out.println(i + 1 + ". " + Cine.get(i).getCineplex_name());
				}
				System.out.println("====================");
				System.out.print("Select Cineplex: ");
				tmpcineplex = Cine.get(sc.nextInt() - 1);
				break;
			} catch (InputMismatchException e) {
				if (sc.hasNextLine())
					sc.nextLine();
				System.out.println("Invalid input, please try again!");
			}
			catch (IndexOutOfBoundsException e) {
				System.out.println("Invalid input, please try again!");
			} 
		}
		
		Cinema tmpCinema;
		while (true) {
			try {
				System.out.println("====================");
				List<Cinema> tmpCinemaList = tmpcineplex.getCinemas();
				for (int k = 0; k < tmpCinemaList.size(); k++) {
					System.out.println(k + 1 + ". " + tmpCinemaList.get(k).getCinemaID());
				}
				System.out.println("====================");
				System.out.print("Select Cinema: ");
				tmpCinema = tmpCinemaList.get(sc.nextInt() - 1);
				break;
			} catch (InputMismatchException e) {
				if (sc.hasNextLine())
					sc.nextLine();
				System.out.println("Invalid input, please try again!");
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Invalid input, please try again!");
			} 
		}
		
		Movie tmpMovie;
		while (true) {
			try {
				System.out.println("====================");
				TreeMap<Integer, Integer> newmList = new TreeMap<Integer, Integer>();
				int mapIndex = 0;
				for (int m = 0; m < movieList.size(); m++) {
					if (movieList.get(m).getStatus() != MovieStatus.END_OF_SHOWING) {
						System.out.println(mapIndex + 1 + ". " + movieList.get(m).getMovieTitle());
						newmList.put(mapIndex, m);
						mapIndex++;
					}
				}
				System.out.println("====================");
				System.out.print("Select Movie: ");
				tmpMovie = movieList.get((newmList.get(sc.nextInt() - 1)));
				break;
			} catch (InputMismatchException e) {
				if (sc.hasNextLine())
					sc.nextLine();
				System.out.println("Invalid input, please try again!");
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Invalid input, please try again!");
			} 
		}
		
		LocalDateTime showDateTime=null;
		while (true) {
			try {
				System.out.println("====================");
				System.out.print("Enter Showing Date [dd-MM-yyyy] : ");
				String showDate = sc.next();
				System.out.print("Enter Showing Time [HH:mm] : ");
				String showTime = sc.next();
				showDateTime = LocalDateTime.parse((showDate + " " + showTime),
						SlotManager.getInstance().getFormatter());
				break;
			} catch (DateTimeParseException e) {
				System.out.println("Invalid input, please try again!");
			}
		}
		
		int ranId = ThreadLocalRandom.current().nextInt(100, 999 + 1);
		String slotId = tmpMovie.getMovieTitle().substring(0,2).toUpperCase() 
				+ tmpCinema.getCinemaID().toUpperCase() 
				+ Integer.toString(ranId);
		
		return controller.SlotManager.getInstance().addSlot(slotId, showDateTime, tmpMovie.getTime(), tmpMovie.getMovieTitle(), tmpCinema);
	}

}
