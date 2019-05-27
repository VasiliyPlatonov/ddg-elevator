package com.vasiliyplatonov.ddgtest.service.elevator;

import com.vasiliyplatonov.ddgtest.domain.Elevator;
import com.vasiliyplatonov.ddgtest.service.elevator.util.ElevatorTask;
import com.vasiliyplatonov.ddgtest.service.elevator.util.ElevatorTaskExecutorImpl;
import com.vasiliyplatonov.ddgtest.service.elevator.util.IllegalFloorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
public class ElevatorService {
    @Autowired
    private Elevator elevator;

    @Autowired
    private ElevatorTaskExecutorImpl taskExecutor;


    public int getCurrentFloor() {
        return elevator.getCurrentFloor();
    }


    public Elevator getElevator() {
        return elevator;
    }

    public String getCurrentState() {
        return new StringBuilder("Current floor is: ")
                .append(elevator.getCurrentFloor())
                .append(". ")
                .append("Current task is: ")
                .append(elevator.getTaskList().peek().getDescription())
                .toString();
    }

    public void stop() {
        taskExecutor.stop();
        elevator.setStopped(true);
    }

    /**
     * The method for moving the Elevator to the desired floor
     *
     * @param floor target floor
     */
    public void moveToFloor(int floor) {
        taskExecutor.submit(new ElevatorTask(
                        "go to " + floor + "th floor",
                        () -> {
                            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                            ScheduledFuture<?> future = executor.scheduleWithFixedDelay(() -> {
                                        if (getCurrentFloor() == floor) throw new IllegalFloorException();
                                        if (getCurrentFloor() > floor) downFloor();
                                        else if (getCurrentFloor() < floor) upFloor();
                                    },
                                    Elevator.STOP_SPEED, Elevator.MOVE_SPEED, TimeUnit.SECONDS);
                            try {
                                future.get();
                            } catch (IllegalFloorException e) {
                                executor.shutdownNow();
                            } catch (InterruptedException | ExecutionException e) {
                                executor.shutdown();
                            }
                        }
                )
        );
    }

    /**
     * The method for moving the Elevator to the desired floor
     *
     * @param floor    target floor
     * @param callback function that will be called when changing floors
     */
    public void moveToFloor(int floor, Runnable callback) {
        taskExecutor.submit(new ElevatorTask(
                        "go to " + floor + "th floor",
                        () -> {
                            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                            ScheduledFuture<?> future = executor.scheduleWithFixedDelay(() -> {
                                        if (getCurrentFloor() == floor) throw new IllegalFloorException();
                                        if (getCurrentFloor() > floor) downFloor();
                                        else if (getCurrentFloor() < floor) upFloor();
                                        callback.run();
                                    },
                                    Elevator.STOP_SPEED, Elevator.MOVE_SPEED, TimeUnit.SECONDS);
                            try {
                                future.get();
                            } catch (IllegalFloorException e) {
                                executor.shutdownNow();
                            } catch (InterruptedException | ExecutionException e) {
                                executor.shutdown();
                            }
                        }
                )
        );
    }

    private void downFloor() {
        elevator.setCurrentFloor(getCurrentFloor() - 1);
    }

    private void upFloor() {
        elevator.setCurrentFloor(getCurrentFloor() + 1);
    }

}
