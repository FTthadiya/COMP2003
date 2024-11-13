package edu.curtin.oose2024s1.assignment2.observer;

//concrete implementation of the Observer interface
public class ServiceBikeObserver implements Observer {

     /**
     * Method called to update the observer. In this implementation, it prints a message indicating a bike has been dropped off for servicing.
     * 'servicingBikes' The number of bikes currently being serviced.
     */
    
    @Override
    public void update(int serviceingBikes) {
        //print a message indicating a bike has been dropped off for servicing
        System.out.println("has dropped off a Bike for servicing");
    }
}