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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
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


    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    public Task replace(Long id, TaskDtoPutBody taskDtoPutBody) {


        Task task = findById(id);
        if (!task.getName().equals(taskRepository.findByName(taskDtoPutBody.getName().trim()).getName()))
            throw new ApiException("A task named: " + taskDtoPutBody.getName().trim() + " already exists");

        Task newTask = TaskDtoPutMapper.INSTANCE.toTask(taskDtoPutBody);
        newTask.setPosition(task.getPosition());
        newTask.setId(task.getId());
        return taskRepository.save(newTask);
    }


    public Task save(TaskDtoPostBody taskDtoPostBody) {

        if (taskRepository.findByName(taskDtoPostBody.getName().trim()) != null)
            throw new ApiException("A task named: " + taskDtoPostBody.getName().trim() + " already exists");
        Task task = TaskDtoPostMapper.INSTANCE.toTask(taskDtoPostBody);
        long count = taskRepository.count() + 1;
        task.setPosition((int) count);
        return taskRepository.save(task);
    }


    public Task updatePartially(Long id, TaskDtoPatchBody taskDtoPatchBody) throws IllegalAccessException {
        Task oldTask = findById(id);
        Task newTask = TaskPatchMapper.INSTANCE.toTask(taskDtoPatchBody);
        newTask.setId(oldTask.getId());
        for (Field declaredField : oldTask.getClass().getDeclaredFields()) {
            declaredField.setAccessible(true);
            if (declaredField.get(newTask) != null && !declaredField.get(newTask).equals(declaredField.get(oldTask)))
                declaredField.set(oldTask, declaredField.get(newTask));
        }
        return this.taskRepository.save(oldTask);
    }

    public void delete(Long id) {
        taskRepository.delete(findById(id));
    }
}
