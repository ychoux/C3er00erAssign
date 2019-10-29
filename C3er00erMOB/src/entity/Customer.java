package entity;

public class Customer {

    private String name;
    private String email;
    private int phone;
    private String ticket; //to be changed to ticket class


    public Customer() {
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

    public String getTicket() {
        return ticket;
    }

    public Customer(String name, String email, int phone, String ticket ) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.ticket = ticket;
    }

}
