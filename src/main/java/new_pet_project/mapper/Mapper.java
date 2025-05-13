package new_pet_project.mapper;

import new_pet_project.DTO.ReminderTimeCreateForm;
import new_pet_project.DTO.TaskCreateForm;
import new_pet_project.DTO.UserCreatForm;
import new_pet_project.entity.ReminderTime;
import new_pet_project.entity.Task;
import new_pet_project.entity.User;

import java.util.ArrayList;
import java.util.List;

public class Mapper {
    public static List<ReminderTimeCreateForm> ReminderTimeListToForm(List<ReminderTime> reminderTimes) {
        List<ReminderTimeCreateForm> list = new ArrayList<>();
        for (ReminderTime reminderTime : reminderTimes) {
            list.add(new ReminderTimeCreateForm(reminderTime.getTime()));
        }
        return list;
    }

    public static List<ReminderTime> FormToReminderTimeList(List<ReminderTimeCreateForm> reminderTimeList, Task task) {
        List<ReminderTime> list = new ArrayList<>();
        for (ReminderTimeCreateForm reminderTime : reminderTimeList) {
            list.add(new ReminderTime(task, reminderTime.getTime()));
        }
        return list;
    }

    public static TaskCreateForm TaskToForm(Task task) {
        return new TaskCreateForm(task.getTitle(), task.getDescription(), Mapper.ReminderTimeListToForm(task.getReminderTimes()), task.getCompletionData());
    }

    public static Task formToTask(User user, TaskCreateForm taskCreateForm) {
        Task task = new Task(user, taskCreateForm.getTitle(), taskCreateForm.getDescription(), taskCreateForm.getCompletionData());
        for (ReminderTime reminderTime : Mapper.FormToReminderTimeList(taskCreateForm.getReminderTimes(), task)) {
            task.addReminderTime(reminderTime);
        }
        return task;
    }

    public static User formToUser(UserCreatForm userCreatForm) {
        return new User(userCreatForm.getUsername(), userCreatForm.getPassword(), userCreatForm.getEmail());
    }
}
