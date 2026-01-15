CREATE TABLE image_manager
(
    id                VARCHAR(36)   NOT NULL,
    created_at        TIMESTAMP     NOT NULL,
    updated_at        TIMESTAMP     NOT NULL,
    entity_image_type VARCHAR(255) NULL,
    display_order     INT          NULL,
    image_owner_model VARCHAR(36)   NULL,
    image_url         VARCHAR(255) NULL,
    CONSTRAINT pk_image_manager PRIMARY KEY (id)
);

CREATE TABLE image_owner_model
(
    id         VARCHAR(36)  NOT NULL,
    owner_type VARCHAR(31) NULL,
    created_at TIMESTAMP    NOT NULL,
    updated_at TIMESTAMP    NOT NULL,
    CONSTRAINT pk_image_owner_model PRIMARY KEY (id)
);

CREATE TABLE option_group
(
    id            VARCHAR(36)   NOT NULL,
    created_at    TIMESTAMP     NOT NULL,
    updated_at    TIMESTAMP     NOT NULL,
    product_id    VARCHAR(36)   NOT NULL,
    display_order INT          NULL,
    name          VARCHAR(255) NULL,
    CONSTRAINT pk_option_group PRIMARY KEY (id)
);

CREATE TABLE option_value
(
    id              VARCHAR(36)   NOT NULL,
    created_at      TIMESTAMP     NOT NULL,
    updated_at      TIMESTAMP     NOT NULL,
    name            VARCHAR(255) NULL,
    display_order   INT          NULL,
    option_group_id VARCHAR(36)   NULL,
    price           DECIMAL      NULL,
    CONSTRAINT pk_option_value PRIMARY KEY (id)
);

CREATE TABLE product
(
    id            VARCHAR(36)   NOT NULL,
    created_at    TIMESTAMP     NOT NULL,
    updated_at    TIMESTAMP     NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    product_description VARCHAR(255) NULL,
    name          VARCHAR(255) NULL,
    price         DECIMAL      NULL,
    CONSTRAINT pk_product PRIMARY KEY (id)
);

CREATE TABLE product_post
(
    id         VARCHAR(36) NOT NULL,
    product_id VARCHAR(36) NULL,
    user_id    VARCHAR(36) NULL,
    deleted    BOOLEAN     NOT NULL,
    rating     FLOAT      NULL,
    CONSTRAINT pk_product_post PRIMARY KEY (id)
);

CREATE TABLE product_variant
(
    id         VARCHAR(36)   NOT NULL,
    created_at TIMESTAMP     NOT NULL,
    updated_at TIMESTAMP     NOT NULL,
    product_id VARCHAR(36)   NOT NULL,
    user_id    VARCHAR(36)   NOT NULL,
    sku_code   VARCHAR(255) NOT NULL,
    quantity   DECIMAL      NULL,
    CONSTRAINT pk_product_variant PRIMARY KEY (id)
);

CREATE TABLE reply
(
    id           VARCHAR(36)   NOT NULL,
    created_at   TIMESTAMP     NOT NULL,
    updated_at   TIMESTAMP     NOT NULL,
    deleted      BOOLEAN       NOT NULL,
    users_id     VARCHAR(36)   NULL,
    review_id    VARCHAR(36)   NOT NULL,
    comment_text VARCHAR(255) NULL,
    CONSTRAINT pk_reply PRIMARY KEY (id)
);

CREATE TABLE review
(
    id              VARCHAR(36)   NOT NULL,
    deleted         BOOLEAN       NOT NULL,
    product_post_id VARCHAR(36)   NULL,
    users_id        VARCHAR(36)   NULL,
    comment_text    VARCHAR(255) NULL,
    rating          FLOAT        NULL,
    CONSTRAINT pk_review PRIMARY KEY (id)
);

CREATE TABLE shopping_basket_product
(
    id              VARCHAR(36)   NOT NULL,
    created_at      TIMESTAMP     NOT NULL,
    updated_at      TIMESTAMP     NOT NULL,
    user_id         VARCHAR(36)   NOT NULL,
    product_variant_id VARCHAR(36)   NULL,
    name            VARCHAR(255) NULL,
    quantity        DECIMAL      NULL,
    price           DECIMAL      NULL,
    CONSTRAINT pk_shopping_basket_product PRIMARY KEY (id)
);

CREATE TABLE users
(
    id         VARCHAR(36)   NOT NULL,
    created_at TIMESTAMP     NOT NULL,
    updated_at TIMESTAMP     NOT NULL,
    name       VARCHAR(255) NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE variant_option_value
(
    id                 VARCHAR(36) NOT NULL,
    created_at         TIMESTAMP   NOT NULL,
    updated_at         TIMESTAMP   NOT NULL,
    product_variant_id VARCHAR(36) NULL,
    option_group_id    VARCHAR(36) NULL,
    option_value_id    VARCHAR(36) NULL,
    CONSTRAINT pk_variant_option_value PRIMARY KEY (id)
);

CREATE TABLE preempted_product_variant (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id VARCHAR(36) NOT NULL,
    product_variant_id VARCHAR(36) NOT NULL,
    quantity INT NOT NULL,
    expired_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

