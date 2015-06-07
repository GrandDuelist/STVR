CREATE VIEW desktop_firefox_steps as SELECT library_casestep.id, library_casestep.instruction, library_casestep.expected,
	   library_caseversion.name as version_name,library_caseversion.description,library_caseversion.status,
	   core_productversion.version,core_product.name  as product_name
FROM  library_casestep,library_caseversion,core_productversion,core_product
WHERE library_casestep.caseversion_id = library_caseversion.id
  and library_caseversion.productversion_id = core_productversion.id
  and core_productversion.product_id = core_product.id
  and core_product.name = 'Desktop Firefox'