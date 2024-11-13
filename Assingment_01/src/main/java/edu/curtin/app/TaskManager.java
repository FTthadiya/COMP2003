package edu.curtin.app;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

//This class does all the tasks related operations, including task parsing, task printing, calcultaing effort summery,task searching and updating, etc....
class TaskManager {
    //Logger for logging messages
    private static final Logger LOGGER = Logger.getLogger(TaskManager.class.getName());
    //Map to store task with parent id's
    private Map<String, List<TaskInterface>> taskMap;
    //strategy for Reconciliation approaches
    private ReconciliationInterface reconciliationStrategy; 
   
    //initialize HashMap for task "Task Map" 
    public TaskManager() {
        taskMap = new HashMap<>();
    }

    // Getters and setters

    //get the task map
    public Map<String, List<TaskInterface>> getTaskMap() {
        return taskMap;
    }
    
    //get the subtask under a specific parent id
    public List<TaskInterface> getSubtasks(String parentId) {
        return taskMap.getOrDefault(parentId, new ArrayList<>());
    }

    //check if the task has sub task or sub tasks
    public boolean hasSubtasks(String taskId) {
        return !taskMap.getOrDefault(taskId, List.of()).isEmpty();
    }

    //Defult value for number of estimators is set to '3' 
    private int estimators = 3; 

    //setter for estimators
    public void setEstimators(int estimators) {
        this.estimators = estimators;
    }

    //getter for estimators
    public int getEstimators() {
        return estimators;
    }

    //Setter for reconcilation sttrategy 
    public void setReconciliationStrategy(ReconciliationInterface reconciliationStrategy) {
        this.reconciliationStrategy = reconciliationStrategy;
    }
    //getter for reconcilation sttrategy
    public ReconciliationInterface getReconciliationStrategy() {
        return reconciliationStrategy;
    }

    //Method to parse tasks from a file
    public void parseTasksFromFile(String filename) throws IOException {
        try {
            // Parsing tasks from the file using a TaskParser 
            List<TaskInterface> tasks = new TaskParser().parseTasksFromFile(filename);
           
            for (TaskInterface task : tasks) {
                String parentId = task.getParentId();
                //check parent id is empty or not
                if (parentId.isEmpty()) {
                    taskMap.computeIfAbsent("", k -> new ArrayList<>()).add(task); 
                } else {
                    taskMap.computeIfAbsent(parentId, k -> new ArrayList<>()).add(task); 
                }
            }
        } catch (IOException e) {
            //Logging a  message for the error that occurred during parsing
            LOGGER.log(Level.SEVERE, () -> "Error parsing tasks from file : " + e.getMessage());
            LOGGER.log(Level.SEVERE, "Exception : ", e);
            throw e; 
        }
    }

// method to print task recursively
private void printTasksRecursive(TaskInterface task, int depth) {
    // indentation string based on the depth of recursion
    StringBuilder indent = new StringBuilder();
    for (int i = 0; i < depth; i++) {
        indent.append("  ");
    }
    
// Check if task has subtasks
List<TaskInterface> subtasks = taskMap.getOrDefault(task.getTaskId(), new ArrayList<>());
    // Check if task doesn't have subtasks
    if (subtasks.isEmpty()) {
        // If task doesn't have subtasks, print task details with effort estimate if available
         String taskDetails = indent.toString() + task.getTaskId() + ": " + task.getDescription();
         String effortDetails = task.getEffortEstimate() > 0 ? ", effort = " + task.getEffortEstimate() : "";
         System.out.println(taskDetails + effortDetails);
    } else {
        // If task has subtasks, print task details without effort estimate
        System.out.println(indent.toString() + task.getTaskId() + ": " + task.getDescription());
        // Recursively print subtasks
        for (TaskInterface subtask : subtasks) {
         printTasksRecursive(subtask, depth + 1);
        }
    }
}

// method to print task in WBS
public void printTasks() {
    System.out.println("\n WBS - Work Breakdown Structure \n");
    // iterating through top level tasks and printing them recursively
    for (TaskInterface task : taskMap.getOrDefault("", new ArrayList<>())) {
        printTasksRecursive(task, 0);
    }
}

//method to print effort summary
public void printEffortSummary() {
    //calculatee known effort
     int totalKnownEffort = calculateTotalKnownEffort();
    //calcaultae unknown tasks
    int unknownTasksCount = countUnknownTasks();
    System.out.println("Total known effort = " + totalKnownEffort);
    System.out.println("Unknown tasks = " + unknownTasksCount);
}

// method to calculate total known effort
private int calculateTotalKnownEffort() {
    return calculateTaskEffortRecursiveLeaf(taskMap.getOrDefault("", new ArrayList<>()));
}

// method to calculate task effort recursively, considering only leaf tasks
private int calculateTaskEffortRecursiveLeaf(List<TaskInterface> tasks) {
    int totalEffort = 0;
    for (TaskInterface task : tasks) {
        if (taskMap.getOrDefault(task.getTaskId(), new ArrayList<>()).isEmpty()) {
            // If task has no subtasks, add its effort to total
            totalEffort += task.getEffortEstimate();
        } else {
            // If task has subtasks, recursively calculate total for subtasks
            totalEffort += calculateTaskEffortRecursiveLeaf(taskMap.getOrDefault(task.getTaskId(), new ArrayList<>()));
        }
    }
    return totalEffort;
}
    
