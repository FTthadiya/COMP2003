package edu.curtin.app;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

//class to save task to a file
public class TaskSaver {
    public static void saveTasksToFile(TaskManager taskManager, String originalFilename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(originalFilename))) {
            //gettign the task map from the task manager
            Map<String, List<TaskInterface>> taskMap = taskManager.getTaskMap();

            //going through top level tasks
            for (TaskInterface task : taskMap.getOrDefault("", List.of())) {
                //save each top level task 
                saveTask(writer, task, taskMap, "");
            }
        }
    }

    //method to save a task and its subtasks to the file (recursive )
    private static void saveTask(BufferedWriter writer, TaskInterface task, Map<String, List<TaskInterface>> taskMap, String parentTaskId) throws IOException {
      
        if (!parentTaskId.isEmpty()) {
            //if the task has a parent task, keep a space before the task ID
            writer.write(" ");
        }
        writer.write(parentTaskId + " ; " + task.getTaskId() + " ; " + task.getDescription());
    
        //save effort estimate only if it is greater than 0
        int effortEstimate = task.getEffortEstimate();
        if (effortEstimate > 0) {
            writer.write(" ; " + effortEstimate);
        }
    
        writer.newLine(); 
    
        //if the task have sub tasks save them 
        for (TaskInterface subtask : taskMap.getOrDefault(task.getTaskId(), List.of())) {
            //Passing the current task ID as the parent task ID for subtasks
            saveTask(writer, subtask, taskMap, task.getTaskId());
        }
    }
     
}
