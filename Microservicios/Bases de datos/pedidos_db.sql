-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 19-05-2025 a las 22:43:49
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
-- Base de datos: `pedidos_db`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_pedido`
--

CREATE TABLE `detalle_pedido` (
  `id_detalle` bigint(20) NOT NULL,
  `id_pedido` bigint(20) NOT NULL,
  `id_producto` bigint(20) NOT NULL,
  `id_talla` bigint(20) DEFAULT NULL,
  `id_color` bigint(20) DEFAULT NULL,
  `cantidad` int(11) NOT NULL,
  `precio_unitario` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `detalle_pedido`
--

INSERT INTO `detalle_pedido` (`id_detalle`, `id_pedido`, `id_producto`, `id_talla`, `id_color`, `cantidad`, `precio_unitario`) VALUES
(3, 2, 2, 1, 3, 3, 60000.00),
(4, 3, 2, 2, 2, 1, 75000.00),
(5, 4, 1, 2, 1, 1, 120000.00),
(35, 34, 1, 2, 1, 1, 120000.00),
(36, 35, 2, 3, 2, 1, 50000.00),
(37, 36, 2, 3, 2, 1, 50000.00),
(38, 37, 1, 2, 1, 1, 120000.00),
(43, 41, 1, 1, 1, 1, 120000.00),
(44, 41, 2, 2, 2, 1, 50000.00),
(47, 43, 1, 2, 1, 1, 120000.00),
(48, 44, 1, 2, 1, 1, 120000.00),
(49, 45, 1, 2, 1, 1, 120000.00),
(50, 46, 1, 1, 1, 1, 120000.00),
(51, 46, 2, 2, 2, 1, 50000.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estado_pago`
--

