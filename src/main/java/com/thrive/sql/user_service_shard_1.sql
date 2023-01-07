create database user_service_shard_1;
use user_service_shard_1;
CREATE TABLE users (
                       id varchar(255) NOT NULL,
                       name varchar(255),
                       phone_number varchar(255),
                       active bit(1),
                       ssn varchar(255),
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       CONSTRAINT PK_Base PRIMARY KEY (id)
);