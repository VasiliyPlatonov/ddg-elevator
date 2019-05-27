package com.vasiliyplatonov.ddgtest.service.elevator.util;

import com.vasiliyplatonov.ddgtest.domain.Elevator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ElevatorTaskExecutorImpl implements ElevatorTaskExecutor {
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @Autowired
    Elevator elevator;

    public void submit(ElevatorTask task) {
        if (executor.isShutdown()) executor = Executors.newSingleThreadExecutor();
        if (elevator.isStopped()) elevator.setStopped(false);

        elevator.getTaskList().add(task);
        CompletableFuture
                .runAsync(task, executor)
                .thenRun(() -> elevator.getTaskList().remove(task));
    }


    public void stop() {
        executor.shutdownNow();
        elevator.getTaskList().clear();
    }
}
