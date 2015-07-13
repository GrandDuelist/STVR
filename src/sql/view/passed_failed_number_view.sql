DROP VIEW passed_failed_number_view;
CREATE VIEW passed_failed_number_view AS
SELECT passed_failed_view.caseversion_id, count(*) AS number
FROM passed_failed_view
GROUP BY passed_failed_view.caseversion_id