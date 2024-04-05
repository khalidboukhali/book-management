package ma.norsys.bookmanagementapi.controller;

import jakarta.validation.Valid;
import ma.norsys.bookmanagementapi.dto.requestDto.BookRequest;
import ma.norsys.bookmanagementapi.dto.responseDto.ApiResponse;
import ma.norsys.bookmanagementapi.dto.responseDto.BookResponse;
import ma.norsys.bookmanagementapi.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createBook(@Valid @RequestBody BookRequest bookRequest) {
        try {
            bookService.createNewBook(bookRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(200, "New book was created successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(400, "Error adding new book"));
        }
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        List<BookResponse> books = bookService.getAllBooks();
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable long bookId) {
        BookResponse book = bookService.getBookById(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<ApiResponse> updateBook(@PathVariable long bookId, @Valid @RequestBody BookRequest bookRequest) {
        try {
            bookService.updateBook(bookId, bookRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(200, "Book updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(400, "Error updating book"));
        }
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable long bookId) {
        try {
            bookService.deleteBook(bookId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(200, "Book deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(400, "Error deleting book: " + e.getMessage()));
        }
    }
}