    //method to Count unknown tasks
    public int countUnknownTasks() {
        //count unknown tasks starting from top level tasks
        return countUnknownTasksRecursive(taskMap.getOrDefault("", new ArrayList<>()));
    }
    
    //method to calculte unknown task effort recurcively
    private int countUnknownTasksRecursive(List<TaskInterface> tasks) {
        int unknownTasksCount = 0;
        for (TaskInterface task : tasks) {
            //checking if the task's effort estimate is 0 and it has no subtasks
            if (task.getEffortEstimate() == 0 && taskMap.getOrDefault(task.getTaskId(), new ArrayList<>()).isEmpty()) {
                //both conditions are met, increment the counter
                unknownTasksCount++;
            }
            //recursively call the method for subtasks of the current task
            unknownTasksCount += countUnknownTasksRecursive(taskMap.getOrDefault(task.getTaskId(), new ArrayList<>()));
        }
        return unknownTasksCount;
    }

    //method to find task by id
    public TaskInterface findTaskById(String taskId) {
        //iterating through tasks in the task map
        for (List<TaskInterface> tasks : taskMap.values()) {
            for (TaskInterface task : tasks) {
                //searching or the task with the specified ID
                TaskInterface foundTask = findTaskByIdRecursive(task, taskId);
                //if the task i found return it
                if (foundTask != null) {
                    return foundTask;
                }
            }
        }
        return null;
    }

    private TaskInterface findTaskByIdRecursive(TaskInterface task, String taskId) {
        //Check task has the specific id
        if (task.getTaskId().equals(taskId)) {
            return task;
        } else {
            //if taskdoes not have specific id, recurcivly search in its subtask
            for (TaskInterface subtask : task.getSubtasks()) {
                TaskInterface foundTask = findTaskByIdRecursive(subtask, taskId);
                //if fount retun task
                if (foundTask != null) {
                    return foundTask;
                }
            }
        }
        //if not fount retun null
        return null;
    }

    //method to update task effort 
    public void updateTaskEffort(String taskId, int effortEstimate) {
        //find for the task with id
        TaskInterface task = findTaskById(taskId);
        //if found update its estimate
        if (task != null) {
            task.setEffortEstimate(effortEstimate);
        }
    }

    //method to take Reconcilation estimation approach
    public int calculateReconciledEstimation(List<Integer> estimates, ReconciliationInterface reconciliationStrategy) {
         //call the reconcileEstimates method of the provided reconciliation strategy and pass the list of estimates as input
        return reconciliationStrategy.reconcileEstimates(estimates);
    }
    
}
