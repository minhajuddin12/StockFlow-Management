CREATE TABLE companies (
                           id BIGSERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           subscription_plan VARCHAR(50) DEFAULT 'FREE',
                           created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       role VARCHAR(20) NOT NULL,
                       company_id BIGINT NOT NULL REFERENCES companies(id)
);