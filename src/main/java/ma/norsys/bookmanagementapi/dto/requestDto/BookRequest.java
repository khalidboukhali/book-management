package ma.norsys.bookmanagementapi.dto.requestDto;

import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record BookRequest(
        @Size(min = 3, message = "Title should at least contain {min} characters.")
        String title,
        @Size(min = 3, message = "Author should at least contain {min} characters.")
        String author,
        @Size(min = 3, message = "Genre should at least contain {min} characters.")
        String genre
) {}
