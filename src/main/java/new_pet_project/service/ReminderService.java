package new_pet_project.service;

import new_pet_project.entity.ReminderTime;
import new_pet_project.entity.Task;
import new_pet_project.entity.User;
import new_pet_project.repository.ReminderTimeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReminderService {

    private final ReminderTimeRepository reminderTimeRepository;

    private final NotificationService notificationService;

    public ReminderService(ReminderTimeRepository reminderTimeRepository, NotificationService notificationService) {
        this.reminderTimeRepository = reminderTimeRepository;
        this.notificationService = notificationService;
    }

    @Transactional(readOnly = true)
    public List<ReminderTime> getDueReminders() {
        LocalDateTime now = LocalDateTime.now();
        return reminderTimeRepository.findByTimeLessThanEqualWithTaskAndUser(now);
    }

    @Transactional
    public void processReminder(ReminderTime reminder) {
        Task task = reminder.getTask();
        User user = task.getUser();

        String subject = "Напоминание: " + task.getTitle();
        String message = String.format(
                "У вас запланирована задача: %s\nОписание: %s\nСрок выполнения: %s",
                task.getTitle(),
                task.getDescription(),
                task.getCompletionData().toString().replace("T",  " ")
        );

        notificationService.sendNotification(user.getEmail(), subject, message);

        reminderTimeRepository.delete(reminder);
    }
}