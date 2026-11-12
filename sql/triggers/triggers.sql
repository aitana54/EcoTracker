-- Trigger: After INSERT on sostenibilitat
DELIMITER //
CREATE TRIGGER after_sostenibilitat_insert
AFTER INSERT ON sostenibilitat
FOR EACH ROW
BEGIN
    INSERT INTO actions (time_of_action, action_performed)
    VALUES (
        NOW(),
        CONCAT('Inserted entry (id=', NEW.id, ') with descripcio: "', NEW.descripcio, '"')
    );
END;
//
DELIMITER ;

-- Trigger: After DELETE on sostenibilitat
DELIMITER //
CREATE TRIGGER after_sostenibilitat_delete
AFTER DELETE ON sostenibilitat
FOR EACH ROW
BEGIN
    INSERT INTO actions (time_of_action, action_performed)
    VALUES (
        NOW(),
        CONCAT('Deleted entry (id=', OLD.id, ') with descripcio: "', OLD.descripcio, '"')
    );
END;
//
DELIMITER ;

-- Trigger: After UPDATE on sostenibilitat
DELIMITER //
CREATE TRIGGER after_sostenibilitat_update
AFTER UPDATE ON sostenibilitat
FOR EACH ROW
BEGIN
    INSERT INTO actions (time_of_action, action_performed)
    VALUES (
        NOW(),
        CONCAT(
            'Updated entry (id=', NEW.id, ') from descripcio: "', OLD.descripcio,
            '" to: "', NEW.descripcio, '"'
        )
    );
END;
//
DELIMITER ;
