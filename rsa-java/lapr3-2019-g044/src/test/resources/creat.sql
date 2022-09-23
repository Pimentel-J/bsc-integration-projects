delimiter /

CREATE TABLE user_app(
user_id			number(10) 	GENERATED ALWAYS AS IDENTITY,
username		varchar(100) 	NOT NULL,
email			varchar(50)	UNIQUE,
user_password 		varchar(20) 	NOT NULL,
credit_card		varchar(16)	NOT NULL,
height			number(3)	DEFAULT 0,
weight			number(3)	DEFAULT 0,
points			number(10)      DEFAULT 0,
regist_fee		number(5,2)	DEFAULT 0.0,
avgSpeed		number(5,2)	DEFAULT 0.0,
gender                  varchar(100),
PRIMARY KEY (username)
)
/
--------------------------------------------------------
--  DDL and Constraints for Table GeograficalPoints
--------------------------------------------------------
-- Creates "GeograficalPoints" table
CREATE TABLE geograficalpoints (
    latitude            NUMERIC(8,6)     CONSTRAINT nn_geograficalpoints_latitude NOT NULL,
    longitude           NUMERIC(9,6)     CONSTRAINT nn_geograficalpoints_longitude NOT NULL,
    PRIMARY KEY (latitude, longitude)
)
/
--------------------------------------------------------
--  DDL and Constraints for Table PARKS
--------------------------------------------------------
-- Creates "Parks" table
CREATE TABLE parks (
    pid                 VARCHAR(40)      CONSTRAINT pk_parks_pid PRIMARY KEY,
    latitude            NUMERIC(8,6)     CONSTRAINT nn_parks_latitude NOT NULL,
    longitude           NUMERIC(9,6)     CONSTRAINT nn_parks_longitude NOT NULL,
    elevation           INTEGER          DEFAULT ON NULL 0,
    pdescription        VARCHAR(40)      CONSTRAINT u_parks_pdescription UNIQUE
                                         CONSTRAINT nn_parks_pdescription NOT NULL,
    maxNumberBicycles   INTEGER          CONSTRAINT nn_parks_maxNumberBicycles NOT NULL,
    maxNumberEscooters  INTEGER          CONSTRAINT nn_parks_maxNumberEscooters NOT NULL,
    inputVoltage        INTEGER          CONSTRAINT nn_parks_inputVoltage NOT NULL,
    inputCurrent        INTEGER          CONSTRAINT nn_parks_inputCurrent NOT NULL
)
/
-- Adds constraints to "Parks" table
ALTER TABLE parks 
ADD CONSTRAINT fk_parks_latitude_longitude FOREIGN KEY (latitude,longitude) REFERENCES geograficalpoints(latitude,longitude)
/
ALTER TABLE parks
ADD CONSTRAINT latitude_longitude_unique UNIQUE (latitude, longitude)
/

--------------------------------------------------------
--  DDL and Constraints for Table BICYCLES
--------------------------------------------------------
-- Creates "Bicycles" table
CREATE TABLE bicycles (
    descr               VARCHAR(10)      CONSTRAINT pk_bicycles_descr PRIMARY KEY,
    weight              INTEGER          CONSTRAINT nn_bicycles_weight NOT NULL,
    parkLat             NUMERIC(8,6)     CONSTRAINT nn_bicycles_parkLat NOT NULL,
    parkLong            NUMERIC(9,6)     CONSTRAINT nn_bicycles_parkLong NOT NULL,
    aerodynCoeffic      NUMERIC(3,2)     CONSTRAINT nn_bicycles_aerodynCoeffic NOT NULL,
    frontalArea         NUMERIC(3,1)     CONSTRAINT nn_bicycles_frontalArea NOT NULL,
    wheelSize           INTEGER          CONSTRAINT nn_bicycles_wheelSize NOT NULL
)
/
-- Adds constraints to "Bicycles" table
ALTER TABLE bicycles
ADD CONSTRAINT ck_bicycles_descr         CHECK(REGEXP_LIKE(descr, '[PT]{1}[0-9]{3}'))
--ALTER TABLE bicycles
--ADD CONSTRAINT something_unique UNIQUE (something1, something2)
/

