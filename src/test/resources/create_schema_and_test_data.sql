DROP SCHEMA IF EXISTS pqm_example;

CREATE SCHEMA IF NOT EXISTS pqm_example DEFAULT CHARACTER SET utf8;

USE pqm_example;


CREATE TABLE IF NOT EXISTS pqm_example.PROBLEM (
  `problem_number` BIGINT(255) NOT NULL,
  `title` VARCHAR(300) NOT NULL,
  `status` INT NULL COMMENT 'Can only contain values in the range [0;100].',
  PRIMARY KEY (`problem_number`))
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `pqm_example`.`MEASURE` (
  `measure_number` BIGINT(255) NOT NULL,
  `title` VARCHAR(300) NOT NULL,
  `description` VARCHAR(1500) NULL,
  `status` INT NOT NULL COMMENT 'Can only contain values in the range [0;100].',
  `problem_number` BIGINT(255) NOT NULL,
  PRIMARY KEY (`measure_number`),
  INDEX `fk_MEASURE_PROBLEM_idx` (`problem_number` ASC) VISIBLE,
  CONSTRAINT `fk_MEASURE_PROBLEM`
    FOREIGN KEY (`problem_number`)
    REFERENCES `pqm_example`.`PROBLEM` (`problem_number`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = '	';


CREATE TABLE IF NOT EXISTS `pqm_example`.`NOTIFICATION` (
  `notification_number` BIGINT(255) NOT NULL,
  `title` VARCHAR(300) NOT NULL,
  `description` VARCHAR(1500) NULL,
  `status` INT NULL COMMENT 'Can only contain values in the range [0;100].',
  PRIMARY KEY (`notification_number`))
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `pqm_example`.`PROBLEM_TO_NOTIFICATION` (
  `problem_number` BIGINT(255) NOT NULL,
  `notification_number` BIGINT(255) NOT NULL,
  PRIMARY KEY (`problem_number`, `notification_number`),
  INDEX `fk_table1_REPORT1_idx` (`notification_number` ASC) VISIBLE,
  UNIQUE INDEX `report_number_UNIQUE` (`notification_number` ASC) VISIBLE,
  CONSTRAINT `fk_table1_PROBLEM1`
    FOREIGN KEY (`problem_number`)
    REFERENCES `pqm_example`.`PROBLEM` (`problem_number`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_table1_REPORT1`
    FOREIGN KEY (`notification_number`)
    REFERENCES `pqm_example`.`NOTIFICATION` (`notification_number`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


START TRANSACTION;
USE `pqm_example`;
INSERT INTO `pqm_example`.`PROBLEM` (`problem_number`, `title`, `status`) VALUES (123, 'Erstes Problem', 0);
INSERT INTO `pqm_example`.`PROBLEM` (`problem_number`, `title`, `status`) VALUES (789, 'Zweites Problem', 88);
INSERT INTO `pqm_example`.`PROBLEM` (`problem_number`, `title`, `status`) VALUES (567, 'Drittes Problem', 10);
COMMIT;


START TRANSACTION;
USE `pqm_example`;
INSERT INTO `pqm_example`.`NOTIFICATION` (`notification_number`, `title`, `description`, `status`) VALUES (345, 'Erste Meldung', 'Beschreibung der ersten Meldung.', 12);
COMMIT;


START TRANSACTION;
USE `pqm_example`;
INSERT INTO `pqm_example`.`PROBLEM_TO_NOTIFICATION` (`problem_number`, `notification_number`) VALUES (123, 345);
COMMIT;
