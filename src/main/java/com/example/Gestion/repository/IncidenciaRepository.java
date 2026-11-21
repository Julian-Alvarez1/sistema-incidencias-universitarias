package com.example.Gestion.repository;

import com.example.Gestion.entity.Incidencia;
import com.example.Gestion.entity.TipoIncidencia;
import com.example.Gestion.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia, Integer> {

    List<Incidencia> findByReportante(Usuario reportante);

    List<Incidencia> findByEstado(Incidencia.EstadoIncidencia estado);

    List<Incidencia> findByPrioridad(Incidencia.Prioridad prioridad);

    List<Incidencia> findByTipo(TipoIncidencia tipo);

    @Query("SELECT i FROM Incidencia i WHERE i.reportante.id = :idReportante")
    List<Incidencia> findByReportanteId(@Param("idReportante") Integer idReportante);

    @Query("SELECT i FROM Incidencia i WHERE i.estado IN :estados")
    List<Incidencia> findByEstadoIn(@Param("estados") List<Incidencia.EstadoIncidencia> estados);
}
