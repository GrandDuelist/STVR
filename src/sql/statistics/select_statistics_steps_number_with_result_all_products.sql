SELECT count(*) AS test_case_number_with_result,
       all_products_steps_result.product_name
FROM all_products_steps_result
GROUP BY all_products_steps_result.product_name