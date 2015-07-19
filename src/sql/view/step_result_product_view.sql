CREATE VIEW step_result_product_view as 
SELECT step_result_table.id, step_result_table.caseversion_id,step_result_table.result_status,
core_productversion.version, core_product.name 
FROM step_result_table, core_productversion,core_product,library_caseversion
WHERE step_result_table.caseversion_id = library_caseversion.id
 and library_caseversion.productversion_id = core_productversion.id
 and core_productversion.product_id = core_product.id