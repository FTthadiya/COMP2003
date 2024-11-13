package edu.curtin.oose2024s1.assignment2.state;
import edu.curtin.oose2024s1.assignment2.BikeShop;

//Define the AvailableInventorySpace class that implements the State interface
public class AvailableInventorySpace implements State {
    private BikeShop bikeShop;

    public AvailableInventorySpace(BikeShop bikeShop) {
        //Assign the bikeShop parameter to the bikeShop field
        this.bikeShop = bikeShop;
    }

    @Override
    public void handleDropOff(String email) {
        //Check if the servicing queue size is less than the maximum allowed size
        if (bikeShop.getServicingQueue().size() < BikeShop.MAX_QUEUE_SIZE) {
            //Add the email to the servicing queue
            bikeShop.getServicingQueue().offer(email);
            System.out.print("User With Email: " + email);
            //Update the inventory
            bikeShop.updateInventory(1);
            //increment the servicingBikes counter
            bikeShop.serviceingBikes++;
            //start a new thread to service the bike
            new Thread(() -> bikeShop.serviceBike(email)).start();
        } else {
            //If the servicing queue is full increment the failure message count
            bikeShop.failuremassagecount++;
            System.out.println("FAILURE: Drop-off denied: Servicing queue is full.");
        }
    }
}
