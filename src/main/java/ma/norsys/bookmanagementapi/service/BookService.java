package ma.norsys.bookmanagementapi.service;

import ma.norsys.bookmanagementapi.dto.requestDto.BookRequest;
import ma.norsys.bookmanagementapi.dto.responseDto.BookResponse;
import ma.norsys.bookmanagementapi.entities.Book;
import ma.norsys.bookmanagementapi.exception.EntityNotFoundException;
import ma.norsys.bookmanagementapi.mapper.BookMapper;
import ma.norsys.bookmanagementapi.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void createNewBook(BookRequest bookRequest) {
        Book book = BookMapper.INSTANCE.toEntity(bookRequest);
        bookRepository.save(book);
    }

    public List<BookResponse> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return mapBooksToBookResponses(books);
    }

    public BookResponse getBookById(long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            return mapBookToBookResponse(optionalBook.get());
        } else {
            throw new EntityNotFoundException("Book not found with id: " + bookId);
        }
    }

    public void updateBook(long bookId, BookRequest bookRequest) {
        Book oldBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id " + bookId + " not found"));
        oldBook.setTitle(bookRequest.title());
        oldBook.setAuthor(bookRequest.author());
        oldBook.setGenre(bookRequest.genre());
        bookRepository.save(oldBook);
    }

    public void deleteBook(long bookId) {
        if (bookRepository.existsById(bookId)) {
            bookRepository.deleteById(bookId);
        } else {
            throw new EntityNotFoundException("Book with id " + bookId + " not found");
        }
    }

    private BookResponse mapBookToBookResponse(Book book) {
        return BookMapper.INSTANCE.toDto(book);
    }

    private List<BookResponse> mapBooksToBookResponses(List<Book> books) {
        return books.stream()
                .map(book -> mapBookToBookResponse(book))
                .toList();
    }
}
