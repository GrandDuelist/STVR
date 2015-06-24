CREATE VIEW case_product_view  AS 
SELECT core_product.name as product_name,core_product.id as product_id,core_product.description as product_desciption, #product
core_productversion.id as productversion_id,core_productversion.version as product_version,  #productversion
library_caseversion.id as caseversion_id, #caseversion
library_case.id as case_id, library_case.priority as case_priority
FROM library_caseversion, library_case, core_productversion, core_product
WHERE library_caseversion.case_id = library_case.id and library_caseversion.productversion_id = core_productversion.id
      and core_productversion.product_id = core_product.id
      