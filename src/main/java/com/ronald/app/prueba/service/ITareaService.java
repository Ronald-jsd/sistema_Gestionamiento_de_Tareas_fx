package com.ronald.app.prueba.service;

import com.ronald.app.prueba.model.Estado;
import com.ronald.app.prueba.model.Tarea;

import java.util.List;

public interface ITareaService {
    List<Tarea> listarTareas();

    Tarea buscarTareaPorId(Long id);

    Tarea guardarTarea(Tarea tarea);

    void eliminarTarea(Long id);

    Long cantidadTareasByEstado(Estado cantidadTareasByEstado);
}
