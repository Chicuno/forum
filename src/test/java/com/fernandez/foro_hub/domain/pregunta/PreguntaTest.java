package com.fernandez.foro_hub.domain.pregunta;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PreguntaTest {

    @Test
    void marcarComoResueltaActualizaElStatus() {
        var pregunta = new Pregunta();
        pregunta.setStatus(Status.RESPONDIDA);

        pregunta.marcarComoResuelta();

        assertEquals(Status.RESUELTA, pregunta.getStatus());
    }

    @Test
    void marcarComoSpamActualizaElStatus() {
        var pregunta = new Pregunta();
        pregunta.setStatus(Status.RESPONDIDA);

        pregunta.marcarComoSpam();

        assertEquals(Status.SPAM, pregunta.getStatus());
    }
}
