package com.ronald.app.prueba.repository;

import com.ronald.app.prueba.model.Estado;
import com.ronald.app.prueba.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {

    @Query("""
            SELECT count(t) 
            FROM Tarea t 
            WHERE t.estado  = :estado
            """)
    Long countByEstado(@Param(value = "estado") Estado estado);

}

