package modsen.test.task.Modsen_Java_Library.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import modsen.test.task.Modsen_Java_Library.DTO.BookDTO;
import modsen.test.task.Modsen_Java_Library.services.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void getAllBooks_ShouldReturnBooks() throws Exception {
        BookDTO bookDTO = new BookDTO(1L, "1234567890123", "Test Book", "Genre", "Desc", "Author");
        when(bookService.getAllBooks()).thenReturn(List.of(bookDTO));

        mockMvc.perform(get("/books")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))  // Добавляем CSRF-токен
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Book"));

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void getBookById_ShouldReturnBook() throws Exception {
        BookDTO bookDTO = new BookDTO(1L, "1234567890123", "Test Book", "Genre", "Desc", "Author");
        when(bookService.getBookById(1L)).thenReturn(bookDTO);

        mockMvc.perform(get("/books/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))  // Добавляем CSRF-токен
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Book"));

        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void getBookByIsbn_ShouldReturnBook() throws Exception {
        BookDTO bookDTO = new BookDTO(1L, "1234567890123", "Test Book", "Genre", "Desc", "Author");
        when(bookService.getBookByIsbn("1234567890123")).thenReturn(bookDTO);

        mockMvc.perform(get("/books/isbn/1234567890123")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))  // Добавляем CSRF-токен
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Book"));

        verify(bookService, times(1)).getBookByIsbn("1234567890123");
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void createBook_ShouldCreateBook() throws Exception {
        BookDTO bookDTO = new BookDTO(null, "1234567890123", "New Book", "Genre", "Description", "Author");
        doNothing().when(bookService).createBook(bookDTO);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO))
                        .with(csrf()))  // Добавляем CSRF-токен
                .andExpect(status().isOk());

        verify(bookService, times(1)).createBook(bookDTO);
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void updateBook_ShouldUpdateBook() throws Exception {
        BookDTO bookDTO = new BookDTO(null, "1234567890123", "Updated Book", "Genre", "Description", "Author");
        doNothing().when(bookService).updateBook(eq(1L), eq(bookDTO));

        mockMvc.perform(put("/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO))
                        .with(csrf()))  // Добавляем CSRF-токен
                .andExpect(status().isOk());

        verify(bookService, times(1)).updateBook(1L, bookDTO);
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void deleteBook_ShouldDeleteBook() throws Exception {
        doNothing().when(bookService).deleteBook(1L);

        mockMvc.perform(delete("/books/1")
                        .with(csrf()))  // Добавляем CSRF-токен
                .andExpect(status().isOk());

        verify(bookService, times(1)).deleteBook(1L);
    }
}
