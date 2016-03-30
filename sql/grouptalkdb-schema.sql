drop database if exists grouptalkdb;
create database grouptalkdb;

use grouptalkdb;

CREATE TABLE users (
    id BINARY(16) NOT NULL,
    loginid VARCHAR(15) NOT NULL UNIQUE,
    password BINARY(16) NOT NULL,
    email VARCHAR(255) NOT NULL,
    fullname VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE user_roles (
    userid BINARY(16) NOT NULL,
    role ENUM ('registered', 'admin'),
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade,
    PRIMARY KEY (userid, role)
);

CREATE TABLE auth_tokens (
    userid BINARY(16) NOT NULL,
    token BINARY(16) NOT NULL,
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade,
    PRIMARY KEY (token)
);

CREATE TABLE grupos (
    id BINARY(16) NOT NULL,    
    nombre VARCHAR(100) NOT NULL, 
    creation_timestamp DATETIME not null default current_timestamp,    
    PRIMARY KEY (id)
);

CREATE TABLE user_grupos (
    userid BINARY(16) NOT NULL,
    grupoid BINARY(16) NOT NULL,
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade,
    FOREIGN KEY (grupoid) REFERENCES grupos(id) on delete cascade
);

CREATE TABLE temas (
    id BINARY(16) NOT NULL,
    userid BINARY(16) NOT NULL,
    grupoid BINARY(16) NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    contenido VARCHAR(500) NOT NULL,    
    creation_timestamp DATETIME not null default current_timestamp,
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade,
    FOREIGN KEY (grupoid) REFERENCES grupos(id) on delete cascade,
    PRIMARY KEY (id)
);

CREATE TABLE respuestas (
    id BINARY(16) NOT NULL,
    userid BINARY(16) NOT NULL,
    temaid BINARY(16) NOT NULL,    
    contenido VARCHAR(500) NOT NULL,    
    creation_timestamp DATETIME not null default current_timestamp,
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade,
    FOREIGN KEY (temaid) REFERENCES temas(id) on delete cascade,
    PRIMARY KEY (id)
);
