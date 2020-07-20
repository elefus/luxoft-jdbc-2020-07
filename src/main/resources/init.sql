DROP TABLE IF EXISTS CATALOG_ITEMS;
CREATE TABLE CATALOG_ITEMS
(
    SKU CHAR(25) NOT NULL CONSTRAINT pk_sku PRIMARY KEY,
    NAME CHAR(50) NOT NULL,
    DESCRIPTION VARCHAR(50),
    PRICE FLOAT NOT NULL
);

DROP TABLE IF EXISTS USERS;
CREATE TABLE USERS
(
    USERID INT IDENTITY NOT NULL CONSTRAINT pk_userid PRIMARY KEY ,
    NAME CHAR(50) NOT NULL,
    ADDRESS VARCHAR(50),
    USERNAME CHAR(15) NOT NULL,
    PASSWORD CHAR(15) NOT NULL,
    ACCESSLEVEL INT NOT NULL
);

DROP TABLE IF EXISTS ORDERS;
CREATE TABLE ORDERS
(
    ORDERID INT NOT NULL CONSTRAINT pk_orderid PRIMARY KEY,
    USERID INT NOT NULL,
    STATUS CHAR(15) NOT NULL,
    SHIPPING_ADDRESS VARCHAR(50) NOT NULL
);

DROP TABLE IF EXISTS ORDER_ITEMS;
CREATE TABLE ORDER_ITEMS
(
    SKU CHAR(25) NOT NULL,
    ORDERID INT NOT NULL,
    QUANTITY INT NOT NULL
);
ALTER TABLE ORDER_ITEMS
    ADD CONSTRAINT pk_skuorderid Primary Key (
                                              SKU,
                                              ORDERID);

INSERT INTO CATALOG_ITEMS VALUES('0001', 'Stress Ball', 'A red stress ball ideal for stress.', 9.99);
INSERT INTO CATALOG_ITEMS VALUES('0002', 'Pen', 'A blue pen ideal for writing.', 0.79);
INSERT INTO CATALOG_ITEMS VALUES('0003', 'Stapler', 'A black stapler ideal for stapling documents.', 16.99);
INSERT INTO CATALOG_ITEMS VALUES('0004', 'Calculator', 'A small pocket-sized calculator.', 21.99);
INSERT INTO CATALOG_ITEMS VALUES('0005', 'Garbage Can', 'This big garbage can can hold lots of garbage.', 37.99);
INSERT INTO CATALOG_ITEMS VALUES('0006', 'Pencil', 'A normal wood pencil.', 0.25);

INSERT INTO USERS VALUES(1, 'Pablo Ono', '123 That Way', 'pabloo', 'birdie',0);
INSERT INTO USERS VALUES(2, 'Cesar Diamond', '456 Round Circle', 'cesard', 'signal',0);
INSERT INTO USERS VALUES(3, 'Kess Brown', '1 Way Street', 'kessb', 'cortex',0);
INSERT INTO USERS VALUES(4, 'Brad Alter', 'Staplerz Main Office', 'admin', 'admin',2);

INSERT INTO ORDERS VALUES(1, 1, 'Shipped', '123 That Way');
INSERT INTO ORDERS VALUES(2, 1, 'In Progress', '123 That Way');
INSERT INTO ORDERS VALUES(3, 2, 'Out Of Stock', '456 Round Circle');
INSERT INTO ORDERS VALUES(4, 3, 'Shipped', '1 Way Street');

INSERT INTO ORDER_ITEMS VALUES('0001', 1, 1);
INSERT INTO ORDER_ITEMS VALUES('0002', 1, 40);
INSERT INTO ORDER_ITEMS VALUES('0004', 1, 1);
INSERT INTO ORDER_ITEMS VALUES('0001', 2, 1);
INSERT INTO ORDER_ITEMS VALUES('0004', 2, 2);
INSERT INTO ORDER_ITEMS VALUES('0006', 2, 100);
INSERT INTO ORDER_ITEMS VALUES('0005', 3, 1);
INSERT INTO ORDER_ITEMS VALUES('0004', 4, 2);