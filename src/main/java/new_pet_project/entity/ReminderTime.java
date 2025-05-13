package new_pet_project.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name="ReminderTime", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"task_id", "time"})
})
public class ReminderTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDateTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Task task;

    public ReminderTime(Task task, LocalDateTime time) {
        this.task = task;
        this.time = time;
    }
}

