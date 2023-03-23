
CREATE TABLE IF NOT EXISTS Employee (
    id VARCHAR(100) PRIMARY KEY ,
    name VARCHAR(200) NOT NULL ,
    address VARCHAR(200) NOT NULL
);

INSERT INTO Employee (id, name, address) VALUES
                                             ('E001','vipula','veyangoda'),
                                             ('E002','nuwan','kurunagala');

CREATE TABLE IF NOT EXISTS Customer (
    id VARCHAR(10) PRIMARY KEY ,
    name VARCHAR(100) NOT NULL ,
    address VARCHAR(200) NOT NULL
);


create table if not exists Subscriber(
    id varchar(50) primary key ,
    name varchar(100)  not null ,
    address varchar(200) not null
)