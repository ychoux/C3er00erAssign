package controller;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import entity.Movie;
import entity.MovieStatus;

/**
 * The class for all action related to movielist
 * @author
 *
 */
public class MovieListController {
	/**
	 * Path to the csv file that store movielist data
	 */
<<<<<<< HEAD
	//static String MOVIEFILE = "src/data/movielist.csv";
	static String MOVIEFILE = "data/movielist.csv";
	
=======
	static String MOVIEFILE = "src/data/movielist.csv";
>>>>>>> parent of 92d8d6f... IU-2019.2.4 <junro@Jr-Asus Overwrite remote https://github.com/ychoux/C3er00erAssign.git to local
	/**
	 * The seperator in csv file
	 */
	static String cvsSplitBy = ",";
	
	
    /**
     * This function is to fetch movielist data from movielist.csv into list of movie object
     * @return	return list of movie objects
     */
    public List<Movie>getMovieList(){
    	List<Movie>movieList = new ArrayList<>();
    	BufferedReader br = null;
		String line = "";
		Movie movietmp;
		try {
			br = new BufferedReader(new FileReader(MOVIEFILE));
			while((line = br.readLine()) !=null ) {
				String[] moviecsv = line.split(cvsSplitBy);
				if(!moviecsv[0].equals("NAME")) {
					movietmp = new Movie(moviecsv[0],moviecsv[1],moviecsv[2],moviecsv[3],moviecsv[4],Duration.parse(moviecsv[5]),
							MovieStatus.valueOf(moviecsv[6]),Integer.parseInt(moviecsv[7]),Double.parseDouble(moviecsv[8]));
					movieList.add(movietmp);
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		return movieList;
    }
    
    /**
     * This function is to filter out END_OF_SHOWING movies
     * @param mList		A list of movie objects
     * @return			return the filtered list of movie objects
     */
    public static List<Movie> readMovieShowing (List<Movie> mList){
    	List<Movie>movieList = new ArrayList<>();
		Movie movietmp;
		for(Movie m: mList) {
			
			if(m.getStatus().equals(MovieStatus.END_OF_SHOWING)) {
			}
			else {
				//NAME	SYNOPSIS	DIRECTOR	CAST	GENRE	TIME	STATUS	SALES	RATING

				movietmp = new Movie(m.getMovieTitle(),m.getSynopsis(),m.getDirector(),m.getCast(),m.getGenre(),m.getTime()
						,m.getStatus(),m.getSales(),m.getOverallRating());
				movieList.add(movietmp);
				}
		}
		return movieList;
    }
    /**
     * this function is to allow staff to add new movie into movie csv and movie objects
     * @param mList			A list of movie objects
     * @param name			Movie name
     * @param synopsis		Movie Synopsis
     * @param director		Movie Director
     * @param cast			Movie cast
     * @param genre			Movie genre
     * @param time			Movie duration
     * @param status		Movie status
     * @param sale			Movie sales
     * @param rating		Movie Rating
     * @return				It will return true if movies is updated into csv successfully
     */
    public static boolean addMovieList(List<Movie> mList, String name, String synopsis,String director, String cast, String genre, 
    		Duration time, MovieStatus status, int sale, double rating ) {
    	Movie movietmp;
    	movietmp = new Movie(name,synopsis,director,cast,genre, time,status,sale,rating);
    	mList.add(movietmp);
    	return updateMovieListCSV(mList);
    }
    
<<<<<<< HEAD
=======
    
    /**
     * This function allow staff to delete movie in the movie csv and movie objects
     * @param mList		A list of movie objects
     * @param id		selected movie index
     * @return			It will return true if movies is updated into csv successfully
     */
    // will remove if not used
    public static boolean delMovieList(List<Movie> mList,int id) {
    	mList.remove(id);
    	return updateMovieListCSV(mList);

    }
    
>>>>>>> parent of 92d8d6f... IU-2019.2.4 <junro@Jr-Asus Overwrite remote https://github.com/ychoux/C3er00erAssign.git to local
    /**
     * This function is to update movie csv
     * @param mList		A list of movie objects
     * @return			It will return true if movies is updated into csv successfully
     */
    public static boolean updateMovieListCSV(List<Movie> mList) {
		FileWriter csvWriter;
		try {
			csvWriter = new FileWriter(MOVIEFILE);
			csvWriter.append("NAME");
			csvWriter.append(cvsSplitBy);
			csvWriter.append("SYNOPSIS");
			csvWriter.append(cvsSplitBy);
			csvWriter.append("DIRECTOR");
			csvWriter.append(cvsSplitBy);
			csvWriter.append("CAST");
			csvWriter.append(cvsSplitBy);
			csvWriter.append("GENRE");
			csvWriter.append(cvsSplitBy);
			csvWriter.append("TIME");
			csvWriter.append(cvsSplitBy);
			csvWriter.append("STATUS");
			csvWriter.append(cvsSplitBy);
			csvWriter.append("SALES");
			csvWriter.append(cvsSplitBy);
			csvWriter.append("RATING");
			csvWriter.append("\n");
			
			//	NAME	SYNOPSIS	DIRECTOR	CAST	GENRE	TIME 	STATUS		SALES		RATING
			for (Movie movietmp : mList) {
				StringBuilder sb = new StringBuilder();
				sb.append(movietmp.getMovieTitle());
				sb.append(cvsSplitBy);
				sb.append(movietmp.getSynopsis());
				sb.append(cvsSplitBy);
				sb.append(movietmp.getDirector());
				sb.append(cvsSplitBy);
				sb.append(movietmp.getCast());
				sb.append(cvsSplitBy);
				sb.append(movietmp.getGenre());
				sb.append(cvsSplitBy);
				sb.append(movietmp.getTime());
				sb.append(cvsSplitBy);
				sb.append(movietmp.getStatus());
				sb.append(cvsSplitBy);
				sb.append(movietmp.getSales());
				sb.append(cvsSplitBy);
				sb.append(movietmp.getOverallRating());
				sb.append('\n');
				csvWriter.append(sb.toString());
			}

			csvWriter.flush();
			csvWriter.close();
			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

    
}