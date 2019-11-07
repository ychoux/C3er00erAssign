package controller;

import entity.Movie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CusMovController {

    private static String MOVIEFILE = "src/data/movielist.csv";
    private String[] header;
    private List<Movie> movieDetailList = new ArrayList<>();
    private static CusMovController INSTANCE = new CusMovController();

    public static CusMovController getInstance(){return INSTANCE;}

    public List<Movie> movieCSVRead() {
        try {

            BufferedReader br = new BufferedReader(new FileReader(MOVIEFILE));
            String line = br.readLine();
            String[] row = line.split(",");
            this.header = row;

            while ((line = br.readLine()) != null) {

                try {
                    row = line.split(",");
                    this.movieDetailList.add(new Movie(row[0], row[1], row[2], row[3],
                            row[4], Duration.parse(row[5]), row[6], Integer.parseInt(row[7]), Double.parseDouble(row[8])));
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Unable to retrieve ticket information!");
                }

            }

        } catch (IOException e) {
            System.out.println("Unable to retrieve ticket information!");
        }
        return movieDetailList;
    }

    public void listMovie(List<Movie> movieList){
        int i =1;
        for (Movie m : movieList){
            System.out.println((i++) + ": " + m.getMovieTitle());
        }
    }

    public void viewMovieDetails(Movie m){
        System.out.println("Title: "+ m.getMovieTitle());
        System.out.println("Status: " + m.getStatus());
        System.out.println("Genre: "+ m.getGenre());
        System.out.println("Director: "+ m.getDirector());
        System.out.println("Synopsis: "+m.getSynopsis());
        System.out.println("Cast: "+ m.getCast());
        System.out.println("Sales: "+ m.getSales());
        System.out.println("Rating: "+ m.getOverallRating());

    }

}


