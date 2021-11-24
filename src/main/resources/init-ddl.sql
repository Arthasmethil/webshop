-- DDL

CREATE DATABASE  IF NOT EXISTS `webshopdb`;
USE `webshopdb`;

CREATE TABLE `clients`
(
    `id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(45) NOT NULL,
    `lastname` varchar(45) NOT NULL,
    `phone` varchar(11) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `order`
(
    `idClient` int NOT NULL,
    `idProduct` int DEFAULT NULL,
    `id` int NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`id`),
    KEY `id_idx1` (`idProduct`),
    KEY `idClient_idx` (`idClient`),
    CONSTRAINT `idClient` FOREIGN KEY (`idClient`) REFERENCES `clients` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `idProduct` FOREIGN KEY (`idProduct`) REFERENCES `products` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `products`
(
    `id` int NOT NULL AUTO_INCREMENT,
    `device` varchar(64) DEFAULT NULL,
    `category` varchar(64) DEFAULT NULL,
    `price` double NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb3;

COMMIT;

