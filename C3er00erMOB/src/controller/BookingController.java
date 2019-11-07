package controller;

import entity.Movie;
import entity.Slot;
import entity.Ticket;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class BookingController {
    private static BookingController INSTANCE = new BookingController();
    private List<Slot> result;
    public static BookingController getInstance() {
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


    public void booking_flow(List<Movie> movieDetailList)

    {
        CusMovController.getInstance().listMovie(movieDetailList);
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("\nEnter the number of movie to see description: (others to quit)");
            int ch = sc.nextInt();
            CusMovController.getInstance().viewMovieDetails(movieDetailList.get(ch - 1));

            System.out.println("Enter 1 to show the slots available, others to go back");
            int ch1 = sc.nextInt();
            if (ch1 == 1) {
                List<Slot> result;
                Slot slot ;
                result = BookingController.getInstance().viewAvailableSlot(movieDetailList.get(ch - 1).getMovieTitle());
                if (!result.isEmpty()){
                    System.out.println("Choose the slot you are interested in");
                    int ch2 = sc.nextInt();
                    slot = result.get(ch2 - 1);
                    planofcineplex(slot);
                }
                else {System.out.println("No slot available for this movie. Sorry!");return;}

                String seat;
                List<String> seats = new ArrayList<>();
                List<Ticket.TicketType> tic_type = new ArrayList<>();
                System.out.println("Enter the number of seat you want to book");
                int no_of_tic = sc.nextInt();

                for (int i = 0; i < no_of_tic; i++) {
                    System.out.println("Enter the seat you want to book");
                    seat = sc.next();
                    if(slot.getBookings().getBookedSeatsID().contains(seat)){
                        System.out.println("You have chosen a booked seat,try again");
                        i--;
                        break;
                    }
                    seats.add(seat);
                    System.out.println("Enter the ticket type");
                    int idx = 1;
                    for (Ticket.TicketType type : Ticket.TicketType.values()) {
                        System.out.println((idx++) + ". " + type);
                    }

                    int ticket_type = sc.nextInt();
                    tic_type.add((Ticket.TicketType.values()[ticket_type - 1]));
                }

                String tic_id = TicketManager.getInstance().addTicket(slot.getSlotID(), seats, tic_type);
                try {
                    TicketManager.getInstance().printTicketDetails(tic_id);
                    System.out.println("Thank you for you payment. Hope you enjoy the movie");
                    System.out.println("You can view your booking history in user portal");
                    CustomerController.getInstance().addTic(tic_id);
                }catch(NullPointerException e){System.out.println("You have chosen one or more invalid seat(s), try again");}
            }
        }
        catch (InputMismatchException |ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Invalid Input, going back to main menu");
        }
    }
}


