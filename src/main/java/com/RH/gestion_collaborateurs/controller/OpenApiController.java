package com.RH.gestion_collaborateurs.controller;

import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenApiController {
    private final OpenAPI openAPI;

    @Autowired
    public OpenApiController(OpenAPI openAPI) {
        this.openAPI = openAPI;
    }

    @GetMapping(value = "/api-docs-custom", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getOpenApiDoc() {
        return ResponseEntity.ok(Json.pretty(openAPI));
    }
}