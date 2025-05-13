package new_pet_project.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name="Task", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "title"})
})
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, updatable = false)
    private LocalDateTime creatDate;

    private LocalDateTime completionData;

    private boolean completed = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;

    @OneToMany(
            mappedBy = "task",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ReminderTime> reminderTimes = new ArrayList<>();

    public Task(User user, String title, String description, LocalDateTime completionData) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.creatDate = LocalDateTime.now();
        this.completionData = completionData;
    }

    public void addReminderTime(ReminderTime reminderTime) {
        this.reminderTimes.add(reminderTime);
        reminderTime.setTask(this);
    }

    public void removeReminderTime(ReminderTime reminderTime) {
        this.reminderTimes.remove(reminderTime);
        reminderTime.setTask(null);
    }
}

