package me.thiagorigonatti.tasksspring.repository;

import me.thiagorigonatti.tasksspring.domain.Task;
import me.thiagorigonatti.tasksspring.util.TaskUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("TaskRepository Tests")
class TaskRepositoryTest {

  @Autowired
  private TaskRepository taskRepositoryTest;

  @Test
  void findByName_It_Should_Check_Whether_Task_Exists_By_Name() {

    //given
    Task task = TaskUtils.create("Have lunch");
    taskRepositoryTest.save(task);

    //when
    Task byName = taskRepositoryTest.findByName("Have lunch");

    //then
    assertNotNull(byName);
    assertNotNull(byName.getId());
    assertTrue(byName.getId() > 0);
  }


  @Test
  void findByOrderByPosition_It_Should_Check_Whether_Tasks_Are_Being_Ordered_Properly() {

    //given
    List<Task> taskListWithShuffledPositions = TaskUtils.createTaskListWithShuffledPositions(10L);
    taskRepositoryTest.saveAll(taskListWithShuffledPositions);

    //when
    List<Task> byOrderByPosition = taskRepositoryTest.findByOrderByPosition();

    //then
    for (int i = 0; i < byOrderByPosition.size(); i++) {
      assertEquals(i + 1, byOrderByPosition.get(i).getPosition());
    }
  }
}
