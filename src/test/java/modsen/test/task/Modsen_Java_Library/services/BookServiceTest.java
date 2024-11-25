package modsen.test.task.Modsen_Java_Library.services;

import modsen.test.task.Modsen_Java_Library.DTO.BookDTO;
import modsen.test.task.Modsen_Java_Library.exeptions.NotFoundException;
import modsen.test.task.Modsen_Java_Library.models.Book;
import modsen.test.task.Modsen_Java_Library.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private WebClient webClient;

    private ModelMapper modelMapper;

    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        modelMapper = new ModelMapper();
        bookService = new BookService(bookRepository, modelMapper, webClient);
    }

    @Test
    void getAllBooks_ShouldReturnListOfBookDTO() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setIsbn("1234567890123");

        when(bookRepository.findAll()).thenReturn(List.of(book));

        List<BookDTO> result = bookService.getAllBooks();

        assertEquals(1, result.size());
        assertEquals("Test Book", result.get(0).getTitle());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getBookById_ShouldReturnBookDTO() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        BookDTO result = bookService.getBookById(1L);

        assertEquals("Test Book", result.getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void getBookById_ShouldThrowNotFoundException() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.getBookById(1L));
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void createBook_ShouldSaveBook() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setIsbn("1234567890123");
        bookDTO.setTitle("Test Book");

        bookService.createBook(bookDTO);

        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void updateBook_ShouldUpdateExistingBook() {
        Book book = new Book();
        book.setId(1L);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Updated Title");

        bookService.updateBook(1L, bookDTO);

        verify(bookRepository, times(1)).save(any(Book.class));
        assertEquals("Updated Title", book.getTitle());
    }

    @Test
    void deleteBook_ShouldDeleteBook() {
        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }
}
