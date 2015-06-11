SELECT count(*),product_name,version
FROM  all_products_steps_view
GROUP BY product_name,version
