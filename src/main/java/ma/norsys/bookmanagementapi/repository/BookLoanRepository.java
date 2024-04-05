package ma.norsys.bookmanagementapi.repository;

import ma.norsys.bookmanagementapi.entities.BookLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookLoanRepository extends JpaRepository<BookLoan, Long> {
}
