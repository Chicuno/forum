package com.fernandez.foro_hub.controller;

import com.fernandez.foro_hub.domain.pregunta.DatosListaPregunta;
import com.fernandez.foro_hub.domain.usuario.DatosDetalleUsuario;
import com.fernandez.foro_hub.domain.usuario.DatosRegistroUsuario;
import com.fernandez.foro_hub.domain.usuario.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    public ResponseEntity<DatosDetalleUsuario> registrar(@RequestBody @Valid DatosRegistroUsuario datos, UriComponentsBuilder uriComponentsBuilder) {
        var usuario = service.registrar(datos);
        var uri = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuario.id()).toUri();

        return ResponseEntity.created(uri).body(usuario);
    }

    @GetMapping
    public ResponseEntity<Page<DatosDetalleUsuario>> listar(@PageableDefault(size=10, sort={"nombre"}) Pageable paginacion){

        var page = service.listar(paginacion);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{usuarioId}/preguntas")
    public ResponseEntity<Page<DatosListaPregunta>> listarPreguntasDeUsuario(@PathVariable Long usuarioId, @PageableDefault(size=10, sort={"fechaCreacion"}, direction = Sort.Direction.DESC) Pageable paginacion){
        var page = service.listarPreguntas(usuarioId, paginacion);
        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
