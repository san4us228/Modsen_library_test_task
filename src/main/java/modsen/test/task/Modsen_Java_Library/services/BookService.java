package modsen.test.task.Modsen_Java_Library.services;

import modsen.test.task.Modsen_Java_Library.DTO.BookDTO;
import modsen.test.task.Modsen_Java_Library.exeptions.NotFoundException;
import modsen.test.task.Modsen_Java_Library.models.Book;
import modsen.test.task.Modsen_Java_Library.repositories.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.util.List;


@Service
public class BookService {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    private final WebClient webClient;


    public BookService(BookRepository bookRepository, ModelMapper modelMapper,  WebClient webClient) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.webClient = webClient;

    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .toList();
    }

    public BookDTO getBookById(Long id) {
        return bookRepository.findById(id)
                .map(book -> modelMapper.map(book, BookDTO.class))
                .orElseThrow(() -> new NotFoundException("Book not found"));
    }
    public BookDTO getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .map(book -> modelMapper.map(book, BookDTO.class))
                .orElseThrow(() -> new NotFoundException("Book not found"));
    }

    public void createBook(BookDTO bookDTO) {
        Book book = modelMapper.map(bookDTO, Book.class);
        bookRepository.save(book);
        try {
            webClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/library/add")
                            .queryParam("bookId", book.getId())
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException e) {
            System.err.println("Ошибка HTTP: " + e.getStatusCode());
        } catch (Exception e) {
            System.err.println("Общая ошибка: " + e.getMessage());
        }



    }


    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
    public void updateBook(Long id, BookDTO bookDTO) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        book.setIsbn(bookDTO.getIsbn());
        book.setTitle(bookDTO.getTitle());
        book.setDescription(bookDTO.getDescription());
        book.setGenre(bookDTO.getGenre());
        book.setAuthor(bookDTO.getAuthor());

        bookRepository.save(book);
    }
}
