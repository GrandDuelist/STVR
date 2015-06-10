SELECT count(*) as test_case_number, all_products_steps_view.product_name
FROM all_products_steps_view
GROUP BY all_products_steps_view.product_name


