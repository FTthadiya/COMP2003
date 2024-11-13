package edu.curtin.oose2024s1.assignment2.factory;
import edu.curtin.oose2024s1.assignment2.BikeShop;
import java.util.LinkedList;

//define the PurchaseOnlineMessage class that implements the Message interface
public class PurchaseOnlineMessage implements Message {
  private String email;

  //Constructor that initializes the email field
  public PurchaseOnlineMessage(String email) {
    this.email = email;
  }

  @Override
  public void process(BikeShop bikeShop) {
    //check if a bike can be purchased
    if (bikeShop.canPurchaseBike()) {
      System.out.println("Bike sold online to " + email + ". Email sent for confirmation.");
      //add the email to the pick-up queue
      ((LinkedList<String>) bikeShop.getPickUpQueue()).offer(email);
      //increment the awaitingPickUp counter
      bikeShop.awaitingPickUp++;
      //decrement the availableBikes counter
      bikeShop.availableBikes--;
    } else {
      //if no bikes are available for purchase, increment the failure message count
      bikeShop.failuremassagecount++;
      System.out.println("FAILURE: Purchase denied: No bikes available for online purchase.");
    }
  }
}
