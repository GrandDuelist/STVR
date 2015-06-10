SELECT core_productversion.version, core_product.name as product_name
FROM core_product,core_productversion
WHERE core_product.id = core_productversion.product_id