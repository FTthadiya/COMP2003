package edu.curtin.oose2024s1.assignment2;
import edu.curtin.oose2024s1.assignment2.observer.ServiceBikeObserver;
import java.io.*;
import java.util.logging.*;

public class App {

    private static long simulationTime = 0;
    private static final Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        //logger to log to a file
        try {
            FileHandler fileHandler = new FileHandler("app.log", true);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.addHandler(fileHandler);
            logger.setLevel(Level.INFO);
        } catch (IOException e) {
            System.err.println("Error setting up logger: " + e.getMessage());
        }

        //redirecting console output to System.out and simulation_output file.
        try (FileOutputStream fos = new FileOutputStream("simulation_output.txt");
             TeeOutputStream tos = new TeeOutputStream(System.out, fos);
             PrintStream ps = new PrintStream(tos)) {

            //System.out to the TeeOutputStream
            System.setOut(ps);

            //initializing input reader
            BikeShopInput inputfile = new BikeShopInput();

            //BikeShop instance with initial bike count of 50
            BikeShop bikeShop = new BikeShop(50); 
            //observer to bike shop 
            bikeShop.addObserver(new ServiceBikeObserver());
          
            try {
                //Running loop until user presses enter
                while (System.in.available() == 0) {

                    //Increasing simulation time which is days
                    incrementSimulationTime();

                    //Calling method from bikeShop to issue pay check every 7 days
                    bikeShop.issuePaycheck();
                    System.out.println();

                    //Printing current day 
                    System.out.println("Day : " + (simulationTime - 1));
                    System.out.println();

                    //Processing input messages from BikeShopInput file
                    String msg = inputfile.nextMessage();
                    while (msg != null) {
                        System.out.println();
                        System.out.println(msg);
                        //Log the message
                        logger.info("Message :");  
                        logger.info(msg);  
                        SimulationInput.processMessage(msg, bikeShop);
                        msg = inputfile.nextMessage();
                    }
                    
                    //Printing daily report
                    System.out.println();
                    System.out.println("----------DAILY REPORT-----("+(simulationTime - 1)+")-----");
                    System.out.println("Days Elapsed : " + (simulationTime - 1));
                    System.out.println("Cash In Bank: " + bikeShop.getBikeShopCash());
                    System.out.println("Available For Purchase : " + bikeShop.getAvailableBikes());
                    System.out.println("Bikes Being Serviced : " + bikeShop.getServiceBikes());
                    System.out.println("Bikes Awaiting PickUp : " + bikeShop.getAwaitingPickUp());
                    System.out.println("Total Inventory : " + bikeShop.getTotalBikes());

                    //Delaying for 1 second 
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new AssertionError(e);
                    }
                }

                //Printing end of program message and overall simulation statistics
                System.out.println("");
                System.out.println("-------------Program Ended-------------");
                System.out.println("-----Overall Simulation Statistics-----");
                System.out.println("");
                System.out.println("1. Total Number Of Input Messages : " + SimulationInput.getTotalMessages());
                System.out.println("2. Total Number Of Failures : " + bikeShop.getfailurcount());

            } catch (IOException e) {
                System.out.println("Error reading user input");
            }
        } catch (IOException e) {
            System.err.println("Error setting up output streams: " + e.getMessage());
        }
    }

    //method to get current simulation time
    public static long getSimulationTime() {
        return simulationTime - 2;
    }

    //Method to increment simulation time
    public static void incrementSimulationTime() {
        simulationTime++;
    }
}

//Custom output stream class to duplicate output to both console and output file
class TeeOutputStream extends OutputStream {
    private final OutputStream out1;
    private final OutputStream out2;
    
    public TeeOutputStream(OutputStream out1, OutputStream out2) {
        this.out1 = out1;
        this.out2 = out2;
    }

    //Writing to both streams by overriding write methods
    @Override
    public void write(int b) throws IOException {
        out1.write(b);
        out2.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        out1.write(b);
        out2.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        out1.write(b, off, len);
        out2.write(b, off, len);
    }

    //Flush method to flush both streams
    @Override
    public void flush() throws IOException {
        out1.flush();
        out2.flush();
    }

    //closing both streams by overriding the close function
    @Override
    public void close() throws IOException {
        out1.close();
        out2.close();
    }
}
