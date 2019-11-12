package entity;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String username;
    private String name;
    private String email;
    private int phone;
    private List<String> ticket = new ArrayList<>(); //to be changed to ticket class


    public Customer() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public List<String> getTicket() {
        return ticket;
    }

    public void setTicket(List<String> ticket) {
        this.ticket = ticket;
    }

    public void addticket(String tic_id){
        this.ticket.add(tic_id);
    }

    public Customer(String username ,String name, String email, int phone, List<String> ticket ) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.ticket = new ArrayList<>(ticket);
    }

}
