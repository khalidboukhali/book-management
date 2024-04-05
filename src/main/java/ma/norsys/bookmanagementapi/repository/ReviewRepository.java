package ma.norsys.bookmanagementapi.repository;

import ma.norsys.bookmanagementapi.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
