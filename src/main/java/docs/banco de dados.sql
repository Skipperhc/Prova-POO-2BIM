-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema prova2bim
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `prova2bim` ;

-- -----------------------------------------------------
-- Schema prova2bim
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `prova2bim` DEFAULT CHARACTER SET utf8 ;
USE `prova2bim` ;

-- -----------------------------------------------------
-- Table `prova2bim`.`produtobase`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `prova2bim`.`produtobase` ;

CREATE TABLE IF NOT EXISTS `prova2bim`.`produtobase` (
  `cod` INT NOT NULL AUTO_INCREMENT,
  `peso` DOUBLE NOT NULL,
  `nome` VARCHAR(50) NOT NULL,
  `descricao` VARCHAR(150) NULL,
  `preco` DECIMAL(7,2) NOT NULL,
  `ativo` CHAR(1) NOT NULL DEFAULT 'S',
  PRIMARY KEY (`cod`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `prova2bim`.`processador`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `prova2bim`.`processador` ;

CREATE TABLE IF NOT EXISTS `prova2bim`.`processador` (
  `produtobase_cod` INT NOT NULL,
  `nucleos` INT NOT NULL,
  `threads` INT NOT NULL,
  PRIMARY KEY (`produtobase_cod`),
  CONSTRAINT `fk_processadores_produtobase`
    FOREIGN KEY (`produtobase_cod`)
    REFERENCES `prova2bim`.`produtobase` (`cod`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `prova2bim`.`memoria`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `prova2bim`.`memoria` ;

CREATE TABLE IF NOT EXISTS `prova2bim`.`memoria` (
  `produtobase_cod` INT NOT NULL,
  `ram` INT NOT NULL,
  `frequencia` INT NOT NULL,
  PRIMARY KEY (`produtobase_cod`),
  CONSTRAINT `fk_memorias_produtobase1`
    FOREIGN KEY (`produtobase_cod`)
    REFERENCES `prova2bim`.`produtobase` (`cod`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `prova2bim`.`placadevideo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `prova2bim`.`placadevideo` ;

CREATE TABLE IF NOT EXISTS `prova2bim`.`placadevideo` (
  `produtobase_cod` INT NOT NULL,
  `vram` INT NULL,
  `ddr` VARCHAR(10) NULL,
  PRIMARY KEY (`produtobase_cod`),
  CONSTRAINT `fk_placadevideo_produtobase1`
    FOREIGN KEY (`produtobase_cod`)
    REFERENCES `prova2bim`.`produtobase` (`cod`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `prova2bim`.`pessoa`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `prova2bim`.`pessoa` ;

CREATE TABLE IF NOT EXISTS `prova2bim`.`pessoa` (
  `cod` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `documento` VARCHAR(14) NOT NULL,
  `telefone` VARCHAR(11) NULL,
  `senha` VARCHAR(100) NOT NULL,
  `ativo` VARCHAR(1) NOT NULL DEFAULT 'S',
  PRIMARY KEY (`cod`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `prova2bim`.`funcionario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `prova2bim`.`funcionario` ;

CREATE TABLE IF NOT EXISTS `prova2bim`.`funcionario` (
  `pessoa_cod` INT NOT NULL,
  `cargo` VARCHAR(45) NOT NULL,
  `acesso` INT NOT NULL,
  PRIMARY KEY (`pessoa_cod`),
  CONSTRAINT `fk_funcionario_pessoa1`
    FOREIGN KEY (`pessoa_cod`)
    REFERENCES `prova2bim`.`pessoa` (`cod`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `prova2bim`.`cliente`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `prova2bim`.`cliente` ;

CREATE TABLE IF NOT EXISTS `prova2bim`.`cliente` (
  `pessoa_cod` INT NOT NULL,
  `acesso` INT NULL,
  PRIMARY KEY (`pessoa_cod`),
  CONSTRAINT `fk_cliente_pessoa1`
    FOREIGN KEY (`pessoa_cod`)
    REFERENCES `prova2bim`.`pessoa` (`cod`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
