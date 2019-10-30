package controller;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
				if(!moviecsv[0].equals("ID")) {
					
					movietmp = new Movie(Integer.parseInt(moviecsv[0]),moviecsv[1],moviecsv[2],moviecsv[3],moviecsv[4],
					              moviecsv[5],moviecsv[6],Integer.parseInt(moviecsv[7]),Double.parseDouble(moviecsv[8]));
					movieList.add(movietmp);
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		return movieList;
    }
    public static void updateMovieListCSV(List<Movie> mList) {
		FileWriter csvWriter;
		try {
			csvWriter = new FileWriter(MOVIEFILE);
			csvWriter.append("ID");
			csvWriter.append(",");
			csvWriter.append("Name");
			csvWriter.append(",");
			csvWriter.append("SYNOPSIS");
			csvWriter.append(",");
			csvWriter.append("DIRECTOR");
			csvWriter.append(",");
			csvWriter.append("CAST");
			csvWriter.append(",");
			csvWriter.append("GENRE");
			csvWriter.append(",");
			csvWriter.append("STATUS");
			csvWriter.append(",");
			csvWriter.append("SALES");
			csvWriter.append(",");
			csvWriter.append("RATING");
			csvWriter.append("\n");
			
			//ID	NAME	SYNOPSIS	DIRECTOR	CAST	GENRE	STATUS	SALES	RATING


			for (Movie movietmp : mList) {
				StringBuilder sb = new StringBuilder();
				sb.append(Integer.toString(movietmp.getId()));
				sb.append(',');
				sb.append(movietmp.getMovieTitle());
				sb.append(',');
				sb.append(movietmp.getSynopsis());
				sb.append(',');
				sb.append(movietmp.getDirector());
				sb.append(',');
				sb.append(movietmp.getCast());
				sb.append(',');
				sb.append(movietmp.getGenre());
				sb.append(',');
				sb.append(movietmp.getStatus());
				sb.append(',');
				sb.append(movietmp.getSales());
				sb.append(',');
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