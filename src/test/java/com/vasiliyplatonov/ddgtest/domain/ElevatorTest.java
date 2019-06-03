package com.vasiliyplatonov.ddgtest.domain;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElevatorTest {

    @Autowired
    @Qualifier("elevator")
    private Elevator elevator;


    @Test
    public void getState() throws Exception {
        // Test 0: initial test
        String expected = "Current floor: 1. Current task: No tasks";

        String actual = elevator.getState();
        assertThat(actual).isEqualTo(expected);


        // Test 1: add task "go to 3 floor" and request state between 2th and 3th floor
        expected = "Current floor: 2. Current task: 'go from 1 to 3 floor'."
                + System.lineSeparator() + "Task list: [go from 1 to 3 floor]";

        elevator.moveToFloor(3);
        Thread.sleep(TimeUnit.MILLISECONDS.convert(
                elevator.MOVE_SPEED + elevator.MOVE_SPEED / 2, TimeUnit.SECONDS));
        actual = elevator.getState();
        assertThat(actual).isEqualTo(expected);
        // waiting for the elevator to arrive
        Thread.sleep(TimeUnit.MILLISECONDS.convert(20, TimeUnit.SECONDS));


        // Test 2: waiting for a minute and check the elevator after
        expected = "Current floor: 3. Current task: No tasks";
        actual = elevator.getState();
        assertThat(actual).isEqualTo(expected);


        // Test 3: add two tasks: "go from 3 to 7 floor" and after 5 second add "go from 7 to 4 floor"
        // and request state between 5th and 6th floor ()
        expected = "Current floor: 5. Current task: 'go from 3 to 7 floor'."
                + System.lineSeparator() + "Task list: [go from 3 to 7 floor, go from 7 to 6 floor]";

        elevator.moveToFloor(7);
        Thread.sleep(2000);
        elevator.moveToFloor(6);
        Thread.sleep(TimeUnit.MILLISECONDS.convert(/* time for two floor and close the doors*/
                elevator.MOVE_SPEED * 2 + elevator.STOP_SPEED, TimeUnit.SECONDS));
        actual = elevator.getState();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void moveToFloor() {
        Stream.of(-3, 0, 9).forEach(badFloor -> {
            Throwable thrown = catchThrowable(() ->
                elevator.moveToFloor(badFloor)
            );

            assertThat(thrown).isInstanceOf(IllegalFloorException.class);

        });
    }
}