package com.fernandez.foro_hub.controller;

import com.fernandez.foro_hub.domain.curso.CursoService;
import com.fernandez.foro_hub.domain.curso.DatosDetalleCurso;
import com.fernandez.foro_hub.domain.curso.DatosRegistroCurso;
import com.fernandez.foro_hub.domain.pregunta.DatosListaPregunta;
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
@RequestMapping("/cursos")

public class CursoController {

    @Autowired private CursoService service;

    @PostMapping
    public ResponseEntity<DatosDetalleCurso> registrar(@RequestBody @Valid DatosRegistroCurso datos, UriComponentsBuilder uriComponentsBuilder) {
        var curso = service.registrar(datos);
        var uri = uriComponentsBuilder.path("/cursos/{id}").buildAndExpand(curso.id()).toUri();

        return ResponseEntity.created(uri).body(curso);
    }
    @GetMapping
    public ResponseEntity<Page<DatosDetalleCurso>> listar(@PageableDefault(size=10, sort={"nombre"}) Pageable paginacion){

        var page = service.listar(paginacion);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{cursoId}")
    public ResponseEntity<DatosDetalleCurso> obtenerCurso(@PathVariable Long cursoId) {
        return ResponseEntity.ok(service.obtener(cursoId));
    }

    @GetMapping("/{cursoId}/preguntas")
    public ResponseEntity<Page<DatosListaPregunta>> listarPreguntasDelCurso(@PathVariable Long cursoId, @PageableDefault(size=10, sort={"fechaCreacion"}, direction = Sort.Direction.DESC) Pageable paginacion){
        var page = service.listarPreguntas(cursoId, paginacion);
        return ResponseEntity.ok(page);
    }

}
