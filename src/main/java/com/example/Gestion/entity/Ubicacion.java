package com.example.Gestion.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "ubicacion")
public class Ubicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ubicacion")
    private Integer id;

    @Column(name = "bloque", length = 10, nullable = false)
    private String bloque;

    @Column(name = "piso", nullable = false)
    private Integer piso;

    @Column(name = "espacio", length = 50, nullable = false)
    private String espacio;

    @Column(name = "tipo_espacio", length = 50, nullable = false)
    private String tipoEspacio;

    @OneToMany(mappedBy = "ubicacion", fetch = FetchType.LAZY)
    private List<Incidencia> incidencias;

    public Ubicacion() {}

    public Ubicacion(String bloque, Integer piso, String espacio, String tipoEspacio) {
        this.bloque = bloque;
        this.piso = piso;
        this.espacio = espacio;
        this.tipoEspacio = tipoEspacio;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getBloque() { return bloque; }
    public void setBloque(String bloque) { this.bloque = bloque; }

    public Integer getPiso() { return piso; }
    public void setPiso(Integer piso) { this.piso = piso; }

    public String getEspacio() { return espacio; }
    public void setEspacio(String espacio) { this.espacio = espacio; }

    public String getTipoEspacio() { return tipoEspacio; }
    public void setTipoEspacio(String tipoEspacio) { this.tipoEspacio = tipoEspacio; }

    public List<Incidencia> getIncidencias() { return incidencias; }
    public void setIncidencias(List<Incidencia> incidencias) { this.incidencias = incidencias; }

    @Override
    public String toString() {
        return bloque + "-" + piso + " " + espacio + " (" + tipoEspacio + ")";
    }
}
