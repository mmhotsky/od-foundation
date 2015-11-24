
DROP SEQUENCE sq_foundation_db_test;
CREATE SEQUENCE sq_foundation_db_test INCREMENT BY 1 start with 1 nomaxvalue;

DROP TABLE od_foundation_db_test;
CREATE TABLE od_foundation_db_test (id INT PRIMARY KEY, str VARCHAR(100));
