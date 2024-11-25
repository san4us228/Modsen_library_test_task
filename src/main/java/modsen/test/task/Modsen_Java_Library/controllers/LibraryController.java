package modsen.test.task.Modsen_Java_Library.controllers;

import modsen.test.task.Modsen_Java_Library.DTO.LibraryDTO;
import modsen.test.task.Modsen_Java_Library.models.Library;
import modsen.test.task.Modsen_Java_Library.services.LibraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
public class LibraryController {
    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addNewBook(@RequestParam Long bookId) {
        libraryService.addNewBook(bookId);
        return ResponseEntity.ok("Book added to library records");
    }

    @GetMapping("/available")
    public ResponseEntity<List<LibraryDTO>> getAvailableBooks() {
        return ResponseEntity.ok(libraryService.getAvailableBooks());
    }
    @GetMapping("/unavailable")
    public ResponseEntity<List<LibraryDTO>> getUnavailableBooks() {
        return ResponseEntity.ok(libraryService.getUnavailableBooks());
    }

    @PostMapping("/borrow/{bookId}")
    public ResponseEntity<String> borrowBook(@PathVariable Long bookId) {
        libraryService.borrowBook(bookId);
        return ResponseEntity.ok("Book borrowed successfully");
    }

    @PostMapping("/return/{bookId}")
    public ResponseEntity<String> returnBook(@PathVariable Long bookId) {
        libraryService.returnBook(bookId);
        return ResponseEntity.ok("Book returned successfully");
    }
}
