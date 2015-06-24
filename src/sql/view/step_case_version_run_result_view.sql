SELECT 
    library_caseversion.id AS caseversion,
FROM
    library_casestep,
    library_caseversion,
    execution_runcaseversion,
    execution_result
WHERE
    library_c