CREATE TABLE Box(
isDeleted boolean default FALSE,
bid Integer Primary Key auto_increment,
window boolean,
litter varchar(20) CHECK (litter IN ('straw','sawdust')) ,
horseName varchar(20),
size numeric(20,3),
location varchar(20) CHECK(location IN ('outside', 'inside')),
preisHour numeric(20,2),
picture varchar(200)
);


CREATE TABLE Reservation(
r_id Integer Primary Key auto_increment,
clientName varchar(30),
horseName varchar(40),
start timestamp,
end timestamp,
isDeleted boolean default FALSE,
);


CREATE TABLE Receipt(
r_id Integer References reservation(r_id),
bid Integer References box(bid),
receipt_id Integer auto_increment,
clientName VARCHAR(30),
totalPreis NUMERIC (20,3),
start timestamp,
isDeleted boolean default FALSE,
Primary Key(r_id, bid, receipt_id)
);

