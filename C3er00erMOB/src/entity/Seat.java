package entity;


/**
 * The seat in a cinema or seating plan
 * @author Yew Wei Chee
 *
 */
public class Seat {

	/**
	 * The seat ID
	 */
	private String ID;
	
	/**
	 * The availability of the seat
	 */
	private boolean available;
	
	/**
	 * The constructor of the seat
	 * @param iD			The seat ID
	 * @param available		The availability of the seat
	 */
	public Seat(String iD, boolean available) {
		ID = iD.toUpperCase();
		this.available = available;
	}
	
	/**
	 * The function to get the seat ID
	 * @return The seat ID
	 */
	public String getID() {
		return ID;
	}
	
	/**
	 * The function to set the seat ID
	 * @param iD the seat ID
	 */
	public void setID(String iD) {
		ID = iD.toUpperCase();
	}
	
	/**
	 * The function to get the availability of the seat
	 * @return The availability of the seat
	 */
	public boolean isavailable() {
		return available;
	}
	
	/**
	 * The function to set the availability of the seat
	 * @param available The availability of the seat
	 */
	public void setavailable(boolean available) {
		this.available = available;
	}
	
}
