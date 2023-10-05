package me.thiagorigonatti.tasksspring.repository;

import me.thiagorigonatti.tasksspring.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

  Task findByName(String name);

  List<Task> findByOrderByPosition();
}
