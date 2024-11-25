package modsen.test.task.Modsen_Java_Library.services;

import modsen.test.task.Modsen_Java_Library.DTO.LibraryDTO;
import modsen.test.task.Modsen_Java_Library.models.Book;
import modsen.test.task.Modsen_Java_Library.models.Library;
import modsen.test.task.Modsen_Java_Library.repositories.BookRepository;
import modsen.test.task.Modsen_Java_Library.repositories.LibraryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryService {
    private final LibraryRepository libraryRepository;
    private final BookRepository bookRepository;

    private final ModelMapper modelMapper;

    public LibraryService(LibraryRepository libraryRepository, BookRepository bookRepository, ModelMapper modelMapper) {
        this.libraryRepository = libraryRepository;
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    public void addNewBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book with ID " + bookId + " not found"));


        Library record = modelMapper.map(book, Library.class);
        record.setAvailable(true);
        libraryRepository.save(record);
    }
    public void borrowBook(Long bookId) {
        Library record = libraryRepository.findByBookId(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found in library records"));

        if (!record.isAvailable()) {
            throw new RuntimeException("Book is already borrowed");
        }
        LocalDateTime nowMoscow = LocalDateTime.now(ZoneId.of("Europe/Moscow"));

        record.setAvailable(false);
        record.setBorrowedAt(nowMoscow);
        record.setDueAt(nowMoscow.plusDays(7));
        libraryRepository.save(record);
    }

    public void returnBook(Long bookId) {
        Library record = libraryRepository.findByBookId(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found in library records"));

        record.setAvailable(true);
        record.setBorrowedAt(null);
        record.setDueAt(null);
        libraryRepository.save(record);
    }
    public List<LibraryDTO> getAvailableBooks() {

        List<Library> availableBooks = libraryRepository.findByIsAvailable(true);
        return availableBooks.stream()
                .map(book -> modelMapper.map(book, LibraryDTO.class))
                .collect(Collectors.toList());
    }
    public List<LibraryDTO> getUnavailableBooks() {
        List<Library> availableBooks = libraryRepository.findByIsAvailable(false);
        return availableBooks.stream()
                .map(book -> modelMapper.map(book, LibraryDTO.class))
                .collect(Collectors.toList());
    }

}
