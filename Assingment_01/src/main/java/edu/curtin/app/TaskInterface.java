package edu.curtin.app;

import java.util.List;

// Task interface for a single task or group of tasks
interface TaskInterface {
    String getParentId();
    String getTaskId();
    String getDescription();
    int getEffortEstimate();
    void setEffortEstimate(int effortEstimate);
    void addTask(TaskInterface task);
    void removeTask(TaskInterface task);
    List<TaskInterface> getSubtasks();
}

/*

--Reference--
Name - Composite Design Pattern in Java
Link - https://www.geeksforgeeks.org/composite-design-pattern-in-java/ <--Refere to get an idea about composite pattern 

*/