drop table penalty;
drop table game_result;
drop table game;
drop table table_players;
drop table tournament_player;
drop table team;
drop table tournament;
drop table player;

create table player (
	id int primary key,
	ema varchar(20),
	lastname varchar(100),
	firstname varchar(100),
	country varchar(2),
	pseudo  varchar(100),
	email varchar(255),
	date_arrival date,
	date_departure date,
	date_formular date,
	date_payment date,
	payment_mode varchar(255),
	has_photo smallint default 0,
	cj varchar(255),
	cp varchar(255),
	details varchar(1000),
	club varchar(255)
);
create table tournament (
	id int primary key,
	name varchar(100),
	team_activate varchar(1)
);
create table team (
    id int primary key,
    name varchar(100),
	id_tournament int,
	id_player_1 int,
	id_player_2 int,
	id_player_3 int,
	id_player_4 int
);
create table tournament_player (
	id_tournament int,
	id_player int
);
create table table_players (
	id int primary key,
	id_tournament int,
	round int,
	name varchar(50),
	id_player_1 int,
	id_player_2 int,
	id_player_3 int,
	id_player_4 int,
	id_result int,
	id_penalty int	
);
create table game (
	id int primary key,
	id_table int,
	game_number int,
	result_ema1 int,
	result_ema2 int,
	result_ema3 int,
	result_ema4 int
);
create table game_result (
	id int primary key,
	auto_calculate smallint default 0,
	result_ema1 int,
	points_ema1 double,
	result_ema2 int,
	points_ema2 double,
	result_ema3 int,
	points_ema3 double,
	result_ema4 int,
	points_ema4 double
);
create table penalty (
	id int primary key,
	penalty_player1 int,
	penalty_player2 int,
	penalty_player3 int,
	penalty_player4 int
);