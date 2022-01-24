INSERT INTO users (username, password)
values('user','pass');

INSERT INTO users (username, password)
values('admin','pass');

INSERT INTO authorities (username, authority)
values ('user','ROLE_USER');

INSERT INTO authorities (username, authority)
values ('admin','ROLE_ADMIN');