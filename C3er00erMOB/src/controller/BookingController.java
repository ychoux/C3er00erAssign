package controller;

import entity.Slot;

import java.util.List;

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
                    + s.getShowtime().toLocalDate() + " " + s.getShowtime().toLocalTime() + s.getSlotID());
        }
        return result;
    }

    public void planofcineplex(Slot slot){
        slot.getCinema().getSeating_plan().printSeatingPlan(slot.getBookings());
    }
}
