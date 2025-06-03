package com.alejandrosamuel.apinotas.controller.v1;

import com.alejandrosamuel.apinotas.model.Nota;
import com.alejandrosamuel.apinotas.service.NotaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/notas")
@CrossOrigin(origins = "*")
public class NotaController {
    
    private final NotaService notaService;
    
    @Autowired
    public NotaController(NotaService notaService) {
        this.notaService = notaService;
    }
    
    @GetMapping
    public ResponseEntity<List<Nota>> getAllNotas(
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(defaultValue = "asc") String order) {
        
        if (usuarioId != null) {
            Sort sort = order.equalsIgnoreCase("desc") ? 
                       Sort.by("fechaCreacion").descending() : 
                       Sort.by("fechaCreacion").ascending();
            List<Nota> notas = notaService.getNotasByUsuarioId(usuarioId, sort);
            return ResponseEntity.ok(notas);
        } else {
            List<Nota> notas = notaService.getAll();
            return ResponseEntity.ok(notas);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Nota> getNotaById(@PathVariable @Positive Long id) {
        Optional<Nota> nota = notaService.getById(id);
        return nota.map(ResponseEntity::ok)
                  .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nota no encontrada"));
    }
    
    @PostMapping
    public ResponseEntity<Nota> createNota(@Valid @RequestBody Nota nota, 
                                          @RequestParam @Positive Long usuarioId) {
        Nota savedNota = notaService.createNotaForUsuario(usuarioId, nota);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedNota);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Nota> updateNota(@PathVariable @Positive Long id, 
                                          @Valid @RequestBody Nota nota) {
        Nota updatedNota = notaService.update(id, nota);
        return ResponseEntity.ok(updatedNota);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNota(@PathVariable @Positive Long id) {
        notaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
