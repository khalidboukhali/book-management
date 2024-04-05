package ma.norsys.bookmanagementapi.controller;

import ma.norsys.bookmanagementapi.dto.responseDto.ApiResponse;
import ma.norsys.bookmanagementapi.dto.responseDto.ReviewDto;
import ma.norsys.bookmanagementapi.exception.EntityNotFoundException;
import ma.norsys.bookmanagementapi.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addReview(@RequestParam Long userId, @RequestParam Long bookId, @RequestParam String reviewDescription) {
        try {
            reviewService.addReview(userId, bookId, reviewDescription);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(200, "Review added successfully"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(404, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(500, "Error adding review"));
        }
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse> removeReview(@PathVariable Long reviewId) {
        try {
            reviewService.removeReview(reviewId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(200, "Review deleted successfully"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(404, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(500, "Error deleting review"));
        }
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ApiResponse> updateReview(@PathVariable Long reviewId, @RequestParam Long userId, @RequestParam Long bookId, @RequestParam String newComment) {
        try {
            reviewService.updateReview(reviewId, userId, bookId, newComment);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(200, "Review updated successfully"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(404, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(500, "Error updating review"));
        }
    }

    @GetMapping
    public ResponseEntity<List<ReviewDto>> getAllReviewsWithDetails() {
        List<ReviewDto> reviews = reviewService.getAllReviewsWithBookAndUser();
        return ResponseEntity.status(HttpStatus.OK).body(reviews);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable Long reviewId) {
        ReviewDto review = reviewService.getReviewById(reviewId);
        return ResponseEntity.status(HttpStatus.OK).body(review);
    }

}
