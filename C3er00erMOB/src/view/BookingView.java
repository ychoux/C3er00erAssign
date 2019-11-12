package view;

import controller.PrintMovieList;
import controller.SlotManager;
import controller.TicketManager;
import entity.Customer;
import entity.Movie;
import entity.Slot;
import entity.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookingView {
    private static BookingView INSTANCE = new BookingView();
    private List<Slot> result;
    public static BookingView getInstance() {
        return INSTANCE;
    }

    public List<Slot> getResult() {
        return result;
    }

    public void setResult(List<Slot> result) {
        this.result = result;
    }

    public List<Slot>  viewAvailableSlot(String title) {
        List<Slot> result;
        result = SlotManager.getInstance().getMovieSlots(title);
        for (Slot s : result) {
            System.out.println((result.indexOf(s) + 1) + ". " + s.getCinema().getCineplex_name() + " "
                    + s.getShowtime().toLocalDate() + " " + s.getShowtime().toLocalTime());
        }
        return result;
    }

    public void planofcineplex(Slot slot){
        slot.getCinema().getSeating_plan().printSeatingPlan(slot.getBookings());
    }


    public Customer booking_flow (List<Movie> movieDetailList, Customer cus) {
        int stage = 0; //Flow stage
        Scanner sc = new Scanner(System.in);
        int ch = 999,ch1 = 999 ,ch2 = 999; //for user input and going back main menu
        String dummy; //flush
        List<Slot> result = null; // list containing all slots from a chosen movie
        Slot slot = null; //chosen slot
        List<String> seats = new ArrayList<>(); //list of user input seats
        List<Ticket.TicketType> tic_type = new ArrayList<>(); // list of tickets type

        while (ch != 0 & ch1!=0 & ch2 != 0) {
            switch (stage) {
                //movie choosing stage
                case 0:
                    try {
                        System.out.println("\nMovie List\n"+
                                "-------------------");
                        PrintMovieList.MovieTitle(movieDetailList);
                        System.out.println("\nEnter the number of movie to see description: (0 to quit)");
                        ch = sc.nextInt();
                        if (ch>0 & ch<=movieDetailList.size()){
                            PrintMovieList.printMovieList(movieDetailList.get(ch - 1));
                            System.out.println("\nEnter any number except 0 to show the slots available (0 to quit)");
                            ch1 = sc.nextInt();
                            stage = 1;
                        }

                    } catch (Exception e) {
                        if (sc.hasNext())
                            dummy = sc.next();
                        System.out.println("Invalid input\n");
                        stage = 0;
                    }
                    break;
                //slot choosing
                case 1:
                    try {
                        result = BookingView.getInstance().viewAvailableSlot(movieDetailList.get(ch - 1).getMovieTitle());
                        if (!result.isEmpty()) {
                            System.out.println("\nChoose the slot you are interested in (0 to quit, others number to choose another movie)");
                            ch2 = sc.nextInt();
                            if(ch2 > 0 & ch2 <= (result.size())){
                                slot = result.get(ch2 - 1);
                                stage = 2;
                            }
                            else stage=0;
                        }
                        else {
                            System.out.println("No slot available for this movie. Sorry!");
                            stage = 0;
                        }

                    }catch (Exception e) {
                        if (sc.hasNext())
                            dummy = sc.next();
                        System.out.println("Invalid input\n");
                        stage = 1;
                    }
                    break;
                //ticket booking
                case 2:
                    try {
                        seats.clear();
                        tic_type.clear();
                        planofcineplex(slot);
                        System.out.println("Enter the number of seat you want to book , max 5 tickets per booking (0 to quit)");
                        ch = sc.nextInt();
                        int no_of_tic = ch;

                        if (0 < no_of_tic & no_of_tic<= 5){
                            for (int i = 1; i <= no_of_tic; i++) {
                                System.out.println("Enter the seat you want to book: " + i + "th out of " + no_of_tic +" tickets");
                                String seat = sc.next();

                                if (!slot.getCinema().containSeat(seat) | slot.getBookings().getBookedSeatsID().contains(seat)) {
                                    System.out.println("You have chosen a booked/invalid seat,try again");
                                    i--;
                                    continue;
                                }
                                System.out.println("Enter the ticket type");
                                int idx = 1;
                                for (Ticket.TicketType type : Ticket.TicketType.values()) {
                                    System.out.println((idx++) + ". " + type);
                                }
                                int ticket_type = sc.nextInt();
                                if (ticket_type>0 & ticket_type <=Ticket.TicketType.values().length){
                                    if (!seats.contains(seat)) { // to check whether same seat is input
                                        seats.add(seat);
                                        tic_type.add((Ticket.TicketType.values()[ticket_type - 1]));
                                    }
                                    else {System.out.println("You have chosen repeated seats");i--;}
                                }
                                else {System.out.println("Index out of bound");i--;}
                            }
                            stage = 3;
                        }
                        else if(no_of_tic>5){System.out.println("Max tickets amount is 5, you may have multiple transactions");stage = 2;}

                    }catch (Exception e) {
                        if (sc.hasNext())
                            dummy = sc.next();
                        System.out.println("Invalid input, try again");
                        //System.out.println(ch + " "+" "+ch1+" "+ch2);
                        stage = 2;
                    }
                    break;

                case 3:
                    try {
                        System.out.println("Please confirm the tickets detail, tickets sold are not refundable\n" +
                                "---------------------------------------------------------------");
                        TicketManager.getInstance().printTicketDetails(slot,seats,tic_type);
                        System.out.println("\n0. No, I would like to choose another movie (go back main menu)\n"+
                                "1. No, I would like to make changes on seats or ticket type\n"+
                                "2. No, I would like to choose another slot\n"+
                                "3. Yes\n");
                        int ch3 = sc.nextInt();
                        if (ch3==3){
                            String tic_id = TicketManager.getInstance().addTicket(slot.getSlotID(), seats, tic_type);
                            TicketManager.getInstance().printTicketDetails(tic_id);
                            System.out.println("Thank you for you payment. Hope you enjoy the movie");
                            System.out.println("You can view your booking history in user portal");
                            cus.addticket(tic_id);
                            ch = 0;
                            return cus;
                        }
                        else if (ch3==1) stage = 2;
                        else if (ch3==2) stage = 1;
                        else if (ch3==0) ch=0;
                    }catch (Exception e) {
                        if (sc.hasNext())
                            dummy = sc.next();
                        System.out.println("Invalid input, try again");
                        stage = 3;
                    }
                    break;

                default: ch = 0;break;
            }

        }
        return cus;
    }
}



