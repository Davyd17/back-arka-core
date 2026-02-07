package com.arka.controller;

import com.arka.dto.out.ShippingDetailOut;
import com.arka.mappers.ShippingDetailRestMapper;
import com.arka.request.CreateShippingDetailRequest;
import com.arka.response.save.CreateShippingDetailResponse;
import com.arka.usecase.RegisterShippingDetailsUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/shipping-details")
public class ShippingDetailController {

    private final RegisterShippingDetailsUseCase registerShippingDetailsUseCase;

    private final ShippingDetailRestMapper mapper;

    @PostMapping
    public ResponseEntity<CreateShippingDetailResponse> registerShippingDetails(@Valid @RequestBody CreateShippingDetailRequest request){

        ShippingDetailOut registeredShippingDetails =
                registerShippingDetailsUseCase.execute(mapper.toDomain(request));

        CreateShippingDetailResponse response = mapper.toResponse(registeredShippingDetails);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }
}
