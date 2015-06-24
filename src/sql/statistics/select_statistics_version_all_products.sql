SELECT count(*) AS version_number,
       core_product.name
FROM core_product,
     core_productversion
WHERE core_product.id = core_productversion.product_id
GROUP BY core_product.name