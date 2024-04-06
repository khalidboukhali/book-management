package ma.norsys.bookmanagementapi.dto.responseDto;

public record ReviewDto(
        Long id,
        String comment,
        Long bookId,
        String bookName,
        Long userId,
        String userName
) {}
