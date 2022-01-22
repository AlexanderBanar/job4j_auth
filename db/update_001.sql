create table person (
                        id serial primary key not null,
                        login varchar(2000),
                        password varchar(2000)
);

insert into person (login, password) values ('parsentev', '123');
insert into person (login, password) values ('ban', '123');
insert into person (login, password) values ('ivan', '123');

create table employee(
                         id serial primary key,
                         name varchar(255),
                         surname varchar(255),
                         itn bigint,
                         hiring_date timestamp
);

insert into employee(name, surname, itn, hiring_date) VALUES ('Petr', 'Ivanov', '123000444888', '2022-01-09 12:00:00');
insert into employee(name, surname, itn, hiring_date) VALUES ('Olga', 'Ivanova', '123000444111', '2022-01-10 12:00:00');

alter table person add column employee_id int references employee(id);

update person set employee_id = 1 where login = 'parsentev';
update person set employee_id = 1 where login = 'ban';
update person set employee_id = 1 where login = 'ivan';

insert into person(login, password, employee_id) values ('olga', 'olgpass', 2);
insert into person(login, password, employee_id) values ('ivanova', 'qwerty', 2);

alter table person rename to persons;
alter table employee rename to employees;

alter table persons drop column employee_id;

create table employees_persons (
                                   employee_id int references employees(id),
                                   person_id int references persons(id)
);

insert into employees_persons(employee_id, person_id) VALUES (1, 1);
insert into employees_persons(employee_id, person_id) VALUES (1, 2);
insert into employees_persons(employee_id, person_id) VALUES (1, 3);
insert into employees_persons(employee_id, person_id) VALUES (2, 5);
insert into employees_persons(employee_id, person_id) VALUES (2, 6);

drop table employees_persons;

alter table persons add column employee_id int references employees(id);

update persons set employee_id = 1 where id = 1;
update persons set employee_id = 1 where id = 2;
update persons set employee_id = 1 where id = 3;
update persons set employee_id = 2 where id = 5;
update persons set employee_id = 2 where id = 6;