package assignment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieListCSV {
	String MOVIEFILE = "D:/HDD Desktop/NTU/java/movielist.csv";
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
					/*
					 * System.out.println("ID: "+moviecsv[0]+"Moive: "+moviecsv[1]+"Synopsis: "
					 * +moviecsv[2]+"Director: "+moviecsv[3]
					 * +"Cast: "+moviecsv[4]+"Genre: "+moviecsv[5]+"Status:"+moviecsv[6]+"Sales: "
					 * +Integer.parseInt(moviecsv[7]) +"Rating: "+moviecsv[8]);
					 */
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

    
}