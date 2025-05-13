package new_pet_project.scheduler;

import new_pet_project.entity.ReminderTime;
import new_pet_project.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReminderScheduler {

    @Autowired
    private ReminderService reminderService;

    @Scheduled(cron = "0 * * * * *")
    public void checkAndSendReminders() {
        List<ReminderTime> dueReminders = reminderService.getDueReminders();

        for (ReminderTime reminder : dueReminders) {
            reminderService.processReminder(reminder);
        }
    }
}