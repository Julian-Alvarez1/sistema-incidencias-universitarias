-- ============================================
-- Sistema de Gestión de Incidencias Universitarias
-- Base de datos MySQL
-- ============================================

CREATE DATABASE IF NOT EXISTS gestion_incidencias CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE gestion_incidencias;

-- Tabla: rol
CREATE TABLE rol (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- Tabla: usuario
CREATE TABLE usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    documento VARCHAR(20) NOT NULL UNIQUE,
    correo VARCHAR(100) NOT NULL UNIQUE,
    clave_hash VARCHAR(255) NOT NULL,
    estado ENUM('ACTIVO', 'INACTIVO') DEFAULT 'ACTIVO',
    id_rol INT NOT NULL,
    FOREIGN KEY (id_rol) REFERENCES rol(id_rol)
) ENGINE=InnoDB;

-- Tabla: tipo_incidencia
CREATE TABLE tipo_incidencia (
    id_tipo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    prioridad_base ENUM('BAJA', 'MEDIA', 'ALTA', 'CRITICA') DEFAULT 'MEDIA'
) ENGINE=InnoDB;

-- Tabla: ubicacion
CREATE TABLE ubicacion (
    id_ubicacion INT AUTO_INCREMENT PRIMARY KEY,
    bloque VARCHAR(10) NOT NULL,
    piso INT NOT NULL,
    espacio VARCHAR(50) NOT NULL,
    tipo_espacio VARCHAR(50) NOT NULL
) ENGINE=InnoDB;

-- Tabla: incidencia
CREATE TABLE incidencia (
    id_incidencia INT AUTO_INCREMENT PRIMARY KEY,
    descripcion TEXT NOT NULL,
    prioridad ENUM('BAJA', 'MEDIA', 'ALTA', 'CRITICA') NOT NULL,
    estado ENUM('REGISTRADA', 'ASIGNADA', 'EN_PROCESO', 'RESUELTA', 'CERRADA') DEFAULT 'REGISTRADA',
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_tipo INT NOT NULL,
    id_ubicacion INT NOT NULL,
    id_reportante INT NOT NULL,
    FOREIGN KEY (id_tipo) REFERENCES tipo_incidencia(id_tipo),
    FOREIGN KEY (id_ubicacion) REFERENCES ubicacion(id_ubicacion),
    FOREIGN KEY (id_reportante) REFERENCES usuario(id_usuario)
) ENGINE=InnoDB;

-- Tabla: asignacion
CREATE TABLE asignacion (
    id_asignacion INT AUTO_INCREMENT PRIMARY KEY,
    fecha_asignacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    observacion VARCHAR(255),
    activa BOOLEAN DEFAULT TRUE,
    id_incidencia INT NOT NULL,
    id_tecnico INT NOT NULL,
    FOREIGN KEY (id_incidencia) REFERENCES incidencia(id_incidencia),
    FOREIGN KEY (id_tecnico) REFERENCES usuario(id_usuario)
) ENGINE=InnoDB;

-- Tabla: historial_estado
CREATE TABLE historial_estado (
    id_historial INT AUTO_INCREMENT PRIMARY KEY,
    estado_anterior ENUM('REGISTRADA', 'ASIGNADA', 'EN_PROCESO', 'RESUELTA', 'CERRADA'),
    estado_nuevo ENUM('REGISTRADA', 'ASIGNADA', 'EN_PROCESO', 'RESUELTA', 'CERRADA') NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_incidencia INT NOT NULL,
    id_usuario INT NOT NULL,
    FOREIGN KEY (id_incidencia) REFERENCES incidencia(id_incidencia),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
) ENGINE=InnoDB;

-- Tabla: reporte
CREATE TABLE reporte (
    id_reporte INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(50) NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    id_administrador INT NOT NULL,
    FOREIGN KEY (id_administrador) REFERENCES usuario(id_usuario)
) ENGINE=InnoDB;

-- ============================================
-- Datos iniciales (semilla)
-- ============================================

INSERT INTO rol (nombre) VALUES ('ESTUDIANTE'), ('DOCENTE'), ('TECNICO'), ('ADMINISTRADOR');

INSERT INTO tipo_incidencia (nombre, prioridad_base) VALUES
('Eléctrica', 'ALTA'),
('Hidráulica / Plomería', 'ALTA'),
('Informática / Redes', 'CRITICA'),
('Carpintería / Muebles', 'MEDIA'),
('Pintura', 'BAJA'),
('Aire acondicionado / Ventilación', 'ALTA'),
('Seguridad / Cerraduras', 'CRITICA'),
('Limpieza', 'BAJA'),
('Otro', 'MEDIA');

INSERT INTO usuario (nombre, documento, correo, clave_hash, estado, id_rol) VALUES
('Administrador Sistema', '1000000000', 'admin@universidad.edu', '$2a$10$dummy.hash.for.admin', 'ACTIVO', 4),
('Técnico Mantenimiento', '1000000001', 'tecnico@universidad.edu', '$2a$10$dummy.hash.for.tecnico', 'ACTIVO', 3),
('Estudiante Demo', '1000000002', 'estudiante@universidad.edu', '$2a$10$dummy.hash.for.estudiante', 'ACTIVO', 1),
('Docente Demo', '1000000003', 'docente@universidad.edu', '$2a$10$dummy.hash.for.docente', 'ACTIVO', 2);

INSERT INTO ubicacion (bloque, piso, espacio, tipo_espacio) VALUES
('A', 1, 'A-101', 'Aula'),
('A', 2, 'A-205', 'Laboratorio'),
('B', 1, 'B-101', 'Aula'),
('B', 2, 'B-210', 'Laboratorio'),
('C', 1, 'C-101', 'Oficina Administrativa'),
('D', 1, 'D-101', 'Biblioteca');
