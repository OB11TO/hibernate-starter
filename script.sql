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

CREATE TABLE user_chat
(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users (id),
    chat_id BIGSERIAL REFERENCES chat (id),
    created_at TIMESTAMP NOT NULL,
    created_by VARCHAR(128) NOT NULL
);

CREATE TABLE chat
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE profile
(
    id       BIGSERIAL PRIMARY KEY,
    user_id  BIGINT UNIQUE REFERENCES users (id),
    street   VARCHAR(128),
    language CHAR(3)
);

CREATE TABLE company
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE company_locale
(
    company_id INT NOT NULL REFERENCES company (id),
    lang CHAR(2) NOT NULL,
    description VARCHAR(128) NOT NULL,
    PRIMARY KEY (company_id, lang)
);

DROP TABLE users;
DROP TABLE profile;
DROP TABLE company CASCADE;
DROP TABLE user_chat;
DROP TABLE company_locale;

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