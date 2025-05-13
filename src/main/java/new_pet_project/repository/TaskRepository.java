package new_pet_project.repository;

import new_pet_project.entity.Task;
import new_pet_project.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer> {
    Optional<Task> findByTitleAndUser(String title, User user);
}
