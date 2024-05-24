-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema labcito
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema labcito
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `labcito` DEFAULT CHARACTER SET utf8 ;
USE `labcito` ;

-- -----------------------------------------------------
-- Table `labcito`.`rol`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `labcito`.`rol` (
  `idrol` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idrol`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `labcito`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `labcito`.`usuario` (
  `idUsuario` INT NOT NULL AUTO_INCREMENT,
  `correo` VARCHAR(45) NOT NULL,
  `password` VARCHAR(200) NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `apellido` VARCHAR(45) NOT NULL,
  `estado` TINYINT NOT NULL,
  `idRol` INT NOT NULL,
  PRIMARY KEY (`idUsuario`),
  INDEX `fk_usuario_rol_idx` (`idRol` ASC) VISIBLE,
  CONSTRAINT `fk_usuario_rol`
    FOREIGN KEY (`idRol`)
    REFERENCES `labcito`.`rol` (`idrol`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `labcito`.`mesa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `labcito`.`mesa` (
  `idMesa` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `capacidad` INT NOT NULL,
  `ubicacion` VARCHAR(45) NOT NULL,
  `disponibles` INT NOT NULL,
  PRIMARY KEY (`idMesa`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `labcito`.`reserva`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `labcito`.`reserva` (
  `idReserva` INT NOT NULL AUTO_INCREMENT,
  `idUsuario` INT NOT NULL,
  `idMesa` INT NOT NULL,
  `fechaInicio` DATE NOT NULL,
  `fechaFin` DATE NOT NULL,
  PRIMARY KEY (`idReserva`),
  INDEX `fk_reserva_usuario1_idx` (`idUsuario` ASC) VISIBLE,
  INDEX `fk_reserva_mesa1_idx` (`idMesa` ASC) VISIBLE,
  CONSTRAINT `fk_reserva_usuario1`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `labcito`.`usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_reserva_mesa1`
    FOREIGN KEY (`idMesa`)
    REFERENCES `labcito`.`mesa` (`idMesa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
