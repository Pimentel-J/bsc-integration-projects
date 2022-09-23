delimiter /


--------------------------------------------------------
--  Functions and procedures for Table USERS   
--------------------------------------------------------
-- procedure that creates a new user
create or replace PROCEDURE newUser(p_username VARCHAR, p_email varchar, p_password varchar, p_credit_card_number varchar, 
                 p_height numeric, p_weight numeric,p_credit_points numeric,p_regist_fee numeric, p_avg_speed numeric, p_gender varchar) 
AS
BEGIN
    insert into USER_APP(username,email,user_password,credit_card,height,weight,points,regist_fee,avgSpeed,gender) 
        values (p_username,p_email,p_password,p_credit_card_number,p_height,p_weight,p_credit_points,p_regist_fee,p_avg_speed,p_gender);
    
end;
/
-- Procedure that updates a users in the table "USER_APP"
CREATE OR REPLACE PROCEDURE UPDATEUSER(p_user_id NUMBER,p_username VARCHAR,p_email VARCHAR, p_password VARCHAR,p_credit_card_number VARCHAR, 
                        p_height NUMERIC,p_weight NUMERIC,p_credit_points NUMERIC, p_regist_fee NUMERIC,p_avg_speed NUMERIC, p_gender VARCHAR)
AS
BEGIN
 UPDATE USER_APP
    SET username            = p_username,
        email               = p_email,
        user_password       = p_password,
        credit_card         = p_credit_card_number,
        height              = p_height,
        weight              = p_weight,
        points              = p_credit_points,
        regist_fee          = p_regist_fee,
        avgSpeed            = p_avg_speed,
        gender              = p_gender
  WHERE username = p_username;
END;
/

-- Procedure that creates a new user from file
create or replace PROCEDURE addUser(p_username varchar, p_email varchar, p_height numeric,p_weight numeric,p_avg_speed numeric , p_credit_card_number varchar,p_gender VARCHAR,p_password VARCHAR ) 
             
AS
BEGIN
    insert into USER_APP(username,email,height,weight,avgSpeed,credit_card,gender,user_password) 
        values (p_username,p_email,p_height,p_weight,p_avg_speed,p_credit_card_number,p_gender,p_password);
    
end;
/


-- Function that returns the reference to a cursor object that contains info about a user
CREATE OR REPLACE FUNCTION GETUSERBYEMAIL(email varchar)
RETURN SYS_REFCURSOR
AS
  curUser SYS_REFCURSOR;	-- variável de cursor do tipo predefinido SYS_REFCURSOR 
BEGIN
  -- criar um objeto cursor e armazenar a referÍncia desse objeto na vari·vel de cursor curSailor
  OPEN curUser FOR SELECT * FROM USER_APP WHERE USER_APP.EMAIL = GETUSERBYEMAIL.email; RETURN curUser; END;
/

-- Function that returns the reference to a cursor object that contains info about a user
create or replace FUNCTION GETUSERBYUSERNAME(username varchar)
RETURN SYS_REFCURSOR
AS
  curUser SYS_REFCURSOR;	-- variável de cursor do tipo predefinido SYS_REFCURSOR 
BEGIN
    OPEN curUser FOR SELECT * FROM USER_APP WHERE USER_APP.USERNAME = GETUSERBYUSERNAME.username; RETURN curUser; END;
/

-- Function that removes a User
CREATE OR REPLACE PROCEDURE removeUser(email varchar) 
IS
BEGIN
  DELETE FROM USER_APP WHERE USER_APP.email = removeUser.email;
END;
/

--------------------------------------------------------
--  Functions and procedures for Table PARKS   
--------------------------------------------------------
-- Function that returns the reference to a cursor object that contains info about a park
CREATE OR REPLACE FUNCTION getPark(pid VARCHAR)
RETURN SYS_REFCURSOR
AS
  curPark SYS_REFCURSOR;	-- cursor variable from predefined type SYS_REFCURSOR