--------------------------------------------------------
--  DDL and Constraints for Table ESCOOTERS
--------------------------------------------------------
-- Creates "Escooters" table
CREATE TABLE escooters (
    descr               VARCHAR(10)      CONSTRAINT pk_escooters_descr PRIMARY KEY,
    weight              INTEGER          CONSTRAINT nn_escooters_weight NOT NULL,
    etype               VARCHAR(10)      CONSTRAINT nn_escooters_type NOT NULL,
    parkLat             NUMERIC(8,6)     CONSTRAINT nn_escooters_parkLat NOT NULL,
    parkLong            NUMERIC(9,6)     CONSTRAINT nn_escooters_parkLong NOT NULL,
    maxBattery          NUMERIC(3,2)     CONSTRAINT nn_escooters_maxBattery NOT NULL,
    actualBattery       INTEGER          CONSTRAINT nn_escooters_actualBattery NOT NULL,
    aerodynCoeffic      NUMERIC(3,2)     CONSTRAINT nn_escooters_aerodynCoeffic NOT NULL,
    frontalArea         NUMERIC(3,1)     CONSTRAINT nn_escooters_frontalArea NOT NULL,
    motor               INTEGER          CONSTRAINT nn_escooters_motor NOT NULL
)
/
-- Adds constraints to "Escooters" table
ALTER TABLE escooters
ADD CONSTRAINT ck_escooters_descr        CHECK(REGEXP_LIKE(descr, '[ePT]{1}[0-9]{3}'))
--ALTER TABLE escooters
--ADD CONSTRAINT something_unique UNIQUE (something1, something2);
/
--------------------------------------------------------
--  VIEW for VEHICLES (BICYCLES and ESCOOTERS)
--------------------------------------------------------
CREATE VIEW vehicles (descr, weight, parkLat, parkLong, aerodynCoeffic, frontalArea, wheelSize, etype, maxBattery, actualBattery, motor) AS
SELECT descr, weight, parkLat, parkLong, aerodynCoeffic, frontalArea, wheelSize, null, null, null, null
FROM bicycles
UNION ALL
SELECT descr, weight, parkLat, parkLong, aerodynCoeffic, frontalArea, null, etype, maxBattery, actualBattery, motor
FROM escooters
/
--------------------------------------------------------
--  DDL and Constraints for Table POIS
--------------------------------------------------------
-- Creates "Pois" table
CREATE TABLE pois (
    pid                 NUMBER           GENERATED ALWAYS AS IDENTITY
                                         CONSTRAINT pk_pois_pid PRIMARY KEY,
    latitude            NUMERIC(8,6)     CONSTRAINT nn_pois_latitude NOT NULL,
    longitude           NUMERIC(9,6)     CONSTRAINT nn_pois_longitude NOT NULL,
    elevation           INTEGER          DEFAULT ON NULL 0,
    pdescription        VARCHAR(40)
)
/
-- Adds constraints to "Pois" table
ALTER TABLE pois 
ADD CONSTRAINT fk_pois_latitude_longitude FOREIGN KEY (latitude,longitude) REFERENCES geograficalpoints(latitude,longitude)
/
ALTER TABLE pois 
ADD CONSTRAINT u_pois_latitude_longitude UNIQUE (latitude,longitude)
/



