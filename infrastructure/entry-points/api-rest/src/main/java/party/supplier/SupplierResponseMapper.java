package party.supplier;

import com.arka.entities.party.Supplier;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplierResponseMapper {

    SupplierResponse toResponse(Supplier domain);
}
