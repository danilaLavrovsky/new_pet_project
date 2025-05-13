package new_pet_project.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskTitleRequest {
    @NotBlank(message = "Название задачи не может быть пустым")
    private String taskTitle;
}