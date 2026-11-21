package com.example.Gestion.repository;

import com.example.Gestion.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByCorreo(String correo);

    Usuario findByDocumento(String documento);

    List<Usuario> findByRolNombre(String rolNombre);

    List<Usuario> findByRolNombreAndEstado(String rolNombre, Usuario.EstadoUsuario estado);
}
