package net.ausiasmarch.tareas.api;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.tareas.entity.ProyectoEntity;
import net.ausiasmarch.tareas.service.ProyectoService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/proyecto")
public class ProyectoApi {
    @Autowired
    ProyectoService oProyectoService;

    @GetMapping("/{id}")
    public ResponseEntity<ProyectoEntity> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(oProyectoService.get(id));
    }

    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody ProyectoEntity oProyectoEntity) {
        oProyectoEntity.setFechaInicio(LocalDateTime.now());
        Long idFechaCreada = oProyectoService.create(oProyectoEntity);
        return ResponseEntity.ok(idFechaCreada);
    }

    @PutMapping("")
    public ResponseEntity<ProyectoEntity> update(@RequestBody ProyectoEntity oProyectoEntity) {
        return ResponseEntity.ok(oProyectoService.update(oProyectoEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(oProyectoService.delete(id));
    }

    @GetMapping("")
    public ResponseEntity<Page<ProyectoEntity>> getPage(Pageable oPageable,
            @RequestParam(value = "usuario", defaultValue = "0", required = false) Long usuario_id) {
        return ResponseEntity.ok(oProyectoService.getPage(oPageable, usuario_id));
    }

    @PostMapping("/populate/{amount}")
    public ResponseEntity<Long> populate(@PathVariable("amount") Integer amount) {
        return ResponseEntity.ok(oProyectoService.populate(amount));
    }

    @DeleteMapping("/empty")
    public ResponseEntity<Long> empty() {
        return ResponseEntity.ok(oProyectoService.empty());
    }

}