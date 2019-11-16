package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * The Customer class containing the specific user's details
 * @author kaishern
 */
public class Customer {
    /**
     * The username, used for verification
     */
    private String username;

    /**
     * User's name
     */
    private String name;

    /**
     * User's email
     */
    private String email;

    /**
     * User's phone number, used for verification
     */
    private int phone;

    /**
     * A list storing the TicketIDs booked by the user
     */
    private List<String> ticket = new ArrayList<>(); //to be changed to ticket class


    /**
     * Function to get the user's username
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Function to get the user's name
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Function to get the user's email
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Function to get the phone number of user
     * @return The phone number
     */
    public int getPhone() {
        return phone;
    }

    /**
     * Function to get list of tickets booked
     * @return The list of tickets booked
     */
    public List<String> getTicket() {
        return ticket;
    }

    /**
     * Function to add a newly booked ticket
     * @param tic_id TicketID to be added to the list
     */
    public void addticket(String tic_id){
        this.ticket.add(tic_id);
    }

    /**
     * Constructor for a customer
     * @param username Username
     * @param name Name
     * @param email Email address
     * @param phone Phone number
     * @param ticket Tickets booked, empty for new customer
     */
    public Customer(String username ,String name, String email, int phone, List<String> ticket ) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.ticket = new ArrayList<>(ticket);
    }

}
