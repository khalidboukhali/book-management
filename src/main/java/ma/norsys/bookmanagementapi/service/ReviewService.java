package ma.norsys.bookmanagementapi.service;

import ma.norsys.bookmanagementapi.dto.responseDto.ReviewDto;
import ma.norsys.bookmanagementapi.entities.Book;
import ma.norsys.bookmanagementapi.entities.Review;
import ma.norsys.bookmanagementapi.entities.User;
import ma.norsys.bookmanagementapi.exception.EntityNotFoundException;
import ma.norsys.bookmanagementapi.repository.BookRepository;
import ma.norsys.bookmanagementapi.repository.ReviewRepository;
import ma.norsys.bookmanagementapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public void addReview(Long userId, Long bookId, String reviewDescription) {
        // Check if user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        // Check if book exists
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));

        // Create new review
        Review review = new Review();
        review.setComment(reviewDescription);
        review.setUser(user);
        review.setBook(book);

        // Save the review
        review = reviewRepository.save(review);
    }

    public void removeReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + reviewId));

        reviewRepository.delete(review);
    }

    public void updateReview(Long reviewId, Long userId, Long bookId, String newComment) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + reviewId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));

        review.setUser(user);
        review.setBook(book);
        review.setComment(newComment);

        reviewRepository.save(review);
    }

    public List<ReviewDto> getAllReviewsWithBookAndUser() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
                .map(this::mapReviewToDto)
                .collect(Collectors.toList());
    }

    public ReviewDto getReviewById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + reviewId));

        return mapReviewToDto(review);
    }

    private ReviewDto mapReviewToDto(Review review) {
        ReviewDto reviewDto = new ReviewDto(review.getId(), review.getComment(),
                review.getBook().getTitle(), review.getUser().getFullName());

        return reviewDto;
    }
}
