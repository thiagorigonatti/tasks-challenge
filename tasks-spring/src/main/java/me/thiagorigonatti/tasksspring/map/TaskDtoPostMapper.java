package me.thiagorigonatti.tasksspring.map;

import me.thiagorigonatti.tasksspring.domain.Task;
import me.thiagorigonatti.tasksspring.dto.TaskDtoPostBody;

public enum TaskDtoPostMapper {

    INSTANCE;

    public Task toTask(TaskDtoPostBody taskDtoPostBody) {

        if (taskDtoPostBody == null) return null;

        Task task = new Task();

        task.setName(taskDtoPostBody.getName());
        task.setCost(taskDtoPostBody.getCost());
        task.setLimitDate(taskDtoPostBody.getLimitDate());

        return task;

    }


}
