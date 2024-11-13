package edu.curtin.app;
import java.util.ArrayList;
import java.util.List;

// This "TaskGroup" class implement Task for groups of task
class TaskGroup implements TaskInterface {
    private String parentId;
    private List<TaskInterface> subtasks;
    private String description;
    private int effortEstimate;


    //Initialing parentId, description of the task, subtask lis, effort estimate
    public TaskGroup(String parentId, String description) {
        this.parentId = parentId;
        this.description = description;
        subtasks = new ArrayList<>();
        effortEstimate = 0; 
    }

    //Getters And Setters

    //getter for identify the parent task or specifict task group
    @Override
    public String getParentId() {
        return parentId;
    }

    //getter for identify pecifict task group
    @Override
    public String getTaskId() {
        return parentId;
    }

    //get list of Sub tasks
    @Override
    public List<TaskInterface> getSubtasks() {
        return subtasks;
    }

    //add task to list
    @Override
    public void addTask(TaskInterface task) {
        subtasks.add(task);
    }

    //remove task from the list
    @Override
    public void removeTask(TaskInterface task) {
        subtasks.remove(task);
    }

    //getter for deescription of the task
    @Override
    public String getDescription() {
        return description;
    }

    //getter for effort estimation
    @Override
    public int getEffortEstimate() {
        return effortEstimate;
    }

    //setter for effort estimation
    @Override
    public void setEffortEstimate(int effortEstimate) {
        this.effortEstimate = effortEstimate;
    }
}
