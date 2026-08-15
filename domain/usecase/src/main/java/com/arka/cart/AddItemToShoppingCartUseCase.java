package com.arka.cart;

import com.arka.cart.dto.AddItemShoppingCartIn;
import com.arka.cart.dto.ShoppingCartOut;
import com.arka.cart.mapper.ShoppingCartMapperImpl;
import com.arka.entities.cart.ShoppingCart;
import com.arka.entities.information.Contact;
import com.arka.entities.product.Product;
import com.arka.enums.ShoppingCartStatus;
import com.arka.cart.gateway.ShoppingCartGateway;
import com.arka.cart.mapper.ShoppingCartMapper;
import com.arka.party.service.ContactService;
import com.arka.product.service.ProductService;
import com.arka.inventory.service.WarehouseInventoryService;
import com.arka.util.NullValidator;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;

@RequiredArgsConstructor
public class AddItemToShoppingCartUseCase {

    private final ShoppingCartGateway cartGateway;

    private final ProductService productService;
    private final WarehouseInventoryService inventoryService;
    private final ContactService contactService;

    private final ShoppingCartMapper mapper =
            Mappers.getMapper(ShoppingCartMapper.class);

    public ShoppingCartOut execute(AddItemShoppingCartIn input, String callerEmail){

        NullValidator.validate(input, "input");

        Contact existingContact = contactService.findByEmail(callerEmail);

        Product foundProduct = productService.findById(input.productId());

        inventoryService.validateGeneralStockAvailability(
                input.productId(), input.quantity());

        ShoppingCart cart = getOrCreateCart(existingContact);

        cart.addItem(foundProduct, input.quantity());

        return mapper.toOutDto(cartGateway.save(cart));
    }

    private ShoppingCart getOrCreateCart(Contact contact){
        return cartGateway.getLastCreatedCart(contact.getId())
                .filter(cart ->
                        cart.getStatus().equals(ShoppingCartStatus.ABANDONED) ||
                        cart.getStatus().equals(ShoppingCartStatus.ACTIVE))
                .orElseGet(() -> ShoppingCart.create(contact));
    }
}
