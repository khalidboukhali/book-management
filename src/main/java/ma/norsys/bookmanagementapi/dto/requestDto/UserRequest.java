package ma.norsys.bookmanagementapi.dto.requestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @Size(min = 3, message = "Full name should at least contain {min} characters.")
        String fullName,
        @Email(message = "Please enter a valid email address.")
        String email,
        @Pattern(regexp = "^0[67][0-9]{8}$", message = "Please enter a valid phone number.")
        String phone_number
) {}
