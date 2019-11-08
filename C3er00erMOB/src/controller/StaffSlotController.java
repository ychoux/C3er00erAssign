package controller;

import java.util.List;

import entity.Movie;

public class StaffSlotController {

	public void staffAddSlot(List<Movie> movieList) {
		CusMovController.getInstance().listMovie(movieList);
	}
	
}
