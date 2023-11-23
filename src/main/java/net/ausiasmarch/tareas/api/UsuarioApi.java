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
import net.ausiasmarch.tareas.entity.UsuarioEntity;
import net.ausiasmarch.tareas.service.UsuarioService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/usuario")
public class UsuarioApi {

    @Autowired
    UsuarioService oUsuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioEntity> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(oUsuarioService.get(id));
    }

    @GetMapping("/byusername/{username}")
    public ResponseEntity<UsuarioEntity> get(@PathVariable("username") String username) {
        return ResponseEntity.ok(oUsuarioService.getByUsername(username));
    }

    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody UsuarioEntity oUsuarioEntity) {
        return ResponseEntity.ok(oUsuarioService.create(oUsuarioEntity));
    }

    @PutMapping("")
    public ResponseEntity<UsuarioEntity> update(@RequestBody UsuarioEntity oUsuarioEntity) {
        return ResponseEntity.ok(oUsuarioService.update(oUsuarioEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(oUsuarioService.delete(id));
    }

    @GetMapping("")
    public ResponseEntity<Page<UsuarioEntity>> getPage(Pageable oPageable) {
        return ResponseEntity.ok(oUsuarioService.getPage(oPageable));
    }

    @PostMapping("/populate/{amount}")
    public ResponseEntity<Long> populate(@PathVariable("amount") Integer amount) {
        return ResponseEntity.ok(oUsuarioService.populate(amount));
    }

    @DeleteMapping("/empty")
    public ResponseEntity<Long> empty() {
        return ResponseEntity.ok(oUsuarioService.empty());
    }

}