CREATE TABLE users
(
    id                VARCHAR(36)   NOT NULL,
    created_at        TIMESTAMP     NOT NULL,
    updated_at        TIMESTAMP     NOT NULL,
    email             VARCHAR(255) NOT NULL,
    profile_image_url VARCHAR(255) NULL,
    user_role            VARCHAR(255) NOT NULL,
    auth_provider     VARCHAR(255) NOT NULL,
    provider_id       VARCHAR(255) NULL,
    name              VARCHAR(255) NULL,
    point             BIGINT       NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE outbox
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    created_at        TIMESTAMP     NOT NULL,
    updated_at        TIMESTAMP     NOT NULL,
    aggregate_type  VARCHAR(50) NOT NULL,
    aggregate_id    VARCHAR(50) NOT NULL,
    event_type      VARCHAR(50) NOT NULL,
    payload         VARCHAR(255) NOT NULL,
    state           VARCHAR(50) NOT NULL
)