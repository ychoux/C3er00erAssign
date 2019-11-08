package controller;

import entity.Customer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CustomerController {

    private static final String USERFILE = "src/data/user.csv";
    private Customer customer;
    private String[] header;
    private  static  CustomerController INSTANCE = new CustomerController();
    public CustomerController() {
        try {

            BufferedReader br = new BufferedReader(new FileReader(USERFILE));
            String line = br.readLine();
            String[] row = line.split(",");
            this.header = row;

            while ((line = br.readLine()) != null) {

                try {
                    row = line.split(",");
                    String name = row[0].toUpperCase();
                    String mail = row[1].toUpperCase();
                    int phone = Integer.parseInt(row[2]);
                    List<String> ticket = Arrays.asList(row[3].split("\\+"));
                    this.customer = new Customer(name, mail, phone, ticket);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Unable to retrieve ticket information!");
                }

            }

            br.close();
            this.autoSave();

        } catch (IOException e) {
            System.out.println("Unable to retrieve ticket information!");
        }
    }

    public void addTic(String Tic_id){
        if (Tic_id == null)
            return;
        customer.getTicket().add(Tic_id);
        this.saveToCSV();
    }

    public void print(){
        System.out.println("Name " + customer.getName());
        System.out.println("Mail " + customer.getEmail());
        System.out.println("Phone "+ customer.getPhone());

        System.out.println("Booking History: ");
        for (String s : customer.getTicket()){
            System.out.println((customer.getTicket().indexOf(s)+1)+ ". "+
                    s);
        }

        System.out.println("You can check ticket detail by selecting corresponding number");
        System.out.println("Other number to exit");
        Scanner sc = new Scanner(System.in);
        try {
            int choice = sc.nextInt();
            if (choice <= customer.getTicket().size()) {
                checkTic(choice);
            }
        }catch (InputMismatchException e){}

    }
    private void checkTic(int index)
    {
        String ticID = customer.getTicket().get(index-1);
        TicketManager.getInstance().printTicketDetails(ticID);
    }
    public  static CustomerController getInstance(){return INSTANCE;}
    private boolean saveToCSV() {

        try {

            FileWriter csvWriter = new FileWriter(USERFILE);
            csvWriter.append(String.join(",", this.header));
            csvWriter.append("\n");
            StringBuilder sb = new StringBuilder();
            sb.append(customer.getName());
            sb.append(',');
            sb.append(customer.getEmail());
            sb.append(',');
            sb.append(customer.getPhone());
            sb.append(',');
            sb.append(String.join("+", customer.getTicket()));
            sb.append("+\n");
            csvWriter.append(sb.toString());

            csvWriter.flush();
            csvWriter.close();
            return true;
        }
        catch (IOException e) {
            return false;
        }
    }

    private void autoSave() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run(){
                saveToCSV();
            }
        });
    }

    public static void main(String[] args) {
        //CustomerController.getInstance().addTic("JOK000120191106220743");
        CustomerController.getInstance().print();
    }
}

