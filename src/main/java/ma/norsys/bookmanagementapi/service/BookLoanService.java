package ma.norsys.bookmanagementapi.service;

import ma.norsys.bookmanagementapi.dto.responseDto.BookLoanResponse;
import ma.norsys.bookmanagementapi.entities.Book;
import ma.norsys.bookmanagementapi.entities.BookLoan;
import ma.norsys.bookmanagementapi.entities.User;
import ma.norsys.bookmanagementapi.exception.EntityNotFoundException;
import ma.norsys.bookmanagementapi.repository.BookLoanRepository;
import ma.norsys.bookmanagementapi.repository.BookRepository;
import ma.norsys.bookmanagementapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookLoanService {
    private final BookLoanRepository bookLoanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BookLoanService(BookLoanRepository bookLoanRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.bookLoanRepository = bookLoanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public void addBookLoan(Long userId, Long bookId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));

        BookLoan bookLoan = new BookLoan();
        bookLoan.setDate_Borrowed(LocalDate.now());
        bookLoan.setUser(user);
        bookLoan.setBook(book);

        bookLoanRepository.save(bookLoan);
    }

    public void removeBookLoan(Long bookLoanId) {
        BookLoan bookLoan = bookLoanRepository.findById(bookLoanId)
                .orElseThrow(() -> new EntityNotFoundException("Book loan not found with id: " + bookLoanId));

        bookLoanRepository.delete(bookLoan);
    }

    public void updateBookLoanBookAndUser(Long bookLoanId, Long newBookId, Long newUserId) {
        BookLoan bookLoan = bookLoanRepository.findById(bookLoanId)
                .orElseThrow(() -> new EntityNotFoundException("Book loan not found with id: " + bookLoanId));

        Book newBook = bookRepository.findById(newBookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + newBookId));

        User newUser = userRepository.findById(newUserId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + newUserId));

        bookLoan.setBook(newBook);
        bookLoan.setUser(newUser);

        bookLoanRepository.save(bookLoan);
    }

    public List<BookLoanResponse> getAllBookLoans() {
        List<BookLoan> bookLoans = bookLoanRepository.findAll();
        return bookLoans.stream()
                .map(this::mapBookLoanToResponse)
                .collect(Collectors.toList());
    }

    public BookLoanResponse getBookLoanById(Long bookLoanId) {
        BookLoan bookLoan = bookLoanRepository.findById(bookLoanId)
                .orElseThrow(() -> new EntityNotFoundException("Book loan not found with id: " + bookLoanId));
        return mapBookLoanToResponse(bookLoan);
    }

    private BookLoanResponse mapBookLoanToResponse(BookLoan bookLoan) {
        return new BookLoanResponse(
                bookLoan.getId(),
                bookLoan.getDate_Borrowed(),
                bookLoan.getUser().getId(),
                bookLoan.getUser().getFullName(),
                bookLoan.getBook().getId(),
                bookLoan.getBook().getTitle()
        );
    }
}
