drop table if exists parking_spot;
drop table if exists spot;
drop table if exists vehicle;

create table spot (
    ID BIGINT NOT NULL AUTO_INCREMENT,
    code varchar(255) not null,
    type varchar(50) not null,
    spot_id_previous int null,
    spot_id_next int null,
    available tinyint(1) not null default '1',
    PRIMARY KEY (ID)
);

INSERT INTO spot (id, code, type, spot_id_previous, spot_id_next, available)  VALUES (1, 'G1', 'MOTORCYCLE', null, 2, 1);
INSERT INTO spot (id, code , type, spot_id_previous, spot_id_next, available) VALUES (2, 'G2', 'MOTORCYCLE', 1, 3, 1);
INSERT INTO spot (id, code , type, spot_id_previous, spot_id_next, available) VALUES (3, 'G3', 'MOTORCYCLE', 2, 4, 1);
INSERT INTO spot (id, code , type, spot_id_previous, spot_id_next, available) VALUES (4, 'G4', 'CAR', 3, 5, 1);
INSERT INTO spot (id, code , type, spot_id_previous, spot_id_next, available) VALUES (5, 'G5', 'CAR', 4, 6, 1);
INSERT INTO spot (id, code , type, spot_id_previous, spot_id_next, available) VALUES (6, 'G6', 'CAR', 5, 7, 1);
INSERT INTO spot (id, code , type, spot_id_previous, spot_id_next, available) VALUES (7, 'G7', 'VAN', 6, null, 1);



create table vehicle (
    ID BIGINT NOT NULL AUTO_INCREMENT,
    code varchar(255) not null,
    type varchar(50) not null,
    PRIMARY KEY (ID)
);

INSERT INTO vehicle (id, code, type) VALUES (1, 'BR-45871', 'MOTORCYCLE');
INSERT INTO vehicle (id, code, type) VALUES (2, 'BR-45872', 'MOTORCYCLE');
INSERT INTO vehicle (id, code, type) VALUES (3, 'BR-45873', 'MOTORCYCLE');
INSERT INTO vehicle (id, code, type) VALUES (4, 'BR-45874', 'CAR');
INSERT INTO vehicle (id, code, type) VALUES (5, 'BR-45875', 'CAR');
INSERT INTO vehicle (id, code, type) VALUES (6, 'BR-45876', 'VAN');
INSERT INTO vehicle (id, code, type) VALUES (7, 'BR-45877', 'VAN');


create table if not exists parking_spot (
    vehicle_id BIGINT not null,
    spot_id BIGINT not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp null,
    type varchar(50) not null,
    foreign key (vehicle_id) references vehicle(id),
    foreign key (spot_id) references spot(id),
    Constraint PK_Vehicle_Spot Primary Key (vehicle_id, spot_id)
);
