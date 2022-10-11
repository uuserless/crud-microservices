
insert into users (age, email, first_name, last_name, password) VALUES (30, 'admin@mail.ru', 'admin', 'admin', '$2a$12$bW5uTXl.QahmCIAZnyyrP.xkJqiUkSYrQSe4HyWCc08a4.LEiPp..');
insert into users (age, email, first_name, last_name, password) VALUES (18, 'user@mail.ru', 'user', 'user', '$2a$12$bW5uTXl.QahmCIAZnyyrP.xkJqiUkSYrQSe4HyWCc08a4.LEiPp..');
insert into roles (role) value ('ROLE_ADMIN');
insert into roles (role) value ('ROLE_USER');
insert into users_roles (user_id, role_id) values ((select id from users where email = 'admin@mail.ru'), (select id from roles where role = 'ROLE_ADMIN'));
insert into users_roles (user_id, role_id) values ((select id from users where email = 'admin@mail.ru'), (select id from roles where role = 'ROLE_USER'));
insert into users_roles (user_id, role_id) values ((select id from users where email = 'user@mail.ru'), (select id from roles where role = 'ROLE_USER'));