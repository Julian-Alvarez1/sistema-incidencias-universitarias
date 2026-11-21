package com.example.Gestion.repository;

import com.example.Gestion.entity.Asignacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsignacionRepository extends JpaRepository<Asignacion, Integer> {

    List<Asignacion> findByTecnicoId(Integer tecnicoId);

    List<Asignacion> findByIncidenciaId(Integer incidenciaId);

    List<Asignacion> findByActiva(Boolean activa);
}
