package edu.curtin.oose2024s1.assignment2.observer;

//Observer interface that defines the update method
public interface Observer {
    
    /**
     * Method to update the observer with the number of bikes being serviced.
     * 'servicingBikes' The number of bikes currently being serviced.
     */
    void update(int serviceingBikes);
}
