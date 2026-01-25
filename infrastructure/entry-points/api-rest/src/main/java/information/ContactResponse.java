package information;

import java.time.Instant;
import java.util.List;

public record ContactResponse(
        Long contactId,
        String name,
        String lastName,
        String company,
        String position,
        String email,
        List<AddressResponse>addresses,
        List<PhoneNumberResponse> phoneNumbers,
        Instant created_at,
        Instant updated_at,
        boolean isActive,
        Long userId
) {

}
