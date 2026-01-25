package information;

import com.arka.entities.enums.AddressType;

public record AddressResponse(
        Long id,
        String country,
        String city,
        short zipCode,
        String address,
        String notes,
        AddressType addressType,
        boolean isActive
) {
}
