package new_pet_project.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class TaskCreateForm {

    @NotBlank(message = "Заголовок задачи не может быть пустым")
    @Size(max = 255, message = "Заголовок не может быть длиннее 255 символов")
    private String title;

    @NotBlank(message = "Описание задачи не может быть пустым")
    private String description;

    @Valid
    private List<ReminderTimeCreateForm> reminderTimes;

    @NotNull(message = "Время завершения не может быть пустым")
    @FutureOrPresent(message = "Время завершения не может быть в прошлом")
    private LocalDateTime completionData;
}
