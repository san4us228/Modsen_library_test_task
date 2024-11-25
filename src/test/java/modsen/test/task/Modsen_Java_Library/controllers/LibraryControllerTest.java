package modsen.test.task.Modsen_Java_Library.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import modsen.test.task.Modsen_Java_Library.DTO.LibraryDTO;
import modsen.test.task.Modsen_Java_Library.services.LibraryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(LibraryController.class)
class LibraryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryService libraryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void getAvailableBooks_ShouldReturnAvailableBooks() throws Exception {
        LibraryDTO libraryDTO = new LibraryDTO(1L, 1L, "Test Book", null, null, true);
        when(libraryService.getAvailableBooks()).thenReturn(List.of(libraryDTO));

        mockMvc.perform(get("/library/available")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookTitle").value("Test Book"));

        verify(libraryService, times(1)).getAvailableBooks();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void getAvailableBooks_ShouldReturnUnavailableBooks() throws Exception {
        LibraryDTO libraryDTO = new LibraryDTO(1L, 1L, "Test Book", null, null, false);
        when(libraryService.getUnavailableBooks()).thenReturn(List.of(libraryDTO));

        mockMvc.perform(get("/library/unavailable")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookTitle").value("Test Book"));

        verify(libraryService, times(1)).getUnavailableBooks();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void addNewBook_ShouldAddBookToLibrary() throws Exception {
        doNothing().when(libraryService).addNewBook(1L);

        mockMvc.perform(post("/library/add?bookId=1")
                        .with(csrf())) // Добавляем CSRF-токен
                .andExpect(status().isOk());

        verify(libraryService, times(1)).addNewBook(1L);
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void borrowBook_ShouldBorrowBook() throws Exception {
        doNothing().when(libraryService).borrowBook(1L);

        mockMvc.perform(post("/library/borrow/1")
                        .with(csrf())) // Добавляем CSRF-токен
                .andExpect(status().isOk());

        verify(libraryService, times(1)).borrowBook(1L);
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void returnBook_ShouldReturnBook() throws Exception {
        doNothing().when(libraryService).returnBook(1L);

        mockMvc.perform(post("/library/return/1")
                        .with(csrf())) // Добавляем CSRF-токен
                .andExpect(status().isOk());

        verify(libraryService, times(1)).returnBook(1L);
    }
}
