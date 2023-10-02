package me.thiagorigonatti.tasksspring.map;

import me.thiagorigonatti.tasksspring.domain.Task;
import me.thiagorigonatti.tasksspring.dto.TaskDto;

public enum TaskDtoMapper {

    INSTANCE;

    public Task toTask(TaskDto taskDto) {

        if (taskDto == null) return null;

        Task task = new Task();

        task.setName(taskDto.getName());
        task.setCost(taskDto.getCost());
        task.setLimitDate(taskDto.getLimitDate());

        return task;

    }


}
