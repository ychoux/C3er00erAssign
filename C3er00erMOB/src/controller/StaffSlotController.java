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
 * This controller is for show time creation
 * 
 * @author JIAYING
 *
 */
public class StaffSlotController {

	/**
	 * The function adds a new slot time showing for a particular cineplex
	 * 
	 * @param movieList Passes in a list of movies that are screening
	 * @return Returns a boolean to see if the slot was created
	 */
	public boolean staffAddSlot(List<Movie> movieList) {
		Scanner sc = new Scanner(System.in);

		Cineplex tmpCineplex;
		while (true) {
			try {
				List<Cineplex> cineplexList = controller.CineplexManager.getInstance().getCineplexes();
				System.out.println("====================");
				for (int i = 0; i < cineplexList.size(); i++) {
					System.out.println(i + 1 + ". " + cineplexList.get(i).getCineplex_name());
				}
				System.out.println("====================");
				System.out.print("Select Cineplex: ");
				tmpCineplex = cineplexList.get(sc.nextInt() - 1);
				break;
			} catch (InputMismatchException e) {
				if (sc.hasNextLine())
					sc.nextLine();
				System.out.println("Invalid input, please try again!");
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Invalid input, please try again!");
			}
		}

		Cinema tmpCinemaObj;
		while (true) {
			try {
				System.out.println("====================");
				List<Cinema> tmpCinemaList = tmpCineplex.getCinemas();
				for (int k = 0; k < tmpCinemaList.size(); k++) {
					System.out.println(k + 1 + ". " + tmpCinemaList.get(k).getCinemaID());
				}
				System.out.println("====================");
				System.out.print("Select Cinema: ");
				tmpCinemaObj = tmpCinemaList.get(sc.nextInt() - 1);
				break;
			} catch (InputMismatchException e) {
				if (sc.hasNextLine())
					sc.nextLine();
				System.out.println("Invalid input, please try again!");
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Invalid input, please try again!");
			}
		}

		Movie tmpMovieObj;
		while (true) {
			try {
				System.out.println("====================");
				TreeMap<Integer, Integer> newMovieList = new TreeMap<Integer, Integer>();
				int mapIndex = 0;
				for (int m = 0; m < movieList.size(); m++) {
					if (movieList.get(m).getStatus() != MovieStatus.END_OF_SHOWING) {
						System.out.println(mapIndex + 1 + ". " + movieList.get(m).getMovieTitle());
						newMovieList.put(mapIndex, m);
						mapIndex++;
					}
				}
				System.out.println("====================");
				System.out.print("Select Movie: ");
				tmpMovieObj = movieList.get((newMovieList.get(sc.nextInt() - 1)));
				break;
			} catch (InputMismatchException e) {
				if (sc.hasNextLine())
					sc.nextLine();
				System.out.println("Invalid input, please try again!");
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Invalid input, please try again!");
			}
		}

		LocalDateTime showDateTime = null;
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
		String slotId = tmpMovieObj.getMovieTitle().substring(0, 2).toUpperCase()
				+ tmpCinemaObj.getCinemaID().toUpperCase() + Integer.toString(ranId);

		return controller.SlotManager.getInstance().addSlot(slotId, showDateTime, tmpMovieObj.getTime(),
				tmpMovieObj.getMovieTitle(), tmpCinemaObj);
	}

}
