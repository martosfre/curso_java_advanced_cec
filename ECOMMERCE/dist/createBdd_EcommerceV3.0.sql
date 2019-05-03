-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema ecommerce
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `ecommerce` ;

-- -----------------------------------------------------
-- Schema ecommerce
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ecommerce` DEFAULT CHARACTER SET utf8 ;
USE `ecommerce` ;

-- -----------------------------------------------------
-- Table `ecommerce`.`tipo_producto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`tipo_producto` (
  `id_tip_pro` INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador de tipo de producto',
  `nombre_tip_pro` VARCHAR(25) NOT NULL COMMENT 'Nombre del Tipo Producto',
  `descripcion_tip_pro` VARCHAR(200) NULL,
  PRIMARY KEY (`id_tip_pro`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ecommerce`.`producto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`producto` (
  `id_pro` INT NOT NULL AUTO_INCREMENT,
  `nombre_pro` VARCHAR(25) NOT NULL,
  `descripcion_pro` VARCHAR(200) NULL,
  `precio_pro` DECIMAL(10,2) NOT NULL,
  `fecha_cad_pro` DATE NULL,
  `id_tip_pro` INT NOT NULL,
  PRIMARY KEY (`id_pro`),
  INDEX `fk_tipo_producto_idx` (`id_tip_pro` ASC) VISIBLE,
  CONSTRAINT `fk_tipo_producto`
    FOREIGN KEY (`id_tip_pro`)
    REFERENCES `ecommerce`.`tipo_producto` (`id_tip_pro`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ecommerce`.`cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`cliente` (
  `id_cli` INT NOT NULL AUTO_INCREMENT,
  `nombre_per` VARCHAR(25) NOT NULL,
  `apellido_per` VARCHAR(25) NOT NULL,
  `identificacion_per` VARCHAR(13) NOT NULL,
  `fecha_nac_per` DATE NULL,
  `direccion_per` VARCHAR(45) NOT NULL,
  `telefono_per` VARCHAR(10) NOT NULL,
  `correo_per` VARCHAR(45) NOT NULL,
  `cuota_ven_cli` DECIMAL(10,2) NULL,
  PRIMARY KEY (`id_cli`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ecommerce`.`factura`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`factura` (
  `id_fac` INT NOT NULL AUTO_INCREMENT,
  `fac_numero` VARCHAR(16) NOT NULL,
  `fecha_fac` DATE NOT NULL,
  `subtotal_fac` DECIMAL(10,2) NOT NULL,
  `iva_fac` DECIMAL(10,2) NOT NULL,
  `total_fac` DECIMAL(10,2) NOT NULL,
  `id_cli` INT NOT NULL,
  PRIMARY KEY (`id_fac`),
  INDEX `fk_factura_cliente1_idx` (`id_cli` ASC) VISIBLE,
  CONSTRAINT `fk_factura_cliente1`
    FOREIGN KEY (`id_cli`)
    REFERENCES `ecommerce`.`cliente` (`id_cli`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ecommerce`.`proveedor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`proveedor` (
  `id_prov` INT NOT NULL AUTO_INCREMENT,
  `nombre_per` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_prov`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ecommerce`.`producto_has_proveedor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`producto_has_proveedor` (
  `id_pro` INT NOT NULL,
  `id_prov` INT NOT NULL,
  PRIMARY KEY (`id_pro`, `id_prov`),
  INDEX `fk_producto_has_proveedor_proveedor1_idx` (`id_prov` ASC) VISIBLE,
  INDEX `fk_producto_has_proveedor_producto1_idx` (`id_pro` ASC) VISIBLE,
  CONSTRAINT `fk_producto_has_proveedor_producto1`
    FOREIGN KEY (`id_pro`)
    REFERENCES `ecommerce`.`producto` (`id_pro`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_producto_has_proveedor_proveedor1`
    FOREIGN KEY (`id_prov`)
    REFERENCES `ecommerce`.`proveedor` (`id_prov`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ecommerce`.`detalle_factura`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`detalle_factura` (
  `id_detfac` INT NOT NULL AUTO_INCREMENT,
  `can_detfac` INT NOT NULL,
  `obs_detfac` VARCHAR(45) NOT NULL,
  `subtotal_detfac` DECIMAL(10,2) NOT NULL,
  `iva_detfac` DECIMAL(10,2) NOT NULL,
  `total_detfac` DECIMAL(10,2) NOT NULL,
  `id_fac` INT NOT NULL,
  `id_pro` INT NOT NULL,
  `id_prov` INT NOT NULL,
  PRIMARY KEY (`id_detfac`),
  INDEX `fk_detalle_factura_factura1_idx` (`id_fac` ASC) VISIBLE,
  INDEX `fk_detalle_factura_producto_has_proveedor1_idx` (`id_pro` ASC, `id_prov` ASC) VISIBLE,
  CONSTRAINT `fk_detalle_factura_factura1`
    FOREIGN KEY (`id_fac`)
    REFERENCES `ecommerce`.`factura` (`id_fac`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_detalle_factura_producto_has_proveedor1`
    FOREIGN KEY (`id_pro` , `id_prov`)
    REFERENCES `ecommerce`.`producto_has_proveedor` (`id_pro` , `id_prov`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ecommerce`.`rol`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`rol` (
  `id_rol` INT NOT NULL AUTO_INCREMENT,
  `nombre_rol` VARCHAR(45) NOT NULL,
  `descripcion_rol` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id_rol`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ecommerce`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`usuario` (
  `id_usu` INT NOT NULL AUTO_INCREMENT,
  `nombre_usu` VARCHAR(10) NOT NULL,
  `clave_usu` VARCHAR(10) NOT NULL,
  `id_rol` INT NOT NULL,
  PRIMARY KEY (`id_usu`),
  INDEX `fk_usuario_rol1_idx` (`id_rol` ASC) VISIBLE,
  CONSTRAINT `fk_usuario_rol1`
    FOREIGN KEY (`id_rol`)
    REFERENCES `ecommerce`.`rol` (`id_rol`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
