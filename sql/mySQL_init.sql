CREATE table CLIENT
(
    ID_CLI INT auto_increment PRIMARY KEY,
    mail             VARCHAR(100),
    nom              VARCHAR(50),
    prenom           VARCHAR(50),
    telephone_number VARCHAR(25)
);

CREATE table CHARGING_STATION
(
    ID_CST INT AUTO_INCREMENT PRIMARY KEY,
    STATUS ENUM('DISPONIBLE', 'INDISPONIBLE', 'RESERVER', 'OCCUPE')
);

CREATE table RESERVATION
(
    ID_RES INT AUTO_INCREMENT PRIMARY KEY,
    num_RESERVATION   VARCHAR(50),
    reservation_cost  double,
    recharge_cost     double,
    penalty_cost      double,
    beginning         TIMESTAMP default CURRENT_TIMESTAMP,
    ending            TIMESTAMP default CURRENT_TIMESTAMP,
    waiting_extention INT (1),
    nbProlongation    INT (1),
    ID_RES_CLI INT,
    FOREIGN KEY (ID_RES_CLI) REFERENCES CLIENT(ID_CLI),
    ID_RES_CST INT,
    FOREIGN KEY (ID_RES_CST) REFERENCES CHARGING_STATION(ID_CST)
);

CREATE table IMMATRICULATION_PLATE
(
    immat varchar(20) primary key
);