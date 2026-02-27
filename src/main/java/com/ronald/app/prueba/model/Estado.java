package com.ronald.app.prueba.model;

import lombok.Getter;

@Getter
public enum Estado {
    PENDIENTE("Pendiente"),
    EN_PROCESO("En Proceso"),
    FINALIZADO("Terminado");

    private final String descripcion;

    Estado(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
