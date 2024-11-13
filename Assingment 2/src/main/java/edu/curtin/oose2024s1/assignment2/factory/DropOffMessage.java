package edu.curtin.oose2024s1.assignment2.factory;
import edu.curtin.oose2024s1.assignment2.BikeShop;

//define the DropOffMessage class that implements the Message interface
public class DropOffMessage implements Message {
    private String email;
  
    //Constructor ,initializes the email field
    public DropOffMessage(String email) {
      this.email = email;
    }
  
    @Override
    public void process(BikeShop bikeShop) {
      //Call the handleDropOff method on the current state of the bike shop, and passing the email
      bikeShop.currentState.handleDropOff(email);
    }
  }
