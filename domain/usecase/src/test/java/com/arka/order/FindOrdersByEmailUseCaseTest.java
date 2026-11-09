package com.arka.order;

import com.arka.entities.order.Order;
import com.arka.enums.OrderStatus;
import com.arka.enums.OrderType;
import com.arka.exceptions.NotFoundException;
import com.arka.order.dto.OrderOut;
import com.arka.order.gateway.OrderGateway;
import com.arka.party.gateway.ContactGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class FindOrdersByEmailUseCaseTest {

    private ContactGateway contactGateway;
    private OrderGateway orderGateway;

    private FindOrdersByEmailUseCase useCase;

    @BeforeEach
    void setUp() {
        contactGateway = mock(ContactGateway.class);
        orderGateway = mock(OrderGateway.class);

        useCase = new FindOrdersByEmailUseCase(
                contactGateway,
                orderGateway
        );
    }

    @Test
    void shouldReturnMappedOrdersWhenContactExists() {
        // given
        String email = "customer@arka.com";

        when(contactGateway.existsByEmail(email)).thenReturn(true);

        Order order = Order.create(
                "Test order notes", OrderType.SALES, null);

        when(orderGateway.findByContact(email)).thenReturn(List.of(order));

        // when
        List<OrderOut> result = useCase.execute(email);

        // then
        assertThat(result).hasSize(1);
        OrderOut orderOut = result.getFirst();

        assertThat(orderOut.number()).startsWith("ORD-");
        assertThat(orderOut.status()).isEqualTo(OrderStatus.PENDING);
        assertThat(orderOut.totalPrice()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(orderOut.notes()).isEqualTo("Test order notes");

        verify(contactGateway).existsByEmail(email);
        verify(orderGateway).findByContact(email);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenContactDoesNotExist() {
        // given
        String email = "unknown@arka.com";

        when(contactGateway.existsByEmail(email)).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> useCase.execute(email))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("contact with email: " + email + " not found");

        verify(contactGateway).existsByEmail(email);
        verifyNoInteractions(orderGateway);
    }

    @Test
    void shouldThrowExceptionWhenEmailIsNullOrBlank() {
        // when & then
        assertThatThrownBy(() -> useCase.execute(null))
                .isInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(contactGateway);
        verifyNoInteractions(orderGateway);
    }

    @Test
    void shouldReturnEmptyListWhenContactExistsButHasNoOrders() {
        // given
        String email = "customer@arka.com";

        when(contactGateway.existsByEmail(email)).thenReturn(true);
        when(orderGateway.findByContact(email)).thenReturn(List.of());

        // when
        List<OrderOut> result = useCase.execute(email);

        // then
        assertThat(result).isEmpty();

        verify(contactGateway).existsByEmail(email);
        verify(orderGateway).findByContact(email);
    }
}
