CREATE VIEW all_products_steps_view AS
SELECT library_case.id as case_id,library_casestep.id as step_id, library_casestep.instruction, library_casestep.expected,
	   library_caseversion.name as version_name,library_caseversion.description,library_caseversion.status,
	   core_productversion.version,core_product.name  as product_name
FROM  library_casestep,library_caseversion,library_case,core_productversion,core_product
WHERE library_casestep.caseversion_id = library_caseversion.id
  and library_caseversion.productversion_id = core_productversion.id
  and core_productversion.product_id = core_product.id
  and library_caseversion.case_id = library_case.id
