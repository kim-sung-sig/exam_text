drop table if exists find_dust_alarm;
drop table if exists inspection;

create table find_dust_alarm(
	idx int primary key auto_increment,
	stationName varchar(16) not null,
	stationCode varchar(16) not null,
	alertLevel int not null,
	issuedTime varchar(20) not null
);

create table inspection(
	idx int primary key auto_increment,
	stationName varchar(16) not null,
	stationCode varchar(16) not null,
	PM varchar(4) not null,
	issuedTime varchar(20) not null
);