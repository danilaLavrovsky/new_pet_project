package new_pet_project.DTO;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReminderTimeCreateForm {
    @NotNull(message = "Время напоминания не может быть пустым")
    @FutureOrPresent(message = "Время напоминания не может быть в прошлом")
    private LocalDateTime time;
}

