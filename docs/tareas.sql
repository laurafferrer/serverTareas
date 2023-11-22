SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

-- Crear base de datos: `tareas`
CREATE DATABASE tareas;

USE tareas;

-- Estructura de tabla para la tabla `usuario`
CREATE TABLE `usuario` (
  `id` bigint AUTO_INCREMENT PRIMARY KEY,
  `nombre` varchar(255) NOT NULL,
  `apellidos` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `puesto` tinyint(1) NOT NULL,
  `password` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Estructura de tabla para la tabla `tarea`
CREATE TABLE `tarea` (
  `id` bigint AUTO_INCREMENT PRIMARY KEY,
  `nombre` varchar(255) NOT NULL,
  `id_usuario` BIGINT,
  FOREIGN KEY (id_usuario) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Estructura de tabla para la tabla `proyecto`
CREATE TABLE `proyecto` (
  `id` bigint AUTO_INCREMENT PRIMARY KEY,
  `nombre` varchar(255) NOT NULL,
  `fecha_inicio` datetime NOT NULL,
  `id_tarea` BIGINT,
  FOREIGN KEY (id_tarea) REFERENCES tarea(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Insertar datos en la tabla `usuario`
INSERT INTO usuario (nombre, apellidos, username, puesto, password) 
VALUES ('Laura', 'Ferrer', 'lauraferrer', 0, 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e');

INSERT INTO usuario (nombre, apellidos, username, puesto, password) 
VALUES ('Juan', 'Perez', 'juanperez', 1, 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e');

INSERT INTO usuario (nombre, apellidos, username, puesto, password) 
VALUES ('Maria', 'Lopez', 'marialopez', 0, 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e');

INSERT INTO usuario (nombre, apellidos, username, puesto, password)
VALUES ('Pedro', 'Gomez', 'pedrogomez', 0, 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e');

-- Insertar datos en la tabla 'tarea'
INSERT INTO tarea (nombre, id_usuario)
VALUES ('Tarea 1', 1);

INSERT INTO tarea (nombre, id_usuario)
VALUES ('Tarea 2', 2);

-- Insertar datos en la tabla 'proyecto'
INSERT INTO proyecto (nombre, fecha_inicio, id_tarea)
VALUES ('Proyecto 1', '2023-11-20', 2);

INSERT INTO proyecto (nombre, fecha_inicio, id_tarea)
VALUES ('Proyecto 2', '2023-11-21', 1);


