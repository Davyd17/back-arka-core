package party.supplier;

import com.arka.entities.party.Supplier;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplierEntityMapper {

    Supplier toDomain(SupplierEntity supplierEntity);
}
