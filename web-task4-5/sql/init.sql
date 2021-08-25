DROP DATABASE IF EXISTS `phone_station`;

CREATE DATABASE `phone_station` DEFAULT CHARACTER SET utf8;

USE `phone_station`;

CREATE TABLE IF NOT EXISTS `account`
(
    `id`       INT         NOT NULL AUTO_INCREMENT,
    `login`    VARCHAR(50) NOT NULL UNIQUE,
    `password` VARCHAR(64) NOT NULL,
    `role`     TINYINT(1)  NOT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `user`
(
    `id`            INT         NOT NULL AUTO_INCREMENT,
    `account_id`    INT(10)     NOT NULL,
    `firstname`     VARCHAR(50) NOT NULL,
    `lastname`      VARCHAR(50) NOT NULL,
    `phone_number`  VARCHAR(20) NOT NULL UNIQUE,
    `tariff`        TINYINT(1)  NOT NULL,
    `balance`       INT(10)     NOT NULL,
    `is_blocked`    TINYINT(1)  NOT NULL,
    `is_registered` TINYINT(1)  NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`account_id`)
        REFERENCES `account` (`id`)
        ON DELETE CASCADE
        ON UPDATE RESTRICT
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `phone_station`.`tariff`
(
    `id`               INT          NOT NULL AUTO_INCREMENT,
    `name`             VARCHAR(50)  NOT NULL,
    `description`      VARCHAR(200) NOT NULL,
    `subscription_fee` INT(10)      NOT NULL,
    `call_cost`        INT(10)      NOT NULL,
    `sms_cost`         INT(10)      NOT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `service`
(
    `id`            INT          NOT NULL AUTO_INCREMENT,
    `name`          VARCHAR(50)  NOT NULL,
    `description`   VARCHAR(200) NOT NULL,
    `duration_days` INT(5)       NOT NULL,
    `price`         INT(10)      NOT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `user_service`
(
    `id`         INT     NOT NULL AUTO_INCREMENT,
    `user_id`    INT(10) NOT NULL,
    `service_id` INT(10) NOT NULL,
    `begin_date` DATE    NOT NULL,
    `end_date`   DATE    NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `id_idx` (`user_id` ASC) VISIBLE,
    INDEX `service_id_idx` (`service_id` ASC) VISIBLE,
    CONSTRAINT `user_id`
        FOREIGN KEY (`user_id`)
            REFERENCES `user` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `service_id`
        FOREIGN KEY (`service_id`)
            REFERENCES `service` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

INSERT INTO `tariff` (`name`, `description`, `subscription_fee`, `call_cost`, `sms_cost`)
VALUES ('Базовый', 'Базовый тариф для звонков', 200, 5, 7);

INSERT INTO `account` (`login`, `password`, `role`)
VALUES ('admin', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', '0'),
       ('Test1', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', '1'),
       ('Test2', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', '1'),
       ('Test3', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', '1'),
       ('Test4', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', '1'),
       ('Test5', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', '1'),
       ('Test6', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', '1'),
       ('Test7', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', '1'),
       ('Test8', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', '1'),
       ('Test9', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', '1'),
       ('Test10', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', '1');

INSERT INTO `user` (`account_id`, `firstname`, `lastname`, `phone_number`, `balance`, `tariff`, `is_blocked`,
                    `is_registered`)
VALUES ('2', 'Иван', 'Иванов', '+375000000000', 0, 1, 1, 0),
       ('3', 'Петр', 'Петров', '+375000000001', 0, 1, 1, 0),
       ('4', 'Борис', 'Борисов', '+375000000002', 0, 1, 1, 0),
       ('5', 'Анна', 'Анина', '+375000000003', 0, 1, 1, 0),
       ('6', 'Виктория', 'Викториева', '+375000000004', 1, 0, 1, 0),
       ('7', 'Юлия', 'Юлиева', '+375000000005', 0, 1, 1, 0),
       ('8', 'Наташа', 'Наталина', '+375000000006', 0, 1, 1, 0),
       ('9', 'Сидор', 'Сидоров', '+375000000007', 0, 1, 1, 0),
       ('10', 'Василий', 'Васильев', '+375000000008', 0, 1, 1, 0),
       ('11', 'Марина', 'Маринина', '+375000000009', 0, 1, 1, 0);
