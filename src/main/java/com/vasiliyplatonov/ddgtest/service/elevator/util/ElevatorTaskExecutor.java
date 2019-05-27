package com.vasiliyplatonov.ddgtest.service.elevator.util;

public interface ElevatorTaskExecutor {

    /**
     * Submits a ElevatorTask for execution. Tasks should be executing in order
     *
     * @param task - the task for executing
     * */
    void submit(ElevatorTask task);

    /**
     * Stop all tasks and remove them from the task queue
     * */
    void stop();
}
