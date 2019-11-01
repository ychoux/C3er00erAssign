package controller;

import entity.MovieDetail;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CusMovController {

    private static String MOVIEFILE = "/src/data/movie.csv";


    public static  List<MovieDetail> movieCSVRead() {
        BufferedReader br = null;
        String line = "";
        List<MovieDetail> movieDetailList = new ArrayList<>();
        MovieDetail md;
        try {
            br = new BufferedReader(new FileReader(MOVIEFILE));
            while ((line = br.readLine()) != null) {
                String[] mov = line.split(",");
                if (!mov[0].equals("Title")) {
                    md = new MovieDetail(mov[0], mov[1], mov[2], mov[3], mov[4]);
                    movieDetailList.add(md);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieDetailList;
    }

    public static void listMovie(List<MovieDetail> movieList){
        int i =1;
        for (MovieDetail m : movieList){
            System.out.println((i++) + ": " + m.getTitle());
        }
    }

    public static void viewMovieDetails(MovieDetail m){
        System.out.println("Title: "+ m.getTitle());
        System.out.println("Status: " + m.getStatus());
        System.out.println("Type: "+ m.getType());
        System.out.println("Synopsis: "+m.getSynopsis());
        System.out.println("Director: "+ m.getDirector());
    }

}


