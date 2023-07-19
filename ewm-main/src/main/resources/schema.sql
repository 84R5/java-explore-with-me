DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS events CASCADE;
DROP TABLE IF EXISTS participation_request CASCADE;
DROP TABLE IF EXISTS compilations CASCADE;
DROP TABLE IF EXISTS compilations_events CASCADE;
DROP TABLE IF EXISTS rates CASCADE;
DROP TABLE IF EXISTS comments CASCADE;

CREATE TABLE IF NOT EXISTS users
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY         NOT NULL,
    name        VARCHAR(128)                                    NOT NULL,
    email       VARCHAR(255)                                    NOT NULL,
    CONSTRAINT pk_user          PRIMARY KEY (id),
    CONSTRAINT UQ_USER_EMAIL    UNIQUE      (email)
);

CREATE TABLE IF NOT EXISTS categories
(
    id      BIGINT GENERATED BY DEFAULT AS IDENTITY             NOT NULL,
    name    VARCHAR(255)                                        NOT NULL,
    CONSTRAINT pk_categories      PRIMARY KEY (id),
    CONSTRAINT uq_categories_name UNIQUE      (name)
);


CREATE TABLE IF NOT EXISTS locations
(
    id      BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    lat     DOUBLE PRECISION                                    NOT NULL,
    lon     DOUBLE PRECISION                                    NOT NULL
);

CREATE TABLE IF NOT EXISTS events
(
    id                  BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    annotation          VARCHAR(2000)                           NOT NULL,
    categories          BIGINT                                  NOT NULL,
    confirmed_requests  INT                                     NOT NULL,
    created_on          TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    description         VARCHAR(7000)                           NOT NULL,
    event_date          TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    initiator_id        BIGINT                                  NOT NULL,
    locations_id        BIGINT                                  NOT NULL,
    paid                BOOL                                    NOT NULL,
    participant_limit   INT,
    published_on        TIMESTAMP WITHOUT TIME ZONE,
    request_moderation  BOOL,
    state               VARCHAR(32)                             NOT NULL,
    title               VARCHAR(128)                            NOT NULL,
    views               BIGINT,
    CONSTRAINT pk_event         PRIMARY KEY (id),
    FOREIGN KEY (categories)   REFERENCES categories (id)   ON DELETE CASCADE,
    FOREIGN KEY (initiator_id)  REFERENCES users (id)       ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS compilations
(
    id                  BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    pinned              BOOLEAN                                 NOT NULL,
    title               VARCHAR(50)                         NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS compilations_events
(
    compilation_id      BIGINT                                  NOT NULL,
    event_id            BIGINT                                  NOT NULL,
    PRIMARY KEY (compilation_id, event_id),
    FOREIGN KEY (compilation_id)    REFERENCES compilations(id),
    FOREIGN KEY (event_id)          REFERENCES events(id)
);

CREATE TABLE IF NOT EXISTS participation_request(
    id                  BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    created             TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    event_id            BIGINT                                  NOT NULL,
    requester_id        BIGINT                                  NOT NULL,
    status              VARCHAR(50),
    FOREIGN KEY (event_id)          REFERENCES events(id),
    FOREIGN KEY (requester_id)      REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS comments
(
    id                  BIGINT  GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    creator             BIGINT                                  NOT NULL,
    created             TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    text                VARCHAR(1000),
    FOREIGN KEY (creator)       REFERENCES users(id)    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS rates
(
    user_id             BIGINT                                  NOT NULL,
    event_id            BIGINT                                  NOT NULL,
    rate                INTEGER                                         ,
    comment_id          BIGINT                                          ,
    PRIMARY KEY (user_id, event_id),
    FOREIGN KEY (user_id)       REFERENCES users(id)    ON DELETE CASCADE,
    FOREIGN KEY (event_id)      REFERENCES events(id)   ON DELETE CASCADE,
    FOREIGN KEY (comment_id)    REFERENCES comments(id)  ON DELETE CASCADE
);

