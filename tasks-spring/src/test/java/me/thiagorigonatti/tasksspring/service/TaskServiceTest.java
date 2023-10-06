package me.thiagorigonatti.tasksspring.service;

import me.thiagorigonatti.tasksspring.domain.Task;
import me.thiagorigonatti.tasksspring.dto.TaskDtoPatchBody;
import me.thiagorigonatti.tasksspring.dto.TaskDtoPostBody;
import me.thiagorigonatti.tasksspring.dto.TaskDtoPutBody;
import me.thiagorigonatti.tasksspring.exception.ApiException;
import me.thiagorigonatti.tasksspring.repository.TaskRepository;
import me.thiagorigonatti.tasksspring.util.TaskUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {


  @Mock
  private TaskRepository taskRepository;

  private TaskService taskService;

  @BeforeEach
  void setUp() {
    taskService = new TaskService(taskRepository);
  }


  @Test
  void findAll_It_Should_Check_Whether_FindAll_Method_Is_Being_Invoked() {

    //when
    taskService.findAll();

    //then
    Mockito.verify(taskRepository).findAll();
  }


  @Test
  void save_It_Should_Check_Whether_Task_Name_Existing_Throws_Exception_When_Creating_Task_With_Same_Name() {

    //given
    TaskDtoPostBody taskDtoPostBody = new TaskDtoPostBody();
    taskDtoPostBody.setName("Test");
    Mockito.when(taskRepository.findByName(Mockito.any(String.class))).thenReturn(TaskUtils.create("Test"));

    //when

    //then
    assertThrows(ApiException.class, () -> taskService.save(taskDtoPostBody));
  }

  @Test
  void save_It_Should_Check_Whether_Positions_Are_Being_Added() {

    //given
    TaskDtoPostBody taskDtoPostBody = new TaskDtoPostBody();
    taskDtoPostBody.setName("Test");

    //when
    taskService.save(taskDtoPostBody);

    //then
    ArgumentCaptor<Task> taskArgumentCaptor = ArgumentCaptor.forClass(Task.class);
    Mockito.verify(taskRepository).save(taskArgumentCaptor.capture());
    Task task = taskArgumentCaptor.getValue();
    assertNotNull(task.getPosition());
  }

  @Test
  void canReplace_It_Should_Check_Whether_Task_Can_Be_Replaced() {

    //given
    Task originalTask = new Task.Builder().id(1L).name("Original Task").position(1L).build();
    TaskDtoPutBody taskDtoPutBody = new TaskDtoPutBody();
    taskDtoPutBody.setName("Replacing Task");

    Mockito.when(taskRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.ofNullable(originalTask));
    Mockito.when(taskRepository.findByName(Mockito.any(String.class))).thenReturn(originalTask);
    //when

    taskService.replace(1L, taskDtoPutBody);

    //then
    ArgumentCaptor<Task> taskArgumentCaptor = ArgumentCaptor.forClass(Task.class);
    Mockito.verify(taskRepository).save(taskArgumentCaptor.capture());
    assertEquals("Replacing Task", taskArgumentCaptor.getValue().getName());
  }

  @Test
  void canReplace_It_Should_Check_Whether_Task_When_Could_Not_Be_Replaced_Throws_Exception() {

    //given
    Task originalTask = new Task.Builder().id(1L).name("Original Task").position(1L).build();
    TaskDtoPutBody taskDtoPutBody = new TaskDtoPutBody();
    taskDtoPutBody.setName("Replacing Task");

    Mockito.when(taskRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.ofNullable(originalTask));
    Mockito.when(taskRepository.findByName(Mockito.any(String.class))).thenReturn(TaskUtils.create("Replacing Task"));


    //then - when
    assertThrows(ApiException.class, () -> taskService.replace(1L, taskDtoPutBody));
  }

  @Test
  void replace_It_Should_Check_Whether_Tasks_Are_Being_Replaced() {

    //given
    Task originalTask = new Task.Builder().id(1L).name("Original Task").position(1L).build();

    TaskDtoPutBody taskDtoPutBody = new TaskDtoPutBody();
    taskDtoPutBody.setName("Replacing Task");

    Mockito.when(taskRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.ofNullable(originalTask));

    //when
    taskService.replace(1L, taskDtoPutBody);

    //then
    ArgumentCaptor<Task> taskArgumentCaptor = ArgumentCaptor.forClass(Task.class);
    Mockito.verify(taskRepository).save(taskArgumentCaptor.capture());

    Task replacingTask = taskArgumentCaptor.getValue();

    assertNotNull(replacingTask.getId());
    assertNotNull(replacingTask.getPosition());
    assertNotNull(originalTask);
    assertEquals(replacingTask.getPosition(), originalTask.getPosition());
    assertEquals(replacingTask.getId(), originalTask.getId());
    assertEquals("Replacing Task", replacingTask.getName());
  }


  @Test
  void updatePartially_It_Should_Check_Whether_Tasks_Are_Being_Replaced_Partially() throws IllegalAccessException {

    //given
    Task originalTask = new Task.Builder()
      .id(1L)
      .name("Original Task")
      .cost(BigDecimal.valueOf(10.0D))
      .limitDate(LocalDate.now().plusDays(15L))
      .position(1L)
      .build();

    TaskDtoPatchBody taskDtoPatchBody = new TaskDtoPatchBody();
    taskDtoPatchBody.setName("Replacing Task");


    Mockito.when(taskRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.ofNullable(originalTask));
    Mockito.when(taskRepository.findByName(Mockito.any(String.class))).thenReturn(originalTask);

    //when
    taskService.updatePartially(1L, taskDtoPatchBody);

    //then
    ArgumentCaptor<Task> taskArgumentCaptor = ArgumentCaptor.forClass(Task.class);
    Mockito.verify(taskRepository).save(taskArgumentCaptor.capture());

    Task updatingTask = taskArgumentCaptor.getValue();

    assertNotNull(originalTask);
    assertEquals(originalTask.getLimitDate(), LocalDate.now().plusDays(15L));
    assertEquals(originalTask.getPosition(), 1L);
    assertEquals(originalTask.getCost(), BigDecimal.valueOf(10.0D));
    assertEquals(updatingTask.getName(), "Replacing Task");
  }


  @Test
  void updatePartially_It_Should_Check_Whether_Throws_Exception_When_Could_Not_Be_Updated() {

    //given
    Task originalTask = new Task.Builder().id(1L).name("Original Task").position(1L).build();
    TaskDtoPatchBody taskDtoPatchBody = new TaskDtoPatchBody();
    taskDtoPatchBody.setName("Original Task");
    Mockito.when(taskRepository.findByName(Mockito.any(String.class))).thenReturn(originalTask);

    //then
    assertThrows(ApiException.class, () -> taskService.updatePartially(2L, taskDtoPatchBody));
  }


  @Test
  void findByOrderByPosition_It_Should_Check_Whether_Its_Being_Invoked() {

    //when
    taskService.findByOrderByPosition("asc");

    //then
    Mockito.verify(taskRepository).findByOrderByPosition();
  }


  @Test
  void delete_It_Should_Check_Whether_Its_Being_Invoked() {

    //given
    Task originalTask = new Task.Builder().id(1L).name("Temp Task").position(1L).build();
    Mockito.when(taskRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.ofNullable(originalTask));

    //when
    taskService.delete(1L);

    //then
    ArgumentCaptor<Task> taskArgumentCaptor = ArgumentCaptor.forClass(Task.class);
    Mockito.verify(taskRepository).delete(taskArgumentCaptor.capture());
    Task deletingTask = taskArgumentCaptor.getValue();
    assertNotNull(deletingTask);
  }
}
