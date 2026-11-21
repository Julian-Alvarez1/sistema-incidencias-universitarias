package com.example.Gestion.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historial_estado")
public class HistorialEstado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial")
    private Integer id;

    @Column(name = "estado_anterior")
    @Enumerated(EnumType.STRING)
    private Incidencia.EstadoIncidencia estadoAnterior;

    @Column(name = "estado_nuevo", nullable = false)
    @Enumerated(EnumType.STRING)
    private Incidencia.EstadoIncidencia estadoNuevo;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_incidencia", nullable = false)
    private Incidencia incidencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    public HistorialEstado() {
        this.fecha = LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Incidencia.EstadoIncidencia getEstadoAnterior() { return estadoAnterior; }
    public void setEstadoAnterior(Incidencia.EstadoIncidencia estadoAnterior) { this.estadoAnterior = estadoAnterior; }

    public Incidencia.EstadoIncidencia getEstadoNuevo() { return estadoNuevo; }
    public void setEstadoNuevo(Incidencia.EstadoIncidencia estadoNuevo) { this.estadoNuevo = estadoNuevo; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public Incidencia getIncidencia() { return incidencia; }
    public void setIncidencia(Incidencia incidencia) { this.incidencia = incidencia; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}
