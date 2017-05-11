INSERT INTO Box (window, litter, horsename, size, location, preishour, picture) VALUES
(FALSE, 'straw', 'Lana', 10, 'inside', 10, '/Users/rasmina/Desktop/Software Eng&Proj/Einzelbeispiel/src/sepm/ss17/e1228930/BoxImages/1.jpg' ),
(FALSE, 'sawdust', 'Luna', 10, 'inside', 14, '/Users/rasmina/Desktop/Software Eng&Proj/Einzelbeispiel/src/sepm/ss17/e1228930/BoxImages/2.jpg' ),
(FALSE, 'straw', 'Karamel', 10, 'inside', 15, '/Users/rasmina/Desktop/Software Eng&Proj/Einzelbeispiel/src/sepm/ss17/e1228930/BoxImages/3.jpg' ),
(FALSE, 'sawdust', 'Lucia', 10, 'inside', 17, '/Users/rasmina/Desktop/Software Eng&Proj/Einzelbeispiel/src/sepm/ss17/e1228930/BoxImages/4.jpg' ),
(FALSE, 'straw', 'Luca', 10, 'inside', 14, '/Users/rasmina/Desktop/Software Eng&Proj/Einzelbeispiel/src/sepm/ss17/e1228930/BoxImages/5.jpg' ),
(TRUE, 'sawdust', 'Petit', 10, 'outside', 11, '/Users/rasmina/Desktop/Software Eng&Proj/Einzelbeispiel/src/sepm/ss17/e1228930/BoxImages/6.jpg' ),
(TRUE, 'straw', 'Star', 10, 'outside', 10, '/Users/rasmina/Desktop/Software Eng&Proj/Einzelbeispiel/src/sepm/ss17/e1228930/BoxImages/7.jpg' ),
(TRUE, 'sawdust', 'Bethi', 10, 'outside', 15, '/Users/rasmina/Desktop/Software Eng&Proj/Einzelbeispiel/src/sepm/ss17/e1228930/BoxImages/8.jpg' ),
(TRUE, 'straw', 'Troy', 10, 'outside', 17, '/Users/rasmina/Desktop/Software Eng&Proj/Einzelbeispiel/src/sepm/ss17/e1228930/BoxImages/9.jpg' ),
(TRUE, 'straw', 'Gabriel', 10, 'outside', 12, '/Users/rasmina/Desktop/Software Eng&Proj/Einzelbeispiel/src/sepm/ss17/e1228930/BoxImages/10.jpg' );

INSERT INTO Reservation (r_id, clientname, horsename, start, end)VALUES
(1, 'Rasmina', 'Lana', '2017-01-11 13:23:44', '2017-01-11 15:23:44'),
(2, 'Sunny', 'Luna', '2017-02-11 13:23:44', '2017-02-11 15:23:44'),
(3, 'Kate', 'Karamel', '2017-01-11 13:23:44', '2017-01-11 15:23:44'),
(4, 'Lori', 'Star', '2017-03-11 13:23:44', '2017-03-11 15:23:44'),
(5, 'Fiona', 'Lana', '2017-02-11 11:23:44', '2017-02-11 15:23:44'),
(6, 'Rasmina', 'Star', '2017-01-11 10:23:44', '2017-01-11 15:23:44'),
(7, 'Rasmina', 'Lucia', '2017-01-11 09:23:44', '2017-01-11 15:23:44'),
(8, 'Rasmina', 'Lana', '2017-01-11 13:23:44', '2017-01-11 15:23:44'),
(9, 'Stefan', 'Petit', '2017-01-11 13:23:44', '2017-01-11 15:23:44'),
(10, 'Stefan', 'Karamel', '2017-01-11 13:23:44', '2017-01-11 15:23:44');

INSERT INTO Receipt(r_id, bid, receipt_id, totalpreis, clientname, start) VALUES
(1, 1, 1, 20, 'Rasmina', '2017-01-11 13:23:44'),
(5, 1, 2, 40, 'Fiona','2017-02-11 11:23:44'),
(8, 1, 3, 20, 'Rasmina', '2017-01-11 13:23:44'),
(5, 2, 4, 56, 'Fiona', '2017-02-11 11:23:44'),
(8, 2, 5, 28, 'Rasmina','2017-01-11 13:23:44' ),
(1, 3, 6, 30, 'Rasmina', '2017-01-11 13:23:44'),
(1, 5, 7, 28,'Rasmina', '2017-01-11 13:23:44'),
(8, 5, 8, 28,'Rasmina', '2017-01-11 13:23:44'),
(5, 7, 9, 40, 'Fiona', '2017-02-11 11:23:44'),
(8, 10, 10, 24,'Rasmina', '2017-01-11 13:23:44');
