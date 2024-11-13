package edu.curtin.app;
import java.util.List;
import java.util.Scanner;

//Strategy for Revised Estimate (Option 3)
public class ReviseEstimateStrategy implements ReconciliationInterface {
    private final Scanner scanner;

    public ReviseEstimateStrategy(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    //Ask the estimators to discuss among themselves, and request a single revised estimate.
    public int reconcileEstimates(List<Integer> estimates) {
        System.out.println("Estimators, please discuss and provide a single revised estimate:");
        System.out.print("Revised Estimate: ");
        int revisedEstimate = scanner.nextInt();
        //taking new estimation and retrn it as the new estimation for the task
        scanner.nextLine(); 
        return revisedEstimate;
    }
}
