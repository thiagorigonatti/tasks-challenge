package me.thiagorigonatti.tasksspring.map;

import me.thiagorigonatti.tasksspring.domain.Task;
import me.thiagorigonatti.tasksspring.dto.TaskDtoPutBody;

public enum TaskDtoPutMapper {

    INSTANCE;

    public Task toTask(TaskDtoPutBody taskDtoPutBody) {

        if (taskDtoPutBody == null) return null;

        Task task = new Task();

        task.setName(taskDtoPutBody.getName());
        task.setCost(taskDtoPutBody.getCost());
        task.setLimitDate(taskDtoPutBody.getLimitDate());

        return task;

    }


}
