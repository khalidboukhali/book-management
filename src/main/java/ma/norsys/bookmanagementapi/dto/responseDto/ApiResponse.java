package ma.norsys.bookmanagementapi.dto.responseDto;

public record ApiResponse(
        int statusCode,
        String message
) {}
