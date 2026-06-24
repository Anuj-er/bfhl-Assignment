package com.bajajfinserv.bfhl.controller;

import com.bajajfinserv.bfhl.dto.BfhlRequest;
import com.bajajfinserv.bfhl.dto.BfhlResponse;
import com.bajajfinserv.bfhl.service.BfhlService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bfhl")
public class BfhlController {

    private final BfhlService bfhlService;

    public BfhlController(BfhlService bfhlService) {
        this.bfhlService = bfhlService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BfhlResponse process(@Valid @RequestBody BfhlRequest request) {
        return bfhlService.process(request);
    }
}
