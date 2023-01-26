create database stock_broking_shard_1;
use stock_broking_shard_1;
CREATE TABLE users (
                       id varchar(255) NOT NULL,
                       fullname varchar(255),
                       type varchar(255) NOT NULL,
                       email varchar(255) NOT NULL,
                       active bit(1),
                       password varchar(255),
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       CONSTRAINT PK_Base PRIMARY KEY (id)
);
CREATE TABLE stocks (
                       id varchar(255) NOT NULL,
                       name varchar(255),
                       available_unit int NOT NULL,
                       current_price int NOT NULL,
                       day_low int NOT NULL,
                       day_high int NOT NULL,
                       prev_price int NOT NULL,
                       active bit(1),
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       CONSTRAINT PK_Base PRIMARY KEY (id)
);

CREATE TABLE wallet (
                        id varchar(255) NOT NULL,
                        account_number varchar(255) NOT NULL,
                        user_id varchar(255) NOT NULL,
                        amount int NOT NULL,
                        active bit(1),
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        CONSTRAINT PK_Base PRIMARY KEY (id)
);

CREATE TABLE user_stock_mapping (
                                    id varchar(255) NOT NULL,
                                    user_id varchar(255) NOT NULL,
                                    stock_id varchar(255) NOT NULL,
                                    total_unit int NOT NULL,
                                    total_amount int NOT NULL,
                                    active bit(1),
                                    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                    CONSTRAINT PK_mapping PRIMARY KEY (id),
                                    CONSTRAINT PK_user_stock UNIQUE KEY (user_id, stock_id)
);

CREATE TABLE user_stock_mapping (
                                    id varchar(255) NOT NULL,
                                    user_id varchar(255) NOT NULL,
                                    stock_id varchar(255) NOT NULL,
                                    total_unit int NOT NULL,
                                    total_amount int NOT NULL,
                                    active bit(1),
                                    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                    CONSTRAINT PK_mapping PRIMARY KEY (id),
                                    CONSTRAINT PK_user_stock UNIQUE KEY (user_id, stock_id)
);

CREATE TABLE transactions (
                              id varchar(255) NOT NULL,
                              user_id varchar(255) NOT NULL,
                              type varchar(255) NOT NULL,
                              stock_id varchar(255),
                              amount int NOT NULL,
                              unit int,
                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              CONSTRAINT PK_mapping PRIMARY KEY (id)
);

CREATE TABLE market_timing (
                              id varchar(255) NOT NULL,
                              start_time int NOT NULL,
                              end_time int NOT NULL,
                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              CONSTRAINT PK_mapping PRIMARY KEY (id)
);