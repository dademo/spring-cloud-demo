#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "SPRING_CLOUD_TEST" <<-EOSQL
    CREATE USER spring_task WITH ENCRYPTED PASSWORD 'spring_task';
    GRANT LOGIN ON DATABASE SPRING_CLOUD_TEST TO spring_task;

    CREATE SCHEMA spring_task;
    GRANT ALL PRIVILEGES ON SCHEMA SPRING_TASK TO spring_task;
EOSQL