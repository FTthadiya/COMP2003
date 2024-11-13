package edu.curtin.oose2024s1.assignment2;
import edu.curtin.oose2024s1.assignment2.state.AvailableInventorySpace;
import edu.curtin.oose2024s1.assignment2.state.State;
import edu.curtin.oose2024s1.assignment2.observer.Observer;
import edu.curtin.oose2024s1.assignment2.observer.Subject;
import edu.curtin.oose2024s1.assignment2.state.FullCapacityState;
import edu.curtin.oose2024s1.assignment2.state.OutOfStockState;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

public final class BikeShop implements Subject {

    public int totalBikes;
    public int bikeShopCash;
    private int inventorySpace;
    private int employeePaycheck;

    //queues for servicing and pick-up
    private Queue<String> servicingQueue;
    private Queue<String> pickUpQueue;

    public Object getServicingQueue;
    public static final int MAX_QUEUE_SIZE = 1000;
    public int awaitingPickUp;
    public int serviceingBikes;
    public int availableBikes;
    public int failuremassagecount;

    //list of observers and current state
    private List<Observer> observers;
    public State currentState;

    //initializing the bike shop with initial values ; ex: bike quantity
    public BikeShop(int initialQuantity) {
        failuremassagecount =0;
        awaitingPickUp = 0;
        serviceingBikes = 0;
        availableBikes = initialQuantity;

        //Initial cash in bank
        bikeShopCash = 15000;

        //Max inventory space 
        inventorySpace = 100; 

        //initializing weekly paycheck for employees
        employeePaycheck = 1000; 

        pickUpQueue = new LinkedList<>();
        servicingQueue = new LinkedList<>(); 
        totalBikes = availableBikes+awaitingPickUp+serviceingBikes;
        this.availableBikes = initialQuantity;
        this.serviceingBikes = 0;
        observers = new LinkedList<>();
        this.currentState = new AvailableInventorySpace(this);
    }

    //method to set the state of the bike shop
    public void setState(State state) {
      this.currentState = state;
    }

    //adding observer 
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    //removing an observer from the list
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    //notifying  observer of the change of status
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(serviceingBikes);
        }
    }

    //updating the state based on the number of bikes in inventory
    public void setState() {
      //Check the bike count and set the necessary state
      if (totalBikes >= inventorySpace) {
          currentState = new FullCapacityState(this);
      } else if (availableBikes == 0) {
          currentState = new OutOfStockState(this);
      } else {
          currentState = new AvailableInventorySpace(this);
      }
    }

    //method for updating inventory and notifying observers
    public void updateInventory(int change) {
      totalBikes += change;
      notifyObservers();
      setState();
    }

    //service a bike and move it to the pick-up que
    public void serviceBike(String email) {
      try {
          //simulate servicing 2 days 
          Thread.sleep(2000); 
      } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          System.err.println("Thread interrupted while servicing bike: " + e.getMessage());
      }
      //put bike after service
      pickUpQueue.offer(email);
      serviceingBikes--;
      awaitingPickUp++;
    }
   
    //issuing paycheck to employees every 7 days
    public void issuePaycheck() {
        long time;
        time = App.getSimulationTime();
        if (time>0 && time % 7 == 0) {
            //deduct money from bike shop cash for employee pay check
            bikeShopCash -= employeePaycheck;
            System.out.println("Employee paycheck issued: -$" + employeePaycheck);
        }
    }

    //Getters for cash balance
    public int getBikeShopCash() {
        return bikeShopCash;
    }

    //getter for employee pay check
    public int getEmployeePaycheck() {
        return employeePaycheck;
    }

    //getter for total bikes amount
    public int getTotalBikes() {
      return availableBikes + awaitingPickUp + serviceingBikes;    
    }

    //return if contains email in pick up que
    public boolean canPickUp(String email) {
      return pickUpQueue.contains(email);
    }

    //return if contains email in serviving que
    public boolean isServicedBike(String email) {
      return servicingQueue.contains(email);
    }

    //add 100 cash after service
    public void receiveServicePayment() {
      bikeShopCash += 100;
    }

    //check if bike can purchaseable by checking available bike count
    public boolean canPurchaseBike() {
      return availableBikes > 0;
    }

    //getter for inventory space
    public int getInventorySpace() {
      return inventorySpace;
    }

    //Servicing Queue
    public Queue<String> getServicingQueue() {
      return servicingQueue;
    }

    //return available bikes count
    public int getAvailableBikes() {
      return availableBikes;
    }

    //return serving bikes count
    public int getServiceBikes() {
      return serviceingBikes;
    }
    
    //get awaiting pick up count
    public int getAwaitingPickUp() {
      return awaitingPickUp;
    }

    //get pickup queue
    public Queue<String> getPickUpQueue() {
      return pickUpQueue;
    }
   
    //get fail massage count
    public int getfailurcount() {
      return failuremassagecount;
    }
}
