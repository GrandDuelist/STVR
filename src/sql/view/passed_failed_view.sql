CREATE VIEW passed_failed_view AS
    SELECT 
        *
    FROM
        step_case_version_run_result_view
    WHERE
        result_status = 'passed'
            or result_status = 'failed'