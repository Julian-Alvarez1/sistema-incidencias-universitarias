package com.example.Gestion.repository;

import com.example.Gestion.entity.HistorialEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialEstadoRepository extends JpaRepository<HistorialEstado, Integer> {

    List<HistorialEstado> findByIncidenciaIdOrderByFechaDesc(Integer incidenciaId);

    List<HistorialEstado> findByUsuarioId(Integer usuarioId);
}
