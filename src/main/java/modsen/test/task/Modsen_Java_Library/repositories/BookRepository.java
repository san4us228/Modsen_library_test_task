package modsen.test.task.Modsen_Java_Library.repositories;

import modsen.test.task.Modsen_Java_Library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<Book> findByIsbn(String isbn);
}
