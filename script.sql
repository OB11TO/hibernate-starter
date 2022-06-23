CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    username   VARCHAR(128) UNIQUE,
    firstname  VARCHAR(128),
    lastname   VARCHAR(128),
    birth_date DATE,
    role       VARCHAR(128),
    info       JSONB,
    company_id INT REFERENCES company (id)
);

CREATE TABLE company
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE
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