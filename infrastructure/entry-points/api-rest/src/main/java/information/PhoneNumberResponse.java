package information;

import java.time.Instant;

public record PhoneNumberResponse(
        Long id,
        String countryCode,
        short extension,
        String phone,
        boolean isActive,
        Instant created_at,
        Instant updated_at
) {
}
