create table "users"
("id" SERIAL NOT NULL PRIMARY KEY,
"email" VARCHAR(254) NOT NULL,
"name" VARCHAR(254),
"surname" VARCHAR(254),
"password_id" integer
);

create table "passwords"
("id" SERIAL NOT NULL PRIMARY KEY,
"hashed_password" VARCHAR(254),
"salt" VARCHAR(254));

-- User: test1@test.com, Password: password1
INSERT INTO passwords(
id, hashed_password, salt)
VALUES (1,'$2a$10$U3gBQ50FY5qiQ5XeQKgWwO6AADKjaGqh/6l3RzWitAWelWCQxffUC', '$2a$10$U3gBQ50FY5qiQ5XeQKgWwO');

INSERT INTO users(
id, email, name, surname, password_id)
VALUES (1, 'test1@test.com', '', '', 1);