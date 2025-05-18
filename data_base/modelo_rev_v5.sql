-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema iweb_proy
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema iweb_proy
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `iweb_proy` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `iweb_proy` ;

-- -----------------------------------------------------
-- Table `iweb_proy`.`categoria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `iweb_proy`.`categoria` (
  `idcategoria` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idcategoria`))
ENGINE = InnoDB
AUTO_INCREMENT = 105
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `iweb_proy`.`zona`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `iweb_proy`.`zona` (
  `idzona` INT NOT NULL,
  `nombre` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idzona`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `iweb_proy`.`distritos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `iweb_proy`.`distritos` (
  `iddistritos` INT NOT NULL,
  `nombre` VARCHAR(45) NULL DEFAULT NULL,
  `idzona` INT NOT NULL,
  PRIMARY KEY (`iddistritos`, `idzona`),
  INDEX `fk_distritos_zona1_idx` (`idzona` ASC) VISIBLE,
  CONSTRAINT `fk_distritos_zona1`
    FOREIGN KEY (`idzona`)
    REFERENCES `iweb_proy`.`zona` (`idzona`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `iweb_proy`.`formulario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `iweb_proy`.`formulario` (
  `idformulario` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(80) NULL DEFAULT NULL,
  `fecha_creacion` DATE NOT NULL,
  `fecha_limite` DATE NOT NULL,
  `estado` TINYINT NOT NULL DEFAULT '1',
  `registros_esperados` INT NULL DEFAULT NULL,
  `idcategoria` INT NOT NULL,
  PRIMARY KEY (`idformulario`),
  INDEX `fk_formulario_categoria1_idx` (`idcategoria` ASC) VISIBLE,
  CONSTRAINT `fk_formulario_categoria1`
    FOREIGN KEY (`idcategoria`)
    REFERENCES `iweb_proy`.`categoria` (`idcategoria`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `iweb_proy`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `iweb_proy`.`roles` (
  `idroles` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idroles`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `iweb_proy`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `iweb_proy`.`usuario` (
  `idusuario` INT NOT NULL AUTO_INCREMENT,
  `nombres` VARCHAR(150) NULL DEFAULT NULL,
  `apellidos` VARCHAR(150) NULL DEFAULT NULL,
  `contraseña` VARCHAR(255) NOT NULL,
  `DNI` INT NOT NULL,
  `correo` VARCHAR(150) NOT NULL,
  `direccion` VARCHAR(200) NULL DEFAULT NULL,
  `estado` TINYINT NOT NULL,
  `idroles` INT NOT NULL,
  `idzona` INT NULL DEFAULT NULL,
  `iddistritos` INT NULL DEFAULT NULL,
  `fecha_incorporacion` DATE NOT NULL,
  `foto` BLOB NULL DEFAULT NULL,
  `cod_enc` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`idusuario`),
  UNIQUE INDEX `DNI_UNIQUE` (`DNI` ASC) VISIBLE,
  UNIQUE INDEX `correo_UNIQUE` (`correo` ASC) VISIBLE,
  INDEX `fk_usuario_roles1_idx` (`idroles` ASC) VISIBLE,
  INDEX `fk_usuario_zona1_idx` (`idzona` ASC) VISIBLE,
  INDEX `fk_usuario_distritos1_idx` (`iddistritos` ASC) VISIBLE,
  CONSTRAINT `fk_usuario_distritos1`
    FOREIGN KEY (`iddistritos`)
    REFERENCES `iweb_proy`.`distritos` (`iddistritos`),
  CONSTRAINT `fk_usuario_roles1`
    FOREIGN KEY (`idroles`)
    REFERENCES `iweb_proy`.`roles` (`idroles`),
  CONSTRAINT `fk_usuario_zona1`
    FOREIGN KEY (`idzona`)
    REFERENCES `iweb_proy`.`zona` (`idzona`))
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `iweb_proy`.`enc_has_formulario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `iweb_proy`.`enc_has_formulario` (
  `idenc_has_formulario` INT NOT NULL AUTO_INCREMENT,
  `enc_idusuario` INT NOT NULL,
  `idformulario` INT NOT NULL,
  `codigo` VARCHAR(45) NOT NULL,
  `fecha_asignacion` DATE NOT NULL,
  PRIMARY KEY (`idenc_has_formulario`),
  UNIQUE INDEX `codigo_UNIQUE` (`codigo` ASC) VISIBLE,
  INDEX `fk_usuario_has_formulario_formulario1_idx` (`idformulario` ASC) VISIBLE,
  INDEX `fk_usuario_has_formulario_usuario1_idx` (`enc_idusuario` ASC) VISIBLE,
  CONSTRAINT `fk_usuario_has_formulario_formulario1`
    FOREIGN KEY (`idformulario`)
    REFERENCES `iweb_proy`.`formulario` (`idformulario`),
  CONSTRAINT `fk_usuario_has_formulario_usuario1`
    FOREIGN KEY (`enc_idusuario`)
    REFERENCES `iweb_proy`.`usuario` (`idusuario`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `iweb_proy`.`seccion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `iweb_proy`.`seccion` (
  `idseccion` INT NOT NULL AUTO_INCREMENT,
  `nombre_sec` VARCHAR(40) NOT NULL,
  `idformulario` INT NOT NULL,
  PRIMARY KEY (`idseccion`),
  INDEX `fk_seccion_formulario1_idx` (`idformulario` ASC) VISIBLE,
  CONSTRAINT `fk_seccion_formulario1`
    FOREIGN KEY (`idformulario`)
    REFERENCES `iweb_proy`.`formulario` (`idformulario`))
ENGINE = InnoDB
AUTO_INCREMENT = 16
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `iweb_proy`.`pregunta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `iweb_proy`.`pregunta` (
  `idpregunta` INT NOT NULL AUTO_INCREMENT,
  `enunciado` VARCHAR(400) NULL DEFAULT NULL,
  `tipo_dato` VARCHAR(40) NULL DEFAULT 'char',
  `idseccion` INT NOT NULL,
  PRIMARY KEY (`idpregunta`),
  INDEX `fk_preguntas_seccion1_idx` (`idseccion` ASC) VISIBLE,
  CONSTRAINT `fk_preguntas_seccion1`
    FOREIGN KEY (`idseccion`)
    REFERENCES `iweb_proy`.`seccion` (`idseccion`))
ENGINE = InnoDB
AUTO_INCREMENT = 31
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `iweb_proy`.`opcion_pregunta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `iweb_proy`.`opcion_pregunta` (
  `idopcion_pregunta` INT NOT NULL,
  `opcion` VARCHAR(30) NULL DEFAULT NULL,
  `idpregunta` INT NOT NULL,
  PRIMARY KEY (`idopcion_pregunta`),
  INDEX `fk_opciones_pregunta_pregunta1_idx` (`idpregunta` ASC) VISIBLE,
  CONSTRAINT `fk_opciones_pregunta_pregunta1`
    FOREIGN KEY (`idpregunta`)
    REFERENCES `iweb_proy`.`pregunta` (`idpregunta`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `iweb_proy`.`pregunta_demo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `iweb_proy`.`pregunta_demo` (
  `idpregunta` INT NOT NULL AUTO_INCREMENT,
  `enunciado` VARCHAR(400) NULL DEFAULT NULL,
  `idseccion` INT NOT NULL,
  PRIMARY KEY (`idpregunta`),
  INDEX `fk_preguntas_seccion1_idx` (`idseccion` ASC) VISIBLE,
  CONSTRAINT `fk_preguntas_seccion10`
    FOREIGN KEY (`idseccion`)
    REFERENCES `iweb_proy`.`seccion` (`idseccion`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `iweb_proy`.`registro_respuestas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `iweb_proy`.`registro_respuestas` (
  `idregistro_respuestas` INT NOT NULL AUTO_INCREMENT,
  `fecha_registro` DATE NOT NULL,
  `estado` ENUM('B', 'C') NOT NULL,
  `idenc_has_formulario` INT NOT NULL,
  PRIMARY KEY (`idregistro_respuestas`),
  INDEX `fk_registro_respuestas_enc_has_formulario1_idx` (`idenc_has_formulario` ASC) VISIBLE,
  CONSTRAINT `fk_registro_respuestas_enc_has_formulario1`
    FOREIGN KEY (`idenc_has_formulario`)
    REFERENCES `iweb_proy`.`enc_has_formulario` (`idenc_has_formulario`))
ENGINE = InnoDB
AUTO_INCREMENT = 18
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `iweb_proy`.`respuesta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `iweb_proy`.`respuesta` (
  `idrespuesta` INT NOT NULL AUTO_INCREMENT,
  `respuesta` VARCHAR(400) NULL DEFAULT NULL,
  `idpregunta` INT NOT NULL,
  `idregistro_respuestas` INT NOT NULL,
  PRIMARY KEY (`idrespuesta`),
  INDEX `fk_respuesta_Preguntas1_idx` (`idpregunta` ASC) VISIBLE,
  INDEX `fk_respuesta_registro_respuesta1_idx` (`idregistro_respuestas` ASC) VISIBLE,
  CONSTRAINT `fk_respuesta_Preguntas1`
    FOREIGN KEY (`idpregunta`)
    REFERENCES `iweb_proy`.`pregunta` (`idpregunta`),
  CONSTRAINT `fk_respuesta_registro_respuesta1`
    FOREIGN KEY (`idregistro_respuestas`)
    REFERENCES `iweb_proy`.`registro_respuestas` (`idregistro_respuestas`))
ENGINE = InnoDB
AUTO_INCREMENT = 102
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `iweb_proy`.`respuesta_demo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `iweb_proy`.`respuesta_demo` (
  `idrespuesta` INT NOT NULL AUTO_INCREMENT,
  `respuesta` VARCHAR(400) NULL DEFAULT NULL,
  `idregistro_respuestas` INT NOT NULL,
  `idpregunta_demo` INT NOT NULL,
  PRIMARY KEY (`idrespuesta`),
  INDEX `fk_respuesta_registro_respuesta1_idx` (`idregistro_respuestas` ASC) VISIBLE,
  INDEX `fk_respuesta_demo_pregunta_copy11_idx` (`idpregunta_demo` ASC) VISIBLE,
  CONSTRAINT `fk_respuesta_demo_pregunta_copy11`
    FOREIGN KEY (`idpregunta_demo`)
    REFERENCES `iweb_proy`.`pregunta_demo` (`idpregunta`),
  CONSTRAINT `fk_respuesta_registro_respuesta10`
    FOREIGN KEY (`idregistro_respuestas`)
    REFERENCES `iweb_proy`.`registro_respuestas` (`idregistro_respuestas`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
