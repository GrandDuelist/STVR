DROP procedure if exists GetTargetData;
DELIMITER //
CREATE PROCEDURE GetTargetData()

BEGIN
DECLARE done INT DEFAULT 0;
DECLARE CurrentCaseID INT;
DECLARE CurrentNumber INT;
DECLARE StepInstruction LONGTEXT;
DECLARE ResultStatus varchar(50);
DECLARE rs CURSOR FOR SELECT `passed_failed_number_view`.`caseversion_id`,
    `passed_failed_number_view`.`number`
FROM `firefox`.`passed_failed_number_view`;
DECLARE CONTINUE HANDLER  FOR NOT FOUND SET done = 1;
OPEN rs;


get_case: LOOP
FETCH NEXT FROM rs INTO CurrentCaseID,CurrentNumber;

IF done then
LEAVE get_case;
END IF;

	BEGIN
		DECLARE set_inner INT DEFAULT 0;
		DECLARE ConvergeResultStatus varchar(50) DEFAULT "passed";
		DECLARE result_id INT default 0;
		DECLARE temp_result_id INT;
		DECLARE rs2 CURSOR FOR SELECT  passed_failed_view.result_status, passed_failed_view.result_id  FROM passed_failed_view WHERE passed_failed_view.caseversion_id = CurrentCaseID;
		declare continue handler for not found set set_inner = 1; 
		open rs2;
		get_result: LOOP
		if set_inner=1 then
			leave get_result;
		end if; 
		SET ConvergeResultStatus = "passed";
		FETCH NEXT FROM rs2 INTO  ResultStatus,temp_result_id;
		if ResultStatus = "failed" then
		SET ConvergeResultStatus = "failed";
		SET result_id = temp_result_id;
		END IF;

END LOOP get_result;

INSERT INTO `firefox`.`step_result_table`
		(`result_status`,
	`caseversion_id`,
	`result_id`)
		VALUES
(ConvergeResultStatus,
	CurrentCaseID,
	result_id);


close rs2;
end;
END LOOP get_case;

CLOSE rs;

END//
DELIMITER ;
