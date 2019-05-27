package com.vasiliyplatonov.ddgtest.service.elevator.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vasiliyplatonov.ddgtest.util.Task;

/**
 * An <code>ElevatorTask</code> is a class that encapsulates a task
 * for an Elevator and a description for that task.
 */
public class ElevatorTask implements Runnable {
    @JsonIgnore /* we don`t need the Task on the client just a description */
    private Task task;
    private String description;

    public String getDescription() {
        return description;
    }

    public Task getTask() {
        return task;
    }

    public ElevatorTask(String description, Task task) {
        this.description = description;
        this.task = task;
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public void run() {
        task.apply();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ElevatorTask task1 = (ElevatorTask) o;

        if (task != null ? !task.equals(task1.task) : task1.task != null) return false;
        return description != null ? description.equals(task1.description) : task1.description == null;
    }

    @Override
    public int hashCode() {
        int result = task != null ? task.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
