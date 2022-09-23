delimiter /
--------------------------------------------------------
--  DDL and Constraints for Trigger addCoordinatesPOIS
--------------------------------------------------------
-- Creates "addCoordinatesPOIS" Trigger

CREATE OR REPLACE TRIGGER addCoordinatesPOIS
    BEFORE 
    INSERT OR UPDATE 
    ON pois
    FOR EACH ROW
    
    BEGIN
        INSERT INTO geograficalpoints (latitude, longitude)
            VALUES (:new.latitude,:new.longitude);
 
    END;
/

--------------------------------------------------------
--  DDL and Constraints for Trigger addCoordinatesPARKS
--------------------------------------------------------
-- Creates "addCoordinatesPARKS" Trigger
CREATE OR REPLACE TRIGGER addCoordinatesPARKS
    BEFORE 
    INSERT OR UPDATE 
    ON parks
    FOR EACH ROW
    
    BEGIN
        INSERT INTO geograficalpoints (latitude, longitude)
            VALUES (:new.latitude,:new.longitude);
    END;
/
