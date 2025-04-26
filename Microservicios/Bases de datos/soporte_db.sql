-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 31-03-2025 a las 05:35:26
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `soporte_db`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estado_ticket`
--

CREATE TABLE `estado_ticket` (
  `id_estado` int(11) NOT NULL,
  `nombre_estado` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `estado_ticket`
--

INSERT INTO `estado_ticket` (`id_estado`, `nombre_estado`) VALUES
(1, 'Abierto'),
(3, 'Cerrado'),
(2, 'En Proceso');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mensaje_soporte`
--

CREATE TABLE `mensaje_soporte` (
  `id_mensaje` bigint(20) NOT NULL,
  `id_ticket` bigint(20) NOT NULL,
  `mensaje` text NOT NULL,
  `fecha_envio` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `mensaje_soporte`
--

INSERT INTO `mensaje_soporte` (`id_mensaje`, `id_ticket`, `mensaje`, `fecha_envio`) VALUES
(1, 1, 'No puedo pagar con mi tarjeta, ¿qué puedo hacer?', '2025-03-10 10:05:00'),
(2, 1, 'Verifica que tu tarjeta tenga saldo disponible.', '2025-03-10 10:10:00'),
(3, 2, 'Mi pedido llegó sin la camiseta que ordené.', '2025-03-09 15:50:00'),
(4, 3, 'Intenté recuperar mi cuenta y no recibo el correo.', '2025-03-08 12:05:00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ticket`
--

CREATE TABLE `ticket` (
  `id_ticket` bigint(20) NOT NULL,
  `id_usuario` bigint(20) NOT NULL,
  `asunto` varchar(255) NOT NULL,
  `descripcion` text NOT NULL,
  `fecha_creacion` datetime NOT NULL DEFAULT current_timestamp(),
  `fecha_cierre` datetime DEFAULT NULL,
  `id_estado` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ticket`
--

INSERT INTO `ticket` (`id_ticket`, `id_usuario`, `asunto`, `descripcion`, `fecha_creacion`, `fecha_cierre`, `id_estado`) VALUES
(1, 1, 'Problema con el pago', 'No puedo completar mi compra con tarjeta de crédito.', '2025-03-10 10:00:00', NULL, 1),
(2, 2, 'Error en la entrega', 'Mi pedido llegó incompleto.', '2025-03-09 15:45:00', NULL, 2),
(3, 3, 'Cuenta bloqueada', 'No puedo acceder a mi cuenta.', '2025-03-08 12:00:00', NULL, 3);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `estado_ticket`
--
ALTER TABLE `estado_ticket`
  ADD PRIMARY KEY (`id_estado`),
  ADD UNIQUE KEY `nombre_estado` (`nombre_estado`);

--
-- Indices de la tabla `mensaje_soporte`
--
ALTER TABLE `mensaje_soporte`
  ADD PRIMARY KEY (`id_mensaje`),
  ADD KEY `id_ticket` (`id_ticket`);

--
-- Indices de la tabla `ticket`
--
ALTER TABLE `ticket`
  ADD PRIMARY KEY (`id_ticket`),
  ADD KEY `id_estado` (`id_estado`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `estado_ticket`
--
ALTER TABLE `estado_ticket`
  MODIFY `id_estado` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `mensaje_soporte`
--
ALTER TABLE `mensaje_soporte`
  MODIFY `id_mensaje` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `ticket`
--
ALTER TABLE `ticket`
  MODIFY `id_ticket` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `mensaje_soporte`
--
ALTER TABLE `mensaje_soporte`
  ADD CONSTRAINT `mensaje_soporte_ibfk_1` FOREIGN KEY (`id_ticket`) REFERENCES `ticket` (`id_ticket`) ON DELETE CASCADE;

--
-- Filtros para la tabla `ticket`
--
ALTER TABLE `ticket`
  ADD CONSTRAINT `ticket_ibfk_1` FOREIGN KEY (`id_estado`) REFERENCES `estado_ticket` (`id_estado`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
