package ma.norsys.bookmanagementapi.controller;

import ma.norsys.bookmanagementapi.dto.responseDto.ApiResponse;
import ma.norsys.bookmanagementapi.dto.responseDto.BookLoanResponse;
import ma.norsys.bookmanagementapi.exception.EntityNotFoundException;
import ma.norsys.bookmanagementapi.service.BookLoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book-loans")
public class BookLoanController {
    private final BookLoanService bookLoanService;

    public BookLoanController(BookLoanService bookLoanService) {
        this.bookLoanService = bookLoanService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addBookLoan(@RequestParam Long userId, @RequestParam Long bookId) {
        try {
            bookLoanService.addBookLoan(userId, bookId);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(200, "Book loan added successfully"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(404, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(500, "Error adding book loan"));
        }
    }

    @DeleteMapping("/{bookLoanId}")
    public ResponseEntity<ApiResponse> removeBookLoan(@PathVariable Long bookLoanId) {
        try {
            bookLoanService.removeBookLoan(bookLoanId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(200, "Book loan deleted successfully"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(404, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(500, "Error deleting book loan"));
        }
    }

    @PutMapping("/{bookLoanId}")
    public ResponseEntity<ApiResponse> updateBookLoanBookAndUser(@PathVariable Long bookLoanId, @RequestParam Long newBookId, @RequestParam Long newUserId) {
        try {
            bookLoanService.updateBookLoanBookAndUser(bookLoanId, newBookId, newUserId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(200, "Book loan updated successfully"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(404, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(500, "Error updating book loan"));
        }
    }

    @GetMapping
    public ResponseEntity<List<BookLoanResponse>> getAllBookLoans() {
        List<BookLoanResponse> bookLoans = bookLoanService.getAllBookLoans();
        return ResponseEntity.status(HttpStatus.OK).body(bookLoans);
    }

    @GetMapping("/{bookLoanId}")
    public ResponseEntity<BookLoanResponse> getBookLoanById(@PathVariable Long bookLoanId) {
        BookLoanResponse bookLoan = bookLoanService.getBookLoanById(bookLoanId);
        return ResponseEntity.status(HttpStatus.OK).body(bookLoan);
    }
}

