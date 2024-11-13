package edu.curtin.app;
import java.util.ArrayList;
import java.util.List;


// This "TaskSingle" class implementing Task for single tasks
class TaskSingle implements TaskInterface {
    private String parentId;
    private String taskId;
    private String description;
    private int effortEstimate;

    //Initializing parentID, task ID, there decriptions and effort estimate
    public TaskSingle(String parentId, String taskId, String description, int effortEstimate) {
        this.parentId = parentId;
        this.taskId = taskId;
        this.description = description;
        this.effortEstimate = effortEstimate;
    }

    // Getters and setters

    //get identifier of the parent task of task group
    @Override
    public String getParentId() {
        return parentId;
    }

    //get the identifier of the task
    @Override
    public String getTaskId() {
        return taskId;
    }

    //get the description of the task
    @Override
    public String getDescription() {
        return description;
    }

    //get the efort estimation for task
    @Override
    public int getEffortEstimate() {
        return effortEstimate;
    }

    //set the effort estimation fot the tasks
    @Override
    public void setEffortEstimate(int effortEstimate) {
        this.effortEstimate = effortEstimate;
    }

    //Methods for subtasks are not applicable to single tasks, they are implemented but left empty
    
    @Override
    //not applicable for ingle task, so empty no returns
    public void addTask(TaskInterface task) {}

    @Override
    //not applicable for ingle task, so empty no returns
    public void removeTask(TaskInterface task) {}
   
    //get the sub task list 
    @Override
    public List<TaskInterface> getSubtasks() {
        // Not applicable for single tasks, so return an empty list
        return new ArrayList<>();
    }
    
}
