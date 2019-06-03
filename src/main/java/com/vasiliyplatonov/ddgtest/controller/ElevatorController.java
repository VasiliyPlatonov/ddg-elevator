package com.vasiliyplatonov.ddgtest.controller;


import com.vasiliyplatonov.ddgtest.domain.IllegalFloorException;
import com.vasiliyplatonov.ddgtest.service.ElevatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("elevator")
public class ElevatorController {
    @Autowired
    ElevatorService service;

    @GetMapping("/state")
    public String getState() {
        return service.getState();
    }

    @PostMapping("{floor}")
    public String moveToFloor(@PathVariable int floor) {
        try {
            service.moveToFloor(floor);
        } catch (IllegalFloorException e) {
            return e.getMessage();
        }
        return null;
    }


}
