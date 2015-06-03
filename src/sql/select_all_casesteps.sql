SELECT `library_casestep`.`id`,
    `library_casestep`.`created_on`,
    `library_casestep`.`created_by_id`,
    `library_casestep`.`modified_on`,
    `library_casestep`.`modified_by_id`,
    `library_casestep`.`deleted_on`,
    `library_casestep`.`deleted_by_id`,
    `library_casestep`.`caseversion_id`,
    `library_casestep`.`number`,
    `library_casestep`.`instruction`,
    `library_casestep`.`expected`,
    `library_casestep`.`cc_version`
FROM `firefox`.`library_casestep`;
