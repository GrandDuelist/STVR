CREATE VIEW statistic_desktop_firefox AS SELECT COUNT(*) as case_count, desktop_firefox_steps.version, desktop_firefox_steps.product_name
FROM desktop_firefox_steps
GROUP BY desktop_firefox_steps.version
