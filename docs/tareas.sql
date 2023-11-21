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

-- Estructura de tabla para la tabla `proyecto`
CREATE TABLE `proyecto` (
  `id` bigint AUTO_INCREMENT PRIMARY KEY,
  `nombre` varchar(255) NOT NULL,
  `fecha_inicio` datetime NOT NULL,
  `fecha_fin` datetime DEFAULT NULL,
  `usuario_id` BIGINT,
  FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Estructura de tabla para la tabla `tarea`
CREATE TABLE `tarea` (
  `id` bigint AUTO_INCREMENT PRIMARY KEY,
  `nombre` varchar(255) NOT NULL,
  `proyecto_id` BIGINT NOT NULL,
  `usuario_id` BIGINT NOT NULL,
  FOREIGN KEY (proyecto_id) REFERENCES proyecto(id) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Insertar datos en la tabla `usuario`
INSERT INTO usuario (nombre, apellidos, username, puesto, password) 
VALUES ('Juan', 'Perez', 'juanperez', 1, '123456');

INSERT INTO usuario (nombre, apellidos, username, puesto, password) 
VALUES ('Maria', 'Lopez', 'marialopez', 0, '123456');

INSERT INTO usuario (nombre, apellidos, username, puesto, password)
VALUES ('Pedro', 'Gomez', 'pedrogomez', 0, '123456');

-- Insertar datos en la tabla 'proyecto'
INSERT INTO proyecto (nombre, fecha_inicio, fecha_fin, usuario_id)
VALUES ('Proyecto 1', '2023-11-20', '2023-11-24', 1);

INSERT INTO proyecto (nombre, fecha_inicio, fecha_fin, usuario_id)
VALUES ('Proyecto 2', '2023-11-21', '2023-11-30', 2);

-- Insertar datos en la tabla 'tarea'
INSERT INTO tarea (nombre, proyecto_id, usuario_id)
VALUES ('Tarea 1', 1, 1);

INSERT INTO tarea (nombre, proyecto_id, usuario_id)
VALUES ('Tarea 2', 1, 2);
