package com.arka.controller;

import com.arka.mappers.ContactRestMapper;
import com.arka.party.CreateContactUseCase;
import com.arka.party.dto.ContactOutput;
import com.arka.request.CreateContactRequest;
import com.arka.response.AppResponse;
import com.arka.response.get.ContactResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/v1/internal")
@RequiredArgsConstructor
public class InternalRestController {

    private final CreateContactUseCase createContactUseCase;
    private final ContactRestMapper contactRestMapper;

    @PostMapping("/contacts")
    public ResponseEntity<ContactResponse> save(
            @Valid @RequestBody CreateContactRequest request){

        ContactOutput contactOutput = createContactUseCase.execute(
                contactRestMapper.toCreateInput(request));

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(contactOutput.id())
                .toUri();

        return ResponseEntity.created(uri).body(
                contactRestMapper.toResponse(contactOutput));
    }
}
