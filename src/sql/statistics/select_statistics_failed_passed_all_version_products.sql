SELECT failed_statistic.failed_number,passed_statistic.passed_number,passed_statistic.version,passed_statistic.product_name
FROM
(SELECT count(failed_cases.status) as failed_number,failed_cases.version,failed_cases.product_name
FROM
(SELECT distinct main_table.id,main_table.instruction,temp_failed_cases.status,main_table.product_name,main_table.version
FROM  (all_products_steps_result as main_table LEFT JOIN
      (SELECT * FROM all_products_steps_result  WHERE all_products_steps_result.status="failed") as temp_failed_cases ON main_table.id=temp_failed_cases.id)) as failed_cases
GROUP BY failed_cases.product_name,failed_cases.version) as failed_statistic,

(SELECT count(passed_cases.status) as passed_number,passed_cases.version,passed_cases.product_name
FROM
(SELECT distinct main_table.id,main_table.instruction,temp_passed_cases.status,main_table.product_name,main_table.version
FROM  (all_products_steps_result as main_table LEFT JOIN
      (SELECT * FROM all_products_steps_result  WHERE all_products_steps_result.status="passed") as temp_passed_cases ON main_table.id=temp_passed_cases.id)) as passed_cases
GROUP BY passed_cases.product_name,passed_cases.version) as passed_statistic

WHERE failed_statistic.product_name = passed_statistic.product_name  and failed_statistic.version= passed_statistic.version
