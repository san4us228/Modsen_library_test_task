package modsen.test.task.Modsen_Java_Library.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Long id;
    private String isbn;
    private String title;
    private String genre;
    private String description;
    private String author;
}
