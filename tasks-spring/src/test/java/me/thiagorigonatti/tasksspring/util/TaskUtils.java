package me.thiagorigonatti.tasksspring.util;

import me.thiagorigonatti.tasksspring.domain.Task;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskUtils {

  public static Task create() {

    return new Task.Builder()
      .id(null)
      .name("Task %d".formatted(System.nanoTime()))
      .cost(BigDecimal.valueOf(10D))
      .limitDate(LocalDate.now().plusDays(15L))
      .position(null)
      .build();
  }


  public static Task create(String name) {
    Task task = create();
    task.setName(name);
    return task;
  }


  public static List<Task> createTaskList(long amount) {

    List<Task> taskList = new ArrayList<>();

    for (int i = 0; i < amount; i++) {
      taskList.add(create());
    }

    return taskList;
  }


  public static List<Task> createTaskListWithShuffledPositions(Long amount) {

    List<Task> taskList = createTaskList(amount);

    List<Long> positions = new ArrayList<>(amount.intValue());

    for (int i = 0; i < amount; i++) {
      positions.add(i + 1L);
    }

    Collections.shuffle(positions);

    for (Long position : positions) {
      taskList.get(position.intValue() - 1).setPosition(position);
    }

    return taskList;
  }
}
