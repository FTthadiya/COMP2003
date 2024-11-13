package edu.curtin.oose2024s1.assignment2.observer;

//subject interface that defines methods to manage observers
public interface Subject {
    
    //Adds an observer to the subject's list of observers. 
    void addObserver(Observer observer);
    //Removes an observer from the subject's list of observers. 
    void removeObserver(Observer observer);
    //Notifies all observers about an update. 
    void notifyObservers();
}
