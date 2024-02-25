CREATE TABLE IF NOT EXISTS users
(
    id       BIGSERIAL,
    email    VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    CONSTRAINT users_pkey_id PRIMARY KEY (id)
);

comment on column users.id is 'User ID';
comment on column users.email is 'User Email';
comment on column users.password is 'User Password';


CREATE TABLE IF NOT EXISTS doc
(
    id               BIGSERIAL,
    user_id          BIGINT,
    title            VARCHAR(255),
    create_date_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    update_date_time TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT doc_pkey_id PRIMARY KEY (id),
    CONSTRAINT FK_DOC_ON_USER FOREIGN KEY (user_id) REFERENCES users (id)
);

comment on column doc.id is 'Document ID';
comment on column doc.user_id is 'User ID';
comment on column doc.title is 'Document Title';
comment on column doc.create_date_time is 'Document Create Date Time';
comment on column doc.update_date_time is 'Document Update Date Time';