BEGIN
  -- create a cursor object and save the reference of that object in the cursor variable curPark
  OPEN curPark FOR SELECT * FROM parks WHERE parks.pid = getPark.pid; 
  RETURN curPark; 
END;
/

-- Function that returns the reference to a cursor object that contains info about a park
CREATE OR REPLACE FUNCTION getParkByCoordinates(plat NUMBER, plong NUMBER)
RETURN SYS_REFCURSOR
AS
  curPark SYS_REFCURSOR;	-- cursor variable from predefined type SYS_REFCURSOR
BEGIN
  -- create a cursor object and save the reference of that object in the cursor variable curPark
  OPEN curPark FOR SELECT * FROM parks WHERE parks.latitude = getParkByCoordinates.plat 
                                            AND parks.longitude = getParkByCoordinates.plong; 
  RETURN curPark; 
END;
/

-- Procedure that adds a park in the table "Parks"
CREATE OR REPLACE PROCEDURE addPark(pid VARCHAR, latitude NUMBER, longitude NUMBER, elevation INTEGER, pdescription VARCHAR,
    maxNumberBicycles INTEGER, maxNumberEscooters INTEGER, inputVoltage INTEGER, inputCurrent INTEGER)
AS
BEGIN
  INSERT INTO parks VALUES(pid,latitude,longitude,elevation,pdescription,maxNumberBicycles,maxNumberEscooters,inputVoltage,inputCurrent);
END;
/

-- Function that updates a park
create or replace FUNCTION updatePark( p_pid varchar, p_latitude number, p_longitude number, p_elevation INTEGER,p_pdescription  varchar,
p_maxNumberBicycles  INTEGER,p_maxNumberEscooters INTEGER,p_inputVoltage  INTEGER,p_inputCurrent INTEGER )
   return varchar
as
r_id_park varchar(40);
begin
 update parks
    set latitude            = p_latitude,
        longitude           = p_longitude,
        elevation           = p_elevation,
        pdescription        = p_pdescription,
        maxNumberBicycles   = p_maxNumberBicycles,
        maxNumberEscooters  = p_maxNumberEscooters,
        inputVoltage        = p_inputVoltage,
        inputCurrent        = p_inputCurrent
  where pid = p_pid
  returning pid into r_id_park;
  commit;
  return r_id_park;
end;
/

-- Function that removes a Park

CREATE OR REPLACE FUNCTION deletePark(p_pid VARCHAR) 
RETURN varchar
AS
 r_id_park varchar(40);	
BEGIN
  DELETE FROM parks WHERE parks.pid = p_pid
returning pid into r_id_park;
commit;
return r_id_park;
END;
/

-- Function that returns the reference to a cursor object that contains info about all parks
create or replace FUNCTION getAllParks RETURN SYS_REFCURSOR AS
f_cursor SYS_REFCURSOR;
    BEGIN
    OPEN f_cursor FOR
        SELECT *
        FROM parks;
        RETURN (f_cursor);
    END;
/

--------------------------------------------------------
--  Functions and procedures for Table BICYCLES
--------------------------------------------------------
-- Function that returns the reference to a cursor object that contains info about a bicycle
CREATE OR REPLACE FUNCTION getBicycle(descr VARCHAR)
RETURN SYS_REFCURSOR
AS
  curBicycle SYS_REFCURSOR;	-- cursor variable from predefined type SYS_REFCURSOR
BEGIN
  -- create a cursor object and save the reference of that object in the cursor variable curBicycle
  OPEN curBicycle FOR SELECT * FROM bicycles WHERE bicycles.descr = getBicycle.descr;
  RETURN curBicycle;
END;
/

-- Procedure that adds a bicycle in the table "Bicycles"
CREATE OR REPLACE PROCEDURE addBicycle(descr VARCHAR, weight INTEGER, parkLat NUMBER, parkLong NUMBER,
    aerodynCoeffic NUMBER, frontalArea NUMBER, wheelSize INTEGER)
AS
BEGIN
  INSERT INTO bicycles VALUES(descr, weight, parkLat, parkLong, aerodynCoeffic, frontalArea, wheelSize);
