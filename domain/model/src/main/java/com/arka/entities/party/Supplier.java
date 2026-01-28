package com.arka.entities.party;

import com.arka.entities.information.Contact;
import com.arka.entities.product.ProductCategory;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supplier {
    private Long supplierId;
    private Instant createdAt;
    private Contact contact;
    private Set<ProductCategory> productCategories;
}
