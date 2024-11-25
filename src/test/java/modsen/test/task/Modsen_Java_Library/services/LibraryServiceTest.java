package modsen.test.task.Modsen_Java_Library.services;

import modsen.test.task.Modsen_Java_Library.DTO.LibraryDTO;
import modsen.test.task.Modsen_Java_Library.models.Book;
import modsen.test.task.Modsen_Java_Library.models.Library;
import modsen.test.task.Modsen_Java_Library.repositories.BookRepository;
import modsen.test.task.Modsen_Java_Library.repositories.LibraryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LibraryServiceTest {
    @Mock
    private LibraryRepository libraryRepository;

    @Mock
    private BookRepository bookRepository;

    private ModelMapper modelMapper;

    private LibraryService libraryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        modelMapper = new ModelMapper();
        libraryService = new LibraryService(libraryRepository, bookRepository, modelMapper);
    }

    @Test
    void addNewBook_ShouldAddBookToLibrary() {
        Book book = new Book();
        book.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        libraryService.addNewBook(1L);

        verify(libraryRepository, times(1)).save(any(Library.class));
    }

    @Test
    void borrowBook_ShouldMarkAsUnavailable() {
        Library library = new Library();
        library.setAvailable(true);

        when(libraryRepository.findByBookId(1L)).thenReturn(Optional.of(library));

        libraryService.borrowBook(1L);

        assertFalse(library.isAvailable());
        verify(libraryRepository, times(1)).save(library);
    }

    @Test
    void returnBook_ShouldMarkAsAvailable() {
        Library library = new Library();
        library.setAvailable(false);

        when(libraryRepository.findByBookId(1L)).thenReturn(Optional.of(library));

        libraryService.returnBook(1L);

        assertTrue(library.isAvailable());
        verify(libraryRepository, times(1)).save(library);
    }
}