END;
/
-- Function that removes a Bicycle
CREATE OR REPLACE FUNCTION deleteBicycle(b_descr VARCHAR)
RETURN varchar
AS
 r_descr_bicycle VARCHAR(10);
BEGIN
  DELETE FROM bicycles WHERE bicycles.descr = b_descr
RETURNING b_descr INTO r_descr_bicycle;
COMMIT;
RETURN r_descr_bicycle;
END;
/

-- Function that returns the reference to a cursor object that contains info about all bicycles
create or replace FUNCTION getAllBicycles
RETURN SYS_REFCURSOR
AS
  curBicycle SYS_REFCURSOR;	-- cursor variable from predefined type SYS_REFCURSOR
BEGIN
  OPEN curBicycle FOR SELECT * FROM bicycles;
  RETURN curBicycle;
END;
/

--------------------------------------------------------
--  Functions and procedures for Table ESCOOTERS
--------------------------------------------------------
-- Function that returns the reference to a cursor object that contains info about a escooter
CREATE OR REPLACE FUNCTION getEscooter(descr VARCHAR)
RETURN SYS_REFCURSOR
AS
  curEscooter SYS_REFCURSOR;	-- cursor variable from predefined type SYS_REFCURSOR
BEGIN
  -- create a cursor object and save the reference of that object in the cursor variable curEscooter
  OPEN curEscooter FOR SELECT * FROM escooters WHERE escooters.descr = getEscooter.descr;
  RETURN curEscooter;
END;
/

-- Procedure that adds a escooter in the table "Escooters"
CREATE OR REPLACE PROCEDURE addEscooter(descr VARCHAR, weight INTEGER, etype VARCHAR, parkLat NUMBER, parkLong NUMBER,
    maxBattery NUMBER, actualBattery INTEGER, aerodynCoeffic NUMBER, frontalArea NUMBER, motor INTEGER)
AS
BEGIN
  INSERT INTO escooters VALUES(descr, weight, etype, parkLat, parkLong, maxBattery, actualBattery,
                               aerodynCoeffic, frontalArea, motor);
END;
/
-- Function that returns the reference to a cursor object that contains info about all eScooters
create or replace FUNCTION getAllEscooters
RETURN SYS_REFCURSOR
AS
  curEscooter SYS_REFCURSOR;	-- cursor variable from predefined type SYS_REFCURSOR
BEGIN
  OPEN curEscooter FOR SELECT * FROM escooters;
  RETURN curEscooter;
END;
/

-- Function that removes an Escooter
CREATE OR REPLACE FUNCTION deleteEscooter(s_descr VARCHAR)
RETURN varchar
AS
 r_descr_escooter VARCHAR(10);
BEGIN
  DELETE FROM escooters WHERE escooters.descr = s_descr
RETURNING s_descr INTO r_descr_escooter;
COMMIT;
RETURN r_descr_escooter;
END;
/

-- Function that returns all Escooters from a Park
CREATE OR REPLACE FUNCTION getEscootersFromPark(latitude NUMBER, longitude NUMBER)
RETURN SYS_REFCURSOR
AS
  curEscPark SYS_REFCURSOR; -- cursor variable from predefined type SYS_REFCURSOR
BEGIN
  -- create a cursor object and save the reference of that object in the cursor variable curEscPark
  OPEN curEscPark FOR
    SELECT descr, weight, etype, parkLat, parkLong, maxBattery, actualBattery, aerodynCoeffic, frontalArea, motor
    FROM escooters
    WHERE escooters.parklat = getEscootersFromPark.latitude AND escooters.parklong = getEscootersFromPark.longitude AND escooters.actualbattery != 100;
  RETURN curEscPark;
END;
/