--------------------------------------------------------
--  DDL and Constraints for Table Invoice_Line
--------------------------------------------------------
-- Creates "Invoice_Line" table
CREATE TABLE Invoice_Line (
    invoice_line_id         NUMBER(10)          GENERATED ALWAYS AS IDENTITY,
    username                VARCHAR(100)        CONSTRAINT nn_invoice_line_username NOT NULL,
    previous_points         NUMBER(10)          CONSTRAINT nn_invoice_line_previous_points NOT NULL,
    earned_points           NUMBER(10)          CONSTRAINT nn_invoice_line_earned_points NOT NULL,
    discouted_points        NUMBER(10)          CONSTRAINT nn_invoice_line_discouted_points NOT NULL,
    actual_points           NUMBER(10)          CONSTRAINT nn_invoice_line_actual_points  NOT NULL,
    cost                    NUMBER(10,2)       CONSTRAINT nn_invoice_line_cost NOT NULL,
    PRIMARY KEY(invoice_line_id)
)
/
ALTER TABLE Invoice_Line ADD CONSTRAINT fk_invoice_line_username FOREIGN KEY (username) REFERENCES user_app (username)
/
--------------------------------------------------------
--  DDL and Constraints for Table Trips
--------------------------------------------------------
-- Creates "Trips" table
CREATE TABLE Trips (
  tripID                number(10)          GENERATED ALWAYS AS IDENTITY,
  vehicle_description   varchar(10)         CONSTRAINT nn_vehicle_description NOT NULL,
  username              varchar(100)        CONSTRAINT nn_username NOT NULL,
  start_park_id         varchar(40)         CONSTRAINT nn_start_park_id NOT NULL,
  end_park_id           varchar(40),
  timestamp_start       timestamp(0)        CONSTRAINT nn_timestamp_start NOT NULL,
  timestamp_finish      timestamp(0),
  cost                  number(10,2)       DEFAULT 0.0,
  points                number(10),
  invoice_id            number(10),
  PRIMARY KEY (tripID)
)
/
ALTER TABLE Trips ADD CONSTRAINT fk_trips_username FOREIGN KEY (username) REFERENCES user_app (username)
/
ALTER TABLE Trips ADD CONSTRAINT fk_trips_start_park_id FOREIGN KEY (start_park_id) REFERENCES parks (pid)
/
ALTER TABLE Trips ADD CONSTRAINT fk_trips_end_park_id FOREIGN KEY (end_park_id) REFERENCES parks (pid)
/
ALTER TABLE Trips ADD CONSTRAINT fk_trips_invoice_id FOREIGN KEY (invoice_id) REFERENCES Invoice_Line (invoice_line_id)
/
--------------------------------------------------------
--  DDL and Constraints for Table paths
--------------------------------------------------------
-- Creates "paths" table
CREATE TABLE paths (
    latitudeA            NUMERIC(8,6)     CONSTRAINT nn_paths_latitudeA NOT NULL,
    longitudeA           NUMERIC(9,6)     CONSTRAINT nn_paths_longitudeA NOT NULL,
    latitudeB            NUMERIC(8,6)     CONSTRAINT nn_paths_latitudeB NOT NULL,
    longitudeB           NUMERIC(9,6)     CONSTRAINT nn_paths_longitudeB NOT NULL,
    k_coeffic            NUMERIC(4,3)     DEFAULT ON NULL 0,
    wind_dir             INTEGER          DEFAULT ON NULL 0,
    wind_speed           NUMERIC(3,1)     DEFAULT ON NULL 0,
    PRIMARY KEY (latitudeA,longitudeA,latitudeB,longitudeB)
)
/
ALTER TABLE paths 
ADD CONSTRAINT fk_paths_latitude_longitudeA FOREIGN KEY (latitudeA,longitudeA) REFERENCES geograficalpoints(latitude,longitude)
/
ALTER TABLE paths 
ADD CONSTRAINT fk_paths_latitude_longitudeB FOREIGN KEY (latitudeB,longitudeB) REFERENCES geograficalpoints(latitude,longitude)
/
ALTER TABLE paths
ADD CONSTRAINT u_paths_latitude_longitudePair CHECK (NOT(latitudeA = latitudeB AND longitudeA = longitudeB))
/
