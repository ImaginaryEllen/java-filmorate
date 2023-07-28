CREATE TABLE IF NOT EXISTS RATINGS
(
    RATING_ID bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    NAME      varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS FILMS
(
    FILM_ID      bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    NAME         varchar(100) NOT NULL,
    DESCRIPTION  varchar(200),
    RELEASE_DATE date         NOT NULL,
    DURATION     int          NOT NULL,
    RATING_ID    bigint REFERENCES RATINGS (RATING_ID)
);

CREATE TABLE IF NOT EXISTS USERS
(
    USER_ID  bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    EMAIL    varchar(100) NOT NULL,
    LOGIN    varchar(100) NOT NULL,
    NAME     varchar(100),
    BIRTHDAY date         NOT NULL
);

CREATE TABLE IF NOT EXISTS GENRES
(
    GENRE_ID bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    NAME     varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS FILM_GENRES
(
    FILM_ID  bigint REFERENCES FILMS (FILM_ID),
    GENRE_ID bigint REFERENCES GENRES (GENRE_ID),
    PRIMARY KEY (FILM_ID, GENRE_ID)
);

CREATE TABLE IF NOT EXISTS FRIENDS
(
    USER_ID   bigint REFERENCES USERS (USER_ID),
    FRIEND_ID bigint REFERENCES USERS (USER_ID),
    STATUS    boolean,
    PRIMARY KEY (USER_ID, FRIEND_ID)
);

CREATE TABLE IF NOT EXISTS LIKES
(
    FILM_ID bigint REFERENCES FILMS (FILM_ID),
    USER_ID bigint REFERENCES USERS (USER_ID),
    PRIMARY KEY (FILM_ID, USER_ID)
);