/*SELECT count(*),all_products_steps_result.status,all_products_steps_result.product_name
FROM all_products_steps_result
GROUP BY all_products_steps_result.status,all_products_steps_result.product_name*/

SELECT
statistic_result.result_case_number,statistic_steps.steps_number,(statistic_steps.steps_number-statistic_result.result_case_number) as no_result_number,statistic_steps.product_name

FROM 
(SELECT count(*) as result_case_number,all_products_steps_has_result.product_name
FROM  (SELECT all_products_steps_view.id as steps_id,all_products_steps_view.product_name
	   FROM all_products_steps_view
	   WHERE all_products_steps_view.id in (select execution_stepresult.step_id FROM execution_stepresult)) as all_products_steps_has_result
GROUP BY all_products_steps_has_result.product_name) as statistic_result,

(SELECT count(*) as steps_number,all_products_steps_view.product_name
FROM all_products_steps_view
GROUP BY all_products_steps_view.product_name) as statistic_steps


WHERE statistic_steps.product_name=statistic_result.product_name
