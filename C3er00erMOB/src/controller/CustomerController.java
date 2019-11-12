package controller;

import entity.Customer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CustomerController {

    private static final String USERFILE = "src/data/user.csv";
    private List<Customer> customerlst = new ArrayList<>();
    private Customer cus=null;
    private String[] header;

    public CustomerController() {
        try {

            BufferedReader br = new BufferedReader(new FileReader(USERFILE));
            String line = br.readLine();
            String[] row = line.split(",");
            this.header = row;

            while ((line = br.readLine()) != null) {

                try {
                    row = line.split(",");
                    String username = row[0].toUpperCase();
                    String name = row[1].toUpperCase();
                    String mail = row[2].toUpperCase();
                    int phone = Integer.parseInt(row[3]);
                    List<String> ticket = Arrays.asList(row[4].split("\\+"));
                    this.customerlst.add(new Customer(username,name,mail, phone, ticket));
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Unable to retrieve user information!");
                }

            }

            br.close();
            this.autoSave();

        } catch (IOException e) {
            System.out.println("Unable to retrieve user information!");
        }
    }

    public Customer userVeri(String username, int tel_no){
            cus = null;
            for (Customer cust : customerlst){
                if (cust.getUsername().equals(username.toUpperCase()) & cust.getPhone() == tel_no){
                    this.cus = cust;
                    break;
                }
            }
            if (cus!=null) return cus;
            return null;
    }

    public void addUser(String username,String name, String mail,int tel_no){
        Customer new_cus = new Customer(username.toUpperCase(),name.toUpperCase(),mail.toUpperCase(),tel_no,new ArrayList<>());
        customerlst.add(new_cus);
        this.saveToCSV();
    }

    public void update_user(Customer cus) {
        try{
        for (Customer cust : customerlst) {
            if (cust.getUsername().equals(cus.getUsername().toUpperCase()) & cust.getPhone() == cus.getPhone()) {
                customerlst.set(customerlst.indexOf(cust), cus);
                break;
            }
            this.saveToCSV();
        }
        }catch (Exception e){System.out.println("Fail to update in user");}
    }

    public void print(){
        System.out.println("Name " + cus.getName());
        System.out.println("Mail " + cus.getEmail());
        System.out.println("Phone "+ cus.getPhone());

        System.out.println("Booking History: ");
        for (String s : cus.getTicket()){
            System.out.println((cus.getTicket().indexOf(s)+1)+ ". "+
                    s);
        }

        System.out.println("You can check ticket detail by selecting corresponding number");
        System.out.println("Other number to exit");
        Scanner sc = new Scanner(System.in);
        try {
            int choice = sc.nextInt();
            if (choice <= cus.getTicket().size()) {
                checkTic(choice);
            }
        }catch (InputMismatchException e){}

    }
    private void checkTic(int index)
    {
        String ticID = cus.getTicket().get(index-1);
        TicketManager.getInstance().printTicketDetails(ticID);
    }

    public List<Customer> getCustomerlst() {
        return customerlst;
    }

    private boolean saveToCSV() {

        try {

            FileWriter csvWriter = new FileWriter(USERFILE);
            csvWriter.append(String.join(",", this.header));
            csvWriter.append("\n");
            for(Customer customer : this.customerlst){
                StringBuilder sb = new StringBuilder();
                sb.append(customer.getUsername());
                sb.append(',');
                sb.append(customer.getName());
                sb.append(',');
                sb.append(customer.getEmail());
                sb.append(',');
                sb.append(customer.getPhone());
                sb.append(',');
                sb.append(String.join("+", customer.getTicket()));
                sb.append("+\n");
                csvWriter.append(sb.toString());
            }

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
//        CustomerController cc = new CustomerController();
//        Customer css = cc.userVeri("abc",135790);
//        css.addticket("JOK000120191106220743");
//        cc.update_user(css);
//        cc.print();
    }
}

