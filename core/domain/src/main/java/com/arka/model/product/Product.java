package com.arka.model.product;

import com.arka.exceptions.InvalidProductStateException;
import com.arka.exceptions.NotFoundException;
import jakarta.annotation.Nullable;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class Product {
    private Long id;
    private String sku;
    @Setter private String name;
    @Setter private String description;
    private BigDecimal basePrice;
    private Map<String, Object> attributes;
    @Setter private ProductCategory category;
    private boolean active;

    public static Product create(
            String sku,
            String name,
            @Nullable String description,
            BigDecimal basePrice,
            ProductCategory category
    ){

        return Product.builder()
                .sku(sku)
                .name(name)
                .description(description)
                .basePrice(basePrice)
                .attributes(new HashMap<>())
                .category(category)
                .active(true)
                .build();
    }

    public void activate(){

        if(this.active)
            throw new InvalidProductStateException(this.sku, this.active);

        this.active = true;
    }

    public void deactivate(){

        if(!this.active)
            throw new InvalidProductStateException(this.sku, this.active);

        this.active = false;
    }

    public void updatePrice(BigDecimal newPrice){

        if(newPrice == null || newPrice.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Price must be greater than zero");

        basePrice = newPrice;
    }

    public void addAttribute(String key, Object value){
        this.attributes.put(key, value);
    }

    public void removeAttribute(String key){

        this.validateIfAttributeExists(key);

        this.attributes.remove(key);
    }

    public void updateAttribute(String key, Object value){

        this.validateIfAttributeExists(key);

        this.attributes.replace(key, value);
    }

    private void validateIfAttributeExists(String key){

        if(!this.attributes.containsKey(key))
            throw new NotFoundException(
                    String.format("Attribute [%s] not found in product [%s]", key, this.sku));
    }

    public void correctSku(String newSku){
        this.sku = newSku;
    }

}
