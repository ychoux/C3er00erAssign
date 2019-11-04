package controller;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import entity.Movie;

public class MovieListController {
	static String MOVIEFILE = "src/data/movielist.csv";
	String cvsSplitBy = ",";
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
							moviecsv[6],Integer.parseInt(moviecsv[7]),Double.parseDouble(moviecsv[8]));
					movieList.add(movietmp);
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		return movieList;
    }
    //(String movieTitle, String synopsis, String director, String cast, String genre, Duration time,String status, int sales,double overallRating)


    public static void addMovieList(List<Movie> mList, String name, String synopsis,String director, String cast, String genre, 
    		Duration time, String status, int sale, double rating ) {
    	Movie movietmp;
    	movietmp = new Movie(name,synopsis,director,cast,genre, time,status,sale,rating);
    	mList.add(movietmp);
    	updateMovieListCSV(mList);
    }
    
    public static void delMovieList(List<Movie> mList,int id) {
    	mList.remove(id);
    	updateMovieListCSV(mList);
    }
    public static void updateMovieListCSV(List<Movie> mList) {
		FileWriter csvWriter;
		String cvsSplitBy = ",";
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

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

    
}