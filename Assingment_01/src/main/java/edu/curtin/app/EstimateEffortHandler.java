package edu.curtin.app;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//Class to handle the estimation of effort for tasks and there subtasks
public class EstimateEffortHandler {

    //handle Estimations efforts for tasks    
    public static void handleEstimateEffort(TaskManager taskManager, Scanner scanner) {
        System.out.print("Enter task ID: ");
        String taskId = scanner.nextLine();

        // Validate if the task ID contains at least one letter
        if(!isValidTaskId(taskId)) {
             System.out.println("Invalid task ID format. Please enter a task ID.");
             return;
        }

        TaskInterface task = taskManager.findTaskById(taskId);
        if (task != null) {

            //check entered task id has Sub tasks or just a Main Task
            if (taskManager.hasSubtasks(taskId)) {
                System.out.println("Task has subtasks.");
                //call for method to handle Sub Task estimations
                subtaskEffort(taskManager, taskId, scanner);
                //Call method to print Sub Tasks and effort summery
                taskManager.printTasks();
                taskManager.printEffortSummary();

            } else {
                System.out.println("Task does not have subtasks.");
                //call for method to handle Main Task estimations
                taskEffort(taskManager, task, scanner);
                //Call method to print Tasks and effort summary
                taskManager.printTasks();
                taskManager.printEffortSummary();
            }
        } else {
            System.out.println("Task not found.");
        }
    }

    //Method to handle the estimation of the (Task that HAVE Sub Tasks)
    private static void subtaskEffort(TaskManager taskManager, String parentId, Scanner scanner) {
        //list to store subtasks needing estimation
        List<TaskInterface> subtasksNeedingEstimation = new ArrayList<>();
        List<String> allEstimates = new ArrayList<>(); 
        System.out.println("\n");

        //loop through each subtask of the parent task
        for (TaskInterface subtask : taskManager.getSubtasks(parentId)) {
            //pringting sub task details
            System.out.println("\nTask ID : "+subtask.getTaskId()+" , Description : "+subtask.getDescription() + ", effort = " + subtask.getEffortEstimate());
            subtasksNeedingEstimation.add(subtask);
            int estimators = taskManager.getEstimators();
            StringBuilder subtaskEstimates = new StringBuilder();
            subtaskEstimates.append("Estimations for Task ID : ").append(subtask.getTaskId()).append("");
            //List to store effort estimates from each estimator
            List<Integer> estimates = new ArrayList<>(); 

            //taking input from each Estimator
            for (int i = 1; i <= estimators; i++) {
                System.out.print("Enter effort estimate from estimator " + i + ": ");
                int estimate = scanner.nextInt();
                //Adding estimate to the list
                estimates.add(estimate);
                scanner.nextLine(); 
                //Append estimator's estimate to the message "adding estimations and show together, print at once"
                subtaskEstimates.append("\n Estimator ").append(i).append(" - Estimation = ").append(estimate);
            }
            allEstimates.add(subtaskEstimates.toString()); 

            //check for difference estimations and if found use the specific reconciliation statergy configured by user 
            if (estimationDifference(estimates)) {
                int reconciledEstimate = taskManager.calculateReconciledEstimation(estimates, taskManager.getReconciliationStrategy());
                updateTask(taskManager, subtask, reconciledEstimate);
            } else {
                //If there are no differences in estimatations, use the estimate from the first estimator
                int effortEstimate = estimates.get(0); 
                updateTask(taskManager, subtask, effortEstimate);
            }   
        }

        // Print all estimations for subtasks 
        for (String estimates : allEstimates) {
            System.out.println("\n");
            System.out.println(estimates);
        }
    }

    //Method to handle the estimation of the Task (Task does NOT have Sub Task)
    private static void taskEffort(TaskManager taskManager, TaskInterface task, Scanner scanner) {
        // Printing task ID, Task description, and current effort estimate if have
        System.out.println("\nTask ID : " + task.getTaskId() + " , Description : " + task.getDescription() + ", effort = " + task.getEffortEstimate());
        int estimators = taskManager.getEstimators();
        List<Integer> estimates = new ArrayList<>(); 

        for (int i = 1; i <= estimators; i++) {
            int estimate;
            while (true) {
                System.out.print("Enter effort estimate from estimator " + i + ": ");
                try {
                    estimate = Integer.parseInt(scanner.nextLine());
                    break; 
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid integer.");
                }
            }
            // Adding the estimate to the list of estimates
            estimates.add(estimate);
        }
    
        //Cheack differences in estimates from different estimators and use the specific reconciliation statergy configured by user
        if (estimationDifference(estimates)) {
            int reconciledEstimate = taskManager.calculateReconciledEstimation(estimates, taskManager.getReconciliationStrategy());
            updateTask(taskManager, task, reconciledEstimate);
        } else {
           // If there are no differences in estimates, use the estimate from the first estimator          
           int effortEstimate = estimates.get(0); 
           updateTask(taskManager, task, effortEstimate);
        }
    
        //Printing Effort estimation for task
        System.out.println("\nEstimations for Task ID : " + task.getTaskId());
        for (int i = 0; i < estimates.size(); i++) {
            System.out.println("Estimator " + (i + 1) + " - Estimation = " + estimates.get(i));
        }     
    }


    //Method to update task estimations of a task Tasks And it's Sub Tasks both
    private static void updateTask(TaskManager taskManager, TaskInterface task, int effortEstimate) {
        task.setEffortEstimate(effortEstimate); 
        //update the task effort estimation in task manager in order to update the WBS
        taskManager.updateTaskEffort(task.getTaskId(), effortEstimate); 
        String parentId = task.getParentId();

        if (!parentId.isEmpty()) {
            TaskInterface parentTask = taskManager.findTaskById(parentId);
            if (parentTask != null) {
                int totalEffort = 0;
                // Iterate through each subtask of the parent task
                for (TaskInterface subtask : taskManager.getSubtasks(parentId)) {
                    totalEffort += subtask.getEffortEstimate();
                }
                //update the parent task's effort estimate With total effort of subtasks
                taskManager.updateTaskEffort(parentId, totalEffort);
                //recursively  update the tasks
                updateTask(taskManager, parentTask, totalEffort);
            }
        }
    }

    //Method to check is any two (or more) effort estimates are different entered by users
    public static boolean estimationDifference(List<Integer> estimates) {
        if (estimates == null || estimates.size() < 2) {
            return false; 
        }
        //check each estimations provide by user 
        for (int i = 0; i < estimates.size() - 1; i++) {
            for (int j = i + 1; j < estimates.size(); j++) {
                if (!estimates.get(i).equals(estimates.get(j))) {
                    return true; 
                }
            }
        }
        return false; 
    }
    
    // Method to validate if the entered task ID contains at least one letter
    private static boolean isValidTaskId(String taskId) {
    // Regular expression to check if the task ID contains at least one letter
    return taskId.matches(".*[a-zA-Z]+.*");
}
    
}
