package party.supplier;

import com.arka.ListSuppliersByCategoryUseCase;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/supplier")
@RequiredArgsConstructor
public class SupplierController {

    private final ListSuppliersByCategoryUseCase listSupplierByCategory;
    private final SupplierResponseMapper mapper;

    @GetMapping("category/{category}")
    public ResponseEntity<List<SupplierResponse>> listByCategoryName(@PathVariable @NotBlank String category) {

        return ResponseEntity
                .ok(listSupplierByCategory.execute(category)
                        .stream()
                        .map(mapper::toResponse)
                        .toList());
    }

}
