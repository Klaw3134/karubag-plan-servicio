package cl.karubag.plan.service;

import cl.karubag.plan.dto.PlanDTO;
import cl.karubag.plan.model.Plan;
import cl.karubag.plan.repository.PlanRepository;
import cl.karubag.plan.exception.ResourceNotFoundException;
import cl.karubag.plan.exception.DuplicateResourceException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanService {

    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public List<PlanDTO> listarTodos() {
        return planRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PlanDTO> listarActivos() {
        return planRepository.findByActivoTrue()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PlanDTO obtenerPorId(Long id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plan no encontrado con id: " + id));
        return toDTO(plan);
    }

    public PlanDTO crear(PlanDTO dto) {
        if (planRepository.existsByNombre(dto.getNombre())) {
            throw new DuplicateResourceException("Ya existe un plan con el nombre: " + dto.getNombre());
        }
        Plan plan = toEntity(dto);
        return toDTO(planRepository.save(plan));
    }

    public PlanDTO actualizar(Long id, PlanDTO dto) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plan no encontrado con id: " + id));
        plan.setNombre(dto.getNombre());
        plan.setDescripcion(dto.getDescripcion());
        plan.setPrecioMensual(dto.getPrecioMensual());
        plan.setActivo(dto.getActivo());
        return toDTO(planRepository.save(plan));
    }

    public void eliminar(Long id) {
        planRepository.deleteById(id);
    }

    private PlanDTO toDTO(Plan plan) {
        PlanDTO dto = new PlanDTO();
        dto.setId(plan.getId());
        dto.setNombre(plan.getNombre());
        dto.setDescripcion(plan.getDescripcion());
        dto.setPrecioMensual(plan.getPrecioMensual());
        dto.setActivo(plan.getActivo());
        return dto;
    }

    private Plan toEntity(PlanDTO dto) {
        Plan plan = new Plan();
        plan.setNombre(dto.getNombre());
        plan.setDescripcion(dto.getDescripcion());
        plan.setPrecioMensual(dto.getPrecioMensual());
        plan.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        return plan;
    }
}