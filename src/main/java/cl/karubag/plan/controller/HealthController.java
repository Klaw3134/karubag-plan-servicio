package cl.karubag.plan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Endpoint de health check para verificar que el microservicio está arriba.
 *
 * GET /health → { "status": "UP", "service": "plan-servicio" }
 */
@RestController
public class HealthController {

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of(
            "status", "UP",
            "service", "plan-servicio"
        );
    }
}
