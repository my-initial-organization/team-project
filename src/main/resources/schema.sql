
CREATE TABLE IF NOT EXISTS Employee (
    id VARCHAR(100) PRIMARY KEY ,
    name VARCHAR(200) NOT NULL ,
    address VARCHAR(200) NOT NULL
);

CREATE TABLE IF NOT EXISTS Customer (
    id VARCHAR(10) PRIMARY KEY ,
    name VARCHAR(100) NOT NULL ,
    address VARCHAR(200) NOT NULL
);


create table if not exists Subscriber(
    id varchar(50) primary key ,
    name varchar(100)  not null ,
    address varchar(200) not null
);
create table if not exists Student(
    id varchar(100) primary key,
    name varchar(100) not null,
    address varchar(100) not null
);
CREATE TABLE IF NOT EXISTS Teacher(
    id VARCHAR(10) PRIMARY KEY ,
    name VARCHAR(200) NOT NULL,
    address VARCHAR(500) NOT NULL
);