package edu.curtin.app;
import java.util.Collections;
import java.util.List;

// Statergy for Take Median Estimate 
public class TakeMedianEstimateStrategy implements ReconciliationInterface {
    @Override //Take All estimations and sort it and find the median value and return it
    public int reconcileEstimates(List<Integer> estimates) {
        //Sort the elements in ascending order
        Collections.sort(estimates);
        int size = estimates.size();
        if (size % 2 == 0) {
            //If even, return the average of the two middle values
            return (estimates.get(size / 2 - 1) + estimates.get(size / 2)) / 2;
        } else {
            //If odd, return the middle value
            return estimates.get(size / 2);
        }
    }
}
