package ma.norsys.bookmanagementapi.dto.responseDto;

public record UserResponse(
        long id,
        String fullName,
        String email,
        String phone_number
) {}
