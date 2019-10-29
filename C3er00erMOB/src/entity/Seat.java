package entity;


public class Seat {

	private String ID;
	private boolean available;
	
	public Seat(String iD, boolean available) {
		ID = iD.toUpperCase();
		this.available = available;
	}
	
	public String getID() {
		return ID;
	}
	
	public void setID(String iD) {
		ID = iD.toUpperCase();
	}
	
	public boolean isavailable() {
		return available;
	}
	
	public void setavailable(boolean available) {
		this.available = available;
	}
	
	public static void main(String[] args) {
		
	}
	
}
