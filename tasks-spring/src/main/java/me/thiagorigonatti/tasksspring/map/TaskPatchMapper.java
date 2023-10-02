package me.thiagorigonatti.tasksspring.map;

import me.thiagorigonatti.tasksspring.domain.Task;
import me.thiagorigonatti.tasksspring.dto.TaskDtoPatchBody;

public enum TaskPatchMapper {

    INSTANCE;


    public Task toTask(TaskDtoPatchBody taskDtoPatchBody) {

        if (taskDtoPatchBody == null) return null;

        Task task = new Task();

        task.setName(taskDtoPatchBody.getName());
        task.setLimitDate(taskDtoPatchBody.getLimitDate());
        task.setCost(taskDtoPatchBody.getCost());

        return task;

    }

}
