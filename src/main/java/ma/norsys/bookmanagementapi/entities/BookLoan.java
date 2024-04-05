package ma.norsys.bookmanagementapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Table(name = "book_loans")
public class BookLoan {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private LocalDate Date_Borrowed;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
