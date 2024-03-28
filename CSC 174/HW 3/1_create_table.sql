create table artist (
	aname varchar(50) primary key, 
	date_born date,
	date_died date,
	description varchar(255),
	country_of_origin varchar(20)
);

create table art_object(
	id_no integer primary key,
	year integer,
	title varchar(50), 
	description varchar(255),
	artist_name varchar(50),
	foreign key (artist_name) references artist(aname)
);

create table others(
	id_no integer primary key, 
	type varchar(20), 
	style varchar(20),
	foreign key (id_no) references art_object(id_no)
);

create table painting(
	id_no integer primary key, 
	type varchar(20), 
	style varchar(20), 
	drawn_on varchar(20),
	foreign key(id_no) references art_object(id_no)
);

create table sculpture (
	id_no integer primary key, 
	material varchar(20), 
	weight double,
	height double,
	foreign key(id_no) references art_object(id_no)
);

create table exhibition (
	ename varchar(60) primary key,
	start_date date,
	end_date date
);

create table shown_at( 
	art int, 
	exhibition_name varchar(60),
	primary key(art, exhibition_name),
	foreign key(art) references art_object(id_no), 
	foreign key (exhibition_name) references exhibition(ename)
);
