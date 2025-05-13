package new_pet_project.controller;

import jakarta.validation.Valid;
import new_pet_project.DTO.DeleteReminderRequest;
import new_pet_project.DTO.TaskCreateForm;
import new_pet_project.DTO.TaskTitleRequest;
import new_pet_project.DTO.UpdateTaskRequest;
import new_pet_project.exception.DataBaseNotFoundException;
import new_pet_project.exception.NameBusyException;
import new_pet_project.security.SecurityUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import new_pet_project.service.TaskService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.validation.FieldError;


@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @DeleteMapping("/task/delete")
    public ResponseEntity<?> deleteTask(
            @AuthenticationPrincipal SecurityUser user,
            @Valid @RequestBody TaskTitleRequest taskTitle
            ) throws DataBaseNotFoundException {
        taskService.deleteTask(user.getId(), taskTitle.getTaskTitle());
        return new ResponseEntity<>("Задача успешно удаленна", HttpStatus.OK);
    }

    @DeleteMapping("/reminderTime/delete")
    public ResponseEntity<?> deleteReminderTime(
            @AuthenticationPrincipal SecurityUser user,
            @Valid @RequestBody DeleteReminderRequest request
    ) throws DataBaseNotFoundException {
        taskService.deleteReminderTime(user.getId(), request.getTaskTitle(), request.getReminderTime());
        return new ResponseEntity<>("Напоминания успешно удаленно", HttpStatus.OK);
    }

    @GetMapping("/task/findTask")
    public TaskCreateForm findTask(
            @AuthenticationPrincipal SecurityUser user,
            @RequestBody String taskTitle
    ) throws DataBaseNotFoundException {
        return taskService.findTask(user.getId(), taskTitle);
    }

    @GetMapping("/task/getMyTasks")
    public List<TaskCreateForm> getAllTask(
            @AuthenticationPrincipal SecurityUser user
    ) throws DataBaseNotFoundException {
        return taskService.getAllTask(user.getId());
    }

    @PostMapping("/task/addTask")
    public ResponseEntity<?> addTask(
            @AuthenticationPrincipal SecurityUser user,
            @Valid @RequestBody TaskCreateForm taskCreateForm
    ) throws NameBusyException, DataBaseNotFoundException {
        taskService.addTask(user.getId(), taskCreateForm);
        return new ResponseEntity<>("Задача успешно добавленна", HttpStatus.CREATED);
    }

    @PutMapping("/task/update")
    public ResponseEntity<?> updateTask(
            @AuthenticationPrincipal SecurityUser user,
            @Valid @RequestBody UpdateTaskRequest request
    ) throws DataBaseNotFoundException, NameBusyException {
        taskService.updateTask(user.getId(), request.getTaskData(), request.getCurrentTaskTitle());
        return new ResponseEntity<>("Задача успешно обновлена", HttpStatus.OK);
    }

    @PutMapping("/task/complete")
    public ResponseEntity<?> completeTask(
            @AuthenticationPrincipal SecurityUser user,
            @Valid @RequestBody TaskTitleRequest taskTitle
    ) throws DataBaseNotFoundException {
        taskService.completeTask(user.getId(), taskTitle.getTaskTitle());
        return new ResponseEntity<>("Задача успешно выполнена", HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataBaseNotFoundException.class)
    public ResponseEntity<?> handleException(DataBaseNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NameBusyException.class)
    public ResponseEntity<?> handleException(NameBusyException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
