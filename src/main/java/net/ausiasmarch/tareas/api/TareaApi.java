package net.ausiasmarch.tareas.api;

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
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.tareas.entity.TareaEntity;
import net.ausiasmarch.tareas.service.TareaService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/tarea")
public class TareaApi {

    @Autowired
    TareaService oTareaService;

    @GetMapping("/{id}")
    public ResponseEntity<TareaEntity> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(oTareaService.get(id));
    }

    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody TareaEntity oTareaEntity) {
        return ResponseEntity.ok(oTareaService.create(oTareaEntity));
    }
/*
    @PutMapping("")
    public ResponseEntity<TareaEntity> update(@RequestBody TareaEntity oTareaEntity) {
        return ResponseEntity.ok(oTareaService.update(oTareaEntity));
    }*/

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(oTareaService.delete(id));
    }

    @GetMapping("")
    public ResponseEntity<Page<TareaEntity>> getPage(Pageable oPageable) {
        return ResponseEntity.ok(oTareaService.getPage(oPageable));
    }

    @PostMapping("/populate/{amount}")
    public ResponseEntity<Long> populate(@PathVariable("amount") Integer amount) {
        return ResponseEntity.ok(oTareaService.populate(amount));
    }

    @DeleteMapping("/empty")
    public ResponseEntity<Long> empty() {
        return ResponseEntity.ok(oTareaService.empty());
    }


}