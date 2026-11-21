package com.example.Gestion.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "tipo_incidencia")
public class TipoIncidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo")
    private Integer id;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "prioridad_base", nullable = false)
    @Enumerated(EnumType.STRING)
    private Prioridad prioridadBase = Prioridad.MEDIA;

    @OneToMany(mappedBy = "tipo", fetch = FetchType.LAZY)
    private List<Incidencia> incidencias;

    public TipoIncidencia() {}

    public TipoIncidencia(String nombre, Prioridad prioridadBase) {
        this.nombre = nombre;
        this.prioridadBase = prioridadBase;
    }

    public enum Prioridad {
        BAJA, MEDIA, ALTA, CRITICA
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Prioridad getPrioridadBase() { return prioridadBase; }
    public void setPrioridadBase(Prioridad prioridadBase) { this.prioridadBase = prioridadBase; }

    public List<Incidencia> getIncidencias() { return incidencias; }
    public void setIncidencias(List<Incidencia> incidencias) { this.incidencias = incidencias; }
}
