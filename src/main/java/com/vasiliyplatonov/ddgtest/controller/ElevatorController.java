package com.vasiliyplatonov.ddgtest.controller;


import com.vasiliyplatonov.ddgtest.domain.Elevator;
import com.vasiliyplatonov.ddgtest.service.elevator.ElevatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;


@Controller
public class ElevatorController {
    @Autowired
    ElevatorService service;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/elevator")
    public Elevator getElevator() {
        System.out.println("getElevator");
        return service.getElevator();
    }

    @MessageMapping("/moveToFloor")
    @SendTo("/topic/elevator")
    public void moveToFloor(int floor) {
        System.out.println("move to floor " + floor);
        service.moveToFloor(floor,
                () -> messagingTemplate.convertAndSend("/topic/elevator", service.getElevator()));
    }

    @MessageMapping("/stop")
    @SendTo("/topic/elevator")
    public Elevator stopElevator() {
        System.out.println("stopElevator");
        service.stop();
        return service.getElevator();
    }
}
