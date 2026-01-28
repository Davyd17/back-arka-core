package com.arka.model.party;

import com.arka.model.information.Contact;
import com.arka.model.product.ProductCategory;
import lombok.*;

import java.time.Instant;
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