-- Function that returns 0 if fail or not 0 if sucess, Update info about escooters or bicycles
create or replace FUNCTION updateVehicle(
f_descr escooters.descr%TYPE,
f_weight escooters.weight%TYPE,
f_parklat escooters.parklat%TYPE,
f_parklon escooters.parklong%TYPE,
f_aerodyn escooters.aerodyncoeffic%TYPE,
f_frontarea escooters.frontalarea%TYPE,
f_etype escooters.etype%TYPE,
f_maxbat escooters.maxbattery%TYPE,
f_actbattery escooters.actualbattery%TYPE,
f_wheelsize bicycles.wheelsize%TYPE,
f_motor escooters.motor%TYPE)
RETURN Integer AS
vehicle_dont_exist EXCEPTION;
result integer;
    BEGIN
        IF(f_maxbat IS NULL AND f_etype IS NULL AND f_actbattery IS NULL) THEN
            SELECT COUNT (*) INTO result
            FROM bicycles b
            WHERE b.descr = f_descr;
            IF(result != 0) THEN
                UPDATE bicycles
                SET
                    weight = f_weight,
                    parklat = f_parklat,
                    parklong = f_parklon,
                    aerodyncoeffic = f_aerodyn,
                    frontalarea = f_frontarea,
                    wheelsize = f_wheelsize
                WHERE
                    f_descr = bicycles.descr;
            ELSE
                raise vehicle_dont_exist;
            END IF;
        ELSE
            SELECT COUNT (*) INTO result
            FROM escooters e
            WHERE e.descr = f_descr;
            IF(result != 0) THEN
                UPDATE escooters
                SET
                    weight = f_weight,
                    parklat = f_parklat,
                    parklong = f_parklon,
                    aerodyncoeffic = f_aerodyn,
                    frontalarea = f_frontarea,
                    etype = f_etype,
                    maxbattery = f_maxbat,
                    actualbattery = f_actbattery,
                    motor = f_motor
                WHERE
                    f_descr = escooters.descr;
             ELSE
                raise vehicle_dont_exist;
            END IF;    
        END IF;
        
    return result;
        
    EXCEPTION
        WHEN vehicle_dont_exist THEN
        RAISE_APPLICATION_ERROR(-20000,'Vehicle dont exist '||f_descr);
    END;
/

--------------------------------------------------------
--  Functions and procedures for Table POIS   
--------------------------------------------------------
-- Function that returns the reference to a cursor object that contains info about a poi
CREATE OR REPLACE FUNCTION getPoi(latitude NUMBER, longitude NUMBER)
RETURN SYS_REFCURSOR
AS
  curPoi SYS_REFCURSOR; -- cursor variable from predefined type SYS_REFCURSOR
BEGIN
  -- create a cursor object and save the reference of that object in the cursor variable curPoi
  OPEN curPoi FOR 
    SELECT latitude, longitude, elevation, pdescription FROM pois 
    WHERE pois.latitude = getPoi.latitude AND pois.longitude = getPoi.longitude; 
  RETURN curPoi; 
END;
/

-- Procedure that adds a poi in the table "Pois"
CREATE OR REPLACE PROCEDURE addPoi(latitude NUMBER, longitude NUMBER, elevation INTEGER, pdescription VARCHAR) 
AS
BEGIN
  INSERT INTO pois (latitude,longitude,elevation,pdescription) VALUES(latitude,longitude,elevation,pdescription);   
END;
/

--------------------------------------------------------
--  Functions and procedures for Table TRIP   
--------------------------------------------------------
-- Function that returns the reference to a cursor object that contains info about a trip
CREATE OR REPLACE FUNCTION getTrip(username VARCHAR)
RETURN SYS_REFCURSOR
AS
  curTrip SYS_REFCURSOR; -- cursor variable from predefined type SYS_REFCURSOR
BEGIN
  -- create a cursor object and save the reference of that object in the cursor variable curTrip
  OPEN curTrip FOR 
    SELECT vehicle_description, username, start_park_id, end_park_id,timestamp_start,timestamp_finish,cost,points,invoice_id FROM Trips 
    WHERE Trips.username = getTrip.username; 
  RETURN curTrip; 
END;
/

