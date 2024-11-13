package edu.curtin.oose2024s1.assignment2.factory;
import edu.curtin.oose2024s1.assignment2.BikeShop;

//define the PickUpMessage class that implements the Message interface
public class PickUpMessage implements Message {
    private String email;
  
    public PickUpMessage(String email) {
      this.email = email;
    }
  
    @Override
    public void process(BikeShop bikeShop) {
      //check any bike available for pickup from calling canPickUp method from bikeshop
      if (bikeShop.canPickUp(email)) {
        //remove that bike from pickupqueue and deduct amount of awaiting pickup bike amount
        bikeShop.getPickUpQueue().remove(email); 
        //deduct amount of awaiting pick up bike count
        bikeShop.awaitingPickUp--;
        //dedcut amount of total bikes
        bikeShop.totalBikes--;
        //print message bike picked up with specific customers email
        System.out.println("Bike picked up by customer: " + email);
        //check if the bike was serviced bike by calling isServicedBike from bikeshop class
        if (bikeShop.isServicedBike(email)) {
          //if the picked up bike is a bike dropped off for service take $100 as serive charge
          bikeShop.receiveServicePayment();
          System.out.println("customer : " + email + " came for pick up  [ Service Bike ] ");
          System.out.println("Service fee of $100 received from " + email);
        } else {
          //if the picked up bike is a bike online Purchase then take $1000 
          bikeShop.bikeShopCash += 1000;
          System.out.println("customer : " + email + " came for pick up  [ bike purchased online ] ");
          System.out.println("$1000 received from " + email + " for bike purchased online.");
        }
      } else {
        //increase failur count if there is no bike for a pickup for a specific user with that email
        bikeShop.failuremassagecount++;
        System.out.println("FAILURE: Pick-up denied: No bike available for pick up for customer: " + email);
      }
    }
  }
  
  