-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 19-05-2025 a las 22:42:43
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
-- Base de datos: `catalogo_db`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `id_categoria` bigint(20) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `descripcion` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`id_categoria`, `nombre`, `descripcion`) VALUES
(1, 'Buzos', 'Buzos urbanos con estilo'),
(2, 'Camisetas', 'Camisetas de algodón de alta calidad'),
(3, 'Gorras', 'Complementos de moda para todos los estilos nuevo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `color`
--

CREATE TABLE `color` (
  `id_color` bigint(20) NOT NULL,
  `nombre` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `color`
--

INSERT INTO `color` (`id_color`, `nombre`) VALUES
(2, 'Blanco'),
(1, 'Negro'),
(3, 'Rojo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `imagen_producto`
--

CREATE TABLE `imagen_producto` (
  `id_imagen` bigint(20) NOT NULL,
  `url_imagen` text NOT NULL,
  `id_producto` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `imagen_producto`
--

INSERT INTO `imagen_producto` (`id_imagen`, `url_imagen`, `id_producto`) VALUES
(3, '/catalog/products/images/1d4f969c-c4f4-463d-a441-0ec3372e2059_asset 4.jpeg', 3),
(4, '/catalog/products/images/0780cfe8-eb73-4dc9-90d1-0262030e797f_asset 2.jpeg', 3),
(5, '/catalog/products/images/59399b0d-62e9-43f5-a939-b4dbb7722a60_asset 3.jpeg', 3),
(7, '/catalog/products/images/06e1f003-c7e9-42e1-8c92-5aeb3b5e892e_Masiosare Studio y sus productos de diseño industrial.jpg', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inventario`
--

CREATE TABLE `inventario` (
  `id_inventario` bigint(20) NOT NULL,
  `id_producto` bigint(20) NOT NULL,
  `id_talla` bigint(20) NOT NULL,
  `id_color` bigint(20) NOT NULL,
  `stock` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `inventario`
--

INSERT INTO `inventario` (`id_inventario`, `id_producto`, `id_talla`, `id_color`, `stock`) VALUES
(1, 1, 2, 1, 20),
(2, 2, 3, 2, 30);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `id_producto` bigint(20) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `descripcion` text DEFAULT NULL,
  `id_categoria` bigint(20) NOT NULL,
  `id_proveedor` bigint(20) NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `descuento_activo` tinyint(1) NOT NULL,
  `porcentaje_descuento` decimal(5,2) DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`id_producto`, `nombre`, `descripcion`, `id_categoria`, `id_proveedor`, `precio`, `descuento_activo`, `porcentaje_descuento`) VALUES
(1, 'Buzo Negro', 'Buzo urbano de algodón', 1, 1, 120000.00, 1, 10.00),
(2, 'Camiseta Blanca', 'Camiseta de algodón premium', 2, 2, 50000.00, 0, 0.00),
(3, 'Gorra', 'Gorra cómoda para actividades al aire libre', 3, 1, 25000.00, 0, 0.00),
(4, 'Gorra Nueva', 'Gorra cómoda para actividades al aire libre', 3, 1, 25000.00, 0, 0.00),
(5, 'Gorra Nueva Nueva', 'Gorra cómoda para actividades al aire libre', 3, 1, 25000.00, 0, 0.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedor`
--

CREATE TABLE `proveedor` (
  `id_proveedor` bigint(20) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `telefono` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `proveedor`
--

INSERT INTO `proveedor` (`id_proveedor`, `nombre`, `telefono`) VALUES
(1, 'Proveedor A', '3124567890'),
(2, 'Proveedor B', '3147890123');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `talla`
--

CREATE TABLE `talla` (
  `id_talla` bigint(20) NOT NULL,
  `nombre` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `talla`
--

INSERT INTO `talla` (`id_talla`, `nombre`) VALUES
(3, 'L'),
(2, 'M'),
(1, 'S'),
(5, 'Única'),
(4, 'XL');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`id_categoria`),
  ADD UNIQUE KEY `nombre` (`nombre`);

--
-- Indices de la tabla `color`
--
ALTER TABLE `color`
  ADD PRIMARY KEY (`id_color`),
  ADD UNIQUE KEY `nombre` (`nombre`);

--
-- Indices de la tabla `imagen_producto`
--
ALTER TABLE `imagen_producto`
  ADD PRIMARY KEY (`id_imagen`),
  ADD KEY `fk_imagen_producto` (`id_producto`);

--
-- Indices de la tabla `inventario`
--
ALTER TABLE `inventario`
  ADD PRIMARY KEY (`id_inventario`),
  ADD KEY `id_producto` (`id_producto`),
  ADD KEY `id_talla` (`id_talla`),
  ADD KEY `id_color` (`id_color`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`id_producto`),
  ADD KEY `id_categoria` (`id_categoria`),
  ADD KEY `id_proveedor` (`id_proveedor`);

--
-- Indices de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  ADD PRIMARY KEY (`id_proveedor`);

--
-- Indices de la tabla `talla`
--
ALTER TABLE `talla`
  ADD PRIMARY KEY (`id_talla`),
  ADD UNIQUE KEY `nombre` (`nombre`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `id_categoria` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `color`
--
ALTER TABLE `color`
  MODIFY `id_color` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `imagen_producto`
--
ALTER TABLE `imagen_producto`
  MODIFY `id_imagen` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `inventario`
--
ALTER TABLE `inventario`
  MODIFY `id_inventario` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `id_producto` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  MODIFY `id_proveedor` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `talla`
--
ALTER TABLE `talla`
  MODIFY `id_talla` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `imagen_producto`
--
ALTER TABLE `imagen_producto`
  ADD CONSTRAINT `fk_imagen_producto` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id_producto`) ON DELETE CASCADE;

--
-- Filtros para la tabla `inventario`
--
ALTER TABLE `inventario`
  ADD CONSTRAINT `inventario_ibfk_1` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id_producto`),
  ADD CONSTRAINT `inventario_ibfk_2` FOREIGN KEY (`id_talla`) REFERENCES `talla` (`id_talla`),
  ADD CONSTRAINT `inventario_ibfk_3` FOREIGN KEY (`id_color`) REFERENCES `color` (`id_color`);

--
-- Filtros para la tabla `producto`
--
ALTER TABLE `producto`
  ADD CONSTRAINT `producto_ibfk_1` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id_categoria`),
  ADD CONSTRAINT `producto_ibfk_2` FOREIGN KEY (`id_proveedor`) REFERENCES `proveedor` (`id_proveedor`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
