CREATE VIEW step_result_view AS 
SELECT library_casestep.id as casestep_id, library_casestep.instruction as step_instruction, library_casestep.expected as casestep_expected, #library_casestep
execution_stepresult.id as stepresult_id, 
execution_result.status as result_status, execution_result.id as result_id
FROM execution_stepresult,library_casestep,execution_result
WHERE execution_stepresult.result_id = execution_result.id and execution_stepresult.step_id = library_casestep.id