package edu.curtin.oose2024s1.assignment2.state;
import edu.curtin.oose2024s1.assignment2.BikeShop;

//Define the OutOfStockState class that implements the State interface
public class OutOfStockState implements State {
    private BikeShop bikeShop;

    public OutOfStockState(BikeShop bikeShop) {
        this.bikeShop = bikeShop;
    }

    @Override
    public void handleDropOff(String email) {
        // Check if the servicing queue size is less than the maximum allowed size
        if (bikeShop.getServicingQueue().size() < BikeShop.MAX_QUEUE_SIZE) {
            bikeShop.getServicingQueue().offer(email);
            System.out.print("User With Email: " + email);
            //Update the inventory
            bikeShop.updateInventory(1);
            //Increment the servicingBikes count
            bikeShop.serviceingBikes++;
            //Start a new thread to service the bike
            new Thread(() -> bikeShop.serviceBike(email)).start();
        } else {
            bikeShop.failuremassagecount++;
            System.out.println("FAILURE: Drop-off denied: Servicing queue is full.");
        }
    }
}
