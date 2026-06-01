package cl.karubag.plan.controller;

import cl.karubag.plan.dto.PlanDTO;
import cl.karubag.plan.service.PlanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/planes")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping
    public ResponseEntity<List<PlanDTO>> listarTodos() {
        return ResponseEntity.ok(planService.listarTodos());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<PlanDTO>> listarActivos() {
        return ResponseEntity.ok(planService.listarActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(planService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<PlanDTO> crear(@Valid @RequestBody PlanDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(planService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanDTO> actualizar(@PathVariable Long id, @Valid @RequestBody PlanDTO dto) {
        return ResponseEntity.ok(planService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        planService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}