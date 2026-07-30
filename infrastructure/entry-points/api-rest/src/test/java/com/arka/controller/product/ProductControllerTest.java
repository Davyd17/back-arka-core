package com.arka.controller.product;

import com.arka.mappers.ProductRestMapperImpl;
import com.arka.product.CreateProductUseCase;
import com.arka.product.ListAllProductsUseCase;
import com.arka.product.dto.CreateProductOut;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class,
        excludeAutoConfiguration = {
                SecurityAutoConfiguration.class,
                SecurityFilterAutoConfiguration.class
        })
@ActiveProfiles("test")
@Import(ProductRestMapperImpl.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CreateProductUseCase createProductUseCase;

    @MockitoBean
    private ListAllProductsUseCase listAllProductsUseCase;

    @Test
    void shouldCreateProduct() throws Exception {
        // given
        CreateProductOut mockOutput = new CreateProductOut(
                1L, "SKU-123", "Test Product", "", BigDecimal.TEN,
                new HashMap<>(), "Test Category");

        when(createProductUseCase.execute(any())).thenReturn(mockOutput);

        Map<String, Object> request = Map.of(
                "sku", "SKU-123",
                "name", "Test Product",
                "description", "Product description",
                "basePrice", new BigDecimal("10.00"),
                "categoryId", 1L
        );

        // when & then
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/v1/products/1"));
    }

}
