package com.example.Gestion.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "incidencia")
public class Incidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_incidencia")
    private Integer id;

    @Column(name = "descripcion", columnDefinition = "TEXT", nullable = false)
    private String descripcion;

    @Column(name = "prioridad", nullable = false)
    @Enumerated(EnumType.STRING)
    private Prioridad prioridad;

    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoIncidencia estado = EstadoIncidencia.REGISTRADA;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo", nullable = false)
    private TipoIncidencia tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ubicacion", nullable = false)
    private Ubicacion ubicacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reportante", nullable = false)
    private Usuario reportante;

    @OneToMany(mappedBy = "incidencia", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Asignacion> asignaciones;

    @OneToMany(mappedBy = "incidencia", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<HistorialEstado> historiales;

    public Incidencia() {
        this.fechaRegistro = LocalDateTime.now();
    }

    public enum EstadoIncidencia {
        REGISTRADA, ASIGNADA, EN_PROCESO, RESUELTA, CERRADA
    }

    public enum Prioridad {
        BAJA, MEDIA, ALTA, CRITICA
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Prioridad getPrioridad() { return prioridad; }
    public void setPrioridad(Prioridad prioridad) { this.prioridad = prioridad; }

    public EstadoIncidencia getEstado() { return estado; }
    public void setEstado(EstadoIncidencia estado) { this.estado = estado; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public TipoIncidencia getTipo() { return tipo; }
    public void setTipo(TipoIncidencia tipo) { this.tipo = tipo; }

    public Ubicacion getUbicacion() { return ubicacion; }
    public void setUbicacion(Ubicacion ubicacion) { this.ubicacion = ubicacion; }

    public Usuario getReportante() { return reportante; }
    public void setReportante(Usuario reportante) { this.reportante = reportante; }

    public List<Asignacion> getAsignaciones() { return asignaciones; }
    public void setAsignaciones(List<Asignacion> asignaciones) { this.asignaciones = asignaciones; }

    public List<HistorialEstado> getHistoriales() { return historiales; }
    public void setHistoriales(List<HistorialEstado> historiales) { this.historiales = historiales; }
}
