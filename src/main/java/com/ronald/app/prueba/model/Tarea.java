package com.ronald.app.prueba.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_tareas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_tarea")
    private String nombreTarea;

    private String responsable;

    @Enumerated(EnumType.STRING)
    private Estado estado;
}
