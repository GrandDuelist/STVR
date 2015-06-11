SELECT count(*),product_name
FROM all_products_steps_view
WHERE all_products_steps_view.id in (select execution_stepresult.step_id FROM execution_stepresult)
GROUP BY all_products_steps_view.product_name