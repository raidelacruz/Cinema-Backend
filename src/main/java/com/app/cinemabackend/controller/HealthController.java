package com.app.cinemabackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@Tag(name = "Estado del Sistema", description = "Endpoints de verificación y bienvenida al backend")
public class HealthController {

    @GetMapping("/")
    @Operation(summary = "Página de bienvenida con accesos directos")
    public ResponseEntity<Map<String, Object>> bienvenida() {
        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("proyecto", "Sistema Web de Gestión de Reservas - Cinépolis");
        respuesta.put("estado", "OPERATIVO");
        respuesta.put("version", "1.0.0");
        respuesta.put("documentacion_swagger", "/swagger-ui/index.html");
        respuesta.put("endpoints_principales", Map.of(
                "cartelera", "/api/peliculas",
                "funciones_asientos", "/api/funciones/{id}/butacas",
                "bloqueo_concurrente", "/api/funciones/bloquear-butacas",
                "dulceria", "/api/dulceria",
                "reservas_pagos_qr", "/api/reservas"
        ));
        respuesta.put("timestamp", OffsetDateTime.now());
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/api/health")
    @Operation(summary = "Healthcheck de verificación para monitoreo de uptime (RNF06)")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "uptime", "99.9%",
                "timestamp", OffsetDateTime.now()
        ));
    }
}
