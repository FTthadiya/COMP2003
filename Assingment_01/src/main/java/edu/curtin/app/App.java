package edu.curtin.app;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

//Main class use to call functions from other classes and "Show menu" is implemented here

public class App {

    private static final Logger LOGGER = Logger.getLogger(App.class.getName());
    //taking file name as commarnd line argument
    public static void main(String[] args) {
        if (args.length != 1) {
            System.exit(1);
        }

        String filename = args[0];
        TaskManager taskManager = new TaskManager();

        try {

            //Parsing data from the provided file
            taskManager.parseTasksFromFile(filename);

            LOGGER.info(() -> "Work Breakdown Structure:");
            //Pringting WBS on terminal
            taskManager.printTasks();

            LOGGER.info(() -> "\nEffort Summary:");
            //Pringitng errort summary
            taskManager.printEffortSummary();
            
            Scanner scanner = new Scanner(System.in);
            //Discuss and revised estimate Statergy is set as defult if user not configur with a specific reconciliation approach
            taskManager.setReconciliationStrategy(new ReviseEstimateStrategy(scanner));
            
            showMenu(taskManager, filename);

        } catch (IOException e) {
            LOGGER.severe(() -> "Error: " + e.getMessage());
            LOGGER.severe(() -> "Exception stack trace: " + e);
        }
    }

    //Main menu of the application 
    private static void showMenu(TaskManager taskManager, String filename) throws IOException {
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
       
            while (!quit) {
                //Main Menu Display
                System.out.println("\nMenu:");
                System.out.println("1. Estimate effort");
                System.out.println("2. Configure");
                System.out.println("3. Quit");
                System.out.print("Enter your choice: ");
    
                if (scanner.hasNextInt()) {
                    int choice = scanner.nextInt();
                    scanner.nextLine(); 
    
                    switch (choice) {
                        case 1:
                            //Effort Estimatition 
                            EstimateEffortHandler.handleEstimateEffort(taskManager, scanner);
                            //save after a change
                            TaskSaver.saveTasksToFile(taskManager, filename);
                            break;
                        case 2:
                            //Confiure menu
                            configure(taskManager, scanner);
                            break;
                        case 3:
                            //saving tasks again after before quitting 
                            TaskSaver.saveTasksToFile(taskManager, filename); 
                            quit = true;
                            break;
                        default:
                            System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                            break;
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid choice.");
                    scanner.next(); 
                }
            }
    }
    
    //Configur menu for setting up no.of estimators and reconciliation choice
    private static void configure(TaskManager taskManager, Scanner scanner) {
       
    // Setting estimators amount defult value to be 3
    int estimators = 3;
    boolean validInput = false;
    
    while (!validInput) {
        System.out.print("Enter the number of estimators (N): ");
        if (scanner.hasNextInt()) {
            estimators = scanner.nextInt();
            if (estimators > 0) {
                validInput = true;
            } else {
                System.out.println("Number of estimators must be a positive integer. Please try again.");
            }
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next();
            }
        }

        taskManager.setEstimators(estimators); 
        //Configur Menu Display
        System.out.println("Number of estimators set to: " + estimators);
        System.out.println("Choose reconciliation approach:");
        System.out.println("1. Take the highest estimate");
        System.out.println("2. Take the median estimate");
        System.out.println("3. Discuss and provide a single revised estimate");
        System.out.print("Enter your choice: ");
        int strategyChoice = scanner.nextInt();
        scanner.nextLine(); 
    

        switch (strategyChoice) {
            case 1:
            //Assign selected reconciliation approaches as Take the highest estimate
            taskManager.setReconciliationStrategy(new TakeHighestEstimateStrategy());
            System.out.println("Reconciliation strategy set to: Take the highest estimate");
            break;
        case 2:
            //Assign selected reconciliation approaches as Take the median estimate
            taskManager.setReconciliationStrategy(new TakeMedianEstimateStrategy());
            System.out.println("Reconciliation strategy set to: Take the median estimate");
            break;
        case 3:
            //Assign selected reconciliation approaches to Take revised estimate value
            taskManager.setReconciliationStrategy(new ReviseEstimateStrategy(scanner));
            System.out.println("Reconciliation strategy set to: Discuss and revise estimate");
            break;
        default:
            System.out.println("Invalid choice, default stratagy will use.");
        }

        System.out.println("Reconciliation strategy set Successfully.");
    }
 }
