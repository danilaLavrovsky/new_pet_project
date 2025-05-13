package new_pet_project.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateTaskRequest {

    @NotBlank(message = "Текущее название задачи для обновления не может быть пустым")
    private String currentTaskTitle;

    @NotNull(message = "Новые данные задачи не могут быть пустыми")
    @Valid
    private TaskCreateForm taskData;
}