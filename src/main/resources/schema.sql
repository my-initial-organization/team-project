CREATE TABLE IF NOT EXISTS Employee (
    id VARCHAR(100) PRIMARY KEY ,
    name VARCHAR(200) NOT NULL ,
    address VARCHAR(200) NOT NULL
);

INSERT INTO Employee (id, name, address) VALUES
                                             ('E001','vipula','veyangoda'),
                                             ('E002','nuwan','kurunagala');