-- Procedure that adds a trips in the table "Trips"
CREATE OR REPLACE PROCEDURE addTrip(p_vehicleDescr VARCHAR, p_userName VARCHAR, p_startParkId VARCHAR, p_endParkId VARCHAR,p_startTime timestamp,p_endTime timestamp,p_cost number ,p_points number, p_invoiceId number) 
AS
BEGIN
  INSERT INTO Trips (vehicle_description,username,start_park_id,end_park_id,timestamp_start,timestamp_finish,cost,points,invoice_id) 
            VALUES(p_vehicleDescr,p_userName,p_startParkId,p_endParkId,p_startTime,p_endTime,p_cost,p_points,p_invoiceId);   
END;
/
-- Procedure that updates a trips in the table "Trips"
CREATE OR REPLACE PROCEDURE UPDATETRIP(p_trip_id NUMBER,p_vehicleDescr VARCHAR,p_userName VARCHAR, p_startParkId NUMBER,
                                    p_endParkId NUMBER, p_startTime TIMESTAMP,p_endTime TIMESTAMP,p_cost NUMBER,p_points NUMBER, p_invoiceId NUMBER)
AS
BEGIN
 update Trips
    set vehicle_description     = p_vehicleDescr,
        username                = p_userName,
        start_park_id           = p_startParkId,
        end_park_id             = p_endParkId,
        timestamp_start         = p_startTime,
        timestamp_finish        = p_endTime,
        cost                    = p_cost,
        points                  = p_points,
        invoice_id              = p_invoiceId
  where tripID = p_trip_id;
end;
/
--------------------------------------------------------
--  Functions and procedures for Table InvoiceLine   
--------------------------------------------------------
-- Procedure that adds a InvoiceLine in the table "InvoiceLine"

CREATE OR REPLACE function addInvoiceLine(p_username VARCHAR ,p_previous_points NUMBER ,p_earned_points NUMBER,p_discouted_points NUMBER,p_actual_points NUMBER,p_cost NUMBER)
RETURN NUMBER
AS
r_invoiceId NUMBER;
BEGIN
  INSERT INTO Invoice_Line (username,previous_points,earned_points,discouted_points,actual_points,cost) 
             VALUES(p_username,p_previous_points,p_earned_points,p_discouted_points,p_actual_points,p_cost)
 returning invoice_line_id into r_invoiceId;
  commit;
    return r_invoiceId;
END;
/

-- Function that returns a path
create or replace FUNCTION GETPATH(f_latA NUMBER, f_longA NUMBER, f_latB NUMBER, f_longB NUMBER)
RETURN SYS_REFCURSOR
AS
  curPath SYS_REFCURSOR;
BEGIN
  OPEN curPath FOR 
  SELECT * 
  FROM PATHS 
  WHERE f_latA = LATITUDEA AND f_longA = LONGITUDEA AND f_latB = LATITUDEB AND f_longB = LONGITUDEB;
  RETURN curPath;
END;
/

-- Function that add a path
create or replace FUNCTION ADDPATH(f_latA NUMBER, f_longA NUMBER, f_latB NUMBER, f_longB NUMBER, f_k_coeff NUMBER, f_wind_dir INTEGER, f_wind_speed NUMBER)
RETURN INTEGER
AS
  
BEGIN
  INSERT INTO Paths (latitudeA, longitudeA, latitudeB, longitudeB, k_coeffic, wind_dir, wind_speed) 
            VALUES(f_latA, f_longA, f_latB, f_longB, f_k_coeff, f_wind_dir, f_wind_speed);
  RETURN 1;   
END;
/

-- Procedure that add a path
create or replace PROCEDURE ADDPATH2(f_latA NUMBER, f_longA NUMBER, f_latB NUMBER, f_longB NUMBER, f_k_coeff NUMBER, f_wind_dir INTEGER, f_wind_speed NUMBER)
AS
BEGIN
  INSERT INTO Paths (latitudeA, longitudeA, latitudeB, longitudeB, k_coeffic, wind_dir, wind_speed) 
            VALUES(f_latA, f_longA, f_latB, f_longB, f_k_coeff, f_wind_dir, f_wind_speed);
END;
/