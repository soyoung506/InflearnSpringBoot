insert into users(id, join_date, name, password, ssn) values(90001, now(), 'User1', 'test111', '000000-1111111');
insert into users(id, join_date, name, password, ssn) values(90002, now(), 'User2', 'test222', '111111-2222222');
insert into users(id, join_date, name, password, ssn) values(90003, now(), 'User3', 'test333', '222222-3333333');

insert into post(description, user_id) values('My first post', 90001);
insert into post(description, user_id) values('My second post', 90001);
