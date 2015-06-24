CREATE VIEW step_case_version_run_result_view AS 
SELECT 
    library_casestep.id as step_id,library_casestep.instruction as step_instruction,library_casestep.expected as step_expected,library_caseversion.id AS caseversion_id, execution_runcaseversion.id as runcaseversion_id, execution_result.id as result_id, execution_result.status 
FROM
    library_casestep,
    library_caseversion,
    execution_runcaseversion,
    execution_result
WHERE
	execution_result.runcaseversion_id = execution_runcaseversion.id and
    execution_runcaseversion.caseversion_id = library_caseversion.id and 
    library_casestep.caseversion_id = library_caseversion.id  
    