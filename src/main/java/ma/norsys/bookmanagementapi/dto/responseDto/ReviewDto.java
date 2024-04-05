package ma.norsys.bookmanagementapi.dto.responseDto;

public record ReviewDto(
        Long id,
        String comment,
        String bookName,
        String userName
) {}
