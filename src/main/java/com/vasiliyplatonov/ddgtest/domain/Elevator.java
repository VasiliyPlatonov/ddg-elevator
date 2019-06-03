package com.vasiliyplatonov.ddgtest.domain;

public interface Elevator {
    /* Elevator stop speed per seconds */
    int STOP_SPEED = 2;
    /* The speed per seconds at which the elevator passes one floor */
    int MOVE_SPEED = 10;


    void moveToFloor(int floor) throws IllegalFloorException;

    String getState();
}
