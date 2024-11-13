package edu.curtin.app;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// TaskParser class is use to parse tasks from a file
class TaskParser {
    //method to parse tasks from a file and return a list of "Task Interface" objects
    public List<TaskInterface> parseTasksFromFile(String filename) throws IOException {
        //create a list to store parsed tasks
        List<TaskInterface> tasks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            //reading each line from the file
            while ((line = reader.readLine()) != null) {
                //splitting the line into different segments using semicolon ";" to mark boundary 
                String[] parts = line.split(";");
                //cheack of the line has 3 separate parts as parentid,taskid,decription
                if (parts.length >= 3) {
                    //extract details of the tasks 
                    String parentId = parts[0].trim();
                    String taskId = parts[1].trim();
                    String description = parts[2].trim();
                    //parse effort estimate if mentiones if not taken as 0
                    int effortEstimate = (parts.length > 3 && !parts[3].isEmpty()) ? Integer.parseInt(parts[3].trim()) : 0;
                    //implementation of Task (e.g., TaskSingle)
                    TaskInterface task = new TaskSingle(parentId, taskId, description, effortEstimate);
                    //adding taks to the list of tasks
                    tasks.add(task);
                }
            }
        }
         //return the list of parsed tasks
        return tasks;
    }
}


/* 

-- References -- 
Name - How split string at semi-colon that appears before a colon
Link - https://stackoverflow.com/questions/28816142/how-split-string-at-semi-colon-that-appears-before-a-colon <-- refer to find how to split string with semi-colon

*/