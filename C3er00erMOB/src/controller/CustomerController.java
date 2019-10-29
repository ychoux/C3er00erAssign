package controller;

import entity.Customer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CustomerController {

    private static String USERFILE = "src/data/user.csv";
    public CustomerController() {
        BufferedReader br = null;
        String line = "";
        Customer customer;

        try {
            br = new BufferedReader(new FileReader(USERFILE));
            while ((line = br.readLine()) != null) {
                String[] cus = line.split(",");
                if (!cus[0].equals("Name")) {
                    // Debug line
                    // System.out.println("Username: "+user[0]+" Password: "+user[1]+" Salt: "+user[2]);
                    customer = new Customer(cus[0], cus[1], Integer.parseInt(cus[2]), cus[3]);
                    System.out.println(customer.getName() + customer.getEmail() + customer.getPhone() +
                            customer.getTicket());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

