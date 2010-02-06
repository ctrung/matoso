/* Migration SQL base Derby */

/* ---------------------------------------- */
/* [CTR] 03/01/10 : refactoring table/games */

/* Déplacer le lien entre game_result et table_players */
/* Attention : perte de données ! */
delete from game_result;
alter table table_players add column id_result int;
/* Evolution de la table game_result */
/* Attention : perte de données ! */
delete from game_result;
alter table game_result drop column points_ema1;
alter table game_result add column points_ema1 double;
alter table game_result drop column points_ema2;
alter table game_result add column points_ema2 double;
alter table game_result drop column points_ema3;
alter table game_result add column points_ema3 double;
alter table game_result drop column points_ema4;
alter table game_result add column points_ema4 double;
/* Déplacer le lien entre penalty et table_players */
/* Attention : perte de données ! */
delete from penalty;
alter table penalty drop column id_table;
alter table table_players add column id_penalty int;

/* ---------------------------------------- */
/* [CTR] 03/01/10 : Evolution de la table player */
rename column player.name to lastname;
rename column player.nationality to country;
rename column player.mahjongtime to pseudo;
alter table player add column email varchar(255);
alter table player add column date_arrival date;
alter table player add column date_departure date;
alter table player add column date_formular date;
alter table player add column date_payment date;
alter table player add column payment_mode varchar(255);
alter table player add column has_photo smallint default 0;
alter table player add column cj varchar(255);
alter table player add column cp varchar(255);
alter table player add column details varchar(1000);
alter table player add column club varchar(255);

/* ---------------------------------------- */
/* [CTR] 30/01/10 : adding a column to save the auto-calculate mode */
alter table game_result add column auto_calculate smallint default 0;