CREATE TABLE `estado_pago` (
  `id_estado_pago` tinyint(4) NOT NULL,
  `estado` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `estado_pago`
--

INSERT INTO `estado_pago` (`id_estado_pago`, `estado`) VALUES
(2, 'Completado'),
(1, 'Pendiente'),
(3, 'Rechazado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estado_pedido`
--

CREATE TABLE `estado_pedido` (
  `id_estado` tinyint(4) NOT NULL,
  `estado` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `estado_pedido`
--

INSERT INTO `estado_pedido` (`id_estado`, `estado`) VALUES
(4, 'Cancelado'),
(3, 'Entregado'),
(2, 'Enviado'),
(1, 'Pendiente');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `metodo_pago`
--

CREATE TABLE `metodo_pago` (
  `id_metodo` tinyint(4) NOT NULL,
  `metodo` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `metodo_pago`
--

INSERT INTO `metodo_pago` (`id_metodo`, `metodo`) VALUES
(3, 'Efectivo'),
(4, 'PayPal'),
(2, 'PSE'),
(1, 'Tarjeta');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pago`
--

CREATE TABLE `pago` (
  `id_pago` bigint(20) NOT NULL,
  `id_pedido` bigint(20) NOT NULL,
  `fecha_pago` datetime DEFAULT NULL,
  `monto` decimal(10,2) NOT NULL,
  `id_metodo` tinyint(4) NOT NULL DEFAULT 1,
  `id_estado_pago` tinyint(4) NOT NULL DEFAULT 1,
  `id_pago_externo` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pago`
--

INSERT INTO `pago` (`id_pago`, `id_pedido`, `fecha_pago`, `monto`, `id_metodo`, `id_estado_pago`, `id_pago_externo`) VALUES
(1, 1, '2025-03-10 11:00:00', 250000.00, 1, 2, 'PAYID-NAUQZ5A2RL79554BX312111V'),
(2, 2, NULL, 180000.00, 1, 2, 'PAYID-NAUQ2GY9G9982359Y561553P'),
(3, 3, '2025-03-08 12:30:00', 75000.00, 1, 2, ''),
(15, 34, '2025-05-17 16:31:01', 120000.00, 4, 1, 'PAYID-NAUQAII22M248754D871915C'),
(16, 35, '2025-05-17 16:59:35', 50000.00, 4, 1, 'PAYID-NAUQNUY2WB73597GG081180V'),
(17, 36, '2025-05-17 17:00:18', 50000.00, 4, 1, 'PAYID-NAUQN7Q7DP609481V281535P'),
(18, 37, '2025-05-17 17:58:40', 120000.00, 4, 1, 'PAYID-NAURJLA7GA75568MP797973L'),
(22, 41, '2025-05-17 21:03:23', 170000.00, 4, 2, 'PAYID-NAUT75Y4PX46797D5386820T'),
(24, 43, '2025-05-17 21:12:27', 120000.00, 4, 2, 'PAYID-NAUUEFY8KM13483HR740590T'),
(25, 44, '2025-05-17 21:28:15', 120000.00, 4, 1, 'PAYID-NAUULSQ4UC948689A7377519'),
(26, 45, '2025-05-17 21:31:07', 120000.00, 4, 2, 'PAYID-NAUUM5Q4M799661518017043'),
(27, 46, '2025-05-17 21:31:43', 170000.00, 4, 2, 'PAYID-NAUUNGY1U8068398W6339002');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedido`
--

CREATE TABLE `pedido` (
  `id_pedido` bigint(20) NOT NULL,
  `id_usuario` bigint(20) NOT NULL,
  `fecha` datetime NOT NULL DEFAULT current_timestamp(),
  `total` decimal(10,2) NOT NULL,
  `id_estado` tinyint(4) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pedido`
--

INSERT INTO `pedido` (`id_pedido`, `id_usuario`, `fecha`, `total`, `id_estado`) VALUES
(1, 1, '2025-03-10 10:30:00', 250000.00, 1),
(2, 2, '2025-03-09 15:45:00', 180000.00, 1),
(3, 3, '2025-03-08 12:00:00', 75000.00, 1),
(4, 5, '2025-05-17 11:54:36', 120000.00, 1),
(34, 5, '2025-05-17 16:30:59', 120000.00, 1),
(35, 5, '2025-05-17 16:59:34', 50000.00, 1),
(36, 5, '2025-05-17 17:00:16', 50000.00, 1),
(37, 5, '2025-05-17 17:58:38', 120000.00, 1),
(41, 5, '2025-05-17 21:03:21', 170000.00, 1),
(43, 5, '2025-05-17 21:12:26', 120000.00, 1),
(44, 5, '2025-05-17 21:28:13', 120000.00, 1),
(45, 5, '2025-05-17 21:31:05', 120000.00, 1),
(46, 5, '2025-05-17 21:31:41', 170000.00, 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `detalle_pedido`
--
ALTER TABLE `detalle_pedido`
  ADD PRIMARY KEY (`id_detalle`),
  ADD KEY `id_pedido` (`id_pedido`),
  ADD KEY `detalle_pedido_ibfk_3` (`id_talla`),
  ADD KEY `detalle_pedido_ibfk_4` (`id_color`),
  ADD KEY `detalle_pedido_ibfk_2` (`id_producto`);

--
-- Indices de la tabla `estado_pago`
--
ALTER TABLE `estado_pago`
  ADD PRIMARY KEY (`id_estado_pago`),
  ADD UNIQUE KEY `estado` (`estado`);

--
-- Indices de la tabla `estado_pedido`
--
ALTER TABLE `estado_pedido`
  ADD PRIMARY KEY (`id_estado`),
  ADD UNIQUE KEY `estado` (`estado`);

--
-- Indices de la tabla `metodo_pago`
--
ALTER TABLE `metodo_pago`
  ADD PRIMARY KEY (`id_metodo`),
  ADD UNIQUE KEY `metodo` (`metodo`);

--
-- Indices de la tabla `pago`
--
ALTER TABLE `pago`
  ADD PRIMARY KEY (`id_pago`),
  ADD UNIQUE KEY `unique_pago_pedido` (`id_pedido`),
  ADD KEY `fk_metodo_pago` (`id_metodo`),
  ADD KEY `fk_estado_pago` (`id_estado_pago`);

--
-- Indices de la tabla `pedido`
--
ALTER TABLE `pedido`
  ADD PRIMARY KEY (`id_pedido`),
  ADD KEY `fk_estado_pedido` (`id_estado`),
  ADD KEY `fk_id_usuario` (`id_usuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `detalle_pedido`
--
ALTER TABLE `detalle_pedido`
  MODIFY `id_detalle` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- AUTO_INCREMENT de la tabla `estado_pago`
--
ALTER TABLE `estado_pago`
  MODIFY `id_estado_pago` tinyint(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `estado_pedido`
--
ALTER TABLE `estado_pedido`
  MODIFY `id_estado` tinyint(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `metodo_pago`
--
ALTER TABLE `metodo_pago`
  MODIFY `id_metodo` tinyint(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `pago`
--
ALTER TABLE `pago`
  MODIFY `id_pago` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT de la tabla `pedido`
--
ALTER TABLE `pedido`
  MODIFY `id_pedido` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalle_pedido`
--
ALTER TABLE `detalle_pedido`
  ADD CONSTRAINT `detalle_pedido_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id_pedido`);

--
-- Filtros para la tabla `pago`
--
ALTER TABLE `pago`
  ADD CONSTRAINT `fk_estado_pago` FOREIGN KEY (`id_estado_pago`) REFERENCES `estado_pago` (`id_estado_pago`),
  ADD CONSTRAINT `fk_metodo_pago` FOREIGN KEY (`id_metodo`) REFERENCES `metodo_pago` (`id_metodo`),
  ADD CONSTRAINT `pago_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id_pedido`);

--
-- Filtros para la tabla `pedido`
--
ALTER TABLE `pedido`
  ADD CONSTRAINT `fk_estado_pedido` FOREIGN KEY (`id_estado`) REFERENCES `estado_pedido` (`id_estado`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
