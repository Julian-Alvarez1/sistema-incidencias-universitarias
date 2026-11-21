package com.example.Gestion.repository;

import com.example.Gestion.entity.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UbicacionRepository extends JpaRepository<Ubicacion, Integer> {

    List<Ubicacion> findByBloque(String bloque);

    List<Ubicacion> findByTipoEspacio(String tipoEspacio);
}
