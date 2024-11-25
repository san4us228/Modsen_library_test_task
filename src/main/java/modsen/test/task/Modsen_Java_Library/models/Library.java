package modsen.test.task.Modsen_Java_Library.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;


@Data
@Entity(name = "library")
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "borrowed_at")
    private LocalDateTime borrowedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "due_at")
    private LocalDateTime dueAt;

    @Column(name = "is_available")
    private boolean isAvailable;
}
