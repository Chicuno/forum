package com.fernandez.foro_hub.domain.usuario;

import com.fernandez.foro_hub.domain.ValidacionException;
import com.fernandez.foro_hub.domain.pregunta.DatosListaPregunta;
import com.fernandez.foro_hub.domain.pregunta.PreguntaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PreguntaRepository preguntaRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public DatosDetalleUsuario registrar(DatosRegistroUsuario datos) {
        var usuario = new Usuario(datos);
        usuario.setContrasena(passwordEncoder.encode(datos.contrasena()));
        usuarioRepository.save(usuario);
        return new DatosDetalleUsuario(usuario);
    }

    @Transactional(readOnly = true)
    public Page<DatosDetalleUsuario> listar(Pageable paginacion) {
        return usuarioRepository.findByActivoTrue(paginacion)
                .map(DatosDetalleUsuario::new);
    }

    @Transactional(readOnly = true)
    public Page<DatosListaPregunta> listarPreguntas(Long usuarioId, Pageable paginacion) {
        return preguntaRepository.findByAutorIdAndActivoTrue(usuarioId, paginacion)
                .map(DatosListaPregunta::new);
    }

    @Transactional
    public void eliminar(Long id) {
        var usuario = usuarioRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new ValidacionException("Id del usuario informado no existe"));
        usuario.eliminar();
    }
}
