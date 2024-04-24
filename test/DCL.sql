CREATE USER 'exam'@'localhost' IDENTIFIED BY '123456';

GRANT ALL PRIVILEGES ON mysql.* TO 'exam'@'localhost';

use mysql;