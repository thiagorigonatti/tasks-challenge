package me.thiagorigonatti.tasksspring.repository;

import me.thiagorigonatti.tasksspring.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Task findByName(String name);
}
