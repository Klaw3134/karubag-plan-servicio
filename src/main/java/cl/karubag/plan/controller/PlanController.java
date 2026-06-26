package cl.karubag.plan.controller;

import cl.karubag.plan.dto.PlanDTO;
import cl.karubag.plan.service.PlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Planes", description = "Gestión de planes de suscripción de Karübag")
@RestController
@RequestMapping("/api/planes")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @Operation(summary = "Listar todos los planes", description = "Retorna la lista completa de planes")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<PlanDTO>> listarTodos() {
        return ResponseEntity.ok(planService.listarTodos());
    }

    @Operation(summary = "Listar planes activos", description = "Retorna solo los planes con estado activo")
    @ApiResponse(responseCode = "200", description = "Lista de planes activos")
    @GetMapping("/activos")
    public ResponseEntity<List<PlanDTO>> listarActivos() {
        return ResponseEntity.ok(planService.listarActivos());
    }

    @Operation(summary = "Obtener plan por ID", description = "Busca un plan por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Plan encontrado"),
        @ApiResponse(responseCode = "404", description = "Plan no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PlanDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(planService.obtenerPorId(id));
    }

    @Operation(summary = "Crear plan", description = "Crea un nuevo plan de suscripción")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Plan creado exitosamente",
            content = @Content(schema = @Schema(implementation = PlanDTO.class),
            examples = @ExampleObject(value = "{\"nombre\": \"Plan Básico\", \"descripcion\": \"Plan residencial\", \"precioMensual\": 9990.0, \"activo\": true}"))),
        @ApiResponse(responseCode = "409", description = "Ya existe un plan con ese nombre")
    })
    @PostMapping
    public ResponseEntity<PlanDTO> crear(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del plan a crear",
            required = true,
            content = @Content(examples = @ExampleObject(value = "{\"nombre\": \"Plan Básico\", \"descripcion\": \"Plan residencial\", \"precioMensual\": 9990.0, \"activo\": true}")))
        @Valid @RequestBody PlanDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(planService.crear(dto));
    }

    @Operation(summary = "Actualizar plan", description = "Actualiza los datos de un plan existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Plan actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Plan no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PlanDTO> actualizar(@PathVariable Long id, @Valid @RequestBody PlanDTO dto) {
        return ResponseEntity.ok(planService.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar plan", description = "Elimina un plan por su ID")
    @ApiResponse(responseCode = "204", description = "Plan eliminado exitosamente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        planService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}