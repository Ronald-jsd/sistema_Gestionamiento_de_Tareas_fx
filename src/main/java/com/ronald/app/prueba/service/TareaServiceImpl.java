package com.ronald.app.prueba.service;

import com.ronald.app.prueba.model.Estado;
import com.ronald.app.prueba.model.Tarea;
import com.ronald.app.prueba.repository.TareaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TareaServiceImpl implements ITareaService{

    @Autowired
    private TareaRepository tareaRepository;

    @Override
    public List<Tarea> listarTareas() {
        return tareaRepository.findAll();
    }

    @Override
    public Tarea buscarTareaPorId(Long id) {
        return tareaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entidad no encontrada con id: " + id));
    }

    @Override
    public Tarea guardarTarea(Tarea tarea) {
        if(tarea == null) throw  new NullPointerException("Entidad sin atributos");
        return tareaRepository.save(tarea);
    }

    @Override
    public void eliminarTarea(Long id) {
        this.tareaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada con id: "+ id));
        this.tareaRepository.deleteById(id);
    }

    @Override
    public Long cantidadTareasByEstado(Estado estado ) {
        if(estado==null ){
            return    this.tareaRepository.count();
        }
        return this.tareaRepository.countByEstado(estado);
    }


}
