package com.vasiliyplatonov.ddgtest.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;


@Getter
public class ElevatorImpl implements Elevator {
    private final int MAX_FLOOR;
    private final int MIN_FLOOR;
    private int currentFloor;
    private ElevatorTask currentTask;
    private LinkedList<ElevatorTask> taskList = new LinkedList<>();

    public ElevatorImpl(int minFloor, int maxFloor) {
        MAX_FLOOR = maxFloor;
        MIN_FLOOR = minFloor;
        this.currentFloor = 1;   // by default on the first floor
    }


    public String getState() {
        calcState();

        if (taskList.isEmpty())
            return String.format("Current floor: %d. Current task: No tasks", currentFloor);
        else
            return String.format("Current floor: %d. Current task: '%s'.%nTask list: %s",
                    currentFloor, taskList.peekFirst(), taskList);
    }

    public void moveToFloor(int floor) throws IllegalFloorException {
        if (floor == currentFloor)
            throw new IllegalFloorException("You have chosen the current floor. Please choose another." +
                    "Max floor is " + MAX_FLOOR + ". Min floor is " + MIN_FLOOR);

        if (floor < MIN_FLOOR || floor > MAX_FLOOR)
            throw new IllegalFloorException("You have chosen an invalid floor. Please choose another." +
                    "Max floor is " + MAX_FLOOR + ". Min floor is " + MIN_FLOOR);

        // is stop_speed + (the finish time of the last task in queue or current time if there are no tasks)
        long start;
        // is stop_speed*2(close+open) the required time for task + its start time
        long finish;

        if (taskList.isEmpty()) {
            start = TimeUnit.SECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);
            finish = start + STOP_SPEED * 2 + (Math.abs(currentFloor - floor) * MOVE_SPEED);
            taskList.addLast(new ElevatorTask(currentFloor, floor, start, finish));
        } else {
            start = taskList.getLast().finish;
            finish = start + STOP_SPEED * 2 + Math.abs(taskList.getLast().getTargetFloor() - floor) * MOVE_SPEED;
            taskList.add(new ElevatorTask(taskList.getLast().getTargetFloor(), floor, start, finish));
        }
    }

    private void calcState() {
        if (taskList.isEmpty()) return; // elevator`s stopped, no tasks and only silence around...

        long now = TimeUnit.SECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);


        // calculate current task
        if (taskList.size() == 1)  // if task list has only one task
            currentTask = taskList.getFirst();
        else {
            currentTask = taskList.stream()
                    .filter(t -> t.finish > now && t.start < now)
                    .findFirst()
                    .orElse(taskList.getLast());
        }


        // calculate current floor
        if (now > currentTask.finish) {  // if the last task is already finished
            currentFloor = currentTask.targetFloor;
            taskList.clear();
        } else {
            int passedFloor = (int) (now - currentTask.start) / MOVE_SPEED;
            if (passedFloor == 0) currentFloor = currentTask.prevFloor;
            else {
                if (currentTask.prevFloor < currentTask.targetFloor) // go to up
                    currentFloor = currentTask.prevFloor + passedFloor;
                else //go to down
                    currentFloor = currentTask.prevFloor - passedFloor;
            }
            // remove all already finished tasks
            taskList.removeIf(t -> t.finish <= currentTask.start);
        }
    }


    @Getter
    @Setter
    @AllArgsConstructor
    private class ElevatorTask {
        private final int prevFloor;
        private final int targetFloor;
        private final long start;  /* time is calculated in seconds */
        private final long finish;

        @Override
        public String toString() {
            return "go from " + prevFloor + " to " + targetFloor + " floor";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ElevatorTask that = (ElevatorTask) o;

            return start == that.start;
        }

        @Override
        public int hashCode() {
            return (int) (start ^ (start >>> 32));
        }
    }
}
