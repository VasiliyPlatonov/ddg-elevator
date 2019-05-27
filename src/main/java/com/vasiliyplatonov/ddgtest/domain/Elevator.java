package com.vasiliyplatonov.ddgtest.domain;

import com.vasiliyplatonov.ddgtest.service.elevator.util.ElevatorTask;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Elevator {
    /* Elevator stop speed per seconds */
    public static final int STOP_SPEED = 1;

    /* The speed per seconds at which the elevator passes one floor */
    public static final int MOVE_SPEED = 3;

    private int currentFloor;
    private boolean stopped = false;

    private ConcurrentLinkedQueue<ElevatorTask> taskList = new ConcurrentLinkedQueue<>();


    public ConcurrentLinkedQueue<ElevatorTask> getTaskList() {
        return taskList;
    }
    private void setTaskList(ConcurrentLinkedQueue<ElevatorTask> taskList) {
        this.taskList = taskList;
    }

    public Elevator() {
        this.currentFloor = 1;   // by default on the first floor
    }

    public Elevator(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }
}
