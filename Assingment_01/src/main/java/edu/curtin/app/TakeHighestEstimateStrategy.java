package edu.curtin.app;
import java.util.List;

// Statergy for Take Highest Estimate 
public class TakeHighestEstimateStrategy implements ReconciliationInterface {
    @Override
    //compare estimations and return highest estimation
    public int reconcileEstimates(List<Integer> estimates) {
        return estimates.stream().max(Integer::compareTo).orElse(0);
    }
}

/*

--Reference--
Name - Stream.max() method in Java with Examples
Link - https://www.geeksforgeeks.org/stream-max-method-java-examples/ <-- use to refer to get an idea about how to take max out of list of intigers (Stream.max() method)

*/
