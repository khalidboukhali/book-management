package ma.norsys.bookmanagementapi.dto.responseDto;

import java.time.LocalDate;

public record BookLoanResponse (
        Long id,
        LocalDate dateBorrowed,
        Long userId,
        String userName,
        Long bookId,
        String bookTitle
){}
