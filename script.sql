CREATE TABLE users
(
    id         BIGINT PRIMARY KEY,
    username   VARCHAR(128) UNIQUE,
    firstname  VARCHAR(128),
    lastname   VARCHAR(128),
    birth_date DATE,
    role       VARCHAR(128),
    info       JSONB
);

DROP TABLE users;

DELETE
FROM users
WHERE username = 'hello =)';

-- Sequences BIGINT
CREATE SEQUENCE user_id_seq
    OWNED BY hibernate_starter.public.users.id;

-- TableGenerator
CREATE TABLE all_sequence
(
    table_name VARCHAR(128) PRIMARY KEY,
    pk_value   BIGINT NOT NULL
);