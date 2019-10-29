package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import entity.Cinema;
import entity.Cinema.CinemaType;
import entity.Cineplex;


/**
 * The class that loads in all the cineplexes and cinemas from a CSV file and
 * store them in a hashtable
 * @author 
 *
 */
public class CineplexManager {

	
	/**
	 * A dictionary of cineplexes, the key is the cineplex_name and the value is the
	 * Cineplex object
	 */
	private Dictionary<String, Cineplex> cineplexes = new Hashtable<String, Cineplex>();
	
	/**
	 * The header of the CSV file, stored in a list of String
	 */
	private String[] header;
	
	/**
	 * The CineplexManager object instance, used as a singleton
	 */
	private static CineplexManager INSTANCE = new CineplexManager();
	
	/**
	 * The path to the CSV file that stores all the cineplexes and cinema
	 */
	private static final String CINEMASPATH = "src/data/cinemas.csv";
	
	/**
	 * The private constructor of the class
	 * Loads all the cineplexes and cinemas from the CSV file and store them in a hashtable
	 */
	private CineplexManager() {
		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(CINEMASPATH));
			String line = br.readLine();
			String[] row = line.split(",");
			this.header = row;
			
			while ((line = br.readLine()) != null) {
				
				try {
					row = line.split(",");
					String cineplex_name = row[0].toUpperCase();
					String cinemaID = row[1].toUpperCase();
					CinemaType cinema_type = CinemaType.valueOf(row[2].toUpperCase());
					if (this.cineplexes.get(cineplex_name) == null)
						this.cineplexes.put(cineplex_name, new Cineplex(cineplex_name));
					this.cineplexes.get(cineplex_name).addCinema(cinemaID, cinema_type);
				}
				catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Unable to retrieve cinema informations!");
				}
				
			}
			
			br.close();
			this.autoSave();
			
		} 
		catch (IOException e) {
			System.out.println("Unable to retrieve cinema informations!");
		}
		
	}
	
	/**
	 * The function to get the instance of CineplexManager object
	 * @return	The CineplexManager object
	 */
	public static CineplexManager getInstance() {
		return INSTANCE;
	}
	
	/**
	 * The function to get all the available cineplexes
	 * @return	A list of available cineplexes
	 */
	public List<Cineplex> getCineplexes() {
		return Collections.list(this.cineplexes.elements());
	}
	
	/**
	 * The function to get the Cineplex object based on its cineplex_name
	 * @param cineplex_name	The name of the cineplex
	 * @return				The Cineplex object correspond to the name parameter passed in
	 */
	public Cineplex getCineplex(String cineplex_name) {
		return this.cineplexes.get(cineplex_name.toUpperCase());
	}
	
	/**
	 * The function to add a new Cineplex object to the hashtable
	 * @param cineplex_name	The name of the cineplex
	 * @return				A boolean variable indication whether the operation is successful or not
	 * 						Return false if the cineplex name already exists
	 */
	public boolean addCineplex(String cineplex_name) {
		if (this.cineplexes.get(cineplex_name.toUpperCase()) == null) {
			this.cineplexes.put(cineplex_name.toUpperCase(), new Cineplex(cineplex_name));
			return true;
		}
		return false;
	}
	
	/**
	 * The function to remove a Cineplex object from the hashtable
	 * @param cineplex_name	The name of the cineplex to be removed
	 * @return				The removed Cineplex object
	 * 						Return null if the Cineplex object does not exist
	 */
	public Cineplex removeCineplex(String cineplex_name) {
		Cineplex c = this.cineplexes.get(cineplex_name.toUpperCase());
		if (c != null) {
			this.cineplexes.remove(cineplex_name.toUpperCase());
		}
		return c;
	}
	
	/**
	 * The function to save back all cineplexes and cinemas to the CSV file
	 * @return	A boolean variable that indicates whether the operation is successful or not
	 */
	private boolean saveToCSV() {
		
		try {
			
			FileWriter csvWriter = new FileWriter(CINEMASPATH);
			csvWriter.append(String.join(",", this.header));
			csvWriter.append("\n");
            
            for (Cineplex cp: Collections.list(this.cineplexes.elements())) {
            	for (Cinema c: cp.getCinemas()) {
            		StringBuilder sb = new StringBuilder();
            		sb.append(cp.getCineplex_name());
            		sb.append(',');
            		sb.append(c.getCinemaID());
            		sb.append(',');
            		sb.append(c.getCinema_type().toString());
            		sb.append('\n');
	            	csvWriter.append(sb.toString());
            	}
            }           
            
            csvWriter.flush();
            csvWriter.close();
			return true;
		}
		catch (IOException e) {
			return false;
		}
		
	}
	
	/**
	 * The function to automatically save all cineplexes and cinema back to 
	 * the CSV file upon exiting the application
	 */
	private void autoSave() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
	        public void run(){
	            saveToCSV();
	        }
	    });
	}
	
	
	/**
	 * The function used to test the CineplexManager class
	 * Not used in the real application
	 * @param args
	 */
	public static void main(String[] args) {
		CineplexManager.getInstance().getCineplex("star @ jurong east").getCinema("a").getSeating_plan().printSeatingPlan();
		
	}
	
}
