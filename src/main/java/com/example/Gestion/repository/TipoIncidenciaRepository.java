package com.example.Gestion.repository;

import com.example.Gestion.entity.TipoIncidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoIncidenciaRepository extends JpaRepository<TipoIncidencia, Integer> {

    List<TipoIncidencia> findByPrioridadBase(TipoIncidencia.Prioridad prioridad);
}
