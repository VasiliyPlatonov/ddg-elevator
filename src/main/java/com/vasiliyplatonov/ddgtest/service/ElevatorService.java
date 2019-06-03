package com.vasiliyplatonov.ddgtest.service;

import com.vasiliyplatonov.ddgtest.domain.Elevator;
import com.vasiliyplatonov.ddgtest.domain.IllegalFloorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElevatorService {
    @Autowired
    Elevator elevator;

    public void moveToFloor(int floor) throws IllegalFloorException {
        elevator.moveToFloor(floor);
    }

    public String getState() {
        return elevator.getState();
    }
}
