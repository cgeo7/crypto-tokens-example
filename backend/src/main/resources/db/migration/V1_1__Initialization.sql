CREATE TABLE tokens
(
    id     BIGINT       NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    token  VARCHAR(255) NOT NULL,
    symbol VARCHAR(255) NOT NULL
);

ALTER TABLE tokens
    ADD CONSTRAINT uc_67717c9a0eb20cf4c8b638b96 UNIQUE (token, symbol);

ALTER TABLE tokens
    ADD CONSTRAINT uc_tokens_symbol UNIQUE (symbol);

ALTER TABLE tokens
    ADD CONSTRAINT uc_tokens_token UNIQUE (token);
