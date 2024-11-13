package edu.curtin.oose2024s1.assignment2.factory;
import edu.curtin.oose2024s1.assignment2.BikeShop;

//The DeliveryMessage class that implements the Message interface
public class DeliveryMessage implements Message {

  public DeliveryMessage() {
  }

  @Override
  public void process(BikeShop bikeShop) {
    //check if the bike shop has enough inventory space for 10 more bikes
    boolean hasEnoughSpace = bikeShop.getTotalBikes() + 10 <= bikeShop.getInventorySpace();
    //check if the bike shop has enogh cash to purchase 10 bikes
    boolean hasEnoughCash = bikeShop.getBikeShopCash() >= 10000;

    if (hasEnoughSpace && hasEnoughCash) {
      //add 10 bikes to available bikes and total bikes
      bikeShop.availableBikes += 10;
      bikeShop.totalBikes += 10;
      //remove $5000 from bike shops cash for 10 bikes
      bikeShop.bikeShopCash -= 5000;
      System.out.println("Delivery accepted: +10 bikes");
    } else {
      //If there is not enough inventory space, print a failure message
      if (!hasEnoughSpace) {
        System.out.println("FAILURE: Delivery denied: Not enough space to store more bikes");
      }
      //If there is not enough cash, print a failure message
      if (!hasEnoughCash) {
        System.out.println("FAILURE: Delivery denied: Not enough cash to purchase new bikes");
      }
      //incremetn no.of failures if not enugh space or cash
      bikeShop.failuremassagecount++;
    }
  }
}
