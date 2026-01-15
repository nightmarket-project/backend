CREATE TABLE detail_order_record
(
    id                 VARCHAR(36)  NOT NULL,
    created_at         TIMESTAMP    NOT NULL,
    updated_at         TIMESTAMP    NOT NULL,
    product_variant_id VARCHAR(36)  NOT NULL,
    state              VARCHAR(255) NOT NULL,
    order_record_id    VARCHAR(36)  NOT NULL,
    quantity           INT          NOT NULL,
    CONSTRAINT pk_detail_order_record PRIMARY KEY (id)
);

CREATE TABLE order_record
(
    id             VARCHAR(36) NOT NULL,
    created_at     TIMESTAMP   NOT NULL,
    updated_at     TIMESTAMP   NOT NULL,
    order_date     date        NOT NULL,
    user_id        VARCHAR(36) NOT NULL,
    zip_code       VARCHAR(255),
    road_address   VARCHAR(255),
    detail_address VARCHAR(255),
    CONSTRAINT pk_order_record PRIMARY KEY (id)
);

CREATE TABLE product_variant
(
    id         VARCHAR(36) NOT NULL,
    created_at TIMESTAMP   NOT NULL,
    updated_at TIMESTAMP   NOT NULL,
    price      BIGINT      NOT NULL,
    CONSTRAINT pk_product_variant PRIMARY KEY (id)
);