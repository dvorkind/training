DROP DATABASE IF EXISTS `phone_station`;

CREATE DATABASE `phone_station` DEFAULT CHARACTER SET utf8;

USE `phone_station`;

CREATE TABLE IF NOT EXISTS `account`
(
    `id`         INT         NOT NULL AUTO_INCREMENT,
    `login`      VARCHAR(50) NOT NULL UNIQUE,
    `password`   VARCHAR(64) NOT NULL,
    `role`       TINYINT(1)  NOT NULL,
    `is_deleted` TINYINT(1)  NOT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `subscriber`
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
    `is_deleted`    TINYINT(1)  NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`account_id`)
        REFERENCES `account` (`id`)
        ON DELETE CASCADE
        ON UPDATE RESTRICT
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `tariff`
(
    `id`               INT          NOT NULL AUTO_INCREMENT,
    `name`             VARCHAR(50)  NOT NULL,
    `description`      VARCHAR(200) NOT NULL,
    `subscription_fee` INT(10)      NOT NULL,
    `call_cost`        INT(10)      NOT NULL,
    `sms_cost`         INT(10)      NOT NULL,
    `is_deleted`       TINYINT(1)   NOT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `service`
(
    `id`          INT          NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(50)  NOT NULL,
    `description` VARCHAR(200) NOT NULL,
    `price`       INT(10)      NOT NULL,
    `is_deleted`  TINYINT(1)   NOT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `bill`
(
    `id`            INT        NOT NULL AUTO_INCREMENT,
    `subscriber_id` INT(10)    NOT NULL,
    `invoice_date`  TIMESTAMP  NOT NULL,
    `sum`           INT(10)    NOT NULL,
    `is_paid`       TINYINT(1) NOT NULL,
    `is_deleted`    TINYINT(1) NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`subscriber_id`)
        REFERENCES `subscriber` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `subscriber_service`
(
    `id`            INT     NOT NULL AUTO_INCREMENT,
    `subscriber_id` INT(10) NOT NULL,
    `service_id`    INT(10) NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`subscriber_id`)
        REFERENCES `subscriber` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    FOREIGN KEY (`service_id`)
        REFERENCES `service` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `subscriber_action`
(
    `id`            INT        NOT NULL AUTO_INCREMENT,
    `subscriber_id` INT(10)    NOT NULL,
    `action`        TINYINT(1) NOT NULL,
    `date`          TIMESTAMP  NOT NULL,
    `sum`           INT(10)    NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`subscriber_id`)
        REFERENCES `subscriber` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

INSERT INTO `tariff` (`name`, `description`, `subscription_fee`, `call_cost`, `sms_cost`, `is_deleted`)
VALUES ('Стартовый', 'Для тех, кто начал пользоваться смартфоном', 893, 0, 8, 0),
       ('Социальный', 'Оставайтесь на связи с близкими', 170, 2, 5, 0),
       ('Комфорт', 'Лучший тариф для смартфона. Включает 10 ГБ трафика с ежемесячным накоплением', 2173, 0, 8, 0),
       ('Без лимита', 'Все возможности безлимитного интернета на полной скорости', 2589, 5, 8, 0),
       ('Голос', 'Много звонков для детей и взрослых', 690, 15, 8, 0);

INSERT INTO `service` (`name`, `description`, `price`, `is_deleted`)
VALUES ('Конференц-связь',
        'Режим «Конференц-связи» позволяет объединять до 6 телефонных разговоров, включая инициатора конференции', 250,
        0),
       ('Антиопределитель номера',
        'Услуга запрет на определение номера позволяет вам запретить другим абонентам сети увидеть ваш мобильный номер при вызове',
        357, 0),
       ('Определитель номера',
        'Позволяет увидеть на дисплее мобильного телефона номер вызывающего вас мобильного абонента', 10, 0),
       ('Мелодия',
        'Звонящие вам будут слышать выбранную мелодию вместо гудков', 322, 0),
       ('Международный доступ',
        'Международный доступ — услуга, позволяющая совершать международные звонки (звонки за пределы страны пребывания). Набор номера необходимо осуществлять в международном формате',
        50, 0),
       ('Ожидание вызова',
        'Позволяет во время телефонного разговора получать звуковое уведомление и информацию на дисплей мобильного телефона о поступлении еще одного телефонного вызова',
        360, 0),
       ('Удержание вызова',
        'Позволяет во время разговора с первым абонентом совершить исходящий вызов или принять входящий вызов от второго абонента, при этом первый абонент будет находиться в режиме удержания',
        160, 0),
       ('Роуминг',
        'Международный роуминг позволяет пользоваться своим номером в любой стране в сети оператора связи, с которым заключено роуминговое соглашение',
        200, 0);

INSERT INTO `account` (`login`, `password`, `role`, `is_deleted`)
VALUES ('admin', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', '0', 0),
       ('Test1', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', '1', 0),
       ('Test2', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', '1', 0),
       ('Test3', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', '1', 0),
       ('Test4', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', '1', 0),
       ('Test5', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', '1', 0),
       ('Test6', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', '1', 0),
       ('Test7', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', '1', 0),
       ('Test8', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', '1', 0),
       ('Test9', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', '1', 0),
       ('Test10', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', '1', 0);

INSERT INTO `subscriber` (`account_id`, `firstname`, `lastname`, `phone_number`, `balance`, `tariff`, `is_blocked`,
                          `is_registered`, `is_deleted`)
VALUES ('2', 'Иван', 'Иванов', '+375000000000', -300, 1, 1, 1, 0),
       ('3', 'Петр', 'Петров', '+375000000001', 0, 2, 0, 0, 0),
       ('4', 'Борис', 'Борисов', '+375000000002', -10, 1, 1, 1, 0),
       ('5', 'Анна', 'Анина', '+375000000003', 0, 1, 1, 1, 0),
       ('6', 'Виктория', 'Викториева', '+375000000004', 0, 1, 0, 0, 0),
       ('7', 'Юлия', 'Юлиева', '+375000000005', 0, 3, 0, 0, 0),
       ('8', 'Наташа', 'Наталина', '+375000000006', 0, 1, 0, 0, 0),
       ('9', 'Сидор', 'Сидоров', '+375000000007', -6600, 1, 1, 1, 0),
       ('10', 'Василий', 'Васильев', '+375000000008', 0, 4, 0, 0, 0),
       ('11', 'Марина', 'Маринина', '+375000000009', 0, 1, 0, 0, 0);

INSERT INTO `subscriber_action` (`subscriber_id`, `action`, `date`, `sum`)
VALUES (1, 0, '2021-09-01 00:00:00', 0),
       (2, 0, '2021-09-01 00:00:00', 0),
       (3, 0, '2021-09-01 00:00:00', 0),
       (4, 0, '2021-09-01 00:00:00', 0),
       (5, 0, '2021-09-01 00:00:00', 0),
       (6, 0, '2021-09-01 00:00:00', 0),
       (7, 0, '2021-09-01 00:00:00', 0),
       (8, 0, '2021-09-01 00:00:00', 0),
       (9, 0, '2021-09-01 00:00:00', 0),
       (10, 0, '2021-09-01 00:00:00', 0);