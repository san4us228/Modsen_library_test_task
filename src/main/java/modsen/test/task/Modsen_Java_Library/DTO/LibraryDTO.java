package modsen.test.task.Modsen_Java_Library.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibraryDTO {
    private Long id;
    private Long bookId;
    private String bookTitle;
    private LocalDateTime borrowedAt;
    private LocalDateTime dueAt;
    private boolean isAvailable;
}

