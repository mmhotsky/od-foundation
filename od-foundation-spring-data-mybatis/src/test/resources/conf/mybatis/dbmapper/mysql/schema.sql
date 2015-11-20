
DROP TABLE IF EXISTS od_foundation_db_test;
CREATE TABLE IF NOT EXISTS od_foundation_db_test (id INT primary key not null, str VARCHAR(100));

DROP TABLE IF EXISTS od_foundation_db_test_increment;
CREATE TABLE IF NOT EXISTS od_foundation_db_test_increment (id INT primary key not null auto_increment, str VARCHAR(100));

DROP TABLE IF EXISTS od_foundation_db_test_custom;
CREATE TABLE IF NOT EXISTS od_foundation_db_test_custom (id INT primary key not null, str VARCHAR(100));
