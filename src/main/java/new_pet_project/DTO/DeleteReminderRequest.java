package new_pet_project.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeleteReminderRequest {

    @NotBlank(message = "Название задачи не может быть пустым")
    private String taskTitle;

    @NotNull(message = "Информация о напоминании не может быть пустой")
    @Valid
    private ReminderTimeCreateForm reminderTime;
}
