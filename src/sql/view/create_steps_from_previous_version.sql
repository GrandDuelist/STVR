CREATE VIEW steps_from_previous_version AS SELECT d1.* 
FROM desktop_firefox_steps_result d1, desktop_firefox_steps_result d2
WHERE d1.instruction = d2.instruction
and d1.version != d2.version