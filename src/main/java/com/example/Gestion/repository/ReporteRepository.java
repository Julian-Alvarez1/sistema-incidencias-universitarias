package com.example.Gestion.repository;

import com.example.Gestion.entity.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Integer> {

    List<Reporte> findByAdministradorId(Integer administradorId);

    List<Reporte> findByTipo(String tipo);
}
