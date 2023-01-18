create database stock_broking_shard_2;
use stock_broking_shard_2;
CREATE TABLE users (
                       id varchar(255) NOT NULL,
                       fullname varchar(255),
                       email varchar(255),
                       active bit(1),
                       password varchar(255),
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       CONSTRAINT PK_Base PRIMARY KEY (id)
);