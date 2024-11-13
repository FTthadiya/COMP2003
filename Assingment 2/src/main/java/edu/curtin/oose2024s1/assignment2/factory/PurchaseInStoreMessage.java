package edu.curtin.oose2024s1.assignment2.factory;
import edu.curtin.oose2024s1.assignment2.BikeShop;

//define the PurchaseInStoreMessage class that implements the Message interface
public class PurchaseInStoreMessage implements Message {

    @Override
    public void process(BikeShop bikeShop) {
        //check a bike can be purchased by calling method from bikeshop which retrun are there any available bikes for Purchase
        if (bikeShop.canPurchaseBike()) {
            //add $1000 bike shop cash for Purchase In Store
            bikeShop.bikeShopCash += 1000;
            //deduct amount of available bikes count
            bikeShop.availableBikes--;
            //deduct amount of total bike count
            bikeShop.totalBikes--;
            System.out.println("Bike sold to in-store customer.");
        } else {
            //increment failur count if there no any avialable bike for purchase
            bikeShop.failuremassagecount++;
            System.out.println("FAILURE : Purchase denied: No bikes available for in-store purchase.");
        }
    }
}
