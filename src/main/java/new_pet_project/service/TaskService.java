package new_pet_project.service;


import new_pet_project.DTO.ReminderTimeCreateForm;
import new_pet_project.DTO.TaskCreateForm;
import new_pet_project.entity.ReminderTime;
import new_pet_project.entity.Task;
import new_pet_project.entity.User;
import new_pet_project.exception.DataBaseNotFoundException;
import new_pet_project.exception.NameBusyException;
import new_pet_project.repository.ReminderTimeRepository;
import new_pet_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import new_pet_project.repository.TaskRepository;
import new_pet_project.mapper.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final UserRepository userRepository;

    private final TaskRepository taskRepository;

    private final ReminderTimeRepository reminderTimeRepository;

    public TaskService(UserRepository userRepository, TaskRepository taskRepository, ReminderTimeRepository reminderTimeRepository) {

        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.reminderTimeRepository = reminderTimeRepository;
    }

    @Transactional
    public void deleteTask(
            Integer userId,
            String taskTitle
    ) throws DataBaseNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new DataBaseNotFoundException("Пользователь не найден"));
        Task task = taskRepository.findByTitleAndUser(taskTitle, user).orElseThrow(() -> new DataBaseNotFoundException("Задачи с таким названием не существует"));
        user.removeTask(task);
        userRepository.save(user);
    }

    @Transactional
    public List<TaskCreateForm> getAllTask(
            Integer id
    ) throws DataBaseNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new DataBaseNotFoundException("Пользователь не найден"));
        if (user.getTasks().isEmpty()) {
            throw new DataBaseNotFoundException("У данного пользователя нет задач");
        }
        List<TaskCreateForm> taskCreateForms = new ArrayList<>();
        for (Task task : user.getTasks()) {
            taskCreateForms.add(Mapper.TaskToForm(task));
        }
        return taskCreateForms;
    }

    @Transactional
    public void addTask(
            Integer userId,
            TaskCreateForm taskCreateForm
    ) throws NameBusyException, DataBaseNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataBaseNotFoundException("Пользователь не найден"));
        if (taskRepository.findByTitleAndUser(taskCreateForm.getTitle(), user).isPresent()) {
            throw new NameBusyException("Задача с таким названием уже существует");
        }
        Task task = Mapper.formToTask(user, taskCreateForm);
        user.addTask(task);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public TaskCreateForm findTask(
            Integer userId,
            String taskTitle
    ) throws DataBaseNotFoundException {
        Task task = taskRepository.
                findByTitleAndUser(taskTitle, userRepository.findById(userId).
                        orElseThrow(() -> new DataBaseNotFoundException("Пользователь не найден"))).
                        orElseThrow(() -> new DataBaseNotFoundException("Задачи с таким названием не существует"));
        return Mapper.TaskToForm(task);
    }

    @Transactional
    public void updateTask(
            Integer userId,
            TaskCreateForm taskCreateForm,
            String taskTitle
    ) throws DataBaseNotFoundException, NameBusyException {
        User user = userRepository.findById(userId).orElseThrow(() -> new DataBaseNotFoundException("Пользователь не найден"));
        Task task = taskRepository.findByTitleAndUser(taskTitle, user).orElseThrow(() -> new DataBaseNotFoundException("Задачи с таким названием не существует"));
        if (!(task.getTitle().equals(taskCreateForm.getTitle())) && taskRepository.findByTitleAndUser(taskCreateForm.getTitle(), user).isPresent()) {
            throw new NameBusyException("Задача с таким названием уже существует");
        }
        task.setTitle(taskCreateForm.getTitle());
        task.setDescription(taskCreateForm.getDescription());
        task.setCompletionData(taskCreateForm.getCompletionData());
        for (ReminderTime reminderTime : Mapper.FormToReminderTimeList(taskCreateForm.getReminderTimes(), task)) {
            task.addReminderTime(reminderTime);
        }
    }

    @Transactional
    public void completeTask(
            Integer userId,
            String taskTitle
    ) throws DataBaseNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new DataBaseNotFoundException("Пользователь не найден"));
        Task task = taskRepository.findByTitleAndUser(taskTitle, user).orElseThrow(() -> new DataBaseNotFoundException("Задачи с таким названием не существует"));
        task.setCompleted(true);
    }

    @Transactional
    public void deleteReminderTime(
            Integer userId,
            String taskTitle,
            ReminderTimeCreateForm reminderTimeCreatForm
    ) throws DataBaseNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new DataBaseNotFoundException("Пользователь не найден"));
        Task task = taskRepository.findByTitleAndUser(taskTitle, user).orElseThrow(() -> new DataBaseNotFoundException("Задачи с таким названием не существует"));
        ReminderTime reminderTime = reminderTimeRepository.findByTaskAndTime(task, reminderTimeCreatForm.getTime()).orElseThrow(() -> new DataBaseNotFoundException("Такого напоминания не существует"));
        task.removeReminderTime(reminderTime);
    }
}
