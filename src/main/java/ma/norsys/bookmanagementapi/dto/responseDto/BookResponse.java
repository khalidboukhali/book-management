package ma.norsys.bookmanagementapi.dto.responseDto;

import java.time.LocalDate;

public record BookResponse(
        long id,
        String title,
        String author,
        String genre
) {}
