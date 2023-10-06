package me.thiagorigonatti.tasksspring.service;

import me.thiagorigonatti.tasksspring.domain.Task;
import me.thiagorigonatti.tasksspring.dto.TaskDtoPatchBody;
import me.thiagorigonatti.tasksspring.dto.TaskDtoPostBody;
import me.thiagorigonatti.tasksspring.dto.TaskDtoPutBody;
import me.thiagorigonatti.tasksspring.exception.ApiException;
import me.thiagorigonatti.tasksspring.map.TaskDtoPostMapper;
import me.thiagorigonatti.tasksspring.map.TaskDtoPutMapper;
import me.thiagorigonatti.tasksspring.map.TaskPatchMapper;
import me.thiagorigonatti.tasksspring.repository.TaskRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

@Service
public class TaskService {

  private final TaskRepository taskRepository;


  public TaskService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }


  public List<Task> findAll() {
    return taskRepository.findAll();
  }


  public List<Task> findByOrderByPosition(String order) {
    List<Task> byPosition = taskRepository.findByOrderByPosition();
    if (order.equalsIgnoreCase(Sort.Direction.DESC.name())) Collections.reverse(byPosition);
    return byPosition;
  }


  public Task findById(Long id) {
    return taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }


  private Task replaceInternal(Task task, TaskDtoPutBody taskDtoPutBody) {
    Task newTask = TaskDtoPutMapper.INSTANCE.toTask(taskDtoPutBody);
    newTask.setPosition(task.getPosition());
    newTask.setId(task.getId());
    return taskRepository.save(newTask);
  }


  public boolean canReplace(Task target, Task replacement) {
    if (target.getName().equals(replacement.getName())) return true;
    else throw new ApiException("A task named: `".concat(replacement.getName()).concat("` already exists"));
  }

  public Task replace(Long id, TaskDtoPutBody taskDtoPutBody) {
    Task task = findById(id);
    Task fromPutBody = taskRepository.findByName(taskDtoPutBody.getName());

    if (fromPutBody != null) {
      if (canReplace(task, fromPutBody)) {
        return replaceInternal(task, taskDtoPutBody);
      }
    }
    return replaceInternal(task, taskDtoPutBody);
  }


  public Task save(TaskDtoPostBody taskDtoPostBody) {
    if (taskRepository.findByName(taskDtoPostBody.getName().trim()) != null)
      throw new ApiException("A task named: `".concat(taskDtoPostBody.getName()).concat("` already exists"));
    Task task = TaskDtoPostMapper.INSTANCE.toTask(taskDtoPostBody);
    long count = taskRepository.count() + 1;
    task.setPosition(count);
    return taskRepository.save(task);
  }


  public Task updatePartially(Long id, TaskDtoPatchBody taskDtoPatchBody) throws IllegalAccessException {
    if (taskDtoPatchBody.getName() != null) {
      if (!taskRepository.findByName(taskDtoPatchBody.getName()).getId().equals(id)) {
        throw new ApiException("A task named: `".concat(taskDtoPatchBody.getName()).concat("` already exists"));
      }
    }
    Task oldTask = findById(id);
    Task newTask = TaskPatchMapper.INSTANCE.toTask(taskDtoPatchBody);
    newTask.setId(oldTask.getId());
    for (Field declaredField : oldTask.getClass().getDeclaredFields()) {
      declaredField.setAccessible(true);
      if (declaredField.get(newTask) != null && !declaredField.get(newTask).equals(declaredField.get(oldTask))) {
        declaredField.set(oldTask, declaredField.get(newTask));
      }
    }
    return this.taskRepository.save(oldTask);
  }
  public void delete(Long id) {
    taskRepository.delete(findById(id));
  }
}
