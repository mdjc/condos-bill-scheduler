insert into users (username, password, role)  values('mirna', '$2a$10$nZAnnVBnl0r23Iii2FBUPOJNcCFL9x9MakFDdlzw2vbN5tUeWcHui', 'MANAGER');
insert into users (username, password, role)  values('luis', '$2a$10$nZAnnVBnl0r23Iii2FBUPOJNcCFL9x9MakFDdlzw2vbN5tUeWcHui', 'MANAGER');
insert into users (username, password, role)  values('manila', '$2a$10$nZAnnVBnl0r23Iii2FBUPOJNcCFL9x9MakFDdlzw2vbN5tUeWcHui', 'MANAGER');
insert into users (username, password, role)  values('virgi', '$2a$10$nZAnnVBnl0r23Iii2FBUPOJNcCFL9x9MakFDdlzw2vbN5tUeWcHui', 'RESIDENT');
insert into users (username, password, role)  values('aldo', '$2a$10$nZAnnVBnl0r23Iii2FBUPOJNcCFL9x9MakFDdlzw2vbN5tUeWcHui', 'RESIDENT');
insert into users (username, password, role)  values('john', '$2a$10$nZAnnVBnl0r23Iii2FBUPOJNcCFL9x9MakFDdlzw2vbN5tUeWcHui', 'RESIDENT');
insert into users (username, password, role)  values('mary', '$2a$10$nZAnnVBnl0r23Iii2FBUPOJNcCFL9x9MakFDdlzw2vbN5tUeWcHui', 'RESIDENT');

insert into condos (name, manager, balance, billing_day_of_month) values ('Shadai I', (select id from users where username='mirna' and role='MANAGER'), 100, 15);
insert into condos (name, manager, balance, billing_day_of_month) values ('Loring  Place 2333', (select id from users where username='mirna' and role='MANAGER'), 50, 1);
insert into condos (name, manager, balance, billing_day_of_month) values ('Mira Flores IV', (select id from users where username='luis' and role='MANAGER'), 220, 1);
insert into condos (name, manager, balance, billing_day_of_month) values('Baldwing IV', (select id from users where username='luis' and role='MANAGER'), 0, 1);
insert into condos (name, manager, balance, billing_day_of_month) values('Baldwing V', (select id from users where username='luis' and role='MANAGER'), 0, 28);
insert into condos (name, manager, balance, billing_day_of_month) values('Baldwing VI', (select id from users where username='luis' and role='MANAGER'), 0, 29);
insert into condos (name, manager, balance, billing_day_of_month) values('Baldwing VII', (select id from users where username='luis' and role='MANAGER'), 0, 30);
insert into condos (name, manager, balance, billing_day_of_month) values('Baldwing VIII', (select id from users where username='luis' and role='MANAGER'), 0, 31);

insert into apartments (name, condo, resident, monthly_share) values ('1A', (select id from condos where name = 'Shadai I'), (select id from users where username='virgi'), 10);
insert into apartments (name, condo, resident, monthly_share) values ('1B', (select id from condos where name = 'Shadai I'), null, 10);
insert into apartments (name, condo, resident, monthly_share) values ('1C', (select id from condos where name = 'Shadai I'), null, 15);
insert into apartments (name, condo, resident, monthly_share) values ('1D', (select id from condos where name = 'Shadai I'), (select id from users where username='aldo'), 15);

insert into apartments (name, condo, resident, monthly_share) values ('1', (select id from condos where name = 'Loring  Place 2333'), (select id from users where username='john'), 600);
insert into apartments (name, condo, resident, monthly_share) values ('2', (select id from condos where name = 'Loring  Place 2333'), (select id from users where username='mary'), 300);
insert into apartments (name, condo, resident, monthly_share) values ('25F', (select id from condos where name = 'Mira Flores IV'), (select id from users where username='mary'), 300);

insert into apartments (name, condo, resident, monthly_share) values ('X', (select id from condos where name = 'Baldwing V'), (select id from users where username='mary'), 150);
insert into apartments (name, condo, resident, monthly_share) values ('Y', (select id from condos where name = 'Baldwing VI'), (select id from users where username='manila'), 300);
insert into apartments (name, condo, resident, monthly_share) values ('Z', (select id from condos where name = 'Baldwing VII'), (select id from users where username='john'), 800);
insert into apartments (name, condo, resident, monthly_share) values ('A9', (select id from condos where name = 'Baldwing VIII'), (select id from users where username='mary'), 90);