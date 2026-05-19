package com.arka.service;

import com.arka.gateway.party.CustomerGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerGateway customerGateway;

    @InjectMocks
    private CustomerService service;

    @Test
    void shouldThrowWhenSinceDateIsAfterUntilDate(){

        LocalDate since = LocalDate.now().minusDays(1);

        LocalDate until = LocalDate.now().minusDays(2);

        assertThrows(IllegalArgumentException.class, () ->
                service.getMostFrequentBuyers(since, until));
    }

    @Test
    void shouldThrowWhenUntilDateIsInTheFuture(){

        LocalDate since = LocalDate.now().minusDays(2);

        LocalDate until = LocalDate.now().plusDays(1);

        assertThrows(IllegalArgumentException.class, () ->
                service.getMostFrequentBuyers(since, until));
    }

    @Test
    void shouldThrowWhenUntilOrSinceDateAreNull(){

        assertThrows(IllegalArgumentException.class, () ->
                service.getMostFrequentBuyers(null, null));
    }


}