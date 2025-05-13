package new_pet_project.repository;

import new_pet_project.entity.ReminderTime;
import new_pet_project.entity.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReminderTimeRepository extends CrudRepository<ReminderTime, Integer> {
    @Query("SELECT rt FROM ReminderTime rt JOIN FETCH rt.task t JOIN FETCH t.user WHERE rt.time <= :now")
    List<ReminderTime> findByTimeLessThanEqualWithTaskAndUser(@Param("now") LocalDateTime now);

    Optional<ReminderTime> findByTaskAndTime(Task task, LocalDateTime reminderTime);
}