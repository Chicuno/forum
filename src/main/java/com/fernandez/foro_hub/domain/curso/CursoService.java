package com.fernandez.foro_hub.domain.curso;

import com.fernandez.foro_hub.domain.ValidacionException;
import com.fernandez.foro_hub.domain.pregunta.DatosListaPregunta;
import com.fernandez.foro_hub.domain.pregunta.PreguntaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private PreguntaRepository preguntaRepository;

    @Transactional
    public DatosDetalleCurso registrar(DatosRegistroCurso datos) {
        var curso = new Curso(datos);
        cursoRepository.save(curso);
        return new DatosDetalleCurso(curso);
    }

    @Transactional(readOnly = true)
    public Page<DatosDetalleCurso> listar(Pageable paginacion) {
        return cursoRepository.findAll(paginacion)
                .map(DatosDetalleCurso::new);
    }

    @Transactional(readOnly = true)
    public DatosDetalleCurso obtener(Long cursoId) {
        var curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new ValidacionException("Curso no encontrado"));
        return new DatosDetalleCurso(curso);
    }

    @Transactional(readOnly = true)
    public Page<DatosListaPregunta> listarPreguntas(Long cursoId, Pageable paginacion) {
        return preguntaRepository.findByCursoIdAndActivoTrue(cursoId, paginacion)
                .map(DatosListaPregunta::new);
    }
}
