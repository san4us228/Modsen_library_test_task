package modsen.test.task.Modsen_Java_Library.repositories;

import modsen.test.task.Modsen_Java_Library.models.Library;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibraryRepository extends JpaRepository<Library,Long> {
    Optional<Library> findByBookId(Long bookId);
    List<Library> findByIsAvailable(boolean isAvailable);
}
