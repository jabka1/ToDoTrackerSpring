package team.todotrackerspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.todotrackerspring.model.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser_Username(String username);
    void deleteById(Long id);
}
