package me.thiagorigonatti.tasksspring.controller;


import jakarta.validation.Valid;
import me.thiagorigonatti.tasksspring.domain.Task;
import me.thiagorigonatti.tasksspring.dto.TaskDtoPatchBody;
import me.thiagorigonatti.tasksspring.dto.TaskDtoPostBody;
import me.thiagorigonatti.tasksspring.dto.TaskDtoPutBody;
import me.thiagorigonatti.tasksspring.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }


  @GetMapping({"", "/"})
  public ResponseEntity<List<Task>> findAll(@RequestParam(name = "order", required = false) String order) {
    if (order == null) return new ResponseEntity<>(taskService.findAll(), HttpStatus.OK);
    else return new ResponseEntity<>(taskService.findByOrderByPosition(order), HttpStatus.OK);
  }


  @GetMapping({"/{id}" , "/{id}/"})
  public ResponseEntity<Task> findById(@PathVariable Long id) {
    return new ResponseEntity<>(taskService.findById(id), HttpStatus.OK);
  }


  @DeleteMapping({"/{id}" , "/{id}/"})
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    taskService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }


  @PutMapping({"/{id}" , "/{id}/"})
  public ResponseEntity<Task> replace(@PathVariable Long id, @Valid @RequestBody TaskDtoPutBody taskDtoPutBody) {
    return new ResponseEntity<>(taskService.replace(id, taskDtoPutBody), HttpStatus.OK);
  }


  @PostMapping
  public ResponseEntity<Task> save(@Valid @RequestBody TaskDtoPostBody taskDtoPostBody) {
    return new ResponseEntity<>(taskService.save(taskDtoPostBody), HttpStatus.CREATED);
  }


  @PatchMapping({"/{id}" , "/{id}/"})
  public ResponseEntity<Task> updatePartially(@PathVariable Long id, @Valid @RequestBody TaskDtoPatchBody
    taskDtoPatchBody) throws IllegalAccessException {

    return new ResponseEntity<>(taskService.updatePartially(id, taskDtoPatchBody), HttpStatus.CREATED);
  }
}
