package com.example.Gestion.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "documento", length = 20, nullable = false, unique = true)
    private String documento;

    @Column(name = "correo", length = 100, nullable = false, unique = true)
    private String correo;

    @Column(name = "clave_hash", length = 255, nullable = false)
    private String claveHash;

    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoUsuario estado = EstadoUsuario.ACTIVO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;

    @OneToMany(mappedBy = "reportante", fetch = FetchType.LAZY)
    private List<Incidencia> incidenciasReportadas;

    @OneToMany(mappedBy = "tecnico", fetch = FetchType.LAZY)
    private List<Asignacion> asignaciones;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<HistorialEstado> historiales;

    @OneToMany(mappedBy = "administrador", fetch = FetchType.LAZY)
    private List<Reporte> reportes;

    public Usuario() {}

    public enum EstadoUsuario {
        ACTIVO, INACTIVO
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getClaveHash() { return claveHash; }
    public void setClaveHash(String claveHash) { this.claveHash = claveHash; }

    public EstadoUsuario getEstado() { return estado; }
    public void setEstado(EstadoUsuario estado) { this.estado = estado; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public List<Incidencia> getIncidenciasReportadas() { return incidenciasReportadas; }
    public void setIncidenciasReportadas(List<Incidencia> incidenciasReportadas) { this.incidenciasReportadas = incidenciasReportadas; }

    public List<Asignacion> getAsignaciones() { return asignaciones; }
    public void setAsignaciones(List<Asignacion> asignaciones) { this.asignaciones = asignaciones; }

    public List<HistorialEstado> getHistoriales() { return historiales; }
    public void setHistoriales(List<HistorialEstado> historiales) { this.historiales = historiales; }

    public List<Reporte> getReportes() { return reportes; }
    public void setReportes(List<Reporte> reportes) { this.reportes = reportes; }

    public boolean isAdministrador() {
        return rol != null && "ADMINISTRADOR".equalsIgnoreCase(rol.getNombre());
    }

    public boolean isTecnico() {
        return rol != null && "TECNICO".equalsIgnoreCase(rol.getNombre());
    }
}
