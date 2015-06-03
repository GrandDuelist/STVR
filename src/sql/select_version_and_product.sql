SELECT core_productversion.id, core_productversion.version, core_product.name 
FROM core_product,core_productversion
WHERE core_productversion.product_id = core_product.id and (core_product.name = 'Firefox' or core_product.name =  'Desktop Firefox')
#WHERE core_productversion.product_id = core_product.id and (core_product.id = 9 or core_product.id = 10)
#WHERE core_productversion.product_id = core_product.id and core_product.id = 1

