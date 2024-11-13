package edu.curtin.oose2024s1.assignment2.state;
import edu.curtin.oose2024s1.assignment2.BikeShop;

//Define the FullCapacityState class that implements the State interface
public class FullCapacityState implements State {
    private BikeShop bikeShop;

    public FullCapacityState(BikeShop bikeShop) {
        this.bikeShop = bikeShop;
    }

    @Override
    public void handleDropOff(String email) {
        //Increment the failure message count
        bikeShop.failuremassagecount++;
        System.out.println("FAILURE: Drop-off denied: Shop is at full capacity.");
    }
}
