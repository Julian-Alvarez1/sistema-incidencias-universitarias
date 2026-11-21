package com.example.Gestion.config;

import com.example.Gestion.entity.*;
import com.example.Gestion.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(
            RolRepository rolRepo,
            UsuarioRepository usuarioRepo,
            TipoIncidenciaRepository tipoRepo,
            UbicacionRepository ubicacionRepo,
            PasswordEncoder passwordEncoder) {

        return args -> {
            // Obtener o crear roles
            Rol rolEstudiante, rolDocente, rolTecnico, rolAdmin;
            if (rolRepo.count() == 0) {
                rolEstudiante = rolRepo.save(new Rol("ESTUDIANTE"));
                rolDocente = rolRepo.save(new Rol("DOCENTE"));
                rolTecnico = rolRepo.save(new Rol("TECNICO"));
                rolAdmin = rolRepo.save(new Rol("ADMINISTRADOR"));
            } else {
                rolEstudiante = rolRepo.findAll().stream()
                        .filter(r -> "ESTUDIANTE".equals(r.getNombre())).findFirst().orElse(null);
                rolDocente = rolRepo.findAll().stream()
                        .filter(r -> "DOCENTE".equals(r.getNombre())).findFirst().orElse(null);
                rolTecnico = rolRepo.findAll().stream()
                        .filter(r -> "TECNICO".equals(r.getNombre())).findFirst().orElse(null);
                rolAdmin = rolRepo.findAll().stream()
                        .filter(r -> "ADMINISTRADOR".equals(r.getNombre())).findFirst().orElse(null);
            }

            // Usuarios demo (crear si no existen o actualizar contraseña si ya existen)
            Usuario admin = usuarioRepo.findByCorreo("admin@universidad.edu");
            if (admin == null) {
                admin = new Usuario();
                admin.setNombre("Administrador Sistema");
                admin.setDocumento("1000000000");
                admin.setCorreo("admin@universidad.edu");
                admin.setEstado(Usuario.EstadoUsuario.ACTIVO);
                admin.setRol(rolAdmin);
            }
            admin.setClaveHash(passwordEncoder.encode("admin123"));
            usuarioRepo.save(admin);

            Usuario tecnico = usuarioRepo.findByCorreo("tecnico@universidad.edu");
            if (tecnico == null) {
                tecnico = new Usuario();
                tecnico.setNombre("Técnico Mantenimiento");
                tecnico.setDocumento("1000000001");
                tecnico.setCorreo("tecnico@universidad.edu");
                tecnico.setEstado(Usuario.EstadoUsuario.ACTIVO);
                tecnico.setRol(rolTecnico);
            }
            tecnico.setClaveHash(passwordEncoder.encode("tecnico123"));
            usuarioRepo.save(tecnico);

            Usuario estudiante = usuarioRepo.findByCorreo("estudiante@universidad.edu");
            if (estudiante == null) {
                estudiante = new Usuario();
                estudiante.setNombre("Estudiante Demo");
                estudiante.setDocumento("1000000002");
                estudiante.setCorreo("estudiante@universidad.edu");
                estudiante.setEstado(Usuario.EstadoUsuario.ACTIVO);
                estudiante.setRol(rolEstudiante);
            }
            estudiante.setClaveHash(passwordEncoder.encode("estudiante123"));
            usuarioRepo.save(estudiante);

            Usuario docente = usuarioRepo.findByCorreo("docente@universidad.edu");
            if (docente == null) {
                docente = new Usuario();
                docente.setNombre("Docente Demo");
                docente.setDocumento("1000000003");
                docente.setCorreo("docente@universidad.edu");
                docente.setEstado(Usuario.EstadoUsuario.ACTIVO);
                docente.setRol(rolDocente);
            }
            docente.setClaveHash(passwordEncoder.encode("docente123"));
            usuarioRepo.save(docente);

            // Tipos de incidencia (solo si no existen)
            if (tipoRepo.count() == 0) {
                tipoRepo.save(new TipoIncidencia("Eléctrica", TipoIncidencia.Prioridad.ALTA));
                tipoRepo.save(new TipoIncidencia("Hidráulica / Plomería", TipoIncidencia.Prioridad.ALTA));
                tipoRepo.save(new TipoIncidencia("Informática / Redes", TipoIncidencia.Prioridad.CRITICA));
                tipoRepo.save(new TipoIncidencia("Carpintería / Muebles", TipoIncidencia.Prioridad.MEDIA));
                tipoRepo.save(new TipoIncidencia("Pintura", TipoIncidencia.Prioridad.BAJA));
                tipoRepo.save(new TipoIncidencia("Aire acondicionado / Ventilación", TipoIncidencia.Prioridad.ALTA));
                tipoRepo.save(new TipoIncidencia("Seguridad / Cerraduras", TipoIncidencia.Prioridad.CRITICA));
                tipoRepo.save(new TipoIncidencia("Limpieza", TipoIncidencia.Prioridad.BAJA));
                tipoRepo.save(new TipoIncidencia("Otro", TipoIncidencia.Prioridad.MEDIA));
            }

            // Ubicaciones (solo si no existen)
            if (ubicacionRepo.count() == 0) {
                ubicacionRepo.save(new Ubicacion("A", 1, "A-101", "Aula"));
                ubicacionRepo.save(new Ubicacion("A", 2, "A-205", "Laboratorio"));
                ubicacionRepo.save(new Ubicacion("B", 1, "B-101", "Aula"));
                ubicacionRepo.save(new Ubicacion("B", 2, "B-210", "Laboratorio"));
                ubicacionRepo.save(new Ubicacion("C", 1, "C-101", "Oficina Administrativa"));
                ubicacionRepo.save(new Ubicacion("D", 1, "D-101", "Biblioteca"));
            }

            System.out.println("Datos iniciales cargados correctamente.");
        };
    }
}
